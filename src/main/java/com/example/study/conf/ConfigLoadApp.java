package com.example.study.conf;


import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * 自定义配置文件目录加载类
 */
public class ConfigLoadApp {
    // 并发访问锁
    private static final ReentrantLock lock = new ReentrantLock();
    private static FileConfiguration instance = null;

    private static final String CONFIG_LOAD_PATH = "/app.properties";

    // 静态初始化
    static {
        if (instance == null) {
            init();
        }
    }

 
    private ConfigLoadApp() {

    }
 
    private static void init() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new PropertiesConfiguration();
                instance.setEncoding("utf-8");
                instance.setURL(ConfigLoadApp.class.getResource(CONFIG_LOAD_PATH));
                // DEFAULT_REFRESH_DELAY = 5000

                instance.setReloadingStrategy(new FileChangedReloadingStrategy());// 使用文件内容发送变化重新加载策略

                try {
                    instance.load();
                    ConfigurationUtils.dump(instance, System.out);
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        } finally {
            lock.unlock();
        }
    }

   
    public static FileConfiguration getInstance() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    
    public static String getStringValue(String key) {
        return instance.getString(key);
    }

    
    public static String getStringValue(String key, String defaultValue) {
        return instance.getString(key, defaultValue);
    }

    
    public static int getIntValue(String key) {
        return instance.getInt(key);
    }

    
    public static int getIntValue(String key, int defaultValue) {
        return instance.getInt(key, defaultValue);
    }
    
    public static long getLongValue(String key, long defaultValue){
        return instance.getLong(key, defaultValue);
    }
    
    public static long getLongValue(String key){
        return instance.getLong(key);
    }
    
    public static boolean getBoolean(String key){
        return instance.getBoolean(key);
    }

    
    public static boolean getBoolean(String key, boolean defaultValue){
        return instance.getBoolean(key,defaultValue);
    }

    
    public static List<?> getList(String key){
        return instance.getList(key);
    }

    public static List<?> getList(String key, List<?> defaultValue){
        return instance.getList(key,defaultValue);
    }

    public static String[] getStringArray(String key){
        return instance.getStringArray(key);
    }
}

