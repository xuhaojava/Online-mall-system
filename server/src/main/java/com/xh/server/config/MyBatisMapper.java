package com.xh.server.config;


import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//配置器
@AutoConfigureAfter(MybatisConfig.class)//在MybatisConfig之后加载
public class MyBatisMapper {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //设置sqlSession工厂的名字，MybatisConfig下的sqlSessionFactory
        mapperScannerConfigurer.setBasePackage("com.xh.server.mapper");
        //设置dao层
        return mapperScannerConfigurer;
    }
}
