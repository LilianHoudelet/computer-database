package com.excilys.cdb.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.service.UserService;

@Controller
public class Login {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserService userService;

	@PostMapping(value = "/login")
	public String loginPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		String errorMessge = null;
		if (error != null) {
			errorMessge = "Username or Password is incorrect !!";
		}
		if (logout != null) {
			errorMessge = "You have been successfully logged out !!";
		}
		model.addAttribute("errorMessge", errorMessge);
		return "login";
	}

	@GetMapping(value = "/login")
	public String loginPage(Model model) {
		return "login";
	}

	@GetMapping(value = "/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout=true";
	}

//	@GetMapping("/register")
//	public void doRegister() {
//		String encodedPassword = passwordEncoder.encode("network");
//		User user = new User("ROLE_USER", "user", encodedPassword, true);
//		userService.create(user);
//		User admin = new User("ROLE_ADMIN", "admin", encodedPassword, true);
//		userService.create(admin);
//	}
	
}