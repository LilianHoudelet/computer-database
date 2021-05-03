package com.excilys.cdb.dao;

import static org.dbunit.Assertion.assertEqualsIgnoreCols;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup("/com/excilys/cdb/dao/data.xml")
public class TestDAOComputer extends DataSourceDBUnitTest {

	@Autowired
	ComputerDAO computerDAOImpl;

	@Autowired
	CompanyDAO companyDAOImpl;

	@Test
	public void testSearchAllPaginationCompanyDAO() throws Exception {

		List<Company> companiesPagination = companyDAOImpl.searchAllPagination(0);
		assertEquals(10, companiesPagination.size());
	}

	@Test
	public void testSearchAllComputerDAO() throws Exception {

		List<Computer> computers = computerDAOImpl.searchAll();
		assertEquals(13, computers.size());
	}

	@Test
	public void testSearchAllPaginationComputerDAO() throws Exception {

		Page<Computer> pageComputer = new Page<Computer>();
		pageComputer.setPageInt(0);
		pageComputer.setObjetPerPage(10);
		List<Computer> computers = computerDAOImpl.searchAllPagination(pageComputer);
		assertEquals(10, computers.size());
	}

	@Test
	public void testAddComputerDAO() throws Exception {
		try (InputStream is = getClass().getClassLoader()
				.getResourceAsStream("com/excilys/cdb/dao/dataExpectedAddComputer.xml")) {
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
			ITable expectedTable = expectedDataSet.getTable("COMPUTER");

			// Connection conn = getDataSource().getConnection();

			Company company = new Company.CompanyBuilder(12L).build();
			Computer computer = new Computer.ComputerBuilder(null).name("testComputerName")
					.introduced(LocalDate.parse("2020-08-06")).discontinued(LocalDate.parse("2020-08-07"))
					.company(company).build();
			computerDAOImpl.create(computer);
			ITable actualData = getConnection().createQueryTable("result_name", "SELECT * FROM COMPUTER ");
			assertEqualsIgnoreCols(expectedTable, actualData, new String[] { "id" });
		}
	}

	@Test
	public void testDeleteComputerDAO() throws Exception {
		try (InputStream is = getClass().getClassLoader()
				.getResourceAsStream("com/excilys/cdb/dao/dataExpectedDeleteComputer.xml")) {
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
			ITable expectedTable = expectedDataSet.getTable("COMPUTER");
			// Connection conn = getDataSource().getConnection();

			computerDAOImpl.delete(4L);
			ITable actualData = getConnection().createQueryTable("result_name", "SELECT * FROM COMPUTER ");
			assertEqualsIgnoreCols(expectedTable, actualData, new String[] { "id" });
		}
	}

	@Test
	public void testSearchComputerDAO() throws Exception {

		Optional<Computer> computer = computerDAOImpl.search(5L);
		Company companyExpected = new Company.CompanyBuilder(5L).name("RCA3").build();
		Computer computerExpected = new Computer.ComputerBuilder(5L).name("CM-7")
				.introduced(LocalDate.parse("1991-04-01")).discontinued(LocalDate.parse("1991-05-02"))
				.company(companyExpected).build();
		assertEquals(computerExpected, computer.get());
	}

	@Test
	public void testUpdateComputerDAO() throws Exception {
		try (InputStream is = getClass().getClassLoader()
				.getResourceAsStream("com/excilys/cdb/dao/dataExpectedUpdateComputer.xml")) {
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
			ITable expectedTable = expectedDataSet.getTable("COMPUTER");
			Company company = new Company.CompanyBuilder(12L).name("RCA10").build();
			Computer computer = new Computer.ComputerBuilder(5L).name("testUpdate")
					.introduced(LocalDate.parse("2000-04-01")).discontinued(LocalDate.parse("2000-05-02"))
					.company(company).build();
			computerDAOImpl.update(computer);
			ITable actualData = getConnection().createQueryTable("result_name", "SELECT * FROM COMPUTER ");
			assertEqualsIgnoreCols(expectedTable, actualData, new String[] { "id" });
		}
	}
}
