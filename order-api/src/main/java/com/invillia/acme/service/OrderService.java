package com.invillia.acme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.invillia.acme.beans.SearchOrder;
import com.invillia.acme.entity.Order;
import com.invillia.acme.enums.OrderStatus;
import com.invillia.acme.repository.IOrderRepository;

/**
 * Classe que contém a lógica de negocio referenre a entidade Order 
 * @author thiago.nvieira
 *
 */
@Service
public class OrderService extends GenericService<Order, Long> {

	IOrderRepository repository;

	@Autowired
	public OrderService(IOrderRepository rep) {
		super(rep);
		this.repository = rep;
	}

	public Page<Order> findPaginated(Pageable pageable, SearchOrder dto) {
		Sort sort = Sort.by(Order.ORDER_CONFIRMATION_ATRIBUTE).ascending();
		pageable.getSortOr(sort);
		
		return repository.find(dto.getStatus(), dto.getConfirmation(), dto.getStreet(), dto.getCity(), dto.getZipCode(), dto.getState(),pageable);
	}

	public Order cancelar(Order order) {
		order.setStatus(OrderStatus.C.name());
		return super.save(order);
	}
	
}
