package com.excilys.cdb.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.rest.CompanyDTORest;
import com.excilys.cdb.exception.InputException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.PageService;

@RestController
@RequestMapping("/api/company")
public class CompanyAPI {
	@Autowired
	CompanyService companyService;
	@Autowired
	MapperCompany mapperCompany;
	@Autowired
	private PageService pageService;

	@GetMapping
	public ResponseEntity<?> getCompany() {

		List<Company> listCompany = null;
		try {
			listCompany = this.companyService.searchAllCompany();
			if (listCompany.size() == 0) {
				throw new InputException("No company found.");
			}
		} catch (InputException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		List<CompanyDTORest> listDTO = mapperCompany.mapFromListModelToListDTORest(listCompany);

		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}

	@GetMapping(value = { "/page/{numPage}", "/page" }, produces = "application/json")
	public ResponseEntity<?> getComputerPage(@PathVariable(required = false) Integer numPage) {
		Page<Company> page = new Page<Company>();

		setPageInt(page, numPage);

		List<Company> listCompany = null;
		try {
			listCompany = this.pageService.searchAllCompanyPage(numPage);
			if (listCompany.size() == 0) {
				throw new InputException("No companie found. Please, verify the page number.");
			}
		} catch (InputException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		List<CompanyDTORest> listDTO = mapperCompany.mapFromListModelToListDTORest(listCompany);

		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}

	@PostMapping(value = { "/delete" }, produces = "application/json")
	public ResponseEntity<?> deleteCompute(@RequestParam Long id) {
		boolean status;
		try {
			status = this.companyService.deleteCompany(id);
			if (!status) {
				throw new InputException("The computer wasn't deleted. Please verify the id.");
			}
		} catch (InputException e) {
			LoggerCdb.logError(getClass(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	private void setPageInt(Page<Company> page, Integer pageno) {
		if (pageno == null) {
			pageno = 1;
		}
		page.setPageInt(pageno - 1);
	}

}
