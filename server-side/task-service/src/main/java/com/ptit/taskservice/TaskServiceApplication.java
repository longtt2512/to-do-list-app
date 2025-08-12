package com.ptit.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ptit.taskservice")
@EntityScan(basePackages = "com.ptit.taskservice")
@EnableJpaAuditing
public class TaskServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskServiceApplication.class, args);
  }

}
