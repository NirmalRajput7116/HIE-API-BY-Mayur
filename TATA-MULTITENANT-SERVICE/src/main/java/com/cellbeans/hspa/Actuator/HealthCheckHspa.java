//package com.cellbeans.hspa.Actuator;
//
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/health_check")
//@Component
//public class HealthCheckHspa implements HealthIndicator {
//
//    @RequestMapping("health")
//    public Health health() {
//        int errorCode = check(); // perform some specific health check
//        if (errorCode != 0) {
//            return Health.down()
//                    .withDetail("Error Code", errorCode).build();
//        }
//
//        return Health.up().build();
//    }
//
//    public int check() {
//        // Our logic to check health
//        return 0;
//    }
//}
