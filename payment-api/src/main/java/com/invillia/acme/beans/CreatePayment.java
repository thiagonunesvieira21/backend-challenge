package com.invillia.acme.beans;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PostPayment")
public class CreatePayment {

	private String creditCardNumber;
	private Long orderId;
	
	public CreatePayment() {
		super();
	}

	public CreatePayment(String creditCardNumber, Long orderId) {
		super();
		this.creditCardNumber = creditCardNumber;
		this.orderId = orderId;
	}

	@ApiModelProperty(required = true)
	@NotEmpty
	@NotNull
	@Size(min=20, max = 20)
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	@ApiModelProperty(required = true)
	@NotNull
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
