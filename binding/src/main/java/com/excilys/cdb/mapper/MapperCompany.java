package com.excilys.cdb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.persistence.CompanyDTOPersistence;
import com.excilys.cdb.dto.rest.CompanyDTORest;
import com.excilys.cdb.dto.web.CompanyDTO;
import com.excilys.cdb.model.Company;

@Component
public class MapperCompany {

	public CompanyDTO mapFromModelToDTO(Company company) {

		CompanyDTO companyDTO = new CompanyDTO.CompanyDTOBuilder(company.getId().toString()).name(company.getName())
				.build();

		return companyDTO;
	}

	public List<CompanyDTO> mapFromModelListToDTOList(List<Company> listCompanies) {

		List<CompanyDTO> listCompaniesDTO = listCompanies.stream().map(c -> mapFromModelToDTO(c))
				.collect(Collectors.toList());

		return listCompaniesDTO;
	}

	public Company mapFromDTOPersistenceToModel(CompanyDTOPersistence companyDTOPersistance) {

		Company company = new Company(companyDTOPersistance.getIdCompany(),companyDTOPersistance.getName());

		return company;
	}

	public List<Company> mapFromListDTOPersistenceToListModel(List<CompanyDTOPersistence> listCompanies) {

		List<Company> listCompaniesDTO = listCompanies.stream().map(c -> mapFromDTOPersistenceToModel(c))
				.collect(Collectors.toList());

		return listCompaniesDTO;
	}

	public CompanyDTOPersistence mapFromModelToDTOPersistence(Company company) {

		if (company == null || (company.getId() == null && company.getName() == null)) {
			return null;
		} else {
			return new CompanyDTOPersistence.CompanyDTOPersistanceBuilder(company.getId()).name(company.getName())
					.build();
		}

	}

	public CompanyDTORest mapFromModelToDTORest(Company company) {

		if (company == null || (company.getId() == null && company.getName() == null)) {
			return null;
		} else {
			return new CompanyDTORest.CompanyDTORestBuilder(company.getId()).name(company.getName()).build();
		}

	}

//	List<Company> mapFromListDTORestToListModel(List<CompanyDTORest> companiesDTO) {
//		return companiesDTO.stream().map(c -> mapFromDTORestToModel(c)).collect(Collectors.toList());
//	}

	public List<CompanyDTORest> mapFromListModelToListDTORest(List<Company> companies) {
		return companies.stream().map(c -> mapFromModelToDTORest(c)).collect(Collectors.toList());
	}

	public Company mapFromDTORestToModel(CompanyDTORest companyDTORest) {

		if (companyDTORest == null || (companyDTORest.getId() == null && companyDTORest.getName() == null)) {
			return null;
		} else {
			return new Company(companyDTORest.getId(),companyDTORest.getName());
		}

	}

}
