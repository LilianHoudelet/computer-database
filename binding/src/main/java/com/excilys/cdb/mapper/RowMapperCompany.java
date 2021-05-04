package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;

@Component
public class RowMapperCompany implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Company company = new Company.CompanyBuilder(resultSet.getLong("id")).name(resultSet.getString("name")).build();

		return company;
	}

}
