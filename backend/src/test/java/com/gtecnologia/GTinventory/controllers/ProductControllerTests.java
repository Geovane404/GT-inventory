package com.gtecnologia.GTinventory.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.gtecnologia.GTinventory.dtos.ProductDTO;
import com.gtecnologia.GTinventory.factory.Factory;
import com.gtecnologia.GTinventory.services.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;
	
	private PageImpl<ProductDTO> page;
	private ProductDTO productDTO;
	private List<ProductDTO> list;

	@BeforeEach
	void setUp() throws Exception {
		
		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDTO));
		
		list = new ArrayList<>();
		list.add(productDTO);
		
		Mockito.when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(service.findAll()).thenReturn(list);
	}

	@Test
	public void findAllPagedShouldReturnPage() throws Exception {

		ResultActions result = mockMvc.perform(get("/products/paged")
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void findAllShouldReturnList() throws Exception {

		ResultActions result = mockMvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

}
