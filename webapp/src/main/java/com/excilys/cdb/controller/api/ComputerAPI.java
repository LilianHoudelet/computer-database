package com.excilys.cdb.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.rest.ComputerDTORest;
import com.excilys.cdb.exception.InputException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.PageService;

@RestController
@RequestMapping("/api/computer")
public class ComputerAPI { // TODO renommer les variables pour les pages
	
	private ComputerService computerService;
	private PageService pageService;
	private MapperComputer mapperComputer;
	
	public ComputerAPI(ComputerService computerService, PageService pageService, MapperComputer mapperComputer) {
		this.computerService = computerService;
		this.pageService = pageService;
		this.mapperComputer = mapperComputer;
	}

	@GetMapping
	public ResponseEntity<?> getComputer() {

		List<Computer> listComputer = null;
		try {
			listComputer = this.computerService.searchAllComputer();
			if (listComputer.size() == 0) {
				throw new InputException("No computer found.");
			}
		} catch (InputException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		List<ComputerDTORest> listDTO = mapperComputer.mapFromListModelToListDTORest(listComputer);

		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}

	@GetMapping(value = { "/page" }, produces = "application/json")
	public ResponseEntity<?> getComputerPage(@RequestParam(required = false) Integer numPage,
			@RequestParam(required = false) Integer nbObject, @RequestParam(required = false) String orderBy,
			@RequestParam(required = false) String sort, @RequestParam(required = false) String name) {
		Page<Computer> page = new Page<Computer>();
		setOrderBy(page, orderBy, sort);
		setPageInt(page, numPage);

		setObjectPerPage(page, nbObject);
		List<Computer> listComputer = null;
		try {
			if (name == null) {
				listComputer = this.pageService.searchAllComputerPagination(page);
				if (listComputer.size() == 0) {
					throw new InputException("No computer found. Please, verify the information.");
				}
			} else {
				listComputer = this.pageService.searchNamePagination(page, name);
				if (listComputer.size() == 0) {
					throw new InputException("No computer found. Please, verify the information.");
				}
			}
		} catch (InputException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		List<ComputerDTORest> listDTO = mapperComputer.mapFromListModelToListDTORest(listComputer);

		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}

	@GetMapping(value = { "/search" }, produces = "application/json")
	public ResponseEntity<?> getComputerSearch(@RequestParam Long id) {
		Computer computer = null;
		try {
			computer = this.computerService.searchByIdComputer(id)
					.orElseThrow(() -> new InputException("No computer found. Please verify the id."));

		} catch (InputException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		ComputerDTORest computerDTO = mapperComputer.mapFromModelToDTORest(computer);
		return new ResponseEntity<>(computerDTO, HttpStatus.OK);
	}

	@GetMapping(value = { "/count" }, produces = "application/json")
	public ResponseEntity<?> getComputerCount() {
		Integer countComputer;
		try {
			countComputer = this.computerService.countComputer();
			if (countComputer == 0) {
				throw new InputException("No computer found.");
			}
		} catch (InputException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(countComputer, HttpStatus.OK);
	}

	@PostMapping(value = { "/delete" }, produces = "application/json")
	public ResponseEntity<?> deleteCompute(@RequestParam Long id) {
		boolean status;
		try {
			status = this.computerService.deleteComputer(id);
			if (!status) {
				throw new InputException("The computer wasn't deleted. Please verify the id.");
			}
		} catch (InputException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@PostMapping(value = { "/update" }, produces = "application/json")
	public ResponseEntity<?> updateCompute(@RequestBody ComputerDTORest computerDTORest, BindingResult bindingResult) {
		boolean status;
		try {
			status = this.computerService.updateComputer(mapperComputer.mapFromDTORestToModel(computerDTORest));
			if (!status) {
				throw new InputException("The computer wasn't updated. Please verify the informations.");
			}
		} catch (InputException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@PostMapping(value = { "/create" }, produces = "application/json")
	public ResponseEntity<?> createCompute(@RequestBody ComputerDTORest computerDTORest, BindingResult bindingResult) {
		boolean status;
		try {
			status = this.computerService.createComputer(mapperComputer.mapFromDTORestToModel(computerDTORest));
			if (!status) {
				throw new InputException("The computer wasn't created. Please verify the informations.");
			}
		} catch (InputException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@GetMapping(value = { "/count/{name}" }, produces = "application/json")
	public ResponseEntity<?> getComputerCountSearch(@PathVariable String name) {
		Integer countComputer;
		try {
			countComputer = this.computerService.searchNameCount(name);
			if (countComputer == 0) {
				throw new InputException("No computer found. Please verify the name");
			}
		} catch (InputException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(countComputer, HttpStatus.OK);
	}

	private void setOrderBy(Page<Computer> page, String orderBy, String sort) {
		if (orderBy != null) {
			page.setOrderAttribute(orderBy);
		} else {
			page.setOrderAttribute("computer.id");
		}
		if (sort != null) {
			page.setOrderSort(sort);
		} else {
			page.setOrderSort("asc");
		}

	}

	private void setPageInt(Page<Computer> page, Integer pageno) {
		if (pageno == null) {
			pageno = 1;
		}
		page.setPageInt(pageno - 1);
	}

	private void setObjectPerPage(Page<Computer> page, Integer nbObject) {
		if (nbObject == null) {
			nbObject = 10;
		}
		page.setObjetPerPage(nbObject);
	}

}
