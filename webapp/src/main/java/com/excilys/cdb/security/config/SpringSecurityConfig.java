package com.excilys.cdb.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.excilys.cdb.security.JwtTokenFilter;
import com.excilys.cdb.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan({ "com.excilys.cdb.security.config" })
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("SELECT username, password, enabled FROM users where username = ?")
				.authoritiesByUsernameQuery(
						"SELECT users.username, roles.role FROM users LEFT JOIN roles ON users.role_id = roles.id WHERE users.username = ?");
	}

//	@Autowired
//	JwtTokenProvider jwtTokenProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()//
				.antMatchers("/login").permitAll() // "/api/**",
//				.mvcMatchers("/users/signup").permitAll()
				.antMatchers("/api**", "/signup").hasRole("ADMIN")
				// Disallow everything else.. .anyRequest()
				.antMatchers("/login").authenticated().and().formLogin().loginPage("/api/computer")
				.defaultSuccessUrl("/api/users", false);
		http.exceptionHandling().accessDeniedPage("/users/signin");
//		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
		
//		JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
//		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);

		// Optional, if you want to test the API from a browser
//		http.httpBasic().realmName("realm").authenticationEntryPoint(authenticationEntryPoint);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}

}
