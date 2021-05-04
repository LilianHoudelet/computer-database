package com.excilys.cdb.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan({ "com.excilys.cdb.security.config" })
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("SELECT username, password, enabled FROM users where username = ?")
				.authoritiesByUsernameQuery(
						"SELECT users.username, roles.role FROM users LEFT JOIN roles ON users.role_id = roles.id WHERE users.username = ?");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/api", "/register").hasAnyRole("ADMIN")
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/api/computer")
				.failureUrl("/login?error=true").permitAll().and().logout().logoutSuccessUrl("/login?logout=true")
				.invalidateHttpSession(true).and().httpBasic().realmName("realm")
				.authenticationEntryPoint(authenticationEntryPoint).and().csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}