package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dto.persistence.CompanyEntity;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

@Repository
@Transactional
public class CompanyDAO {

	private final CompanyMapper companyMapper;
	private final SessionFactory sessionFactory;

	private static final int OBJECT_NUMBER_PER_PAGE = 10;
	private static final String HQL_ALL_COMPANY = "FROM CompanyEntity ORDER BY id";
	private static final String HQL_ALL_COMPANY_PAGINATION = "FROM CompanyEntity ORDER BY id ";
	private static final String HQL_DELETE = "DELETE FROM CompanyEntity WHERE id=:id;";
	private static final String HQL_DELETE_COMPUTER = "DELETE FROM ComputerEntity WHERE companyEntity.id=:id;";
	
	public CompanyDAO(SessionFactory sessionFactory, CompanyMapper companyMapper) {
		this.sessionFactory = sessionFactory;
		this.companyMapper = companyMapper;
	}
	
	public List<Company> searchAll() {
		List<Company> companies = new ArrayList<>();
		List<CompanyEntity> companiesDTO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();

			companiesDTO = session.createQuery(HQL_ALL_COMPANY, CompanyEntity.class).list();

			companies = companyMapper.mapFromListDTOPersistenceToListModel(companiesDTO);
			return companies;
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return companies;
	}
	
	public List<Company> searchAllPagination(int page) throws DAOException {
		List<Company> companies = new ArrayList<>();
		List<CompanyEntity> companiesDTO = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();

			Query<CompanyEntity> query = session.createQuery(HQL_ALL_COMPANY_PAGINATION,
					CompanyEntity.class);

			query.setFirstResult(page * OBJECT_NUMBER_PER_PAGE);
			query.setMaxResults(OBJECT_NUMBER_PER_PAGE);
			companiesDTO = query.list();
			companies = companyMapper.mapFromListDTOPersistenceToListModel(companiesDTO);
			return companies;
		} catch (HibernateException e) {
			LoggerCdb.logError(this.getClass(), e);
		}
		return companies;
	}

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