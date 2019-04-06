package com.invillia.acme;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.invillia.acme.beans.CreatePayment;
import com.invillia.acme.entity.Payment;
import com.invillia.acme.service.PaymentService;

@SqlGroup ({ 
	@Sql(scripts = "classpath:data-payment.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
public class PaymentControllerTest extends PaymentApplicationTests {

	private static final String BASE_URI_API_ORDER = "/api/v1/payment/";
	private static final Long ORDER_ID = 100L;
	private static final String PAYMENT_ID_EXPIRED = "ABC123EFG";

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private PaymentService service;
	
	private MockMvc mockMvc;
	
	private CreatePayment payment = new CreatePayment("012345678910111213AB", ORDER_ID);

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void givePayment_whenGetPayment_thenReturnStatusOk() throws Exception {
		mockMvc.perform(
						get(BASE_URI_API_ORDER + "checkExpired/" + ORDER_ID)
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
	
	@Test
	public void givePayment_whenGetPayment_thenReturnNoResult() throws Exception {
		Optional<Payment> optional = service.findById(PAYMENT_ID_EXPIRED);
		
		Payment payment = optional.get();
		payment.setPaymentDate(LocalDateTime.now().minusDays(11));
		
		service.save(payment);
		
		mockMvc.perform(
						get(BASE_URI_API_ORDER + "checkExpired/" + payment.getOrderId())
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				)
				.andExpect(status().is(HttpStatus.SC_OK));
	}
	
	@Test
	public void givePayment_whenPostPayment_thenReturnStatusCreate() throws Exception {
		String serialized = objectMapper.writeValueAsString(payment);
		
		mockMvc.perform(
						post(BASE_URI_API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(serialized)
					)
				.andExpect(status().is(HttpStatus.SC_CREATED))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.msg").value(MN001));

	}
	
}
