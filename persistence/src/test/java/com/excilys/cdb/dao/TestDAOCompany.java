package com.excilys.cdb.dao;

import java.sql.Connection;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.model.Company;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("/com/excilys/cdb/dao/data.xml")
public class TestDAOCompany extends DataSourceDBUnitTest {

	@Autowired
	CompanyDAO companyDAOImpl;

	@Test
	public void testInsertThenSearchCompanyDAO() throws Exception {

		Connection conn = getDataSource().getConnection();

		conn.createStatement().executeUpdate("INSERT INTO COMPANY (id,name) VALUES ( 42,'Research In Motion')");
		List<Company> companies = companyDAOImpl.getAll();
		assertEquals(13, companies.size());
	}

	@Test
	public void testSearchAllCompanyDAO() throws Exception {

		List<Company> companies = companyDAOImpl.getAll();
		assertEquals(12, companies.size());

	}
}
