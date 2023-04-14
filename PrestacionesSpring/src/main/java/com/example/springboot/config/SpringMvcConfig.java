package com.example.springboot.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.example.springboot.dao.HistorialDAO;
import com.example.springboot.dao.HistorialDAOImpl;
import com.example.springboot.dao.PrestacionesDAO;
import com.example.springboot.dao.PrestacionesDAOImpl;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.springboot")
public class SpringMvcConfig implements WebMvcConfigurer {
	
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/banco");
		dataSource.setUsername("postgres");
		dataSource.setPassword("root");
		
		return dataSource;
	}
	
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	
	@Bean
	public PrestacionesDAO getPrestacionesDAO() {
		return new PrestacionesDAOImpl(getDataSource());
	}
	
	@Bean
	public HistorialDAO getHistorialDAO() {
		return new HistorialDAOImpl(getDataSource());
	}
}
