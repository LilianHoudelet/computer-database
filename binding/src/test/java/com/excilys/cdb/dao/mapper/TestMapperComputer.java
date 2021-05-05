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

//	@Test
//	public void testMapFromModelToDTOList() {
//
//		ComputerMapper mapperComputer = new ComputerMapper();
//		Company company = new Company.CompanyBuilder(2L).name("testCompanyName").build();
//		Computer computer = new Computer.ComputerBuilder(1L).name("testComputerName")
//				.introduced(LocalDate.parse("2020-08-06")).discontinued(LocalDate.parse("2020-08-07")).company(company)
//				.build();
//		ComputerDTOList computerDTOList = mapperComputer.mapFromModelToDTOList(computer);
//		ComputerDTOList computerDTOListExpected = new ComputerDTOList("1", "testComputerName", "2020-08-06",
//				"2020-08-07", "testCompanyName");
//		assertEquals(computerDTOListExpected.getId(), computerDTOList.getId());
//		assertEquals(computerDTOListExpected.getName(), computerDTOList.getName());
//		assertEquals(computerDTOListExpected.getIntroduced(), computerDTOList.getIntroduced());
//		assertEquals(computerDTOListExpected.getDiscontinued(), computerDTOList.getDiscontinued());
//		assertEquals(computerDTOListExpected.getCompanyName(), computerDTOList.getCompanyName());
//	}
//
//	@Test
//	public void testMapFromModelToDTOListWithNull() {
//
//		ComputerMapper mapperComputer = new ComputerMapper();
//		Company company = new Company.CompanyBuilder(null).build();
//		Computer computer = new Computer.ComputerBuilder(1L).name("testComputerName").introduced(null)
//				.discontinued(null).company(company).build();
//		ComputerDTOList computerDTOList = mapperComputer.mapFromModelToDTOList(computer);
//		ComputerDTOList computerDTOListExpected = new ComputerDTOList("1", "testComputerName", null, null, null);
//		assertEquals(computerDTOListExpected.getId(), computerDTOList.getId());
//		assertEquals(computerDTOListExpected.getName(), computerDTOList.getName());
//		assertEquals(computerDTOListExpected.getIntroduced(), computerDTOList.getIntroduced());
//		assertEquals(computerDTOListExpected.getDiscontinued(), computerDTOList.getDiscontinued());
//		assertEquals(computerDTOListExpected.getCompanyName(), computerDTOList.getCompanyName());
//	}
//
//	@Test
//	public void testMapFromDTOAddToModel() {
//
//		ComputerMapper mapperComputer = new ComputerMapper();
//
//		ComputerDTOAdd computerDTOAdd = new ComputerDTOAdd.ComputerDTOAddBuilder("testComputerName")
//				.introduced("2020-08-06").discontinued("2020-08-07").company("2").build();
//		Computer computer = mapperComputer.mapFromDTOAddToModel(computerDTOAdd);
//		Company companyExpected = new Company.CompanyBuilder(2L).build();
//		Computer computerExpected = new Computer.ComputerBuilder(null).name("testComputerName")
//				.introduced(LocalDate.parse("2020-08-06")).discontinued(LocalDate.parse("2020-08-07"))
//				.company(companyExpected).build();
//
//		assertEquals(computerExpected.getId(), computer.getId());
//		assertEquals(computerExpected.getName(), computer.getName());
//		assertEquals(computerExpected.getIntroduced(), computer.getIntroduced());
//		assertEquals(computerExpected.getDiscontinued(), computer.getDiscontinued());
//		assertEquals(computerExpected.getCompany(), computer.getCompany());
//	}
//
//	@Test
//	public void testMapFromDTOAddToModelWithNull() {
//
//		ComputerMapper mapperComputer = new ComputerMapper();
//
//		ComputerDTOAdd computerDTOAdd = new ComputerDTOAdd.ComputerDTOAddBuilder("testComputerName").introduced(null)
//				.discontinued(null).company(null).build();
//		Computer computer = mapperComputer.mapFromDTOAddToModel(computerDTOAdd);
//		Company companyExpected = new Company.CompanyBuilder(null).build();
//		Computer computerExpected = new Computer.ComputerBuilder(null).name("testComputerName").introduced(null)
//				.discontinued(null).company(companyExpected).build();
//
//		assertEquals(computerExpected.getId(), computer.getId());
//		assertEquals(computerExpected.getName(), computer.getName());
//		assertEquals(computerExpected.getIntroduced(), computer.getIntroduced());
//		assertEquals(computerExpected.getDiscontinued(), computer.getDiscontinued());
//		assertEquals(computerExpected.getCompany(), computer.getCompany());
//	}

}
