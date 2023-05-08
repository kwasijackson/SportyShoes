package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bean.Category;
import com.bean.Login;
import com.bean.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	//List<Product> findByCategoryId(int Id);
	List<Product> findByCategory(Category category);
	
	List<Product> findByPnameContainingIgnoreCase(String keyword);
}
