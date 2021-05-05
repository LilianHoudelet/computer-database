package com.excilys.cdb.dao.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.excilys.cdb.dto.web.CompanyDTO;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

public class TestMapperCompany {

	@Test
	public void testMapFromModelToDTO() {
		CompanyMapper mapperCompany = new CompanyMapper();
		Company company = new Company(1L,"test");
		CompanyDTO companyDTO = mapperCompany.mapFromModelToDTO(company);
		CompanyDTO companyDTOExpected = new CompanyDTO.CompanyDTOBuilder("1").name("test").build();
		assertEquals(companyDTO.getId(), companyDTOExpected.getId());
		assertEquals(companyDTO.getName(), companyDTOExpected.getName());

	}

}
