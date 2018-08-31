package com.example.wlsJmsMultiThread;

import com.example.wlsJmsMultiThread.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
public class WlsJmsMultiThreadApplicationTests {

	@Test
	public void contextLoads() {
	}

}
