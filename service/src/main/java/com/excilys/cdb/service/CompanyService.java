package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.exception.DAOConfigurationException;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.model.Company;

@Service
public class CompanyService {

	CompanyDAO companyDAO;
	
	public CompanyService(CompanyDAO companyDAO) {

		this.companyDAO = companyDAO;
	}

	public List<Company> searchAllCompany() {
		try {
			return companyDAO.searchAll();
		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return new ArrayList<Company>();
	}

	public boolean deleteCompany(Long compToDeleteID) { // TODO améliorer le systeme avec le boolean 
		boolean success = false;
		try {
			companyDAO.delete(compToDeleteID);
			success = !success;

		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return success;
	}
}
