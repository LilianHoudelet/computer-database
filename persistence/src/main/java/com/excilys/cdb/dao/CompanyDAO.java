package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dto.persistence.CompanyDTOPersistence;
import com.excilys.cdb.dto.persistence.ComputerDTOPersistence;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.model.Company;

@Repository
public class CompanyDAO {

	private final MapperCompany mapperCompany;
	private final SessionFactory sessionFactory;

	public CompanyDAO(SessionFactory sessionFactory, MapperCompany mapperCompany) {
		this.sessionFactory = sessionFactory;
		this.mapperCompany = mapperCompany;
	}

	private static final int OBJECT_NUMBER_PER_PAGE = 10;
	private static final String HQL_ALL_COMPANY = "FROM CompanyDTOPersistence ORDER BY id";
	private static final String HQL_ALL_COMPANY_PAGINATION = "FROM CompanyDTOPersistence ORDER BY id ";
//	private static final String SQL_DELETE = "DELETE FROM company WHERE id=:id;";
	private static final String HQL_DELETE = "DELETE FROM CompanyDTOPersistence WHERE id=:id;";
//	private static final String SQL_DELETE_COMPUTER = "DELETE FROM computer WHERE company_id=:id;";
	private static final String HQL_DELETE_COMPUTER = "DELETE FROM ComputerDTOPersistence WHERE companyDTOPersistence.id=:id;";

	public List<Company> searchAll() {
		List<Company> companies = new ArrayList<>();
		List<CompanyDTOPersistence> companiesDTO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();

			companiesDTO = session.createQuery(HQL_ALL_COMPANY, CompanyDTOPersistence.class).list();

			companies = mapperCompany.mapFromListDTOPersistenceToListModel(companiesDTO);
			return companies;
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return companies;
	}

	public List<Company> searchAllPagination(int page) throws DAOException {
		List<Company> companies = new ArrayList<>();
		List<CompanyDTOPersistence> companiesDTO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();

			Query<CompanyDTOPersistence> query = session.createQuery(HQL_ALL_COMPANY_PAGINATION,
					CompanyDTOPersistence.class);

			query.setFirstResult(page * OBJECT_NUMBER_PER_PAGE);
			query.setMaxResults(OBJECT_NUMBER_PER_PAGE);
			companiesDTO = query.list();
			companies = mapperCompany.mapFromListDTOPersistenceToListModel(companiesDTO);
			return companies;
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return companies;
	}

	@Transactional
	public void delete(Long id) throws DAOException {

		try {
			Session session = sessionFactory.getCurrentSession();
			Query<?> queryComputer = session.createQuery(HQL_DELETE_COMPUTER);
			queryComputer.setParameter("id", id);
			queryComputer.executeUpdate();

			Query<?> queryCompany = session.createQuery(HQL_DELETE);
			queryCompany.setParameter("id", id);
			int statut = queryCompany.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Deleting company failure, no row were added to the table.");
			}
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
	}

}