package com.example.wlsJmsMultiThread.jms;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.jms.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


@Component
@PropertySource("classpath:/jms.properties")
public class JmsConsumer implements MessageListener {

	@Autowired
	@Qualifier("edealsConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Autowired
	@Qualifier("offerDestination")
	private Destination dest1;

	@Autowired
	private NamedParameterJdbcTemplate namedJdbc;

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsConsumer.class);

	@Autowired
	public JmsConsumer() {
	}
	
	
	@JmsListener(destination = "${target-queue-jndi}", containerFactory = "myFactory")
	public void onMessage(Message msg) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			Date date = new Date();
			String strDate = sdf.format(date);

			Integer times = 5;

			while (times>0) {
				LOGGER.debug("Reading message from Listener: "
						+ strDate + "Message Id:*****" + msg.getJMSMessageID() + "Thread Name: ************" + Thread.currentThread().getName() + "****");
				Thread.sleep(10000);
				times--;
			}

			/*
			String sql = "SELECT PROMO_STAT_CD FROM DEVSQL3.PPAPPRHD WHERE PROMO_ID=:promoId";
			Integer promoId = 33673;
			MapSqlParameterSource par = new MapSqlParameterSource();
			par.addValue("promoId", promoId);
			String promoStatus = this.namedJdbc.query(sql, par, (rs) -> {
				String status = null;
				while (rs.next()) {
					LOGGER.debug("PROMO STAT CODE"+rs.getString("PROMO_STAT_CD").trim());
					status = rs.getString("PROMO_STAT_CD").trim();
					//LOGGER.debug(message);
					return status;
				}
				return null;
			});
			*/
		}
		catch(Exception ex) {

		}

	}


}
