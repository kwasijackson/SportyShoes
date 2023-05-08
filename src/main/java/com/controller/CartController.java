package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bean.Cart;
import com.bean.Login;
import com.bean.Orders;
import com.bean.Product;
import com.repository.CartRepository;
import com.service.CartService;
import com.service.LoginService;
import com.service.OrdersService;
import com.service.ProductService;

@Controller
public class CartController {
	@Autowired
	ProductService productService;
	@Autowired
	OrdersService ordersService;
	@Autowired
	LoginService loginService;
	@Autowired
	CartService cartservice;
	
	@Autowired
	CartRepository cartRepository;
	
	@RequestMapping(value = "/addToCart/{pid}")
	public String toCart(Model mm,@PathVariable("pid") int pid,HttpSession hs,Cart cart) {
		System.out.println("Pid is "+pid);
		String emailid = (String)hs.getAttribute("emailid");
		//Login user =loginService.getById(emailid);
		String result =cartservice.addToCart(pid, emailid);
		List<Cart> cartItems = cartservice.findAllCartItems();
		mm.addAttribute("products", cartItems);
		mm.addAttribute("msg", result);

		return "redirect:/viewProductDetailsByCustomer";
}
	
	
	@RequestMapping(value = "/myCart")
	public String myCart(Model mm, HttpSession hs,Cart cart) {
		
		String emailid = (String)hs.getAttribute("emailid");
		Login user =loginService.getById(emailid);
		
		List<Cart> cartItems = cartservice.findAllCartByUser(user);
		mm.addAttribute("products", cartItems);
	

		return "myCart";
}
	
	@RequestMapping(value = "/checkout/{pid}")
	public String checkout(Model mm, HttpSession hs,Cart cart) {
		
		String emailid = (String)hs.getAttribute("emailid");
		Login user =loginService.getById(emailid);
		
		List<Cart> cartItems = cartservice.findAllCartByUser(user);
		mm.addAttribute("products", cartItems);
		List<Product> products = new ArrayList<Product>();
		if(cartItems.size()>0) {
			cartItems.forEach(
		            (temp) -> { 
		            	products.add(temp.getProduct());
			            
		            });
			mm.addAttribute("products", products);
		            }

		return "buyNow";
}
	
	@GetMapping("/cart/delete/{cartId}")
	  public String deleteTutorial(@PathVariable("cartId") Integer cartId, Model mm, RedirectAttributes redirectAttributes) {
	    try {
	    	cartRepository.deleteById(cartId);

	      redirectAttributes.addFlashAttribute("message", "The Cart with id=" + cartId + " has been deleted successfully!");
	    } catch (Exception e) {
	      redirectAttributes.addFlashAttribute("message", e.getMessage());
	    }

	    return "redirect:/myCart";
	  }
}
