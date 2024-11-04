package com.sms.stockmanagementsystem.project;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class StockManagementSystemApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(StockManagementSystemApplication.class, args);
  }

  @Override
  public void run(String... args) { // NOSONAR
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NotNull CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins().allowCredentials(true);
      }
    };
  }
}
