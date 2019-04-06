package com.invillia.acme.beans;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * DTO para representação do cadastro de Order Item
 * 
 * @author thiago.nvieira
 *
 */
public class CreateOrderItem {
	private String description;
	private Integer quantity;
	private BigDecimal unitPrice;

	public CreateOrderItem() {
		super();
	}

	public CreateOrderItem(String description, Integer quantity, BigDecimal unitPrice) {
		super();
		this.description = description;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	@ApiModelProperty(required = true)
	@NotNull
	@NotEmpty
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ApiModelProperty(required = true)
	@NotNull
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@ApiModelProperty(required = true)
	@NotNull
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

}
