package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DAOConfigurationException;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.exception.InputException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Service
public class PageService {

	ComputerDAO computerDAO;
	CompanyDAO companyDAO;

	public PageService(ComputerDAO computerDAO, CompanyDAO companyDAO) {
		this.computerDAO = computerDAO;
		this.companyDAO = companyDAO;
	}

	public List<Computer> searchAllComputerPagination(Page<Computer> page) {
		try {

			return computerDAO.searchAllPagination(page);
		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return new ArrayList<Computer>();
	}

	public List<Computer> searchNamePagination(Page<Computer> page, String name) {
		try {
			List<Computer> computers = computerDAO.searchNamePagination(page, name);
			if (computers.size() == 0) {
				throw new InputException(name);
			}
			return computers;
		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return new ArrayList<Computer>();
	}

	public List<Company> searchAllCompanyPage(int pageInt) {
		try {
			return companyDAO.searchAllPagination(pageInt);
		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return new ArrayList<Company>();
	}

}
