package com.cellbeans.hspa;

import com.cellbeans.hspa.applicationproperty.Propertyconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableAutoConfiguration
//@EnableDiscoveryClient
public class HspaApplication extends SpringBootServletInitializer {

    private Propertyconfig Propertyconfig;

    public static void main(String[] args) {
        SpringApplication.run(HspaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HspaApplication.class);
    }

    @RequestMapping(value = "/")
    public String Demo() {
        return "HSPA RUNNINGG.....";
    }
}