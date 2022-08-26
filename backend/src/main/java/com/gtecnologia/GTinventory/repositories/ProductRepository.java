package com.gtecnologia.GTinventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gtecnologia.GTinventory.entities.Category;
import com.gtecnologia.GTinventory.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
