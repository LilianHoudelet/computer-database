package com.excilys.cdb.dao.mapper;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.cdb.dto.web.ComputerDTOAdd;
import com.excilys.cdb.dto.web.ComputerDTOList;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.mapper.RowMapperComputer;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class TestMapperComputer {

	@Test
	public void testMapFromResultSet() throws SQLException {

		ResultSet rsMock = Mockito.mock(ResultSet.class);

		RowMapperComputer rowMpMapperComputer = new RowMapperComputer();
		Mockito.when(rsMock.getLong("id")).thenReturn(1L);
		Mockito.when(rsMock.getString("name")).thenReturn("testComputerName");
		Mockito.when(rsMock.getDate("introduced")).thenReturn(Date.valueOf(LocalDate.parse("2020-08-06")));
		Mockito.when(rsMock.getDate("discontinued")).thenReturn(Date.valueOf(LocalDate.parse("2020-08-07")));
		Mockito.when(rsMock.getLong("company_id")).thenReturn(2L);
		Mockito.when(rsMock.getString("companyName")).thenReturn("testCompanyName");
		Mockito.when(rsMock.getObject("company_id")).thenReturn(2L);
		Computer computer = rowMpMapperComputer.mapRow(rsMock, 0);
		Company companyExpected = new Company.CompanyBuilder(2L).name("testCompanyName").build();
		Computer computerExpected = new Computer.ComputerBuilder(1L).name("testComputerName")
				.introduced(LocalDate.parse("2020-08-06")).discontinued(LocalDate.parse("2020-08-07"))
				.company(companyExpected).build();
		assertEquals(computerExpected, computer);

	}

	@Test
	public void testMapFromResultSetWithNull() throws SQLException {

		ResultSet rsMock = Mockito.mock(ResultSet.class);
		RowMapperComputer rowMpMapperComputer = new RowMapperComputer();
		Mockito.when(rsMock.getLong("id")).thenReturn(1L);
		Mockito.when(rsMock.getString("name")).thenReturn("testComputerName");
		Mockito.when(rsMock.getDate("introduced")).thenReturn(null);
		Mockito.when(rsMock.getDate("discontinued")).thenReturn(null);
		Mockito.when(rsMock.getObject("company_id")).thenReturn(null);
		Computer computer = rowMpMapperComputer.mapRow(rsMock, 0);
		Company companyExpected = new Company.CompanyBuilder(null).build();
		Computer computerExpected = new Computer.ComputerBuilder(1L).name("testComputerName").introduced(null)
				.discontinued(null).company(companyExpected).build();
		assertEquals(computerExpected, computer);

	}

	@Test
	public void testMapFromModelToDTOList() {

		MapperComputer mapperComputer = new MapperComputer();
		Company company = new Company.CompanyBuilder(2L).name("testCompanyName").build();
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

		MapperComputer mapperComputer = new MapperComputer();
		Company company = new Company.CompanyBuilder(null).build();
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

		MapperComputer mapperComputer = new MapperComputer();

		ComputerDTOAdd computerDTOAdd = new ComputerDTOAdd.ComputerDTOAddBuilder("testComputerName")
				.introduced("2020-08-06").discontinued("2020-08-07").company("2").build();
		Computer computer = mapperComputer.mapFromDTOAddToModel(computerDTOAdd);
		Company companyExpected = new Company.CompanyBuilder(2L).build();
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

		MapperComputer mapperComputer = new MapperComputer();

		ComputerDTOAdd computerDTOAdd = new ComputerDTOAdd.ComputerDTOAddBuilder("testComputerName").introduced(null)
				.discontinued(null).company(null).build();
		Computer computer = mapperComputer.mapFromDTOAddToModel(computerDTOAdd);
		Company companyExpected = new Company.CompanyBuilder(null).build();
		Computer computerExpected = new Computer.ComputerBuilder(null).name("testComputerName").introduced(null)
				.discontinued(null).company(companyExpected).build();

		assertEquals(computerExpected.getId(), computer.getId());
		assertEquals(computerExpected.getName(), computer.getName());
		assertEquals(computerExpected.getIntroduced(), computer.getIntroduced());
		assertEquals(computerExpected.getDiscontinued(), computer.getDiscontinued());
		assertEquals(computerExpected.getCompany(), computer.getCompany());
	}

}
