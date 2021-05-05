package com.excilys.cdb.dao.mapper;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.cdb.dto.web.CompanyDTO;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

public class TestMapperCompany {

	@Test
	public void testMapFromResultSet() throws SQLException {

		ResultSet rsMock = Mockito.mock(ResultSet.class);
		CompanyMapper mapperCompany = new CompanyMapper();
		Mockito.when(rsMock.getLong("id")).thenReturn(1L);
		Mockito.when(rsMock.getString("name")).thenReturn("test");
		Company company = mapperCompany.mapFromResultSet(rsMock);
		Company companyExpected = new Company.CompanyBuilder(1L).name("test").build();
		assertEquals(company, companyExpected);

	}

	@Test
	public void testMapFromModelToDTO() {

		CompanyMapper mapperCompany = new CompanyMapper();
		Company company = new Company.CompanyBuilder(1L).name("test").build();
		CompanyDTO companyDTO = mapperCompany.mapFromModelToDTO(company);
		CompanyDTO companyDTOExpected = new CompanyDTO.CompanyDTOBuilder("1").name("test").build();
		assertEquals(companyDTO.getId(), companyDTOExpected.getId());
		assertEquals(companyDTO.getName(), companyDTOExpected.getName());

	}

}
