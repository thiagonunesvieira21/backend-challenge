/**
 * 
 */
package com.invillia.acme.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.invillia.acme.entity.Payment;

/**
 * Repositorio de acesso ao banco de dados para a entidade Payment 
 * @author thiago.nvieira
 *
 */
public interface IPaymentRepository extends JpaRepository<Payment, String> {

	@Query("SELECT p FROM Payment p WHERE "
			+ " p.orderId = :orderId "
			+ " AND p.paymentDate > :date ")
	List<Payment> findByStoreIdAndPaymentDateLessTenDay(@Param("orderId") Long orderId, @Param("date") LocalDateTime date);

}
