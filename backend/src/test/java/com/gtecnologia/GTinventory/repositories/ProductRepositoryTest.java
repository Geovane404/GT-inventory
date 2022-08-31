package com.gtecnologia.GTinventory.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.gtecnologia.GTinventory.entities.Product;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository repository;
	
	private PageRequest pageRequest;
	private long existingId;
	private long nonExistingId;
	
	@BeforeEach
	void setUp() throws Exception{
		
		pageRequest = PageRequest.of(0, 12);
		existingId = 1L;
		nonExistingId = 1000L;
	}
	
	
	@Test
	public void  findAllShouldReturnPageProduct() {
		
		Page<Product> result = repository.findAll(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void  findAllShouldReturnListProduct() {
		
		List<Product> result = repository.findAll();
		
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void findByIdShouldReturnOptionalNotNullWhenIdExist() {
		
		Optional<Product> result = repository.findById(existingId);
		
		Assertions.assertTrue(result.isPresent());
		Assertions.assertNotEquals(this.existingId, result);
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void findByIdShouldReturnOptionalNullWhenIdNoExist() {
		
		Optional<Product> result = repository.findById(nonExistingId);
		
		Assertions.assertFalse(result.isPresent());
		Assertions.assertTrue(result.isEmpty());
	}
	
}
