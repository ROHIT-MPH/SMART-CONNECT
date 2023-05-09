package com.example.SmartConnect.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.SmartConnect.DAO.UserRepository;
import com.example.SmartConnect.Entities.Contact;
import com.example.SmartConnect.Entities.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;


	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {

		String userName = principal.getName();
		System.out.println("USERNAME "+ userName);

		// get the user using username(Email)

		User user = userRepository.getUserByUserName(userName);

		System.out.println("USER "+user);

		model.addAttribute("user", user);
	}

	// dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal)
	{	
		model.addAttribute("title", "User Dashboard");
		return "normal/user_dashboard";
	}


	// open add form handler
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {

		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "Normal/Add_Contact_Form";
	}

	//processing add contact form
	@PostMapping("/process-contact")
	public String processContact(
			@ModelAttribute Contact contact,
			@RequestParam("profileImage") MultipartFile file , 
			Principal principal) 
	{

		try {
			String name=principal.getName();
			User user = this.userRepository.getUserByUserName(name);

			//processing and uploading file 
			if(file.isEmpty()) {
				// if the file is empty try our message
			}
			else {
				//upload file to the folder and update the name of contact
				contact.setImage(file.getOriginalFilename());
			
			
			File saveFile = new ClassPathResource("static/image").getFile();
			
			Path path = Paths.get(saveFile.getAbsolutePath()+File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
			System.out.println("image is uploaded");
			}
			
			user.getContacts().add(contact);

			contact.setUser(user);


			this.userRepository.save(user);

			System.out.println("DATA " + contact);

			System.out.println("Added to data base");
		}

		catch (Exception e) {
			System.out.println("ERROR"+e.getMessage());
			e.printStackTrace();
		}

		return "Normal/Add_Contact_Form";
	}


}
