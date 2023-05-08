package com.controller;

import java.util.ArrayList;
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

import com.bean.Login;

import com.repository.LoginRepository;
import com.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	LoginService loginService;
	@Autowired
	LoginRepository loginRepository;
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String open(Model mm, Login ll) {
		mm.addAttribute("login", ll);
		return "index";
	}
	
	@RequestMapping(value = "/openSignUp",method = RequestMethod.GET)
	public String openSignUpPage(Model mm, Login ll) {
		mm.addAttribute("login", ll);
		return "signUp";
	}
	
	@RequestMapping(value = "/signIn",method = RequestMethod.POST)
	public String signIn(Model mm, Login ll,HttpSession hs) {
		String result = loginService.signIn(ll);
		
		if(result.equals("Customer login successfully")) {
			hs.setAttribute("emailid", ll.getEmailid());		// stored session object of that person 
			return "customerHome";
		}else if(result.equals("Admin login successfully")) {
			return "adminHome";
		}else {
			return "index";
		}
	
	}
	
	
	@RequestMapping(value = "/signUp",method = RequestMethod.POST)
	public String signUp(Model mm, Login ll) {
		String result = loginService.signUp(ll);
		mm.addAttribute("login", ll);
		System.out.println(result);

		return "index";

	}
	 @GetMapping("/ViewUsers")
     public String getAll(Model model, @Param("keyword") String keyword) {
       try {
         List<Login> login = new ArrayList<Login>();

         if (keyword == null) {
        	 loginRepository.findAll().forEach(login::add);
         } else {
        	 loginRepository.findByEmailidContainingIgnoreCase(keyword).forEach(login::add);
           model.addAttribute("keyword", keyword);
         }
        
         model.addAttribute("login", login);
       } catch (Exception e) {
         model.addAttribute("message", e.getMessage());
       }

       return "ViewUsers";
     }
	 
	 @GetMapping("/login/delete/{emailid}")
	  public String deleteTutorial(@PathVariable("emailid") String emailid, Model model, RedirectAttributes redirectAttributes) {
	    try {
	    	loginRepository.deleteById(emailid);

	      redirectAttributes.addFlashAttribute("message", "The User with id=" + emailid + " has been deleted successfully!");
	    } catch (Exception e) {
	      redirectAttributes.addFlashAttribute("message", e.getMessage());
	    }

	    return "redirect:/ViewUsers";
	  }
	 @GetMapping("/login/{emailid}")
	  public String editTutorial(@PathVariable("emailid") String emailid, Model model, RedirectAttributes redirectAttributes) {
		  try {
		      Login login = loginRepository.findById(emailid).get();

		      model.addAttribute("login", login);
		      model.addAttribute("pageTitle", "Edit User (ID: " + emailid + ")");

		      return "signUp";
		    } catch (Exception e) {
		      redirectAttributes.addFlashAttribute("message", e.getMessage());

		      return "redirect:/ViewUsers";
		    }
		  }

	
}
