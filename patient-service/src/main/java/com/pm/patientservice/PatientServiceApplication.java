package com.pm.patientservice;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientServiceApplication.class, args);
    }

    @PostConstruct
    public void envCheck() {
        System.out.println("SPRING_SQL_INIT_MODE = " +
                System.getenv("SPRING_SQL_INIT_MODE"));
    }

}
