/**
 * 
 */
package com.invillia.acme.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

/**
 * @author thiago.nvieira
 *
 */
@Entity
@XmlRootElement
@Table(name = "tb04_payment")
@ApiModel(value = "Payment")
public class Payment implements Serializable {
	
	private static final long serialVersionUID = -8557606567951944452L;

	private String id;
	private String creditCardNumber;
	private String status;
	private LocalDateTime paymentDate;
	private Long orderId;

	@Id
	@Column(name="co_payment")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="co_credit_card")
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	
	@Column(name="co_status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="dh_payment")
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	@Column(name="nu_order")
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Payment [creditCardNumber=" + creditCardNumber + ", status=" + status + ", paymentDate=" + paymentDate
				+ ", orderId=" + orderId + "]";
	}

}
