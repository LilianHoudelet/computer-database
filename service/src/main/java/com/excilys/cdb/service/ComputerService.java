package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DAOConfigurationException;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.model.Computer;

@Service
public class ComputerService {

	ComputerDAO computerDAO;

	public ComputerService(ComputerDAO computerDAO) {

		this.computerDAO = computerDAO;
	}

	public List<Computer> searchAllComputer() {
		try {
			return computerDAO.searchAll();
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return new ArrayList<Computer>();
	}

	public Optional<Computer> searchByIdComputer(Long idToSearch) {
		try {
			Optional<Computer> compSearched = computerDAO.search(idToSearch);
			
			return compSearched;
		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return Optional.empty();
	}

	public boolean createComputer(Computer compToCreate) { // TODO améliorer le systeme avec le boolean 
		boolean success = false;
		try {

			computerDAO.create(compToCreate);
			success = true;

		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return success;
	}

	public boolean updateComputer(Computer compToUpdate) { // TODO améliorer le systeme avec le boolean 
		boolean success = false;
		try {
			computerDAO.update(compToUpdate);
			success = true;

		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return success;
	}

	public boolean deleteComputer(Long compToDeleteID) { // TODO améliorer le systeme avec le boolean 
		boolean success = false;
		try {
			computerDAO.delete(compToDeleteID);
			success = true;

		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}

		return success;
	}

	public int countComputer() {
		int nbComputer = 0;
		try {
			nbComputer = computerDAO.searchAllCount();
		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return nbComputer;
	}

	public int searchNameCount(String name) {
		int nbComputer = 0;
		try {
			nbComputer = computerDAO.searchNameCount(name);
		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return nbComputer;
	}

}
