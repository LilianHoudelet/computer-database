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

import com.excilys.cdb.dto.persistence.ComputerDTOPersistence;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Repository
public class ComputerDAOImpl {

	private SessionFactory sessionFactory;

	private MapperComputer mapperComputer;

	public ComputerDAOImpl(SessionFactory sessionFactory, MapperComputer mapperComputer) {

		this.mapperComputer = mapperComputer;
		this.sessionFactory = sessionFactory;

	}

	private static final String SQL_UPDATE = "UPDATE ComputerDTOPersistance SET name=:name, introduced=:introduced, discontinued=:discontinued, companyDTOPersistance.id=:companyId WHERE id=:id";
	private static final String SQL_DELETE = "DELETE FROM ComputerDTOPersistance WHERE id=:id";
	private static final String SQL_SELECT = "FROM ComputerDTOPersistance computer left join fetch computer.companyDTOPersistance as company WHERE computer.id = :id";
	private static final String SQL_ALL_COMPUTER = "FROM ComputerDTOPersistance ORDER BY id";
	private static final String SQL_ALL_COMPUTER_PAGINATION = "FROM ComputerDTOPersistance as computer ORDER BY ORDERATTRIBUTE ORDERSORT  ";
	private static final String SQL_COUNT_ALL_COMPUTER = "SELECT COUNT(id) FROM ComputerDTOPersistance ";
//	private static final String SQL_SEARCH_BY_NAME_COMPA_COMPU = "SELECT New com.excilys.cdb.dto.ComputerDTOPersistance(computer.id, computer.name, computer.introduced, computer.discontinued, computer.companyDTOPersistance)  FROM ComputerDTOPersistance computer left join computer.companyDTOPersistance as company WHERE lower(computer.name) LIKE :nameComputer OR lower(company.name) LIKE :nameCompany ORDER BY ORDERATTRIBUTE ORDERSORT ";
	private static final String SQL_SEARCH_BY_NAME_COMPA_COMPU = "FROM ComputerDTOPersistance computer left join fetch computer.companyDTOPersistance as company WHERE lower(computer.name) LIKE :nameComputer OR lower(company.name) LIKE :nameCompany ORDER BY ORDERATTRIBUTE ORDERSORT ";
	private static final String SQL_SEARCH_BY_NAME_COUNT = "SELECT COUNT(computer.id) FROM ComputerDTOPersistance computer left join computer.companyDTOPersistance  company WHERE lower(computer.name) LIKE :nameComputer OR lower(company.name) LIKE :nameCompany ";

	@Transactional
	public void create(Computer computer) throws DAOException {
		try {
			Number id = (Number) sessionFactory.getCurrentSession()
					.save(mapperComputer.mapFromModelToDTOPersistance(computer));
			if (id != null) {
				computer.setId(id.longValue());
			} else {
				throw new DAOException("Échec de la création de l'ordinateur en base, aucun ID auto-généré retourné.");
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

	@Transactional
	public void update(Computer computer) throws DAOException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query<?> query = session.createQuery(SQL_UPDATE);
			query.setParameter("id", computer.getId());
			query.setParameter("introduced", computer.getIntroduced());
			query.setParameter("discontinued", computer.getDiscontinued());
			query.setParameter("name", computer.getName());
			query.setParameter("companyId", computer.getCompany() != null ? computer.getCompany().getId() : null);
			int statut = query.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Échec de la modification de l'ordinateur.");
			}
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		} catch (DAOException e) {
			LoggerCdb.logError(this.getClass(), e);
		}

	}

	@Transactional
	public void delete(Long id) throws DAOException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query<?> query = session.createQuery(SQL_DELETE);
			query.setParameter("id", id);
			int statut = query.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Échec de la suppression de l'ordinateur, aucune ligne ajoutée dans la table.");
			}
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
	}

	@Transactional
	public Optional<Computer> search(Long id) throws DAOException {
		Optional<Computer> computer = Optional.empty();

		try {
			Session session = sessionFactory.getCurrentSession();
			Query<ComputerDTOPersistence> query = session.createQuery(SQL_SELECT, ComputerDTOPersistence.class);
			query.setParameter("id", id);
			query.setMaxResults(1);
			computer = Optional.ofNullable(mapperComputer.mapFromDTOPersistanceToModel(query.getSingleResult()));
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}

		return computer;
	}

	@Transactional
	public List<Computer> searchAll() {
		List<Computer> computers = new ArrayList<>();
		List<ComputerDTOPersistence> computersDTO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			computersDTO = session.createQuery(SQL_ALL_COMPUTER, ComputerDTOPersistence.class).list();
			computers = mapperComputer.mapFromListDTOPersistanceToListModel(computersDTO);
			return computers;
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return computers;
	}

	@Transactional
	public List<Computer> searchAllPagination(Page<Computer> page) {
		List<Computer> computers = new ArrayList<>();
		List<ComputerDTOPersistence> computersDTO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query<ComputerDTOPersistence> query = session.createQuery(SQL_ALL_COMPUTER_PAGINATION
					.replace("ORDERATTRIBUTE", page.getOrderAttribute()).replace("ORDERSORT", page.getOrderSort()),
					ComputerDTOPersistence.class);
			query.setFirstResult(page.getObjetPerPage() * page.getPageInt());
			query.setMaxResults(page.getObjetPerPage());
			computersDTO = query.list();
			computers = mapperComputer.mapFromListDTOPersistanceToListModel(computersDTO);
			return computers;
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		} catch (java.lang.IllegalArgumentException e) {
			LoggerCdb.logError(this.getClass(), e, "Page start at index 1 and not 0");
		}
		return computers;

	}

	@Transactional
	public int searchAllCount() {
		int nbComputer = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			nbComputer = session.createQuery(SQL_COUNT_ALL_COMPUTER, Long.class).uniqueResult().intValue();
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return nbComputer;
	}

	@Transactional
	public int searchNameCount(String name) {
		int nbComputer = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query<Long> query = session.createQuery(SQL_SEARCH_BY_NAME_COUNT, Long.class);
			query.setParameter("nameComputer", "%" + name + "%");
			query.setParameter("nameCompany", "%" + name + "%");
			nbComputer = query.uniqueResult().intValue();
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return nbComputer;
	}

	@Transactional
	public List<Computer> searchNamePagination(Page<Computer> page, String name) throws DAOException {
		List<Computer> computers = new ArrayList<>();
		List<ComputerDTOPersistence> computersDTO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query<ComputerDTOPersistence> query = session.createQuery(SQL_SEARCH_BY_NAME_COMPA_COMPU
					.replace("ORDERATTRIBUTE", page.getOrderAttribute()).replace("ORDERSORT", page.getOrderSort()),
					ComputerDTOPersistence.class);

			query.setFirstResult(page.getObjetPerPage() * page.getPageInt());
			query.setMaxResults(page.getObjetPerPage());
			query.setParameter("nameComputer", "%" + name.toLowerCase() + "%");
			query.setParameter("nameCompany", "%" + name.toLowerCase() + "%");

			computersDTO = query.list();
			computers = mapperComputer.mapFromListDTOPersistanceToListModel(computersDTO);
			return computers;
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return computers;
	}

}
