package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestComputerParameterizedValide {
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	public TestComputerParameterizedValide(Long id, String name, LocalDate introduced, LocalDate discontinued,
			Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;

	}

	@SuppressWarnings("rawtypes")
	@Parameterized.Parameters
	public static Collection input() {
		return Arrays.asList(new Object[][] {{1L, "test", null, null, null}, {null, "test", null, null, null},
				{null, "test", LocalDate.parse("1900-10-10"), null, null},
				{null, "test", LocalDate.parse("1900-10-10"), LocalDate.parse("1900-10-11"), null},
				{null, "test", null, null, new Company(1L, null)},
				{1L, "test", LocalDate.parse("1900-10-10"), LocalDate.parse("1900-10-11"),
					new Company(1L, null)}});
	}

	@Test
	public void testCompanyCreation() {
		Computer computer = new Computer.ComputerBuilder(id).name(name).introduced(introduced)
				.discontinued(discontinued).company(company).build();
		Computer computerEqual = new Computer.ComputerBuilder(id).name(name).introduced(introduced)
				.discontinued(discontinued).company(company).build();
		assertTrue(computer.getId() == id);
		assertTrue(computer.getName() == name);
		assertTrue(computer.getIntroduced() == introduced);
		assertTrue(computer.getDiscontinued() == discontinued);
		assertTrue(computer.getCompany() == company);
		assertEquals(computer, computerEqual);
		assertTrue(computer.hashCode() == computerEqual.hashCode());
		assertEquals(computer.toString(), computerEqual.toString());
	}

}
