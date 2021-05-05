package com.excilys.cdb.controller.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.dto.web.CompanyDTO;
import com.excilys.cdb.dto.web.ComputerDTOEdit;
import com.excilys.cdb.dto.web.ComputerDTOList;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class EditComputer.
 */
@Controller
public class EditComputer {

	@Autowired
	private CompanyMapper mapperCompany;
	@Autowired
	private ComputerMapper mapperComputer;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;

	@GetMapping(value = "/EditComputer")
	protected ModelAndView showEditComputer(@RequestParam(required = true) String id, ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView("editComputer", "computerDTOEdit", new ComputerDTOEdit());
		modelAndView.addAllObjects(modelMap);
		List<Company> listCompanies = this.companyService.getCompanies();
		Map<String, String> mapCompanies = mapperCompany.mapFromModelListToDTOList(listCompanies).stream()
				.collect(Collectors.toMap(CompanyDTO::getId, companyDTO -> companyDTO.getName()));
		Computer computer = computerService.findComputerById(Long.parseLong(id))
				.orElseGet(() -> new Computer.ComputerBuilder(Long.parseLong(id)).build());
		ComputerDTOList computerDTO = mapperComputer.mapFromModelToDTOList(computer);
		modelAndView.addObject("id", id);
		modelAndView.addObject("name", computerDTO.getName());
		modelAndView.addObject("introduced", computerDTO.getIntroduced());
		modelAndView.addObject("discontinued", computerDTO.getDiscontinued());
		modelAndView.addObject("listCompanies", mapCompanies);
		modelAndView.addObject("companyId", computer.getCompany() != null ? computer.getCompany().getId() : null);
		return modelAndView;
	}

	@PostMapping(value = "/EditComputer")
	protected RedirectView editComputer(@Valid @ModelAttribute("computerDTOEdit") ComputerDTOEdit computerDTOEdit,
			BindingResult bindingResult, RedirectAttributes redir) {

		if (bindingResult.hasErrors()) {
			redir.addAttribute("id", computerDTOEdit.getId());
			redir.addFlashAttribute("org.springframework.validation.BindingResult.computerDTOEdit", bindingResult);
			return new RedirectView("/EditComputer", true);
		}
		Computer computer = mapperComputer.mapFromDTOEditToModel(computerDTOEdit);
		computerService.updateComputer(computer);
		redir.addFlashAttribute("computerEdited", "The computer was successfully updated");
		redir.addAttribute("id", computerDTOEdit.getId());
		return new RedirectView("/EditComputer", true);

	}

}
