package com.excilys.cdb.model;

public class User {

	private int id;
	private Role role;
	private String username;
	private String email;
	private String password;
	private boolean enabled;

	public User(int id, Role role, String username, String email, String password, boolean enabled) {
		this.id = id;
		this.role = role;
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
	}

	public User(Role role, String username, String email, String password, boolean enabled) {
		this.role = role;
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}