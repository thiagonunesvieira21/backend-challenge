package com.invillia.acme.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.entity.Payment;
import com.invillia.acme.enums.PaymentStatus;
import com.invillia.acme.repository.IPaymentRepository;

/**
 * Classe que contém a lógica de negocio referenre a entidade Payment 
 * @author thiago.nvieira
 *
 */
@Service
public class PaymentService extends GenericService<Payment, String> {

	IPaymentRepository repository;

	@Autowired
	public PaymentService(IPaymentRepository rep) {
		super(rep);
		this.repository = rep;
	}

	public Payment savePayment(Payment payment) {
		payment.setPaymentDate(LocalDateTime.now());
		
		String sha256hex = DigestUtils.sha256Hex(payment.toString());
		payment.setId(sha256hex);
		payment.setStatus(PaymentStatus.A.name());
		
		return super.save(payment);
	}

	public List<Payment> findByStoreIdAndNotExpired(Long orderId) {
		LocalDateTime date = LocalDateTime.now().minusDays(10);
		return repository.findByStoreIdAndPaymentDateLessTenDay(orderId, date);
	}
}
