package com.aiplanner.aiprojectplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringBootApplication(
//        exclude = {
//                org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration.class,
//                org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration.class
//        }
//)
@SpringBootApplication
public class AiProjectPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiProjectPlannerApplication.class, args);
    }

}
