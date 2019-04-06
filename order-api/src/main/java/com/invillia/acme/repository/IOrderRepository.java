/**
 * 
 */
package com.invillia.acme.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.invillia.acme.entity.Order;

/**
 * Repositorio de acesso ao banco de dados para a entidade Order 
 * @author thiago.nvieira
 *
 */
public interface IOrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT s FROM Order s WHERE 0=0 "
			+ " AND ( s.status = :status OR :status IS NULL ) "
			+ " AND ( s.confirmation = :confirmation OR :confirmation IS NULL ) "
			+ " AND ( s.street = :street OR :street IS NULL ) "
			+ " AND ( s.zipCode = :zipCode OR :zipCode IS NULL ) "
			+ " AND ( s.city = :city OR :city IS NULL ) "
			+ " AND ( s.state = :state OR :state IS NULL ) ")
	Page<Order> find(@Param("status") String status, @Param("confirmation") LocalDateTime confirmation, 
			@Param("street") String street, @Param("city") String city,
			@Param("zipCode") String zipCode, @Param("state") String state, Pageable page);
}
