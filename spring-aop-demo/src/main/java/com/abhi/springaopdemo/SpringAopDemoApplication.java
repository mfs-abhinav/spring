package com.abhi.springaopdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringAopDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAopDemoApplication.class, args);
    }

}
