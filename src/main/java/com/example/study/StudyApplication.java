package com.example.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan("com.example.study.filter")
@ComponentScan(basePackages = "com.example.study.**")
@EnableTransactionManagement
@SpringBootApplication
@EnableScheduling
public class StudyApplication {

    /**
     * 配置文件路径, 以','分割的字符串. 配置采用覆盖式, 当有多个配置路径, 且包含相同配置属性时, 后者会覆盖前者. (windows环境下 /home/...以当前磁盘为根目录)
     */
    public final static String CONFIG_FILES_PATH = "classpath:application.yml,file:/home/conf/study/application.yml,classpath:application.properties,file:/home/conf/study/application.properties";

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

}


