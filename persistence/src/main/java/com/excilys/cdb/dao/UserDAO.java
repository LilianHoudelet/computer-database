package com.excilys.cdb.dao;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.mapper.MapperUser;
import com.excilys.cdb.model.User;

@Repository
public class UserDAO {
	
	private final SessionFactory sessionFactory;
	private final MapperUser mapperUser;

	public UserDAO(SessionFactory sessionFactory, MapperUser mapperUser) {
		this.mapperUser = mapperUser;
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public void create(User user) throws DAOException {
		try {
			Number id = (Number) sessionFactory.getCurrentSession().save(mapperUser.mapFromModelToDTOPersistance(user));
			if (id != null) {
				user.setId(id.intValue());
			} else {
				throw new DAOException("No user created on the database, no generated ID returned.");
			}
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		} catch (DAOException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
	}
	
}