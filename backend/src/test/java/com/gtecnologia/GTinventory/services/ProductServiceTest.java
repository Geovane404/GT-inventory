package com.gtecnologia.GTinventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gtecnologia.GTinventory.dtos.ProductDTO;
import com.gtecnologia.GTinventory.entities.Category;
import com.gtecnologia.GTinventory.entities.Product;
import com.gtecnologia.GTinventory.factory.Factory;
import com.gtecnologia.GTinventory.repositories.CategoryRepository;
import com.gtecnologia.GTinventory.repositories.ProductRepository;
import com.gtecnologia.GTinventory.services.exception.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;

	private Product product;
	private ProductDTO productDTO;
	private PageImpl<Product> page;
	private List<Product> list;
	
	private long existingId;
	private long nonExistingId;

	@BeforeEach
	void setUp() throws Exception {

		product = Factory.createProduct();
		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(product));

		list = new ArrayList<>();
		list.add(product);
		
		existingId = 1L;
		nonExistingId = 3L;

		Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(repository.findAll()).thenReturn(list);
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		Mockito.when(repository.getOne(existingId)).thenReturn(product);
		Mockito.when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

	}

	@Test
	public void findAllPagedShouldReturnPage() {

		Pageable pageable = PageRequest.of(0, 12);

		Page<ProductDTO> result = service.findAllPaged(pageable);

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
	}

	@Test
	public void findAllShouldReturnList() {

		List<ProductDTO> result = service.findAll();

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertNotNull(result);
		Mockito.verify(repository).findAll();
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExist() {
		
		ProductDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Mockito.verify(repository).findById(existingId);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNoExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
		Mockito.verify(repository).findById(nonExistingId);
	}
	
	@Test
	public void insertShouldReturnProductDTO() {

		ProductDTO dto = service.insert(productDTO);

		Assertions.assertNotNull(dto);
		Mockito.verify(repository).save(ArgumentMatchers.any());
	}
	
	@Test
	public void updateShoulReturnProductDTOWhenIdExist() {

		ProductDTO dto = service.update(existingId, productDTO);

	    Assertions.assertEquals(productDTO.getName(), dto.getName());
		Assertions.assertNotNull(dto);
		Mockito.verify(repository).save(ArgumentMatchers.any());
		Mockito.verify(repository).getOne(existingId);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdNoExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			ProductDTO dto = service.update(nonExistingId, productDTO);
		});
	}
}
