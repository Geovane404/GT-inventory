package com.gtecnologia.GTinventory.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtecnologia.GTinventory.dtos.ProductDTO;
import com.gtecnologia.GTinventory.factory.Factory;
import com.gtecnologia.GTinventory.services.ProductService;
import com.gtecnologia.GTinventory.services.exception.DatabaseException;
import com.gtecnologia.GTinventory.services.exception.ResourceNotFoundException;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private PageImpl<ProductDTO> page;
	private ProductDTO productDTO;
	private List<ProductDTO> list;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;

	@BeforeEach
	void setUp() throws Exception {
		
		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDTO));
		
		list = new ArrayList<>();
		list.add(productDTO);
		
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		
		Mockito.when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(service.findAll()).thenReturn(list);
		
		Mockito.when(service.findById(existingId)).thenReturn(productDTO);
		Mockito.when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		
		when(service.insert(any())).thenReturn(productDTO);
		when(service.update(eq(existingId), any())).thenReturn(productDTO);
		when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
		
		Mockito.doNothing().when(service).delete(existingId);
		Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
		Mockito.doThrow(DatabaseException.class).when(service).delete(dependentId);

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
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() throws Exception {
		
		ResultActions result = mockMvc.perform(get("/products/{id}", existingId)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());
		result.andExpect(jsonPath("$.price").exists());
	}
	
	@Test 
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		
		ResultActions result = mockMvc.perform(get("/products/{id}", nonExistingId)
				.accept(MediaType.APPLICATION_JSON));
		
			result.andExpect(status().isNotFound());
	}
	
	@Test
	public void  insertShouldReturnStatusCreatedAndProductDTO() throws Exception{
		
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = mockMvc.perform(post("/products/")
				
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());
		result.andExpect(jsonPath("$.price").exists());
		result.andExpect(jsonPath("$.imgUrl").exists());
		result.andExpect(jsonPath("$.date").exists());
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception{
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = mockMvc.perform(put("/products/{id}", existingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());
		result.andExpect(jsonPath("$.price").exists());
	}
	
	@Test
	public void updateShouldNotFoundWhenIdDoesNotExists() throws Exception{
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions result = mockMvc.perform(put("/products/{id}", nonExistingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteShouldReturnNoContentwhenIdExist ()throws Exception {
		
		ResultActions result = mockMvc.perform(delete("/products/{id}", existingId)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFounWhenDoesIdNoExist() throws Exception {
		
		ResultActions result = mockMvc.perform(delete("/products{id}", nonExistingId)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteShouldReturnBadRequestWhenDependentId() throws Exception{
		
		ResultActions result = mockMvc.perform(delete("/products/{id}", dependentId)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isBadRequest());
		
	}

}
