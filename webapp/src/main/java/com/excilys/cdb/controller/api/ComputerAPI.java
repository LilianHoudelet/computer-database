package com.excilys.cdb.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.rest.ComputerDTORest;
import com.excilys.cdb.exception.ComputerNotFoundException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@RestController
@RequestMapping("/api/computers")
public class ComputerAPI { // TODO renommer les variables pour les pages

	private ComputerService computerService;
	private ComputerMapper mapperComputer;

	public ComputerAPI(ComputerService computerService, ComputerMapper mapperComputer) {
		this.computerService = computerService;
		this.mapperComputer = mapperComputer;
	}

	@GetMapping
	public ResponseEntity<?> getComputers(@RequestParam(required = false) Long id) {
		if (id == null) {
			List<Computer> listComputer = null;
			listComputer = computerService.findComputers();

			List<ComputerDTORest> listDTO = mapperComputer.mapFromListModelToListDTORest(listComputer);

			return new ResponseEntity<>(listDTO, HttpStatus.OK);
		} else {
			Computer computer = null;
			try {
				computer = computerService.findComputerById(id).orElseThrow();
			} catch (ComputerNotFoundException e) {
				LoggerCdb.logError(getClass(), e);
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
			ComputerDTORest computerDTO = mapperComputer.mapFromModelToDTORest(computer);
			return new ResponseEntity<>(computerDTO, HttpStatus.OK);
		}
	}

	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> getComputer(@PathVariable Long id) {
		Computer computer = null;
		try {
			computer = computerService.findComputerById(id).orElseThrow();

		} catch (ComputerNotFoundException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		ComputerDTORest computerDTO = mapperComputer.mapFromModelToDTORest(computer);
		return new ResponseEntity<>(computerDTO, HttpStatus.OK);
	}

	@GetMapping(value = { "/page" })
	public ResponseEntity<?> getComputerPage(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer nbObject, @RequestParam(required = false) String orderBy,
			@RequestParam(required = false) String sort, @RequestParam(required = false) String name) {
		Page<Computer> computerPage = new Page<Computer>();
		setOrderBy(computerPage, orderBy, sort);
		setPageInt(computerPage, page);

		setObjectPerPage(computerPage, nbObject);
		List<Computer> listComputer = new ArrayList<Computer>();
		try {
			if (name == null) {
				listComputer = computerService.getComputerPage(computerPage);
			} else {
				listComputer = computerService.getComputerPage(computerPage, name);
			}
			List<ComputerDTORest> listDTO = mapperComputer.mapFromListModelToListDTORest(listComputer);
			return new ResponseEntity<>(listDTO, HttpStatus.OK);
		} catch (ComputerNotFoundException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = { "/count" })
	public ResponseEntity<?> getComputerCount() {
		try {
			return new ResponseEntity<>(computerService.countComputer(), HttpStatus.OK);
		} catch (ComputerNotFoundException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = { "/{name}/count" })
	public ResponseEntity<?> getComputerCountSearch(@PathVariable String name) {
		try {
			return new ResponseEntity<>(computerService.countComputer(name), HttpStatus.OK);
		} catch (ComputerNotFoundException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = { "/{id}" })
	public void deleteCompute(@PathVariable Long id) {
		computerService.deleteComputer(id);
	}

	@PutMapping(value = { "/{id}" })
	public void updateCompute(@PathVariable Long id, @RequestBody ComputerDTORest computerDTORest,
			BindingResult bindingResult) {
		computerService.updateComputer(mapperComputer.mapFromDTORestToModel(computerDTORest));
	}

	@PostMapping
	public void createCompute(@RequestBody ComputerDTORest computerDTORest, BindingResult bindingResult) {
		computerService.createComputer(mapperComputer.mapFromDTORestToModel(computerDTORest));
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
