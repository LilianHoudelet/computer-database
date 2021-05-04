package com.excilys.cdb.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.mapper.MapperUser;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	//TODO Add web DTO to replace object
	
	private final UserService userService;
	private final MapperUser userMapper;
	
	public UserController(UserService userService, MapperUser userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}
	
	@GetMapping
	public List<User> getUsers() {
		return userService.getAll();
	}
	
	@GetMapping("/{username}")
	public User getUserByName(@PathVariable String username) {
		return userService.findByUsername(username).get();
	}

}
