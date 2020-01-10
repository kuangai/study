package com.example.study.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.example.study.conf.ConfigLoadApp;
import org.slf4j.LoggerFactory;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.springframework.stereotype.Component;

@WebListener
@Component
public class AppStartListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().log("=============系统启动中，开始加载配置文件===========");

        String system = ConfigLoadApp.getStringValue("system.properties");
        String db = ConfigLoadApp.getStringValue("db.properties");
        String logback = ConfigLoadApp.getStringValue("logback.xml");
        
        System.setProperty("system.properties",system);
        System.setProperty("db.properties",db);
        System.setProperty("logback.xml",logback);
        setLogCfg(event.getServletContext(),logback);

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        event.getServletContext().log("销毁.....配置加载....");
    }


    private static boolean setLogCfg(ServletContext sc,String path) {
        File file = new File(path);
        if (!file.exists()) {
            sc.log("日志配置文件路径："+path+"读取失败");
            return false;
        }
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator joranConfigurator = new JoranConfigurator();
        joranConfigurator.setContext(lc);
        lc.reset();
        try {
            joranConfigurator.doConfigure(file);
        } catch (JoranException e) {
            sc.log("日志配置出现异常",e);
            return false;
        }
        return true;
    }

}