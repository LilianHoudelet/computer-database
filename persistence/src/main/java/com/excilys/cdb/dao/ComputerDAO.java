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

import com.excilys.cdb.dto.persistence.ComputerEntity;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Repository
@Transactional
public class ComputerDAO {

	private final SessionFactory sessionFactory;
	private final MapperComputer mapperComputer;

	private static final String HQL_UPDATE = "UPDATE ComputerEntity SET name=:name, introduced=:introduced, discontinued=:discontinued, companyEntity.id=:companyId WHERE id=:id";
	private static final String HQL_DELETE = "DELETE FROM ComputerEntity WHERE id=:id";
	private static final String HQL_SELECT = "FROM ComputerEntity computer left join fetch computer.companyEntity as company WHERE computer.id = :id";
	private static final String HQL_ALL_COMPUTER = "FROM ComputerEntity ORDER BY id";
	private static final String HQL_ALL_COMPUTER_PAGINATION = "FROM ComputerEntity as computer ORDER BY ORDERATTRIBUTE ORDERSORT  ";
	private static final String HQL_COUNT_ALL_COMPUTER = "SELECT COUNT(id) FROM ComputerEntity ";
//	private static final String SQL_SEARCH_BY_NAME_COMPA_COMPU = "SELECT New com.excilys.cdb.dto.ComputerDTOPersistence(computer.id, computer.name, computer.introduced, computer.discontinued, computer.companyDTOPersistence)  FROM ComputerDTOPersistence computer left join computer.companyDTOPersistence as company WHERE lower(computer.name) LIKE :nameComputer OR lower(company.name) LIKE :nameCompany ORDER BY ORDERATTRIBUTE ORDERSORT ";
	private static final String HQL_SEARCH_BY_NAME_COMPA_COMPU = "FROM ComputerEntity computer left join fetch computer.companyEntity as company WHERE lower(computer.name) LIKE :nameComputer OR lower(company.name) LIKE :nameCompany ORDER BY ORDERATTRIBUTE ORDERSORT ";
	private static final String HQL_SEARCH_BY_NAME_COUNT = "SELECT COUNT(computer.id) FROM ComputerEntity computer left join computer.companyEntity  company WHERE lower(computer.name) LIKE :nameComputer OR lower(company.name) LIKE :nameCompany ";

	public ComputerDAO(SessionFactory sessionFactory, MapperComputer mapperComputer) {
		this.mapperComputer = mapperComputer;
		this.sessionFactory = sessionFactory;
	}
	
	public void create(Computer computer) throws DAOException {
		try {
			Number id = (Number) sessionFactory.getCurrentSession()
					.save(mapperComputer.mapFromModelToDTOPersistence(computer));
			if (id != null) {
				computer.setId(id.longValue());
			} else {
				throw new DAOException("No computer created on the database, no generated ID returned.");
			}

//		} catch (HibernateException e) {
//			if (SQLIntegrityConstraintViolationException.class == e.getCause().getClass()) {
//				throw new DAOException("An error occured because the company doesn't exist.");
//			} else if (MysqlDataTruncation.class == e.getCause().getClass()) {
//				throw new DAOException("An error occured because date cannot be bellow 1970-01-01.");
//			} else {
//				LoggerCdb.logError(this.getClass(), e);
//			}
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		} catch (DAOException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
	}
		
	public void update(Computer computer) throws DAOException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query<?> query = session.createQuery(HQL_UPDATE);
			query.setParameter("id", computer.getId());
			query.setParameter("introduced", computer.getIntroduced());
			query.setParameter("discontinued", computer.getDiscontinued());
			query.setParameter("name", computer.getName());
			query.setParameter("companyId", computer.getCompany() != null ? computer.getCompany().getId() : null);
			int statut = query.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Update computer failure.");
			}
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		} catch (DAOException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
	}
	
	public void delete(Long id) throws DAOException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query<?> query = session.createQuery(HQL_DELETE);
			query.setParameter("id", id);
			int statut = query.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Computer deleting failure, no row added to the table.");
			}
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
	}
	
	public Optional<Computer> search(Long id) throws DAOException {
		Optional<Computer> computer = Optional.empty();
		try {
			Session session = sessionFactory.getCurrentSession();

			Query<ComputerEntity> query = session.createQuery(HQL_SELECT, ComputerEntity.class);

			query.setParameter("id", id);
			query.setMaxResults(1);
			computer = Optional.ofNullable(mapperComputer.mapFromDTOPersistenceToModel(query.getSingleResult()));
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return computer;
	}
		
	public List<Computer> searchAll() {
		List<Computer> computers = new ArrayList<>();
		List<ComputerEntity> computersDTO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			computersDTO = session.createQuery(HQL_ALL_COMPUTER, ComputerEntity.class).list();
			computers = mapperComputer.mapFromListDTOPersistenceToListModel(computersDTO);
			return computers;
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return computers;
	}
	
	public List<Computer> searchAllPagination(Page<Computer> page) {
		List<Computer> computers = new ArrayList<>();
		List<ComputerEntity> computersDTO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query<ComputerEntity> query = session.createQuery(HQL_ALL_COMPUTER_PAGINATION
					.replace("ORDERATTRIBUTE", page.getOrderAttribute()).replace("ORDERSORT", page.getOrderSort()),
					ComputerEntity.class);
			query.setFirstResult(page.getObjetPerPage() * page.getPageInt());
			query.setMaxResults(page.getObjetPerPage());
			computersDTO = query.list();
			computers = mapperComputer.mapFromListDTOPersistenceToListModel(computersDTO);
			return computers;
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		} catch (java.lang.IllegalArgumentException e) {
			LoggerCdb.logError(this.getClass(), e, "Page start at index 1 and not 0");
		}
		return computers;
	}

	public int searchAllCount() {
		int nbComputer = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			nbComputer = session.createQuery(HQL_COUNT_ALL_COMPUTER, Long.class).uniqueResult().intValue();
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return nbComputer;
	}

	
	public int searchNameCount(String name) {
		int nbComputer = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query<Long> query = session.createQuery(HQL_SEARCH_BY_NAME_COUNT, Long.class);
			query.setParameter("nameComputer", "%" + name + "%");
			query.setParameter("nameCompany", "%" + name + "%");
			nbComputer = query.uniqueResult().intValue();
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return nbComputer;
	}
	
	public List<Computer> searchNamePagination(Page<Computer> page, String name) throws DAOException {
		List<Computer> computers = new ArrayList<>();
		List<ComputerEntity> computersDTO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query<ComputerEntity> query = session.createQuery(HQL_SEARCH_BY_NAME_COMPA_COMPU

					.replace("ORDERATTRIBUTE", page.getOrderAttribute()).replace("ORDERSORT", page.getOrderSort()),
					ComputerEntity.class);
			query.setFirstResult(page.getObjetPerPage() * page.getPageInt());
			query.setMaxResults(page.getObjetPerPage());
			query.setParameter("nameComputer", "%" + name.toLowerCase() + "%");
			query.setParameter("nameCompany", "%" + name.toLowerCase() + "%");
			computersDTO = query.list();
			computers = mapperComputer.mapFromListDTOPersistenceToListModel(computersDTO);
			return computers;
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return computers;
	}

}
