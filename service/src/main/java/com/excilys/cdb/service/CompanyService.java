package com.excilys.cdb.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.exception.CompanyNotFoundException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.model.Company;

@Service
public class CompanyService {

	CompanyDAO companyDAO;

	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public List<Company> getCompanies() {
		List<Company> companyList = companyDAO.getAll();
		return companyList;
	}

	public List<Company> getCompanies(int pageInt) {
		try {
			return companyDAO.getAllPage(pageInt);
		} catch (NoResultException e) {
			LoggerCdb.logError(getClass(), e);
			throw new CompanyNotFoundException("No companies were found for the page " + pageInt);
		}
	}

	public void deleteCompany(Long compToDeleteID) { 
		companyDAO.delete(compToDeleteID);
	}
}
