package com.invillia.acme;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvilliaApplicationTests {
	
	@Value("${MN001}")
	protected String MN001;
	
	@Value("${MN002}")
	protected String MN002;
	
	@Value("${MN003}")
	protected String MN003;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void contextLoads() {
	}

}
