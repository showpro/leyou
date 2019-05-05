package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author zhan
 * @create 2019-04-25-15:30
 */
@SpringBootApplication
@EnableDiscoveryClient // 开启Eureka客户端
@EnableZuulProxy // 开启Zuul的网关功能
public class LeyouGatewayApplication {

    public static void main(String[] args) {

        SpringApplication.run(LeyouGatewayApplication.class, args);
    }
}

