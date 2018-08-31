package com.example.wlsJmsMultiThread;

import commonj.work.WorkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.commonj.WorkManagerTaskExecutor;
import org.springframework.web.WebApplicationInitializer;

import javax.naming.InitialContext;
import javax.naming.NamingException;

@SpringBootApplication
@ComponentScan
public class WlsJmsMultiThreadApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(String[] args) {

		SpringApplication.run(WlsJmsMultiThreadApplication.class, args);

		}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WlsJmsMultiThreadApplication.class);
	}



}
