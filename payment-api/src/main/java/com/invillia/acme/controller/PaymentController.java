package com.invillia.acme.controller;

import java.util.HashMap;
import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.invillia.acme.beans.CreatePayment;
import com.invillia.acme.entity.Payment;
import com.invillia.acme.enums.Menssages;
import com.invillia.acme.exceptions.InvalidRequestException;
import com.invillia.acme.service.PaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Store API Payment")
@RestController
@RequestMapping(value = "/api/v1/payment")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class PaymentController extends BaseController {

	@Value("${api.order-uri}")
	String apiOrderUri;
	
	@Autowired
	private PaymentService service;
	
	@Autowired
	RestTemplate restTemplate;

	/**
	 * Endpoint que prover a consulta de Store pela pk 
	 * @param storeId
	 * @return ResponseEntity<?>
	 */
	@ApiOperation(value = "Buscar payment pelo ID da Order", response = Payment.class, notes = "Verifica se pelo ID da Order se o pagamento está vencido", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(value = "/checkExpired/{orderId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getCheckExpired(@PathVariable final Long orderId) {
		List<Payment> store = service.findByStoreIdAndNotExpired(orderId);
		
		if(store==null) {
			throw new NoResultException(getMenssage(Menssages.MN002.value));
		}
		
		return ResponseEntity.ok(store);
	}
	
	/**
	 * Endpoint que prover a criação do Payment
	 * @param model
	 * @param result
	 * @param headers
	 * @return ResponseEntity<?>
	 */
    @ApiOperation(value = "Serviço responsável por cadastrar o Payment")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> create(@RequestBody @Valid CreatePayment model, BindingResult result, @RequestHeader HttpHeaders headers) {

        if (result.hasErrors()) {
            throw new InvalidRequestException("Validação do cadastro do payment", result);
        }

        validOrder(model, headers);
        
        Payment payment = new Payment();
        
        this.preparePayment(model, payment);
        
        payment = service.savePayment(payment);

        HashMap<String, Object> map = new HashMap<>();
        map.put("msg",  getMenssage(Menssages.MN001.value));
        map.put("id", payment.getId());
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    /**
	 * Realiza a verificação da existência de Order. Consome a API Order.
	 * @param model
	 * @param headers
	 */
	private void validOrder(@Valid CreatePayment model, HttpHeaders headers) {
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		try {
			ResponseEntity<String> resultStore = this.restTemplate.exchange(apiOrderUri + model.getOrderId(), HttpMethod.GET, entity, String.class);
        
	        if(resultStore.getStatusCode().equals(HttpStatus.OK)) {
	        	 throw new NoResultException(getMenssage(Menssages.MN002.value));
	        }
        }catch (Exception e) {
        	//@TODO Tratar indisponibilidade da API de Store
		}
	}


	private void preparePayment(CreatePayment model, Payment payment) {

		BeanUtils.copyProperties(model, payment);
		
	}
}
