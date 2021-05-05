package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.ComputerNotFoundException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Service
public class ComputerService {

	ComputerDAO computerDAO;

	public ComputerService(ComputerDAO computerDAO) {

		this.computerDAO = computerDAO;
	}

	public List<Computer> findComputers() {
		return computerDAO.getAll();
	}

	public Optional<Computer> findComputerById(Long idToSearch) {
		try {
			Optional<Computer> compSearched = computerDAO.search(idToSearch);
			return compSearched;
		} catch (NoResultException e) {
			LoggerCdb.logError(getClass(), e);
			throw new ComputerNotFoundException("No computer were found for the id " + idToSearch);
		}
	}

	public int countComputer() {
		return computerDAO.count();
	}

	public int countComputer(String name) {
		return computerDAO.count(name);
	}
	
	public List<Computer> getComputerPage(Page<Computer> page) {
		try {
			return computerDAO.getPage(page);
		} catch (NoResultException e) {
			LoggerCdb.logError(getClass(), e);
			throw new ComputerNotFoundException("No computers were find for this page");
		}
	}

	public List<Computer> getComputerPage(Page<Computer> page, String name) {
		try {
			List<Computer> computers = computerDAO.getPage(page, name);
			return computers;
		} catch (NoResultException e) {
			LoggerCdb.logError(getClass(), e);
			throw new ComputerNotFoundException("No computers were found for the filter " + name);
		}
	}

	public void createComputer(Computer compToCreate) {
		computerDAO.create(compToCreate);
	}

	public void updateComputer(Computer compToUpdate) {
		computerDAO.update(compToUpdate);
	}

	public void deleteComputer(Long compToDeleteID) {
		computerDAO.delete(compToDeleteID);
	}

}
