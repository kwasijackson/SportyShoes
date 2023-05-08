package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bean.Category;
import com.bean.Product;
import com.service.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@RequestMapping(value = "/adminHome",method = RequestMethod.GET)
	public String back(Model mm, Category cc) {
		mm.addAttribute("category", cc);
		return "adminHome";
	}
	
	@RequestMapping(value = "/addCategoryPage",method = RequestMethod.GET)
	public String openAddCategoryPage(Model mm, Category cc) {
		mm.addAttribute("category", cc);
		return "addCategory";
	}
	
	
	@RequestMapping(value = "/storeCategoryInfo",method = RequestMethod.POST)
	public String storeAddCategoryPage(Model mm, Category cc) {
		String result = categoryService.storeCategory(cc);
		mm.addAttribute("category", cc);
		mm.addAttribute("msg", result);
		return "addCategory";
	}
	
	
	@RequestMapping(value = "/viewCategoryPage",method = RequestMethod.GET)
	public String viewCategory(Model mm, Category cc) {
		List<Category> listOfCategories = categoryService.findAllCategory();
		mm.addAttribute("category", listOfCategories);
		return "viewCategory";
	}
	
	@GetMapping("/category/delete/{cid}")
	  public String deleteCategory(@PathVariable("cid") Integer cid, Model mm, RedirectAttributes redirectAttributes) {
	    try {
	    	categoryService.deleteCategoryById(cid);
	    	List<Category> listOfCategories = categoryService.findAllCategory();
			mm.addAttribute("category", listOfCategories);
	      redirectAttributes.addFlashAttribute("message", "The Category with id=" + cid + " has been deleted successfully!");
	      return "viewCategory";
	    } catch (Exception e) {
	      redirectAttributes.addFlashAttribute("message", e.getMessage());
	      return "redirect:/viewCategory";
	    }

	   
	  }


@GetMapping("/category/edit/{cid}")
public String editCategory(@PathVariable("cid") Integer cid, Model mm, RedirectAttributes redirectAttributes) {
  try {
  	Category category = categoryService.findById(cid);
  	 	mm.addAttribute("category",category);
     	mm.addAttribute("pageTitle", "Edit Category (ID: " + cid + ")");
     	return "addCategory";
     	
  } catch (Exception e) {
    redirectAttributes.addFlashAttribute("message", e.getMessage());
    return "redirect:/viewCategory";
     }
   }
}