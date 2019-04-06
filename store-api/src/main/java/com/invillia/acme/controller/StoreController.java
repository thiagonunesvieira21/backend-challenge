package com.invillia.acme.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.beans.CreateStore;
import com.invillia.acme.beans.SearchStore;
import com.invillia.acme.entity.Store;
import com.invillia.acme.enums.Menssages;
import com.invillia.acme.exceptions.InvalidRequestException;
import com.invillia.acme.service.StoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Store API Service")
@RestController
@RequestMapping(value = "/api/v1/store")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class StoreController extends BaseController {

	@Autowired
	private StoreService service;

	/**
	 * Endpoint que prover a consulta de Store pela pk 
	 * @param storeId
	 * @return ResponseEntity<?>
	 */
	@ApiOperation(value = "Buscar store pelo ID", response = Store.class, notes = "Retorna o store a partir do ID especificado", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(value = "/{storeId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getStoreById(@PathVariable final Long storeId) {
		Optional<Store> store = service.findById(storeId);
		
		if(store==null) {
			throw new NoResultException(getMenssage(Menssages.MN003.value));
		}
		
		return ResponseEntity.ok(store);
	}
	
	/**
	 * Endpoint que prover a criação do Store
	 * @param model
	 * @param result
	 * @param headers
	 * @return ResponseEntity<?>
	 */
    @ApiOperation(value = "Serviço responsável por cadastrar a Store")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> create(@RequestBody @Valid CreateStore model, BindingResult result) {

        if (result.hasErrors()) {
            throw new InvalidRequestException("Validação do cadastro de store", result);
        }

        Store store = new Store();
        
        this.prepareStore(model, store);
        
        store = service.save(store);

        HashMap<String, Object> map = new HashMap<>();
        map.put("msg",  getMenssage(Menssages.MN001.value));
        map.put("id", store.getId());
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    /**
     * Endpoint que prover a alteração do Store
     * @param model
     * @param storeId
     * @param result
     * @return ResponseEntity<?>
     */
    @ApiOperation(value = "Serviço responsável por atualizar o store")
    @RequestMapping(value = "/{storeId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> update(@RequestBody @Valid CreateStore model, @PathVariable Long storeId,
                                    BindingResult result) {

        if (result.hasErrors()) {
            throw new InvalidRequestException("Validação do atualizacao de store", result);
        }

        Optional<Store> optional = service.findById(storeId);

        if (optional == null) {
            throw new NoResultException(getMenssage(Menssages.MN003.value));
        }
        
        Store store = optional.get();
        prepareStore(model, store);

        store = service.save(store);
        
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", getMenssage(Menssages.MN002.value));
        map.put("id", store.getId());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
	 * Endpoint que prover a consulta paginada das Store por filtros
     * @param dto
     * @param pageable
     * @param assembler
     * @return ResponseEntity<?>
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "Buscar store por filtros", response = List.class, notes = "Retorna o store a partir dos filtros especificados", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?>  getOrdersForCustomer(SearchStore dto,  @PageableDefault(size = 10, page = 0) Pageable pageable, PagedResourcesAssembler assembler) {
		
		Page<Store> resultPage = service.findPaginated(pageable, dto);
		
        if (resultPage == null) {
            throw new NoResultException(getMenssage(Menssages.MN003.value));
        }
		
		return new ResponseEntity<>(assembler.toResource(resultPage), HttpStatus.OK);
	}

	private void prepareStore(CreateStore model, Store store) {

		BeanUtils.copyProperties(model, store);
		
	}
}
