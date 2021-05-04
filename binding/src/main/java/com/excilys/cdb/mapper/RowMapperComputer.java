package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class RowMapperComputer implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		LocalDate introduced = null;
		LocalDate discontinued = null;
		Company company = new Company.CompanyBuilder(null).build();

		Computer computer;
		if (resultSet.getDate("introduced") != null) {
			introduced = resultSet.getDate("introduced").toLocalDate();
		}
		if (resultSet.getDate("discontinued") != null) {
			discontinued = resultSet.getDate("discontinued").toLocalDate();
		}
		if (resultSet.getObject("company_id") != null) {
			company = new Company.CompanyBuilder(resultSet.getLong("company_id"))
					.name(resultSet.getString("companyName")).build();
		}
		computer = new Computer.ComputerBuilder(resultSet.getLong("id")).name(resultSet.getString("name"))
				.introduced(introduced).discontinued(discontinued).company(company).build();

		return computer;
	}

}
