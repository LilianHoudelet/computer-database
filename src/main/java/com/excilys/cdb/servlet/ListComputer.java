package com.excilys.cdb.servlet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.dto.ComputerDTOList;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.PageService;

/**
 * Servlet implementation class ListComputer.
 */
@Controller
public class ListComputer {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private PageService pageService;
	@Autowired
	private MapperComputer mapperComputer;
	@Autowired
	private SessionAttributes sessionAttributes;

	@GetMapping(value = "/ListComputer")
	protected ModelAndView viewDashboard(@RequestParam(required = false) String pageno,
			@RequestParam(required = false) String search) {

		Page<Computer> page = dashboardPageHandler(pageno, search);
		return dashboardModelAndViewHandler(search, page);
	}

	private Page<Computer> dashboardPageHandler(String pageno, String search) {
		Page<Computer> page = new Page<Computer>();
		setOrderBy(page);
		setPageInt(page, pageno);
		setObjectPerPage(page);
		if (search != null) {
			page.setNbComputer(this.computerService.searchNameCount(search));
			page.setContentPage(this.pageService.searchNamePagination(page, search));

		} else {
			page.setNbComputer(this.computerService.countComputer());
			page.setContentPage(this.pageService.searchAllComputerPagination(page));
		}
		page.setPageMax();
		page.setIndex();
		return page;
	}

	private ModelAndView dashboardModelAndViewHandler(String search, Page<Computer> page) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pageMax", page.getPageMax());
		List<ComputerDTOList> listeComputers = page.getContentPage().stream()
				.map(c -> mapperComputer.mapFromModelToDTOList(c)).collect(Collectors.toList());
		modelAndView.addObject("listeComputers", listeComputers);
		modelAndView.setViewName("dashboard");
		modelAndView.addObject("numeroPage", page.getPageInt() + 1);
		modelAndView.addObject("countComputer", page.getNbComputer());
		if (search != null) {
			modelAndView.addObject("search", search);
		}
		modelAndView.addObject("indexDebut", page.getIndexDebut());
		modelAndView.addObject("indexFin", page.getIndexFin());
		return modelAndView;
	}

	private void setOrderBy(Page<Computer> page) {
		if (sessionAttributes.getOrderAttribute() != null) {
			page.setOrderAttribute(sessionAttributes.getOrderAttribute());
		} else {
			sessionAttributes.setOrderAttribute("id");
		}
		if (sessionAttributes.getOrderSort() != null) {
			page.setOrderSort(sessionAttributes.getOrderSort());
		} else {
			sessionAttributes.setOrderSort("asc");
		}

	}

	private void setPageInt(Page<Computer> page, String pageno) {
		if (pageno == null) {
			sessionAttributes.setPageno("1");
			pageno = "1";
		}
		int numeroPage = Integer.parseInt(pageno);
		page.setPageInt(numeroPage - 1);

	}

	private void setObjectPerPage(Page<Computer> page) {
		String stringNombreObjet = sessionAttributes.getNbObject();
		if (stringNombreObjet == null) {
			stringNombreObjet = "10";
		}
		page.setObjetPerPage(Integer.parseInt(stringNombreObjet));
	}

	@PostMapping(value = "/ListComputer")
	protected RedirectView changeNumberObject(@RequestParam(required = false) String nbObject) {
		sessionAttributes.setNbObject(nbObject);
		return new RedirectView("/ListComputer", true);
	}

}
