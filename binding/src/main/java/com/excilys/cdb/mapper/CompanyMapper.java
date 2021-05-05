package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.persistence.CompanyEntity;
import com.excilys.cdb.dto.rest.CompanyDTORest;
import com.excilys.cdb.dto.web.CompanyDTO;
import com.excilys.cdb.model.Company;

@Component
public class CompanyMapper {

	public Company mapFromResultSet(ResultSet resultSet) throws SQLException {

		Company company = new Company.CompanyBuilder(resultSet.getLong("id")).name(resultSet.getString("name")).build();

		return company;
	}

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

	public Company mapFromDTOPersistenceToModel(CompanyEntity companyDTOPersistance) {

		Company company = new Company.CompanyBuilder(companyDTOPersistance.getIdCompany())
				.name(companyDTOPersistance.getName()).build();

		return company;
	}

	public List<Company> mapFromListDTOPersistenceToListModel(List<CompanyEntity> listCompanies) {

		List<Company> listCompaniesDTO = listCompanies.stream().map(c -> mapFromDTOPersistenceToModel(c))
				.collect(Collectors.toList());

		return listCompaniesDTO;
	}

	public CompanyEntity mapFromModelToDTOPersistence(Company company) {

		if (company == null || (company.getId() == null && company.getName() == null)) {
			return null;
		} else {
			return new CompanyEntity.CompanyEntityBuilder(company.getId()).name(company.getName())
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
			return new Company.CompanyBuilder(companyDTORest.getId()).name(companyDTORest.getName()).build();
		}

	}

}
