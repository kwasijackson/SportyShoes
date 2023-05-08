package com.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;
	@OneToOne
	private Product product;
	@OneToOne
	private Login login;
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
	}
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Cart(Product product, Login login) {
		super();
		this.product = product;
		this.login = login;
	}
	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", product=" + product + ", login=" + login + "]";
	} 
	
	
	

}
