package com.invillia.acme.service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

/**
 * Super Classe dos services que prover operações básicas
 * @author thiago.nvieira
 *
 */
public class GenericService<T, ID extends Serializable> {

    private JpaRepository<T, ID> repository;
    
    public GenericService(JpaRepository<T, ID> repository) {
    	this.repository = repository;
    }
    
    public <S extends T> S save(S entity) {
        return repository.save(entity);
    }
    
	public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Async("asyncExecutor")
	public void delete(ID id) {
		repository.deleteById(id);
	}
    
}