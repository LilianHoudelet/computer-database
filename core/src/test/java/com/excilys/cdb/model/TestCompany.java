package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestCompany {

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEqualsAndHashcode() {
		Company computer = new Company(1L,"test");
		Company computerEqual = new Company(1L,"test");
		Computer computerDiff = new Computer.ComputerBuilder(2L).build();
		assertEquals(computer, computerEqual);
		assertTrue(computer.hashCode() == computerEqual.hashCode());
		assertFalse(computer.equals(computerDiff));
	}

}
