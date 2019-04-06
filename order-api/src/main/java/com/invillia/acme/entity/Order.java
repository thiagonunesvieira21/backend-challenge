/**
 * 
 */
package com.invillia.acme.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.invillia.acme.beans.CreateOrderItem;

import io.swagger.annotations.ApiModel;

/**
 * @author thiago.nvieira
 *
 */
@Entity
@XmlRootElement
@Table(name = "tb02_order")
@SequenceGenerator(name = "generator_order_seq", sequenceName = "seq_nu_order", allocationSize = 1, initialValue = 1)
@ApiModel(value = "Order")
public class Order implements Serializable {

	public static String ORDER_NAME_ATRIBUTE = "name";
	public static String ORDER_STATUS_ATRIBUTE = "status";
	public static String ORDER_CONFIRMATION_ATRIBUTE = "confirmation";
	public static String STORE_ID_ATRIBUTE = "storeId";
	public static String ADDRESS_STREET_ATRIBUTE = "street";
	public static String ADDRESS_ZIP_CODE_ATRIBUTE = "zipCode";
	public static String ADDRESS_CITY_ATRIBUTE = "city";
	public static String ADDRESS_STATE_ATRIBUTE = "state";

	private static final long serialVersionUID = -6108595508632743235L;

	private Long id, storeId;
	private String status;
	private LocalDateTime confirmation;
	private String street, zipCode, city, state;
	
	private Set<OrderItem> itens;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_order_seq")
	@Column(name = "nu_order")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "nu_store")
	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	@Column(name = "co_status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "dh_confirmation")
	public LocalDateTime getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(LocalDateTime confirmation) {
		this.confirmation = confirmation;
	}

	@Column(name = "no_street")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "co_zip_code")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "no_city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "no_state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(mappedBy="order", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public Set<OrderItem> getItens() {
		return itens;
	}

	public void setItens(Set<OrderItem> itens) {
		this.itens = itens;
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
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addOrdemItem(CreateOrderItem orderItem) {
		this.getItens().add(new OrderItem(orderItem.getDescription(), orderItem.getQuantity(), orderItem.getUnitPrice(), this));
	}
	
}
