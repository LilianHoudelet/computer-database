package com.excilys.cdb.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTOAdd;
import com.excilys.cdb.dto.ComputerDTOEdit;
import com.excilys.cdb.dto.ComputerDTOList;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class MapperComputer {

	public ComputerDTOList mapFromModelToDTOList(Computer computer) {
		ComputerDTOList computerDTO = new ComputerDTOList();
		computerDTO.setId(computer.getId().toString());
		computerDTO.setName(computer.getName());

		if (computer.getDiscontinued() != null) {
			computerDTO.setDiscontinued(computer.getDiscontinued().toString());
		}
		if (computer.getIntroduced() != null) {
			computerDTO.setIntroduced(computer.getIntroduced().toString());
		}
		if (computer.getCompany().getName() != null) {
			computerDTO.setCompanyName(computer.getCompany().getName());
		}

		return computerDTO;
	}

	public Computer mapFromDTOAddToModel(ComputerDTOAdd computerDTOAdd) {
		LocalDate introduced = null;
		LocalDate discontinued = null;
		Company company = new Company.CompanyBuilder(null).build();
		Computer computer = new Computer.ComputerBuilder(null).build();
		if (computerDTOAdd.getIntroduced() != null && computerDTOAdd.getIntroduced().compareTo("") != 0) {
			introduced = LocalDate.parse(computerDTOAdd.getIntroduced());
		}

		if (computerDTOAdd.getDiscontinued() != null && computerDTOAdd.getDiscontinued().compareTo("") != 0) {
			discontinued = LocalDate.parse(computerDTOAdd.getDiscontinued());
		}

		if (computerDTOAdd.getCompanyId() != null && computerDTOAdd.getCompanyId().compareTo("0") != 0) {
			company = new Company.CompanyBuilder(Long.parseLong(computerDTOAdd.getCompanyId())).build();

		}
		computer = new Computer.ComputerBuilder(null).name(computerDTOAdd.getComputerName()).introduced(introduced)
				.discontinued(discontinued).company(company).build();
		return computer;
	}

	public Computer mapFromDTOEditToModel(ComputerDTOEdit computerDTOEdit) {
		LocalDate introduced = null;
		LocalDate discontinued = null;
		Company company = new Company.CompanyBuilder(null).build();
		Computer computer = new Computer.ComputerBuilder(null).build();
		if (computerDTOEdit.getIntroduced() != null && computerDTOEdit.getIntroduced().compareTo("") != 0) {
			introduced = LocalDate.parse(computerDTOEdit.getIntroduced());
		}

		if (computerDTOEdit.getDiscontinued() != null && computerDTOEdit.getDiscontinued().compareTo("") != 0) {
			discontinued = LocalDate.parse(computerDTOEdit.getDiscontinued());
		}

		if (computerDTOEdit.getCompanyId() != null && computerDTOEdit.getCompanyId().compareTo("0") != 0) {
			company = new Company.CompanyBuilder(Long.parseLong(computerDTOEdit.getCompanyId())).build();

		}
		computer = new Computer.ComputerBuilder(Long.valueOf(computerDTOEdit.getId()))
				.name(computerDTOEdit.getComputerName()).introduced(introduced).discontinued(discontinued)
				.company(company).build();
		return computer;
	}
}
