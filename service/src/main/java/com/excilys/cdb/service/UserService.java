package com.excilys.cdb.service;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.model.User;

@Service
public class UserService {

	private final UserDAO userDao;

	public UserService(UserDAO userDao) {
		this.userDao = userDao;
	}

	public void create(User user) {
		userDao.create(user);
	}
	
}
