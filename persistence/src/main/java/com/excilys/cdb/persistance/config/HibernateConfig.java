package com.excilys.cdb.persistance.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.excilys.cdb.dao", "com.excilys.cdb.binding.config" })
public class HibernateConfig {

	@Bean("dataSource")
	public HikariDataSource getDataSource() {
		return new HikariDataSource(new HikariConfig("/com/excilys/cdb/dao/datasource.properties"));
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());
		factoryBean.setPackagesToScan("com.excilys.cdb.dto");
//		Properties hibernateProperties = new Properties();
//		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//		factoryBean.setHibernateProperties(hibernateProperties);
		factoryBean.setHibernateProperties(
				(Properties) new Properties().setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect"));
		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}

}
