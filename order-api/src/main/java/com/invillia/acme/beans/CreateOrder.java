package com.invillia.acme.beans;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * DTO para representação do cadastro de Order
 * @author thiago.nvieira
 *
 */
@ApiModel(value = "PostOrder")
public class CreateOrder {

	private String street, city, zipCode, state;
	private Long storeId;
	private Set<CreateOrderItem> itens;
	
	public CreateOrder() {
		super();
	}

	public CreateOrder(String street, String city, String zipCode, String state, Long storeId, Set<CreateOrderItem> itens) {
		super();
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
		this.storeId = storeId;
		this.itens = itens;
	}

	@ApiModelProperty(required = true)
	@NotEmpty
	@NotNull
	@Size(max = 20)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@ApiModelProperty(required = true)
	@NotEmpty
	@NotNull
	@Size(max = 20)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@ApiModelProperty(required = true)
	@NotEmpty
	@NotNull
	@Size(max = 8)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@ApiModelProperty(required = true)
	@NotEmpty
	@NotNull
	@Size(max = 2)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@ApiModelProperty(required = true)
	@NotNull
	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	@NotEmpty
	public Set<CreateOrderItem> getItens() {
		return itens;
	}

	public void setItens(Set<CreateOrderItem> itens) {
		this.itens = itens;
	}
	
}
