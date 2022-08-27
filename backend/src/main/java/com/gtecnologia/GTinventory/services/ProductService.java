package com.gtecnologia.GTinventory.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtecnologia.GTinventory.dtos.CategoryDTO;
import com.gtecnologia.GTinventory.dtos.ProductDTO;
import com.gtecnologia.GTinventory.entities.Category;
import com.gtecnologia.GTinventory.entities.Product;
import com.gtecnologia.GTinventory.repositories.CategoryRepository;
import com.gtecnologia.GTinventory.repositories.ProductRepository;
import com.gtecnologia.GTinventory.services.exception.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {

		List<Product> list = repository.findAll();
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {

		Page<Product> page = repository.findAll(pageable);
		return page.map(x -> new ProductDTO(x));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {

		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado!"));
		return new ProductDTO(entity);
	}

	@Transactional()
	public ProductDTO insert(ProductDTO dto) {
		
		Product entity = new Product();
		copyDtoToEntity(entity, dto);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	
	private void copyDtoToEntity(Product entity, ProductDTO dto) {
		
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.setDate(dto.getDate());
		
		entity.getCategories().clear();
		
		for(CategoryDTO catDto : dto.getCategories()) {
			
			Category category = categoryRepository.getOne(catDto.getId());
			entity.getCategories().add(category);
		}
	}

}
