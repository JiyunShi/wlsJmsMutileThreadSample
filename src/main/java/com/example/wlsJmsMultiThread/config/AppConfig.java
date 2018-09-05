package com.example.wlsJmsMultiThread.config;


import com.example.wlsJmsMultiThread.WlsJmsMultiThreadApplication;
import commonj.work.WorkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.commonj.WorkManagerTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableJms
@ComponentScan(basePackages = { "com.example.wlsJmsMultiThread" })
@ConfigurationProperties(prefix = "allowance")
public class AppConfig {

    @Autowired
    private JmsProperties jmsProps;

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

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
        logger.error("build bean RestTemplate*******************************************");
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


    @Bean(name = "newWorkManager")
    public WorkManager getNewWorkManager(InitialContext ctx) throws Exception {
        WorkManager wm = (WorkManager) ctx.lookup("java:comp/env/wm/default");
        return wm;
    }


	@Bean(name="newWorkTaskExec")
	public WorkManagerTaskExecutor getWorkManagerTaskExecutor(@Autowired InitialContext ctx) throws  NamingException{

		WorkManagerTaskExecutor taskExecutor =  new WorkManagerTaskExecutor();
		WorkManager wm = (WorkManager) ctx.lookup("java:comp/env/wm/default");
		taskExecutor.setWorkManager(wm);

		return taskExecutor;
	}



    @Bean(name = "edealsConnectionFactory")
    @Primary
    public ConnectionFactory getConnectionFactory(InitialContext ctx) throws NamingException {
        logger.debug("build bean ConnectionFactory*******************************************");
        ConnectionFactory cf = null;

         cf = (ConnectionFactory) ctx.lookup(jmsProps.getConnectionFactoryJndi());

        return cf;
    }


    @Bean(name = "edealsJmsTemplate")
    @Primary
    public JmsTemplate getJmsTemplate(@Autowired @Qualifier("edealsConnectionFactory") ConnectionFactory cf) {
        JmsTemplate jmsTemplate = new JmsTemplate(cf);
        return jmsTemplate;
    }



    @Bean(name = "offerDestination")
    @Primary
    public Destination getDestination(InitialContext ctx) throws NamingException {
        Destination dest = null;

        dest = (Destination) ctx.lookup(jmsProps.getTargetQueueJndi());
        return dest;
    }


    @Bean
    public JmsListenerContainerFactory<?> myFactory(@Qualifier("edealsConnectionFactory")ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConcurrency("2-4");
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }




    @Bean(name = "myFactory")
    public JmsListenerContainerFactory<?> myFactory(
            @Autowired @Qualifier("edealsConnectionFactory") ConnectionFactory cf,
            DefaultJmsListenerContainerFactoryConfigurer configurer, @Autowired @Qualifier("newWorkTaskExec") WorkManagerTaskExecutor taskExec) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConcurrency("2-4");
        configurer.configure(factory, cf);
        factory.setTaskExecutor(taskExec);

        return factory;
    }





}
