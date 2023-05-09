package com.example.SmartConnect.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SmartConnect.DAO.UserRepository;
import com.example.SmartConnect.Entities.User;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private	UserRepository userRepository;
	
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
public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,
		@RequestParam(value = "agreement",defaultValue="false")boolean agreement,Model model,
		HttpSession session) {
	
	try {
		
		if(!agreement) {
			System.out.println("You have not agreed the terms and conditons");
			throw new Exception("You have not agreed the terms and conditons");
		}
		
		if(result1.hasErrors()) {
			System.out.println("ERROR" + result1.toString());
			model.addAttribute("user", user);
			return "signup";
		}
		
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		user.setImageUrl("default.png");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		
		System.out.println("Agreement"+agreement);
		System.out.println("USER"+user);
		
		User result = this.userRepository.save(user);
		
		model.addAttribute("user",result);
		
		session.setAttribute("message",new com.example.SmartConnect.Helper.Message("Successfully registered","alert-success"));
		return "signup";
		
	}
	catch(Exception e) {
		e.printStackTrace();
		model.addAttribute("user",user);
		session.setAttribute("message",new com.example.SmartConnect.Helper.Message("Something went wrong!"+e.getMessage(),"alert-danger"));
		return "signup";
		}
 	}


// handler for custom login
@GetMapping("/signin")
public String customLogin(Model model) {
	model.addAttribute("title","Login Page");
	return "login";
}

}