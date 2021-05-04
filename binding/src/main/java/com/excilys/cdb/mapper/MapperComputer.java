package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.persistence.ComputerDTOPersistence;
import com.excilys.cdb.dto.rest.ComputerDTORest;
import com.excilys.cdb.dto.web.ComputerDTOAdd;
import com.excilys.cdb.dto.web.ComputerDTOEdit;
import com.excilys.cdb.dto.web.ComputerDTOList;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class MapperComputer {

	@Autowired
	MapperCompany mapperCompany;

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
		if (computer.getCompany() != null && computer.getCompany().getName() != null) {
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

	public Computer mapFromDTOPersistenceToModel(ComputerDTOPersistence computerDTOPersistence) {
		Computer computer = new Computer.ComputerBuilder(computerDTOPersistence.getId())
				.name(computerDTOPersistence.getName()).discontinued(computerDTOPersistence.getDiscontinued())
				.introduced(computerDTOPersistence.getIntroduced()).build();
		if (computerDTOPersistence.getCompanyDTOPersistence() != null) {
			computer.setCompany(
					mapperCompany.mapFromDTOPersistenceToModel(computerDTOPersistence.getCompanyDTOPersistence()));
		}

		return computer;
	}

	public List<Computer> mapFromListDTOPersistenceToListModel(List<ComputerDTOPersistence> listComputersDTO) {

		List<Computer> listComputers = listComputersDTO.stream().map(c -> mapFromDTOPersistenceToModel(c))
				.collect(Collectors.toList());

		return listComputers;
	}

	public ComputerDTOPersistence mapFromModelToDTOPersistence(Computer computer) {
		ComputerDTOPersistence computerDTOPersistance = new ComputerDTOPersistence.ComputerDTOPersistanceBuilder(
				computer.getId()).name(computer.getName()).discontinued(computer.getDiscontinued())
						.introduced(computer.getIntroduced()).build();
		if (computer.getCompany() != null) {
			computerDTOPersistance
					.setCompanyDTOPersistence(mapperCompany.mapFromModelToDTOPersistence(computer.getCompany()));
		}

		return computerDTOPersistance;
	}

//	public List<ComputerDTOPersistance> mapFromModelListToDTORestList(List<ComputerDTOPersistance>){
//		return null;
//	}

	public ComputerDTORest mapFromModelToDTORest(Computer computer) {
		ComputerDTORest computerDTOPersistance = new ComputerDTORest.ComputerDTORestBuilder(computer.getId())
				.name(computer.getName()).discontinued(computer.getDiscontinued()).introduced(computer.getIntroduced())
				.build();
		if (computer.getCompany() != null) {
			computerDTOPersistance.setCompanyDTORest(mapperCompany.mapFromModelToDTORest(computer.getCompany()));
		}

		return computerDTOPersistance;
	}

	public List<ComputerDTORest> mapFromListModelToListDTORest(List<Computer> listComputers) {
		return listComputers.stream().map(c -> mapFromModelToDTORest(c)).collect(Collectors.toList());
	}

	public Computer mapFromDTORestToModel(ComputerDTORest computerDTORest) {
		Computer computer = new Computer.ComputerBuilder(computerDTORest.getId()).name(computerDTORest.getName())
				.discontinued(computerDTORest.getDiscontinued()).introduced(computerDTORest.getIntroduced()).build();
		if (computerDTORest.getCompanyDTORest() != null) {
			computer.setCompany(mapperCompany.mapFromDTORestToModel(computerDTORest.getCompanyDTORest()));
		}

		return computer;
	}
}
