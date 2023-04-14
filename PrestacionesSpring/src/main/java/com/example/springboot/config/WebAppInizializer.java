package com.example.springboot.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInizializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
		appContext.register(SpringMvcConfig.class);
		
		ServletRegistration.Dynamic dispatcher 
		= servletContext.addServlet("SpringDispatcher", new DispatcherServlet((WebApplicationContext) appContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}