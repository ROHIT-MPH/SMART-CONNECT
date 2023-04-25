package com.example.SmartConnect.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SmartConnect.Entities.User;



@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home(Model model) 
	{
		model.addAttribute("title","Home - SmartConnect");
		return "Home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) 
	{
		model.addAttribute("title","About - SmartConnect");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) 
	{
		model.addAttribute("title","Register - SmartConnect");
		model.addAttribute("user",new User());
		return "Signup";
	}


// handler for registering user
@RequestMapping(value="/do_register",method=RequestMethod.POST)
public String registerUser(@ModelAttribute("user") User user,@RequestParam(value = "agreement",
defaultValue="false")boolean agreement,Model model) {
	
	if(!agreement) {
		System.out.println("You have not agreed the terms and conditons");
	}
	user.setRole("ROLE_USER");
	user.setEnabled(true);
	
	
	
	System.out.println("Agreement"+agreement);
	System.out.println("USER"+user);
	
	model.addAttribute("user",user);
	return "signup";
}

}
