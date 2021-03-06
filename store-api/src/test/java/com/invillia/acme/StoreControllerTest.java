package com.invillia.acme;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.invillia.acme.beans.CreateStore;
import com.invillia.acme.beans.SearchStore;
import com.invillia.acme.entity.Store;
import com.invillia.acme.service.StoreService;

public class StoreControllerTest extends StoreApplicationTests {

	private static final String BASE_URI_API_STORE = "/api/v1/store/";

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private StoreService service;
	
	private MockMvc mockMvc;
	
	private CreateStore store = new CreateStore("nameCreate", "street", "city", "zipCode", "uf");

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	@Sql(scripts = "classpath:data-store.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	public void giveStore_whenGetStore_thenReturnList() throws Exception {
		String serialized = objectMapper.writeValueAsString(new SearchStore());
		mockMvc.perform(
						get(BASE_URI_API_STORE)
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(serialized)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
	
	@Test
	public void giveStore_whenPostStrore_thenReturnStatusCreate() throws Exception {
		String serialized = objectMapper.writeValueAsString(store);
		
		mockMvc.perform(
						post(BASE_URI_API_STORE)
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(serialized)
					)
				.andExpect(status().is(HttpStatus.SC_CREATED))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.msg").value(MN001));

	}
	
	@Test
	public void giveStore_whenPutStrore_thenReturnStatusOk() throws Exception {
		Store entity = service.save(prepareStore(store));
		Long id = entity.getId();
		
		store.setName("nameUpdate");
		String serialized = objectMapper.writeValueAsString(store);
		
		mockMvc.perform(
						put(BASE_URI_API_STORE + id)
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(serialized)
					)
				.andExpect(status().is(HttpStatus.SC_OK))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.msg").value(MN002));

	}
	
	@Test
	public void giveStore_whenPutStrore_thenReturnValidationFail() throws Exception {
		Store entity = service.save(prepareStore(store));
		Long id = entity.getId();
		
		store.setState("SIZE_3");
		String serialized = objectMapper.writeValueAsString(store);
		
		mockMvc.perform(
						put(BASE_URI_API_STORE + id)
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(serialized)
					)
				.andExpect(status().is(HttpStatus.SC_BAD_REQUEST));

	}
	
	
	private Store prepareStore(CreateStore model) {
		Store entity = new Store();

		BeanUtils.copyProperties(model, entity);
		
		return entity;
	}
}
