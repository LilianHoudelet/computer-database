package com.excilys.cdb.validator;

import java.time.LocalDate;

import com.excilys.cdb.exception.ValidatorException;
import com.excilys.cdb.model.Computer;

public class ComputerValidator {

	private ComputerValidator() {
	}

	private static ComputerValidator INSTANCE = null;

	public static ComputerValidator getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ComputerValidator();
		}
		return INSTANCE;
	}

	public boolean validationComputer(Computer computer) throws ValidatorException {
		boolean validate = true;
		String name = computer.getName();
		LocalDate introduced = computer.getIntroduced();
		LocalDate discontinued = computer.getDiscontinued();

		if ("".equals(name) || null == name || "\\s".equals(name)) {
			throw new ValidatorException(ComputerValidatorError.NONAME.getMessage());
		}

		if (null != discontinued) {
			if (null != introduced) {
				if (introduced.isAfter(discontinued) || introduced.equals(discontinued)) {
					throw new ValidatorException(ComputerValidatorError.INTROBEFOREDISCON.getMessage());
				}
			} else {
				throw new ValidatorException(ComputerValidatorError.NOINTRO.getMessage());
			}
		}
		return validate;
	}
}
