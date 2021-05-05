package com.excilys.cdb.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.rest.CompanyDTORest;
import com.excilys.cdb.mapper.MapperCompany;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyAPI {
	
	CompanyService companyService;
	MapperCompany mapperCompany;
	
	public CompanyAPI(CompanyService companyService, MapperCompany mapperCompany){
		this.companyService = companyService;
		this.mapperCompany = mapperCompany;
	}

	@GetMapping
	public ResponseEntity<?> getCompanies() {

		List<Company> listCompany = companyService.getCompanies();
		List<CompanyDTORest> listDTO = mapperCompany.mapFromListModelToListDTORest(listCompany);
		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}

	@GetMapping(value = { "/{page}"})
	public List<CompanyDTORest> getCompanyPage(@PathVariable(required = false) Integer page) {
		Page<Company> companyPage = new Page<Company>();
		setPageInt(companyPage, page-1);
		List<Company> listCompany = companyService.getCompanies(page-1);
		List<CompanyDTORest> listDTO = mapperCompany.mapFromListModelToListDTORest(listCompany);

		return listDTO;
	}

	@DeleteMapping
	public void deleteCompany(@RequestParam Long id) {
		companyService.deleteCompany(id);
	}

	private void setPageInt(Page<Company> page, Integer pageno) {
		if (pageno == null) {
			pageno = 1;
		}
		page.setPageInt(pageno - 1);
	}

}
