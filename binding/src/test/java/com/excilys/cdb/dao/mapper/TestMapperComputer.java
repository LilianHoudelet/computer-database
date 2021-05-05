package com.excilys.cdb.dao.mapper;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.cdb.dto.web.ComputerDTOAdd;
import com.excilys.cdb.dto.web.ComputerDTOList;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class TestMapperComputer {
	
	private final ComputerMapper mapperComputer;
	
	public TestMapperComputer(ComputerMapper mapperComputer) {
		this.mapperComputer = mapperComputer;
	}

	@Test
	public void testMapFromModelToDTOList() {
		Company company = new Company(2L,"testCompanyName");
		Computer computer = new Computer.ComputerBuilder(1L).name("testComputerName")
				.introduced(LocalDate.parse("2020-08-06")).discontinued(LocalDate.parse("2020-08-07")).company(company)
				.build();
		ComputerDTOList computerDTOList = mapperComputer.mapFromModelToDTOList(computer);
		ComputerDTOList computerDTOListExpected = new ComputerDTOList("1", "testComputerName", "2020-08-06",
				"2020-08-07", "testCompanyName");
		assertEquals(computerDTOListExpected.getId(), computerDTOList.getId());
		assertEquals(computerDTOListExpected.getName(), computerDTOList.getName());
		assertEquals(computerDTOListExpected.getIntroduced(), computerDTOList.getIntroduced());
		assertEquals(computerDTOListExpected.getDiscontinued(), computerDTOList.getDiscontinued());
		assertEquals(computerDTOListExpected.getCompanyName(), computerDTOList.getCompanyName());
	}

	@Test
	public void testMapFromModelToDTOListWithNull() {
		Company company = new Company();
		Computer computer = new Computer.ComputerBuilder(1L).name("testComputerName").introduced(null)
				.discontinued(null).company(company).build();
		ComputerDTOList computerDTOList = mapperComputer.mapFromModelToDTOList(computer);
		ComputerDTOList computerDTOListExpected = new ComputerDTOList("1", "testComputerName", null, null, null);
		assertEquals(computerDTOListExpected.getId(), computerDTOList.getId());
		assertEquals(computerDTOListExpected.getName(), computerDTOList.getName());
		assertEquals(computerDTOListExpected.getIntroduced(), computerDTOList.getIntroduced());
		assertEquals(computerDTOListExpected.getDiscontinued(), computerDTOList.getDiscontinued());
		assertEquals(computerDTOListExpected.getCompanyName(), computerDTOList.getCompanyName());
	}

	@Test
	public void testMapFromDTOAddToModel() {
		ComputerDTOAdd computerDTOAdd = new ComputerDTOAdd.ComputerDTOAddBuilder("testComputerName")
				.introduced("2020-08-06").discontinued("2020-08-07").company("2").build();
		Computer computer = mapperComputer.mapFromDTOAddToModel(computerDTOAdd);
		Company companyExpected = new Company(2L, null);
		Computer computerExpected = new Computer.ComputerBuilder(null).name("testComputerName")
				.introduced(LocalDate.parse("2020-08-06")).discontinued(LocalDate.parse("2020-08-07"))
				.company(companyExpected).build();

		assertEquals(computerExpected.getId(), computer.getId());
		assertEquals(computerExpected.getName(), computer.getName());
		assertEquals(computerExpected.getIntroduced(), computer.getIntroduced());
		assertEquals(computerExpected.getDiscontinued(), computer.getDiscontinued());
		assertEquals(computerExpected.getCompany(), computer.getCompany());
	}

	@Test
	public void testMapFromDTOAddToModelWithNull() {
		ComputerDTOAdd computerDTOAdd = new ComputerDTOAdd.ComputerDTOAddBuilder("testComputerName").introduced(null)
				.discontinued(null).company(null).build();
		Computer computer = mapperComputer.mapFromDTOAddToModel(computerDTOAdd);
		Company companyExpected = new Company();
		Computer computerExpected = new Computer.ComputerBuilder(null).name("testComputerName").introduced(null)
				.discontinued(null).company(companyExpected).build();

		assertEquals(computerExpected.getId(), computer.getId());
		assertEquals(computerExpected.getName(), computer.getName());
		assertEquals(computerExpected.getIntroduced(), computer.getIntroduced());
		assertEquals(computerExpected.getDiscontinued(), computer.getDiscontinued());
		assertEquals(computerExpected.getCompany(), computer.getCompany());
	}

}
