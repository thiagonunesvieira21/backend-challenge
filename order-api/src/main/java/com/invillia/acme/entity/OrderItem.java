/**
 * 
 */
package com.invillia.acme.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;

/**
 * @author thiago.nvieira
 *
 */
@Entity
@XmlRootElement
@Table(name = "tb03_order_item")
@SequenceGenerator(name = "generator_order_item_seq", sequenceName = "seq_nu_order_item", allocationSize = 1, initialValue = 1)
@ApiModel(value = "OrderItem")
public class OrderItem implements Serializable {

	public static String ORDER_ITEM_NAME_ATRIBUTE = "description";

	private static final long serialVersionUID = -6108595508632743235L;

	private Long id;
	private String description;
	private Integer quantity;
	private BigDecimal unitPrice;
	private Order order;
	
	public OrderItem() {
		super();
	}

	public OrderItem(String description, Integer quantity, BigDecimal unitPrice, Order order) {
		super();
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.description = description;
		this.order = order;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_order_item_seq")
	@Column(name = "nu_order_item")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "no_description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "qt_order_item")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "vr_unit_price")
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="nu_order")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
