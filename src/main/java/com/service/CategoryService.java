package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Category;
import com.bean.Product;
import com.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	public String storeCategory(Category category) {
		categoryRepository.save(category);
		return "Category details stored";
	}
	
	public List<Category> findAllCategory() {
		return categoryRepository.findAll();
	}
	
	public String deleteCategoryById(int Id) {
		Category category = categoryRepository.findById(Id).orElse(null);
		if(category !=null) {
		categoryRepository.deleteById(Id);
		return "Category Deleted Successfully";
		}
		return null;
		
	}
	
	public Category findById(int cid) {
		// TODO Auto-generated method stub
		return categoryRepository.findById(cid).orElse(null);
	}
}
