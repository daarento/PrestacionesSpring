package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class PrestacionesSpringApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(PrestacionesSpringApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		// TODO Auto-generated method stub
		return application.sources(PrestacionesSpringApplication.class);
	}
}
