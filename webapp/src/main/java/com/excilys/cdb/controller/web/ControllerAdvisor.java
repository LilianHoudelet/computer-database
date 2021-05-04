package com.excilys.cdb.controller.web;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.exception.InputException;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.validator.ComputerValidatorAdd;
import com.excilys.cdb.validator.ComputerValidatorEdit;

@ControllerAdvice
public class ControllerAdvisor {
	@Autowired
	private ComputerValidatorAdd computerValidatorAdd;
	@Autowired
	private ComputerValidatorEdit computerValidatorEdit;

	@InitBinder("computerDTOAdd")
	private void initBinderComputerDTOAdd(WebDataBinder binder) {
		binder.setValidator(computerValidatorAdd);
	}

	@InitBinder("computerDTOEdit")
	private void initBinderComputerDTOEdit(WebDataBinder binder) {
		binder.setValidator(computerValidatorEdit);
	}

	@ExceptionHandler(InputException.class)
	public ModelAndView handleNodataFoundException(InputException e) {
		LoggerCdb.logError(getClass(), e, "No computers or companies that contain \"" + e.getMessage() + "\" found");
		ModelAndView mav = new ModelAndView();
		mav.addObject("timestamp", LocalDateTime.now());
		mav.addObject("errorOccured", "error.dataNotFoundException");
		mav.addObject("value", e.getMessage());
		mav.setViewName("error");
		return mav;
	}

}
