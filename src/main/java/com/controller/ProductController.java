package com.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bean.Category;
import com.bean.Login;
import com.bean.Orders;
import com.bean.Product;
import com.repository.ProductRepository;
import com.service.CategoryService;
import com.service.ProductService;
import com.service.LoginService;

@Controller
public class ProductController {

	
	
	@Autowired
	ProductService productService;
	@Autowired 
	CategoryService categoryService;
	@Autowired
	LoginService loginService;
	@Autowired
	ProductRepository productRepository;
	
	
	@RequestMapping(value = "/addProductPage",method = RequestMethod.GET)
	public String openAddProductPage(Model mm, Product pp) {
			mm.addAttribute("product", pp);
		List<Category> listOfCategory = categoryService.findAllCategory();
			mm.addAttribute("category",listOfCategory);
		return "addProduct";
	}
	 @GetMapping("/product/edit/{pid}")
	  public String editProduct(@PathVariable("pid") Integer pid, Model mm, RedirectAttributes redirectAttributes) {
	    try {
	    	Product product = productService.findById(pid);
	    	mm.addAttribute("product", product);
	    	List<Category> listOfCategory = categoryService.findAllCategory();
			mm.addAttribute("category",listOfCategory);
           	mm.addAttribute("pageTitle", "Edit Product (ID: " + pid + ")");
           	return "addProduct";
           	
	    } catch (Exception e) {
	      redirectAttributes.addFlashAttribute("message", e.getMessage());
	      return "redirect:/ViewProducts";
	    }
	  }

	 @GetMapping("/product/delete/{pid}")
	  public String deleteProduct(@PathVariable("pid") Integer pid, Model model, RedirectAttributes redirectAttributes) {
	    try {
	    	productService.deleteProductById(pid);

	      redirectAttributes.addFlashAttribute("message", "The Prouct with id=" + pid + " has been deleted successfully!");
	    } catch (Exception e) {
	      redirectAttributes.addFlashAttribute("message", e.getMessage());
	    }

	    return "redirect:/ViewProducts";
	  }
	
	
	@RequestMapping(value = "/storeProductInfo",method = RequestMethod.POST)
	public String storeProductData(Model mm,Product pp) {
		String result = productService.storeProduct(pp);
		mm.addAttribute("product", pp);
		mm.addAttribute("msg", result);
		List<Category> listOfCategory = categoryService.findAllCategory();
		mm.addAttribute("category",listOfCategory);
		System.out.println(pp);  
		return "addProduct";
	}
	
	
	@RequestMapping(value = "/viewProductDetailsByCustomer",method = RequestMethod.GET)
	public String viewProduct(Model mm, Product pp,@Param("keyword") String keyword) {
		if (keyword == null) {
			List<Product> listOfProducts = productService.findAllProduts();
			List<Category> category = categoryService.findAllCategory();
			mm.addAttribute("products", listOfProducts);
			mm.addAttribute("category", category);
		return "viewProductsByCustomer";
		} else {
			List<Product> listOfProducts = productRepository.findByPnameContainingIgnoreCase(keyword);
			List<Category> category = categoryService.findAllCategory();
			mm.addAttribute("products", listOfProducts);
			mm.addAttribute("category", category);
			mm.addAttribute("keyword", keyword);
			return "viewProductsByCustomer";
		}
	}
	
	
	@RequestMapping(value = "/ViewProducts",method = RequestMethod.GET)
	public String viewProducts(Model mm, Product pp, Category cc,@Param("keyword") String keyword) {
		if (keyword == null) {
		List<Product> listOfProducts = productService.findAllProduts();
		List<Category> category = categoryService.findAllCategory();
		mm.addAttribute("products", listOfProducts);
		mm.addAttribute("category", category);
		
		return "ViewProducts";
		} else {
			List<Product> listOfProducts = productRepository.findByPnameContainingIgnoreCase(keyword);
			List<Category> category = categoryService.findAllCategory();
			mm.addAttribute("products", listOfProducts);
			mm.addAttribute("category", category);
			mm.addAttribute("keyword", keyword);
			return "ViewProducts";
		}
	}
	
	
	@GetMapping("/buyNow/{pid}")
	public String byNow(Model mm,@PathVariable("pid") int pid,HttpSession hs) {
		System.out.println("Pid is "+pid);
		String emailid = (String)hs.getAttribute("emailid");
		Login user =loginService.getById(emailid);
        Orders orders =  new Orders();
        mm.addAttribute("orders", orders);
		List<Product> listOfProducts = productService.findProductById(pid, user);
		float totalPrice = listOfProducts.stream().map(p -> p.getPrice()).reduce(0.0f, Float::sum);
		mm.addAttribute("totalPrice", totalPrice);
		System.out.println("list is " +listOfProducts);
		 mm.addAttribute("products", listOfProducts);
		 System.out.println(listOfProducts);
		
		return "buyNow";
	
		
	}
	
	@GetMapping("/category/{Id}")
	  public String editTutorial(@PathVariable("Id") Integer Id, Model mm, RedirectAttributes redirectAttributes) {
		  try {
			  List<Product> listOfProducts = productService.findProdutsByCategoryId(Id);

		      mm.addAttribute("products", listOfProducts);
		      

		      return "ViewProducts";
		    } catch (Exception e) {
		      redirectAttributes.addFlashAttribute("message", e.getMessage());

		      return "redirect:/ViewProducts";
		    }
		  }
}
