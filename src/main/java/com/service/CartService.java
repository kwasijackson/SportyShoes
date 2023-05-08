package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Cart;
import com.bean.Login;
import com.bean.Orders;
import com.bean.Product;
import com.repository.CartRepository;
import com.repository.LoginRepository;
import com.repository.ProductRepository;

@Service
public class CartService {

	
	 @Autowired 
	 private CartRepository cartrepository;

	 @Autowired 
	 private ProductRepository productrepository;

	 @Autowired
	 private  LoginRepository loginrepository;
	 
	 public List<Cart> findAllCartItems() {
			return cartrepository.findAll();
		}

	 public List<Cart> findAllCartByUser(Login login) {
			return cartrepository.findByLogin(login);
		}

	 
	 
	 public String addToCart(Integer productId, String emailId){

		 Product product = productrepository.findById(productId).orElse(null);
		Login user = loginrepository.findById(emailId).orElse(null);
		if(product !=null && user !=null){
			Cart cart = new Cart(product, user);
			 cartrepository.save(cart);
			 return "Added to Cart Successfully";
						}
		   return null;
		   }  
	 
}

