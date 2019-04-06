package com.invillia.acme.beans;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PostStore")
public class CreateStore {

	private String name, street, city, zipCode, state;
	
	public CreateStore() {
		super();
	}

	public CreateStore(String name, String street, String city, String zipCode, String state) {
		super();
		this.name = name;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
	}

	@ApiModelProperty(required = true)
	@NotEmpty
	@NotNull
	@Size(max = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
