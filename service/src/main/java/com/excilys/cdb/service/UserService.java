package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.exception.InvalidOrExpiredTokenException;
import com.excilys.cdb.model.User;
import com.excilys.cdb.security.JwtTokenProvider;

@Service
public class UserService {


	private final UserDAO userDao;

	public UserService(UserDAO userDao) { //, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.userDao = userDao;
	}
	
	public List<User> getAll() {
		return this.userDao.getAll();
	}

	public void create(User user) {
		userDao.create(user);
	}
	
	public Optional<User> findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	public Optional<User> findByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	public void deleteById(Long id) {
		userDao.deleteById(id);
	}

//	public String signin(String username, String password) {
//		try {
////			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
////			return jwtTokenProvider.createToken(username, userDao.findByUsername(username).get().getRole());
//		} catch (AuthenticationException e) {
//			throw new InvalidOrExpiredTokenException("Invalid username/password supplied",
//					HttpStatus.UNPROCESSABLE_ENTITY);
//		}
//	}
	
//	public String signin(String username, String password) {
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//			return jwtTokenProvider.createToken(username, userDao.findByUsername(username).get().getRole());
//		} catch (AuthenticationException e) {
//			throw new InvalidOrExpiredTokenException("Invalid username/password supplied",
//					HttpStatus.UNPROCESSABLE_ENTITY);
//		}
//	}
	
}
