package com.gtecnologia.GTinventory.services;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.gtecnologia.GTinventory.dtos.ProductDTO;
import com.gtecnologia.GTinventory.factory.Factory;
import com.gtecnologia.GTinventory.services.exception.ResourceNotFoundException;

@SpringBootTest
public class ProductServiceIT {
	
	@Autowired
	private ProductService service;
	
	private long countTotalProduct;
	private long existingId;
	private long nonExistingId;
	
	private ProductDTO productDTO;
	
	@BeforeEach
	void setUp() throws Exception{
		countTotalProduct = 25L;
		existingId = 1L;
		nonExistingId = 1000L;
		
		productDTO = Factory.createProductDTO();
	}
	
	
	@Test
	public void findALLPagedShouldReturnSortedPagewhenSortByName() {
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		
		Page<ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
	}
	

	@Test
	public void findALLPagedShouldReturnPagewhenPage0Size10() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(countTotalProduct, result.getTotalElements());
	}
	
	@Test
	public void findALLPagedShouldReturnEmptyPagewhenPageDoesNotExist() {
		
		PageRequest pageRequest = PageRequest.of(100, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void findAllShouldReturnList() {
		
		List<ProductDTO> result = service.findAll();
		
		Assertions.assertFalse(result.isEmpty());	
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExist() {
		
		ProductDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(existingId, result.getId());
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNoExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
		
	}
	
	@Test
	public void insertShouldReturnProductDTO() {
		
		ProductDTO dto = service.insert(productDTO);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(productDTO.getName(), dto.getName());
	}	
	
}
