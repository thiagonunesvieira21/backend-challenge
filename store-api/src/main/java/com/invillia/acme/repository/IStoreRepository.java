/**
 * 
 */
package com.invillia.acme.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.invillia.acme.entity.Store;

/**
 * Repositorio de acesso ao banco de dados para a entidade Store 
 * @author thiago.nvieira
 *
 */
public interface IStoreRepository extends JpaRepository<Store, Long> {

	@Query("SELECT s FROM Store s WHERE 0=0 " + " AND ( s.name = :name OR :name IS NULL ) "
			+ " AND ( s.street = :street OR :street IS NULL ) "
			+ " AND ( s.zipCode = :zipCode OR :zipCode IS NULL ) "
			+ " AND ( s.city = :city OR :city IS NULL ) "
			+ " AND ( s.state = :state OR :state IS NULL ) ")
	Page<Store> find(@Param("name") String name, @Param("street") String street, @Param("city") String city,
			@Param("zipCode") String zipCode, @Param("state") String state, Pageable page);
}
