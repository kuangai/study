package com.example.study.after;

import com.example.study.conf.ConfigLoadSystem;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TestConfUpdate  {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //每隔2秒执行一次
    @Scheduled(cron="0/2 * * * * ?")
    public void testTasks() {
        System.out.println("21010100102222");
        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
        System.out.println("配置文件 test.a:"+ ConfigLoadSystem.getStringValue("test.a"));
        System.out.println("配置文件 test.b:"+ConfigLoadSystem.getStringValue("test.b"));
    }
}
