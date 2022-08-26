package com.gtecnologia.GTinventory.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gtecnologia.GTinventory.dtos.ProductDTO;
import com.gtecnologia.GTinventory.entities.Product;
import com.gtecnologia.GTinventory.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {

		List<Product> list = repository.findAll();
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}

}
