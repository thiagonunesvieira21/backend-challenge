/**
 * 
 */
package com.invillia.acme.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

/**
 * @author thiago.nvieira
 *
 */
@Entity
@XmlRootElement
@Table(name = "tb01_store")
@SequenceGenerator(name = "generator_store_seq", sequenceName = "seq_nu_store", allocationSize = 1, initialValue = 1)
@ApiModel(value = "Store")
public class Store implements Serializable {

	public static String STORE_NAME_ATRIBUTE = "name";
	public static String ADDRESS_STREET_ATRIBUTE = "street";
	public static String ADDRESS_ZIP_CODE_ATRIBUTE = "zipCode";
	public static String ADDRESS_CITY_ATRIBUTE = "city";
	public static String ADDRESS_STATE_ATRIBUTE = "state";

	private static final long serialVersionUID = -6108595508632743235L;

	private Long id;
	private String name;
	private String street, zipCode, city, state;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_store_seq")
	@Column(name = "nu_store")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "no_store")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		Store other = (Store) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
