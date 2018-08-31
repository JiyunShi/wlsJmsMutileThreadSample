package com.example.wlsJmsMultiThread.config;


import commonj.work.WorkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.commonj.WorkManagerTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableJms
@ComponentScan(basePackages = { "com.example.wlsJmsMultiThread" })
@ConfigurationProperties(prefix = "allowance")
public class AppConfig {

    @Bean
    public TaskExecutor monitorExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(25);
        return executor;
    }

    @Bean
    @Primary
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        // TODO set timeouts once we have a non-functional requirement (bgrov03)
        RestTemplate restTemplate = builder.build();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        //interceptors.add(ppapRequestIdInterceptor);
        //interceptors.add(loggingInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }


    @Bean
    public InitialContext getInitialContext() throws NamingException {
        InitialContext ctx = new InitialContext();
        return ctx;
    }


	@Bean(name="newWorkTaskExec")
	public WorkManagerTaskExecutor getWorkManagerTaskExecutor(@Autowired InitialContext ctx) throws  NamingException{

		WorkManagerTaskExecutor taskExecutor =  new WorkManagerTaskExecutor();
		WorkManager wm = (WorkManager) ctx.lookup("java:comp/env/wm/default");
		taskExecutor.setWorkManager(wm);

		return taskExecutor;
	}




}
