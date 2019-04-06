package com.invillia.acme.beans;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "SearchStore")
public class SearchStore {
	private String name, street, city, zipCode, state;

	@Size(max = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
	
}
