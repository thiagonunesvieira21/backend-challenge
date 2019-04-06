package com.invillia.acme;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.invillia.acme.beans.CreateOrder;
import com.invillia.acme.beans.CreateOrderItem;
import com.invillia.acme.beans.SearchOrder;
import com.invillia.acme.entity.Order;
import com.invillia.acme.enums.OrderStatus;
import com.invillia.acme.service.OrderService;

@SqlGroup ({ 
	@Sql(scripts = "classpath:data-store.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(scripts = "classpath:data-order.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
public class OrderControllerTest extends OrderApplicationTests {

	private static final String BASE_URI_API_ORDER = "/api/v1/order/";
	private static final Long STORED_ID = 20L;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private OrderService service;
	
	private MockMvc mockMvc;
	
	private CreateOrder order;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Before
	public void init() {
		CreateOrderItem item = new CreateOrderItem("desc", 1, BigDecimal.ONE);
		
		Set<CreateOrderItem> listItens = new HashSet<>();
		listItens.add(item);
		
		order = new CreateOrder("street", "city", "zipCode", "uf", STORED_ID, listItens);
	}

	@Test
	public void giveOrder_whenGetOrder_thenReturnList() throws Exception {
		String serialized = objectMapper.writeValueAsString(new SearchOrder());
		mockMvc.perform(
						get(BASE_URI_API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(serialized)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
	
	@Test
	public void giveOrder_whenPostOrder_thenReturnStatusCreate() throws Exception {
		String serialized = objectMapper.writeValueAsString(order);
		
		mockMvc.perform(
						post(BASE_URI_API_ORDER)
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(serialized)
					)
				.andExpect(status().is(HttpStatus.SC_CREATED))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.msg").value(MN001));

	}
	
	@Test
	public void giveOrder_whenCancelOrder_thenReturnStatusOk() throws Exception {
		Order entity = service.save(prepareOrder(order));
		Long id = entity.getId();
		
		String serialized = objectMapper.writeValueAsString(order);
		
		mockMvc.perform(
						put(BASE_URI_API_ORDER + "cancelOrder/" + id)
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(serialized)
					)
				.andExpect(status().is(HttpStatus.SC_OK))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.msg").value(MN004));

	}
	
	private Order prepareOrder(CreateOrder model) {
		Order entity = new Order();

		BeanUtils.copyProperties(model, entity);
		entity.setId(1L);
		entity.setItens(new HashSet<>());
		entity.setStatus(OrderStatus.P.name());
		entity.setConfirmation(LocalDateTime.now());
		
		for (CreateOrderItem item : model.getItens()) {
			entity.addOrdemItem(item);
		}

		return entity;
	}
}
