package com.example.study.conf;

import com.alibaba.fastjson.JSON;
import com.example.study.StudyApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * application 配置文件刷新机制
 *
 * @author zk
 * @since 2020/1/13
 */
@Configuration
@Slf4j
public class ConfigRefreshConfigure implements InitializingBean {

    @Autowired
    private ContextRefresher contextRefresher;

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        String[] configFilePaths = StudyApplication.CONFIG_FILES_PATH.split(",");
        if (ArrayUtils.isNotEmpty(configFilePaths)) {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.submit(() -> {
                try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                    //absolute config file parent path
                    Set<String> hasRegisterDirs = new HashSet<>();
                    //absolute config path
                    Set<String> hasRegisterFiles = new HashSet<>();

                    //add config file parentDir to register
                    for (String configFilePath : configFilePaths) {
                        Resource configFileResource = defaultResourceLoader.getResource(configFilePath);
                        if (configFileResource.exists() && configFileResource.isFile()) {
                            hasRegisterFiles.add(configFileResource.getFile().getAbsolutePath());
                            File configFileDir = configFileResource.getFile().getParentFile();
                            if (!hasRegisterDirs.contains(configFileDir.getAbsolutePath())) {
                                Paths.get(configFileDir.toURI())
                                        .register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);
                                hasRegisterDirs.add(configFileDir.getAbsolutePath());
                            }
                        }
                    }

                    //watch config file change
                    while (true) {
                        WatchKey key = watchService.take();//block wait
                        boolean hasChange = false;
                        for (WatchEvent<?> pollEvent : key.pollEvents()) {
                            Path changed = (Path) pollEvent.context();
                            if (hasRegisterFiles.stream().anyMatch(s -> s.equals(((Path)key.watchable()).resolve(changed).toString()))) {
                                hasChange = true;
                                break;
                            }
                        }
                        if (hasChange) {
                            log.info("refresh properties ok! changes: " + JSON.toJSONString(contextRefresher.refresh()));
                        }

                        if (!key.reset()) {
                            log.info("some confFiles has been unregistered in refresh");
                        }
                    }
                } catch (Exception e) {
                    log.error("refresh happen error : " + e.getMessage(), e);
                }
            });
        }
    }
}
