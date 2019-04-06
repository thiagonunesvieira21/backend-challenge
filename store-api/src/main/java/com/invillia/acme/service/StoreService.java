package com.invillia.acme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.invillia.acme.beans.SearchStore;
import com.invillia.acme.entity.Store;
import com.invillia.acme.repository.IStoreRepository;

/**
 * Classe que contém a lógica de negocio referenre a entidade Store 
 * @author thiago.nvieira
 *
 */
@Service
public class StoreService extends GenericService<Store, Long> {

	IStoreRepository repository;

	@Autowired
	public StoreService(IStoreRepository rep) {
		super(rep);
		this.repository = rep;
	}

	public Page<Store> findPaginated(Pageable pageable, SearchStore dto) {
		Sort sort = Sort.by(Store.STORE_NAME_ATRIBUTE).ascending();
		pageable.getSortOr(sort);
		return repository.find(dto.getName(), dto.getStreet(), dto.getCity(), dto.getZipCode(), dto.getState(),pageable);
	}
}
