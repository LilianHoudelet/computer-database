package com.excilys.cdb.persistance.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.cdb.logger.LoggerCdb;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.excilys.cdb.dao", "com.excilys.cdb.config" })
public class HibernateConfig {
	
	private static final String PROP_DB = "/config/datasource.properties";
	private static final String PROP_PERSISTENCE = "/config/persistence.properties";

	@Bean("dataSource")
	public HikariDataSource getDataSource() {
		return new HikariDataSource(new HikariConfig(PROP_DB));
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());
		factoryBean.setPackagesToScan("com.excilys.cdb.dto");
		factoryBean.setHibernateProperties(hibernateProperties());
		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}
	
	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		try (InputStream in = this.getClass().getResourceAsStream(PROP_PERSISTENCE)) {
			hibernateProperties.load(in);
		} catch (IOException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return hibernateProperties;
	}
}
