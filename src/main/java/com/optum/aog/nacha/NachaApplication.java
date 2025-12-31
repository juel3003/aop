package com.optum.aog.nacha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class NachaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NachaApplication.class, args);
    }
}
