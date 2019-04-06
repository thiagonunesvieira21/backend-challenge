package com.invillia.acme.enums;

public enum OrderStatus {
	P("PAGO"),
	C("CANCELADO");
	
    private String descricao;

    OrderStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
