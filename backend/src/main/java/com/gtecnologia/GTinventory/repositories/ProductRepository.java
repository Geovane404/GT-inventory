package com.gtecnologia.GTinventory.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gtecnologia.GTinventory.entities.Category;
import com.gtecnologia.GTinventory.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT obj FROM Product obj INNER JOIN obj.categories cats WHERE "
			+ ":category IN cats")
	Page<Product> find(Category category, Pageable pageable);
}
