package com.example.study.conf;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.study.conf.token.TokenConfig;
import com.example.study.conf.token.TokenFactory;
import com.example.study.conf.token.operator.RedisTokenOperator;
import com.google.common.base.Charsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import javax.servlet.MultipartConfigElement;
import java.util.Arrays;

/**
 * 文件上传配置
 */
@Configuration
@RefreshScope
public class WebConfig {


    @Value("${wadp.token-expired-time}")
    private int tokenExpiredTime;

    @Bean
    public TokenFactory getTokenFactoryBean(RedisTemplate<String, String> redisTemplate) {
        TokenFactory tokenFactory = new TokenFactory();
        TokenConfig tokenConfig = new TokenConfig();
        tokenConfig.setAutoDelay(true);
        tokenConfig.setExpiredTimeInMinutes(tokenExpiredTime);

        RedisTokenOperator redisTokenOperator = new RedisTokenOperator(tokenConfig);
        redisTokenOperator.setRedisTemplate(redisTemplate);
        tokenFactory.setOperator(redisTokenOperator);
        return tokenFactory;
    }

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
        fastJsonHttpMessageConverter.setDefaultCharset(Charsets.UTF_8);
        return fastJsonHttpMessageConverter;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(@Value("${multipart.maxFileSize}") Integer maxFileSize, @Value("${multipart.maxRequestSize}") Integer maxRequestSize) {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.of(maxFileSize, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(maxRequestSize, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }
}