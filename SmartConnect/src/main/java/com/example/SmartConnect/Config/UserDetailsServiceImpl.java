package com.example.SmartConnect.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.SmartConnect.DAO.UserRepository;
import com.example.SmartConnect.Entities.User;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetching user from database
		
		User user = userRepository.getUserByUserName(username);
		
		if(user==null) {
			
			throw new UsernameNotFoundException("Could not find user!!");
		}
		
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		
		return customUserDetails;
	} 

}
