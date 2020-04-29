package com.xh.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public <T> RedisTemplate<String,T> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);//redis连接的是connectionFactory
        redisTemplate.setKeySerializer(new StringRedisSerializer());//设置键类型
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());//设置值类型，list,set,hash,zset
        return redisTemplate;
    }

    @Bean
    public <T> RedisTemplate<String , T> redisTemplate1(RedisConnectionFactory connectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));//设置值类型 String
        return redisTemplate;
    }

}
