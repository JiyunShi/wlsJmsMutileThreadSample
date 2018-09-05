package com.example.wlsJmsMultiThread;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.web.WebApplicationInitializer;


@SpringBootApplication
@EnableJms
public class WlsJmsMultiThreadApplication extends SpringBootServletInitializer implements WebApplicationInitializer {



	public static void main(String[] args) {


		SpringApplication.run(WlsJmsMultiThreadApplication.class, args);


		}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WlsJmsMultiThreadApplication.class);
	}



}
