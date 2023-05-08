package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Cart;
import com.bean.Category;
import com.bean.Login;
import com.bean.Product;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	CartService cartservice;
	
	public String storeProduct(Product product) {
		productRepository.save(product);
		return "Product details stored successfully";
	}
	
	public List<Product> findAllProduts() {
		return productRepository.findAll();
	}
	
	public List<Product> findProdutsByCategoryId(int Id) {
		Category category = categoryRepository.findById(Id).orElse(null);
		return productRepository.findByCategory(category);
	}
	
	public String deleteProductById(int Id) {
		Product product = productRepository.findById(Id).orElse(null);
		if(product !=null) {
		productRepository.deleteById(Id);
		return "Product Deleted Successfully";
		}
		return null;
	}
	public void decrementQty(int pid) {
		Optional<Product> result = productRepository.findById(pid);
		if(result.isPresent()) {
			Product p = result.get();
			p.setQty(p.getQty()-1);
			productRepository.saveAndFlush(p);	// like update 
		}
	}

	public Product findById(int pid) {
		// TODO Auto-generated method stub
		return productRepository.findById(pid).orElse(null);
	}
	
	public List<Product> findProductById(int pid, Login user) {
		// TODO Auto-generated method stub
		
		
		if(pid!=0) {
	      Product product= productRepository.findById(pid).orElse(null);
	      
	      List<Product> list = new ArrayList<>();
	      list.add(product);
	      return list;
		}else {
			List<Cart> carts = cartservice.findAllCartByUser(user);
			List<Product> list = new ArrayList<>();
			
			//carts.stream().map(x->x.getProduct()).collect(Collectors.toList());
			if(carts.size()>0) {
		/*		carts.forEach(
			            (temp) -> { 
			            	products.add(temp.getProduct());
				            
			            });
				return  products;
		*/
				//carts.stream().map(x->x.getProduct()).collect(Collectors.toList());
				return  new ArrayList<>(carts.stream().map(x->x.getProduct()).collect(Collectors.toList()));
		}
		
	}
		return null;
		


}
}
