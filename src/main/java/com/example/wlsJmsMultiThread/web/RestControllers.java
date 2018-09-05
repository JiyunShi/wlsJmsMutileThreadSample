package com.example.wlsJmsMultiThread.web;


import com.example.wlsJmsMultiThread.jms.JmsProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllers {
    @Autowired
    private JmsProducer jmsProducer;

    private static final Logger logger = LoggerFactory.getLogger(RestControllers.class);

    @RequestMapping(value ="/greeting", method = RequestMethod.GET)
    public void greeting() {

        logger.debug("start produce JMS message**********");

        if(null==jmsProducer) logger.debug("jmsProducer is null **********");

        jmsProducer.send("test1");
        jmsProducer.send("test2");
        jmsProducer.send("test3");
        jmsProducer.send("test4");

    }

}
