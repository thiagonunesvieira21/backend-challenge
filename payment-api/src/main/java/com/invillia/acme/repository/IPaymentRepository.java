/**
 * 
 */
package com.invillia.acme.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.invillia.acme.entity.Payment;

/**
 * Repositorio de acesso ao banco de dados para a entidade Payment 
 * @author thiago.nvieira
 *
 */
public interface IPaymentRepository extends JpaRepository<Payment, String> {

	@Query("SELECT max(p.id) FROM Payment p WHERE "
			+ " p.orderId = :orderId "
			+ " AND p.paymentDate > :date")
	Optional<String> findByStoreIdAndPaymentDateLessTenDay(Long orderId, LocalDateTime date);

}
