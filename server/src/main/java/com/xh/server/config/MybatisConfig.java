package com.xh.server.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration//配置器
@EnableTransactionManagement//允许使用事务管理器
public class MybatisConfig implements TransactionManagementConfigurer {
    //实现“事务管理配置器”接口
    @Autowired //自动装配
    DataSource dataSource;//dataSource是application.yml配置器中的dataSource

    @Bean(name = "sqlSessionFactory")//IOC控制翻转，getsqlSessionFactory方法作为一个bean，
    //名字是sqlSeesionFactory
    public SqlSessionFactory getsqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);//设置dataSource
        sqlSessionFactory.setTypeAliasesPackage("com.xh.server.model");//MyBatis将哪个包作为模型层进行扫描
        return sqlSessionFactory.getObject();
    }
    @Override
    public TransactionManager annotationDrivenTransactionManager() {//注解驱动的事务管理器
        return new DataSourceTransactionManager(dataSource);
    }
}
