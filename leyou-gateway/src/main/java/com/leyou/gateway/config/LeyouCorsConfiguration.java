package com.leyou.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 配置类，并且注册CorsFilter,解决跨域问题
 * @author zhan
 * @create 2019-04-27-15:12
 */
@Configuration
public class LeyouCorsConfiguration {

    //方法的返回值 就是要注入的过滤器
    @Bean
    public CorsFilter corsFilter() {

        // 1.初始化cors配置对象
        CorsConfiguration configuration = new CorsConfiguration();
        //1) 允许的跨域的域名,不要写*，否则cookie就无法使用了
        configuration.addAllowedOrigin("http://manage.leyou.com");
        //2) 允许发送Cookie信息
        configuration.setAllowCredentials(true);
        //3) 允许的请求方式
        //configuration.addAllowedMethod("OPTIONS");
        //configuration.addAllowedMethod("HEAD");
        //configuration.addAllowedMethod("GET");
        //configuration.addAllowedMethod("PUT");
        //configuration.addAllowedMethod("POST");
        //configuration.addAllowedMethod("DELETE");
        //configuration.addAllowedMethod("PATCH");
        configuration.addAllowedMethod("*");  // * 代表所有的请求方法：GET POST PUT Delete...
        // 4）允许携带任何头信息
        configuration.addAllowedHeader("*");

        // 2.初始化cors配置源对象，添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",configuration);

        // 3.返回corsFilter实例，参数：cors配置源对象CorsConfigurationSource（是一个接口，使用接口的实现类）
        return new CorsFilter(configurationSource);
    }
}
