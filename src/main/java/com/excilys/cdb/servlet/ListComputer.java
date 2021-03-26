package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.cdb.dto.ComputerDTOList;
import com.excilys.cdb.logger.LoggerCdb;
import com.excilys.cdb.mapper.MapperComputer;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.PageService;

/**
 * Servlet implementation class ListComputer
 */
@WebServlet("/ListComputer")
public class ListComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService;
	private PageService pageService;
	private MapperComputer mapperComputer;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListComputer() {
		super();
		this.computerService = new ComputerService();
		this.pageService = new PageService();
		this.mapperComputer = new MapperComputer();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Page<Computer> page = new Page<Computer>();
		HttpSession session = request.getSession();
		setOrderBy(page, session);
		int nbComputer = countComputer(request);
		setObjectPerPage(page, session);
		int objectPerPage = page.getObjetPerPage();
		int pageMax = nbComputer / objectPerPage;
		if (nbComputer % objectPerPage != 0) {
			pageMax += 1;
		}
		setPageInt(request, page, session);
		setIndexDebutFin(page, session, pageMax);
		request.setAttribute("pageMax", pageMax);
		page.setContentPage(this.pageService.searchAllComputerPagination(page));
		List<ComputerDTOList> listeComputers = page.getContentPage().stream()
				.map(c -> mapperComputer.mapFromModelToDTOList(c)).collect(Collectors.toList());
		request.setAttribute("listeComputers", listeComputers);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

	}

	private void setOrderBy(Page<Computer> page, HttpSession session) {
		if (session.getAttribute("orderAttribute") != null) {
			LoggerCdb.logInfo(getClass(), "=========================================");
			page.setOrderAttribute((String) session.getAttribute("orderAttribute"));
		} else {
			session.setAttribute("orderAttribute", "id");
		}
		if (session.getAttribute("orderSort") != null) {
			page.setOrderSort((String) session.getAttribute("orderSort"));
		} else {
			session.setAttribute("orderSort", "asc");
		}

		LoggerCdb.logInfo(getClass(), session.getAttribute("orderAttribute") + "sessionList");
		LoggerCdb.logInfo(getClass(), session.getAttribute("orderSort") + "sessionLiset");
		LoggerCdb.logInfo(getClass(), page.getOrderAttribute() + "pageList");
		LoggerCdb.logInfo(getClass(), page.getOrderSort() + "pageList");
	}

	private void setIndexDebutFin(Page<Computer> page, HttpSession session, int pageMax) {
		page.setIndex(pageMax);
		session.setAttribute("indexDebut", page.getIndexDebut());
		session.setAttribute("indexFin", page.getIndexFin());
	}

	private void setPageInt(HttpServletRequest request, Page<Computer> page, HttpSession session) {
		String stringNumeroDePage = request.getParameter("pageno");
		if (stringNumeroDePage == null) {
			session.setAttribute("pageno", 1);
			stringNumeroDePage = "1";
		}
		int numeroPage = Integer.parseInt(stringNumeroDePage);
		request.setAttribute("numeroPage", numeroPage);
		page.setPageInt(numeroPage - 1);

	}

	private void setObjectPerPage(Page<Computer> page, HttpSession session) {
		String stringNombreObjet = (String) session.getAttribute("nbObject");
		if (stringNombreObjet == null) {
			stringNombreObjet = "10";
		}
		page.setObjetPerPage(Integer.parseInt(stringNombreObjet));
	}

	private int countComputer(HttpServletRequest request) {
		int nbComputer = this.computerService.countComputer();
		request.setAttribute("countComputer", nbComputer + "");
		return nbComputer;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String stringNombreObjet = request.getParameter("nbObject");
		session.setAttribute("nbObject", stringNombreObjet);
		doGet(request, response);
	}

}
