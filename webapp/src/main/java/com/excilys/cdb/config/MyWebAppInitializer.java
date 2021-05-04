package com.excilys.cdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.excilys.cdb.persistance.config.HibernateConfig;
import com.excilys.cdb.security.config.SpringSecurityConfig;

@Configuration
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { HibernateConfig.class, SpringSecurityConfig.class };

	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { MyWebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
