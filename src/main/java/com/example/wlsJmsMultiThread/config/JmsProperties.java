package com.example.wlsJmsMultiThread.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource("classpath:/jms.properties")
public class JmsProperties {

	private String targetQueueJndi;

	private String connectionFactoryJndi;
	
	private String offerRetryConnectionFactoryJndi;
	
	private String offerRetryQueueJndi;

	public String getTargetQueueJndi() {
		return targetQueueJndi;
	}

	public void setTargetQueueJndi(String targetQueueJndi) {
		this.targetQueueJndi = targetQueueJndi;
	}

	public String getConnectionFactoryJndi() {
		return connectionFactoryJndi;
	}

	public void setConnectionFactoryJndi(String connectionFactoryJndi) {
		this.connectionFactoryJndi = connectionFactoryJndi;
	}

	public String getOfferRetryConnectionFactoryJndi() {
		return offerRetryConnectionFactoryJndi;
	}

	public void setOfferRetryConnectionFactoryJndi(String offerRetryConnectionFactoryJndi) {
		this.offerRetryConnectionFactoryJndi = offerRetryConnectionFactoryJndi;
	}

	public String getOfferRetryQueueJndi() {
		return offerRetryQueueJndi;
	}

	public void setOfferRetryQueueJndi(String offerRetryQueueJndi) {
		this.offerRetryQueueJndi = offerRetryQueueJndi;
	}
	
	

}
