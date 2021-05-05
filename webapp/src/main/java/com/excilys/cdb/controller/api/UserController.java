package com.excilys.cdb.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.mapper.UserMapper;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	public UserController(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}

	@GetMapping
	public List<User> getUsers() {
		return userService.getAll();
	}

	@GetMapping("/{username}")
	public User getUserByUsername(@PathVariable String username) {
		return userService.findByUsername(username).get();
	}

	@GetMapping("/{email}")
	public User getUserByEmail(@PathVariable String email) {
		return userService.findByUsername(email).get();
	}
	
	@GetMapping("/signin")
	public String login() {
		return "signin";
	}

//	@PostMapping("/signin")
//	public String login(@RequestParam String username, @RequestParam String password) {
//		return userService.signin(username, password);
//	}

}
