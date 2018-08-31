package com.example.wlsJmsMultiThread.config;


import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import org.springframework.jms.annotation.EnableJms;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import static org.mockito.Mockito.when;

@Configuration
@EnableJms
@ComponentScan(basePackages = { "com.example.wlsJmsMultiThread" })
@Profile("test")
public class TestConfig {

    @Bean
    @Primary
    public InitialContext getInitialContext() throws NamingException {
        InitialContext ctx =Mockito.mock(InitialContext.class);

        when(ctx.lookup("java:comp/env/wm/default")).thenReturn(null);

        return ctx;
    }



}
