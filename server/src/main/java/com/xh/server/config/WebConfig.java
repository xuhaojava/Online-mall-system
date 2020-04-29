package com.xh.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //配置器注解@Configuration代表WebConfig类是一个配置器
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //让sprin对某目录下的资源全部放行，不进行拦截。
        registry.addResourceHandler("/static/**").addResourceLocations("classpath/static/**").resourceChain(true);
        //addResourceHandler为相对路径，addResourceLocations为绝对路径
        //addResourceLocations中的classpath等价于src
        //resourceChain可以将static目录下的文件加入缓存，这里设置值为true，代表加入缓存。
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //当首次访问项目时，需要执行的代码
        registry.addViewController("/").setViewName("forward:/index.html");
        //当首次访问项目时addViewController内的值的时候，访问index.html文件。
        //因为addResourceHandlers方法里规定了对static下的文件不拦截，所以
        //addViewControllers规定的第一次访问/时跳转到static下的index.html文件
    }
    //写完WebConfig代码后第一次启动spring失败，原因是resources目录下的application.properties文件为空
    //解决方法是删除application.properties文件，新建application.yml全局配置文档

    //第二个错误，配置了application.yml后，启动spring，还是不能登录，问题在于ServerApplication的默认的web目录没有配置
    //解决方法：点击右上角ServerApplication按钮->Edit configurations->spring boot->enviroment->在working directory中设置到本项目的web目录上
}
