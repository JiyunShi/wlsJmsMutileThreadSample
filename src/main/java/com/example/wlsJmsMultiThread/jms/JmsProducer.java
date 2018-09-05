package com.example.wlsJmsMultiThread.jms;

import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsProducer {

	private Destination dest;

	private JmsTemplate jmsTemplate;
	private static final Logger LOGGER = LoggerFactory.getLogger(JmsConsumer.class);

	@Autowired
public JmsProducer(@Qualifier("offerDestination") Destination dest, @Qualifier("edealsJmsTemplate") JmsTemplate jmsTemplate) {

		LOGGER.debug("creating JMSProducer ************************");
		this.dest = dest;
		this.jmsTemplate = jmsTemplate;
	}

	public void send(String msg) {

		LOGGER.debug("SENDING ************************");
		jmsTemplate.convertAndSend(dest, msg);
	}
}
