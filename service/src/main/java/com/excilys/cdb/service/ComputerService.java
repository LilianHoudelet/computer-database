package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.ComputerDAOImpl;
import com.excilys.cdb.exception.DAOConfigurationException;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.model.Computer;

@Service
public class ComputerService {

	ComputerDAOImpl computerDAOImpl;

	public ComputerService(ComputerDAOImpl computerDAOImpl) {

		this.computerDAOImpl = computerDAOImpl;
	}

	public List<Computer> searchAllComputer() {
		try {
			return computerDAOImpl.searchAll();
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return new ArrayList<Computer>();
	}

	public Optional<Computer> searchByIdComputer(Long idToSearch) {
		try {

			Optional<Computer> compSearched = computerDAOImpl.search(idToSearch);
			return compSearched;
		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return Optional.empty();
	}

	public boolean createComputer(Computer compToCreate) {
		boolean success = false;
		try {

			computerDAOImpl.create(compToCreate);
			success = true;

		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return success;
	}

	public boolean updateComputer(Computer compToUpdate) {
		boolean success = false;
		try {
//
//			CliMenu.showUpdateOneComputer();
			computerDAOImpl.update(compToUpdate);
			success = true;

		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return success;
	}

	public boolean deleteComputer(Long compToDeleteID) {
		boolean success = false;
		try {

			computerDAOImpl.delete(compToDeleteID);
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

			nbComputer = computerDAOImpl.searchAllCount();

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

			nbComputer = computerDAOImpl.searchNameCount(name);

		} catch (DAOException e) {
			LoggerCdb.logError(getClass(), e);
		} catch (DAOConfigurationException e) {
			LoggerCdb.logError(getClass(), e);
		}
		return nbComputer;
	}

}
