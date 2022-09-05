package com.gtecnologia.GTinventory.factory;

import java.time.Instant;

import com.gtecnologia.GTinventory.dtos.ProductDTO;
import com.gtecnologia.GTinventory.entities.Category;
import com.gtecnologia.GTinventory.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		
		Product product = new Product(1L, "Phone", "Good Phone", 
				800.0, "https://img.com//img.png",
				Instant.parse("2022-09-01T03:00:00Z"));
		
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static Category createCategory() {
		
		return new Category(1L, "Eletronics");
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product);
	}
}
