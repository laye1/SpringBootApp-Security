package com.saraya.laptopApp.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.saraya.laptopApp.service.Laptop;
import com.saraya.laptopApp.service.LaptopService;




@Controller
@SessionAttributes("username")
public class LaptopController {

	@Autowired
	LaptopService laptopService;
	Laptop laptop;
	

	
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String welcome(ModelMap model) {
		
		
		return "welcome";
		
	}
	
private String getLoggedInUserName() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails) {
			
			return ((UserDetails) principal).getUsername();
		}
		
		return principal.toString();
		
	}
	
	
	@RequestMapping(value="/list-laptop", method=RequestMethod.GET)
	public String lists(ModelMap model) {
		
		
		model.addAttribute("laptops", laptopService.showAll());
		return "laptops";
	}
	
	@RequestMapping(value="/addlist-laptop", method=RequestMethod.GET)
	public String create() {
		
		return "createLaptops";
	}
	
	@RequestMapping(value="/addlist-laptop", method=RequestMethod.POST)
	public String addtoTDL(ModelMap model1,@RequestParam String mark,@RequestParam String model,@RequestParam String description,@RequestParam int price,
			@RequestParam String picture) {
		
		model1.addAttribute("username", getLoggedInUserName());
		laptopService.addLaptop("username",mark, model,description, price,picture);
		
		return "redirect:/list-laptop";
	}
	
	@RequestMapping(value="/delete-laptop", method=RequestMethod.GET)
	public String deletefromLp(@RequestParam int id, ModelMap model) {
		laptopService.deleteLaptop(id);
		model.clear();
		return "redirect:/list-laptop";
	}
	
   @RequestMapping(value = "/update-laptop", method = RequestMethod.GET)
	public String update(ModelMap model,@RequestParam int id) {
		model.addAttribute("laptop",laptopService.findById(id));
	
		return "updateLaptop";
	}
	
	@RequestMapping(value = "/update-laptop", method = RequestMethod.POST)
	public String updated( ModelMap model, @ModelAttribute("laptop") Laptop laptop) {
		
        
      
		
		laptop.setMark(laptop.getMark());
		laptop.setModel(laptop.getModel());
		laptop.setDescription(laptop.getDescription());
		laptop.setPrice(laptop.getPrice());
		laptop.setPicture(laptop.getPicture());
		laptopService.updateLaptop(laptop);
		model.clear();
		return "redirect:/list-laptop";
	}
	
	
}