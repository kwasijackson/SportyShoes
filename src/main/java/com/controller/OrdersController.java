package com.controller;

import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bean.Cart;
import com.bean.Category;
import com.bean.Login;
import com.bean.Orders;
import com.bean.Product;
import com.service.CartService;
import com.service.LoginService;
import com.service.OrdersService;
import com.service.ProductService;
import com.repository.OrdersRepository;

@Controller
public class OrdersController {

	@Autowired
	ProductService productService;
	@Autowired
	OrdersService ordersService;
	@Autowired
	LoginService loginService;
	@Autowired
	CartService cartservice;
	@Autowired
	OrdersRepository ordersRepository;

	
	@RequestMapping(value = "placeOrder/{pid}")
	public String placeOrder(Model mm,@PathVariable("pid") int pid,HttpSession hs,Orders order) {
		System.out.println("Pid is "+pid);
		String emailid = (String)hs.getAttribute("emailid");
		Login user =loginService.getById(emailid);
		Product product = productService.findById(pid);
		order.setLogin(user);
		order.setOrderplaced(LocalDate.now());
		order.setProduct(List.of(product));
		System.out.println("Order is " + order);
		String result = ordersService.placeOrder(order);
		productService.decrementQty(pid);
		List<Product> listOfProducts = productService.findAllProduts();
		mm.addAttribute("products", listOfProducts);
		mm.addAttribute("msg", result);
		return "viewProductsByCustomer";
	}	
	
	@RequestMapping(value = "/ViewAllOrders",method = RequestMethod.GET)
	public String viewAllOrders(Model mm, Orders orders, @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam(name = "end", required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		
		if (startDate != null && endDate != null) {

			List<Orders> listOfOrders  = ordersRepository.findByOrderplacedBetween(startDate, endDate);
			mm.addAttribute("orders", listOfOrders);
		} else { List<Orders> listOfOrders = ordersService.viewAllOrderDetails();
		System.out.println("AllOrders is " + listOfOrders);
		mm.addAttribute("orders", listOfOrders);
				
		}
		
				
		return "ViewAllOrders";
		
		
	}
	
	@RequestMapping(value = "/ViewMyOrders",method = RequestMethod.GET)
	public String viewMyOrders(Model mm, Orders orders,HttpSession hs) {
		String emailid = (String)hs.getAttribute("emailid");
		Login user =loginService.getById(emailid);
		List<Orders> listOfOrders = ordersService.viewMyOrders(user);
		System.out.println("AllOrders is " + listOfOrders);
		mm.addAttribute("orders", listOfOrders);
			
		return "ViewMyOrders";
		
		
	}
	
	@PostMapping("/orders/save/{pid}")
	public String placeOrder1(Model mm,@PathVariable("pid") int pid,HttpSession hs,Orders order) {
		System.out.println("Pid is "+pid);
		if (pid!=0) {
			String emailid = (String)hs.getAttribute("emailid");
			Login user =loginService.getById(emailid);
			Product product = productService.findById(pid);
			order.setLogin(user);
			order.setOrderplaced(LocalDate.now());
			order.setProduct(List.of(product));
			
			order.setQuantity(1);
			order.setOrderAmount(product.getPrice() * order.getQuantity());
			System.out.println("Order is " + order);
			String result = ordersService.placeOrder(order);
			productService.decrementQty(pid);
			List<Product> listOfProducts = productService.findAllProduts();
			mm.addAttribute("products", listOfProducts);
			mm.addAttribute("msg", result);
			return "viewProductsByCustomer";
		}else {
			String emailid = (String)hs.getAttribute("emailid");
			Login user =loginService.getById(emailid);
			List<Cart> cartItems = cartservice.findAllCartByUser(user);
			//mm.addAttribute("products", cartItems);
			List<Product> products = new ArrayList(cartItems.stream().map(x->x.getProduct()).collect(Collectors.toList()));
			float totalPrice = products.stream().map(p -> p.getPrice()).reduce(0.0f, Float::sum);
			mm.addAttribute("totalPrice", totalPrice);
			if(cartItems.size()>0) {
				
			            	System.out.println(products); 
			            	order.setLogin(user);
			        		order.setOrderplaced(LocalDate.now());
			        		order.setProduct(products);
			        		
			        		order.setQuantity(1);
			        		order.setOrderAmount(totalPrice);
			        		System.out.println("Order are " + order);
			        		ordersService.placeOrder(order);
			        		productService.decrementQty(pid);
			        		//product.add(temp.getProduct());
			            
			         		
				
			}
			mm.addAttribute("products", products);
			return "viewProductsByCustomer";
		 }
	} 
}
 

