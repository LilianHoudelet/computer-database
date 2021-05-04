package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dto.persistence.CompanyEntity;
import com.excilys.cdb.dto.persistence.UserEntity;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.mapper.MapperUser;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.User;

@Repository
@Transactional
public class UserDAO {
	
	private static final String HQL_ALL_USERS = "FROM UserEntity";
	private static final String HQL_DELETE = "DELETE FROM UserEntity WHERE id=:id;";
	private static final String HQL_FIND_BY_USERNAME = "FROM UserEntity user left join fetch user.roleEntity as role WHERE user.username = :username"; //TODO
	
	private final SessionFactory sessionFactory;
	private final MapperUser mapperUser;

	public UserDAO(SessionFactory sessionFactory, MapperUser mapperUser) {
		this.mapperUser = mapperUser;
		this.sessionFactory = sessionFactory;
	}
	
	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		List<UserEntity> userEntity = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			userEntity = session.createQuery(HQL_ALL_USERS, UserEntity.class).list();
			users = mapperUser.mapToUser(userEntity);
			return users;
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return users;
	}
	
	public Optional<User> findByUsername(String username) throws DAOException {
		Optional<User> user = Optional.empty();
		try {
			Session session = sessionFactory.getCurrentSession();

			Query<UserEntity> query = session.createQuery(HQL_FIND_BY_USERNAME, UserEntity.class);

			query.setParameter("username", username);
			query.setMaxResults(1);
			user = Optional.ofNullable(mapperUser.mapToUser(query.getSingleResult()));
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return user;
	}

	public void create(User user) throws DAOException {
		try {
			Number id = (Number) sessionFactory.getCurrentSession().save(mapperUser.mapToUser(user));
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

	public void deleteById(Long id) throws DAOException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query<?> query = session.createQuery(HQL_DELETE);
			query.setParameter("id", id);
			int statut = query.executeUpdate();
			if (statut == 0) {
				throw new DAOException("User deleting failure, no row altered.");
			}
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
	}

}