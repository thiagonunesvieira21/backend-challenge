package com.invillia.acme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.entity.OrderItem;
import com.invillia.acme.enums.OrderStatus;
import com.invillia.acme.repository.IOrderItemRepository;

/**
 * Classe que contém a lógica de negocio referenre a entidade OrderItem
 * @author thiago.nvieira
 *
 */
@Service
public class OrderItemService extends GenericService<OrderItem, Long> {

	IOrderItemRepository repository;

	@Autowired
	public OrderItemService(IOrderItemRepository rep) {
		super(rep);
		this.repository = rep;
	}

	public OrderItem cancelar(OrderItem orderItem) {
		orderItem.setStatus(OrderStatus.C.name());
		return super.save(orderItem);
	}
	
}
