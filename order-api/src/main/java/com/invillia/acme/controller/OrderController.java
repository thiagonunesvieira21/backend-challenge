package com.invillia.acme.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.invillia.acme.beans.CreateOrder;
import com.invillia.acme.beans.CreateOrderItem;
import com.invillia.acme.beans.SearchOrder;
import com.invillia.acme.entity.Order;
import com.invillia.acme.entity.OrderItem;
import com.invillia.acme.enums.Menssages;
import com.invillia.acme.enums.OrderStatus;
import com.invillia.acme.exceptions.InvalidRequestException;
import com.invillia.acme.service.OrderItemService;
import com.invillia.acme.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Order API Service")
@RestController
@RequestMapping(value = "/api/v1/order")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class OrderController extends BaseController {

	@Value("${api.store-uri}")
	String apiStoreUri;
	
	@Value("${api.payment-uri}")
	String apiPaymentUri;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private OrderService service;
	
	@Autowired
	private OrderItemService serviceItem;

	/**
	 * Endpoint que prover a consulta de Order pela pk 
	 * @param orderId
	 * @return ResponseEntity<?>
	 */
	@ApiOperation(value = "Buscar order pelo ID", response = Order.class, notes = "Retorna o order a partir do ID especificado", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getOrderById(@PathVariable final Long orderId) {
		Optional<Order> order = service.findById(orderId);
		
		if(order==null) {
			throw new NoResultException(getMenssage(Menssages.MN003.value));
		}
		
		return ResponseEntity.ok(order);
	}

	/**
	 * Endpoint que prover a criação da Order
	 * @param model
	 * @param result
	 * @param headers
	 * @return ResponseEntity<?>
	 */
	@ApiOperation(value = "Serviço responsável por cadastrar a order")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> create(@RequestBody @Valid CreateOrder model, BindingResult result, @RequestHeader HttpHeaders headers) {

		if (result.hasErrors()) {
			throw new InvalidRequestException("Validação do cadastro de order", result);
		}

        validStore(model, headers);
		
		Order order = new Order();

		this.prepareOrder(model, order);

		order = service.save(order);

		HashMap<String, Object> map = new HashMap<>();
		map.put("msg", getMenssage(Menssages.MN001.value));
		map.put("id", order.getId());
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	/**
     * Endpoint que prover o cancelamento da order
     * @param orderId
     * @param result
     * @return ResponseEntity<?>
     */
    @ApiOperation(value = "Serviço responsável por cancelar a order")
    @RequestMapping(value = "/cancelOrder/{orderId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId, @RequestHeader HttpHeaders headers) {

    	Optional<Order> optional = service.findById(orderId);

    	if (optional == null) {
    		throw new NoResultException(getMenssage(Menssages.MN003.value));
    	}
    	
    	validPaymentExpired(orderId, headers);
        
        Order order = optional.get();
        
        order = service.cancelar(order);
        
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", getMenssage(Menssages.MN004.value));
        map.put("id", order.getId());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    /**
     * Endpoint que prover o cancelamento de item order
     * @param orderItemId
     * @param result
     * @return ResponseEntity<?>
     */
    @ApiOperation(value = "Serviço responsável por cancelar o item order")
    @RequestMapping(value = "/cancelOrderItem/{orderItemId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> cancelOrderItem(@PathVariable Long orderItemId, @RequestHeader HttpHeaders headers) {

    	Optional<OrderItem> optional = serviceItem.findById(orderItemId);

    	if (optional == null) {
    		throw new NoResultException(getMenssage(Menssages.MN003.value));
    	}
        
    	OrderItem orderItem = optional.get();

        validPaymentExpired(orderItem.getOrder().getId(), headers);
        
        orderItem = serviceItem.cancelar(orderItem);
        
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", getMenssage(Menssages.MN004.value));
        map.put("id", orderItem.getId());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

	/**
	 * Endpoint que prover a consulta paginada das Order por filtros
	 * @param dto
	 * @param pageable
	 * @param assembler
	 * @return ResponseEntity<?> 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Buscar order por filtros", response = List.class, notes = "Retorna o order a partir dos filtros especificados", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getOrdersForCustomer(SearchOrder dto, @PageableDefault(size = 10, page = 0) Pageable pageable,
			PagedResourcesAssembler assembler) {

		Page<Order> resultPage = service.findPaginated(pageable, dto);

		if (resultPage == null) {
			throw new NoResultException(getMenssage(Menssages.MN003.value));
		}

		return new ResponseEntity<>(assembler.toResource(resultPage), HttpStatus.OK);
	}

	/**
	 * Realiza a verificação da existência de Store. Consome a API Store.
	 * @param model
	 * @param headers
	 */
	private void validStore(CreateOrder model, HttpHeaders headers) {
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		try {
	        ResponseEntity<String> resultStore = this.restTemplate.exchange(apiStoreUri + model.getStoreId(), HttpMethod.GET, entity, String.class);
	        if(resultStore.getStatusCode().equals(HttpStatus.OK)) {
	        	 throw new NoResultException(getMenssage(Menssages.MN003.value));
	        }
		} catch (Exception e) {
			//@TODO Tratar indisponibilidade da API de Store
		}
	}
	
	/**
	 * Realiza a verificação se o pagamento está expirado. Consome a API Payment.
	 * @param orderId
	 * @param headers
	 */
	private void validPaymentExpired(Long orderId, HttpHeaders headers) {
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
       
		try {
			ResponseEntity<String> resultStore = this.restTemplate.exchange(apiPaymentUri+ "checkExpired/" + orderId, HttpMethod.GET, entity, String.class);
	        
	        if(!resultStore.getStatusCode().equals(HttpStatus.OK)) {
	        	 throw new NoResultException(getMenssage(Menssages.MN005.value));
	        }
		} catch (Exception e) {
			//@TODO Tratar indisponibilidade da API de Store
		}
	}
	
	private void prepareOrder(CreateOrder model, Order order) {

		BeanUtils.copyProperties(model, order);

		order.setItens(new HashSet<>());
		order.setStatus(OrderStatus.P.name());
		order.setConfirmation(LocalDateTime.now());
		
		for (CreateOrderItem item : model.getItens()) {
			order.addOrdemItem(item);
		}

	}
}
