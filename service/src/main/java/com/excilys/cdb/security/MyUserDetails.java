package com.excilys.cdb.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.User;
import com.excilys.cdb.service.UserService;

@Component
public class MyUserDetails implements UserDetailsService {
	
	private UserService userService;

	public MyUserDetails(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username).get(); //TODO optional
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		UserDetails userDetail = org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(user.getPassword()).authorities(user.getRole().getRole()).build();
		return userDetail;
	}

}
