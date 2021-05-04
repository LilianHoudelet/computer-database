package com.excilys.cdb.model;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestCompanyParameterizedValide {
	private Long id;
	private String name;

	public TestCompanyParameterizedValide(Long id, String name) {
		this.id = id;
		this.name = name;

	}

	@SuppressWarnings("rawtypes")
	@Parameterized.Parameters
	public static Collection input() {
		return Arrays.asList(new Object[][] {{1L, "test" }, {null, "test"}, });
	}

	@Test
	public void testCompanyCreation() {
		Company company = new Company(id,name);
		assertTrue(company.getId() == id);
		assertTrue(company.getName() == name);

	}

}
