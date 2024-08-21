package com.sms.stockmanagementsystem.project;

import com.sms.stockmanagementsystem.project.data.Container;
import com.sms.stockmanagementsystem.project.repositories.ContainerRepository;
import com.sms.stockmanagementsystem.project.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;

@SpringBootApplication
public class StockManagementSystemApplication implements CommandLineRunner {

	@Autowired
	private ContainerRepository containerRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(StockManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(environment.getProperty("test"));

//		Container containerItem = new Container("test", LocalDateTime.now(), "user", "dlakjsltkja");
//		containerRepository.save(containerItem);

//		Group group = new Group("test");
//		groupRepository.save(group);

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
