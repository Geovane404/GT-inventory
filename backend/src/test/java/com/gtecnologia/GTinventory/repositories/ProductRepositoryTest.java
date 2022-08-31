package com.gtecnologia.GTinventory.repositories;

import java.util.List;

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
	
	@BeforeEach
	void setUp() throws Exception{
		
		pageRequest = PageRequest.of(0, 12);
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
	
}
