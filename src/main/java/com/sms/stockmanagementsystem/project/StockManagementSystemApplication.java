package com.sms.stockmanagementsystem.project;

import org.json.simple.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;

@SpringBootApplication
public class StockManagementSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StockManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		ContainerItem containerItem = new ContainerItem("test", LocalDateTime.now(), "user", "dlakjsltkja");
//		JSONConverter.save(containerItem, "test");

//		ContainerItem containerItem = JSONConverter.getContainerItems("test", "test");
//		System.out.println(containerItem);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins().allowCredentials(true);
			}
		};
	}
}
