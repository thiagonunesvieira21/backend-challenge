package com.invillia.acme.beans;

import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

/**
 * DTO para representação dos filtros de consulta de Order
 * @author thiago.nvieira
 *
 */
@ApiModel(value = "SearchOrder")
public class SearchOrder {
	private String street, city, zipCode, state, status;
	private LocalDateTime confirmation;
	private Integer storeId;

	@Size(max = 20)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Size(max = 20)
	public String getCity() {
		return city;
	}

	@Size(max = 8)
	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Size(max = 2)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public LocalDateTime getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(LocalDateTime confirmation) {
		this.confirmation = confirmation;
	}

	@Pattern(regexp = "P|G|C")   
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

}
