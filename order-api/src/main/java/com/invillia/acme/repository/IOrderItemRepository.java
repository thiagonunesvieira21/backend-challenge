/**
 * 
 */
package com.invillia.acme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invillia.acme.entity.OrderItem;

/**
 * Repositorio de acesso ao banco de dados para a entidade OrderItem 
 * @author thiago.nvieira
 *
 */
public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> {

}
