package com.invillia.acme.enums;

public enum PaymentStatus {

	A("Ativo"),
	C("Cancelado");
	
	String descrition;
	
	PaymentStatus(String descrition){
		this.descrition = descrition;
	}
}
