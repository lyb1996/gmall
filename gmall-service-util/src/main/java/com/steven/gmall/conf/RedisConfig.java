package com.steven.gmall.conf;

import com.steven.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //spring容器启动的时候读它，用于定义配置类
public class RedisConfig {
    // 读取配置文件中的Redis的ip地址
    @Value("${spring.redis.host:disabled}") // 如果读不到地址，则为默认值disabled
    private String host;
    @Value("${spring.redis.port:0}")
    private int port;
    @Value("${spring.redis.database:0}")
    private int database;

    @Bean // @Bean在Spring启动的时候创建，一个@Configuration注解可以包含一个或多个@Bean
    public RedisUtil getRedisUtil() {
        if (host.equals("disabled")) {
            return null;
        }
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.initPool(host, port, database);
        return redisUtil; // 将redisUtil创建到spring容器中
    }
}
