package com.jalivv.mry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.jalivv.mry.**")
public class MeirongyuanApplication {


    public static void main(String[] args) {
        SpringApplication.run(MeirongyuanApplication.class, args);
    }


}
