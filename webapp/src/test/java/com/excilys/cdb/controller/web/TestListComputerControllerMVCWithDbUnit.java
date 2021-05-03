package com.excilys.cdb.controller.web;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.excilys.cdb.config.MyWebConfig;
import com.excilys.cdb.dbUnit.DataSourceDBUnitTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = { MyWebConfig.class })
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@DatabaseSetup("/com/excilys/cdb/dao/data.xml")
public class TestListComputerControllerMVCWithDbUnit extends DataSourceDBUnitTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Autowired
//	SessionAttributes sessionAttributes;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).apply(sharedHttpSession())
				.build();
	}

	/**
	 * Test mock servlet context injection.
	 */
	@Test
	public void testProvideBean() {
		ServletContext servletContext = webApplicationContext.getServletContext();
		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(webApplicationContext.getBean("computerValidatorAdd"));
	}

	@Test
	public void testReturnViewName() throws Exception {
		this.mockMvc.perform(get("/ListComputer")).andExpect(view().name("dashboard"));
	}

	@Test
	public void testGetModelDefault() throws Exception {
		this.mockMvc.perform(get("/ListComputer")).andExpect(status().isOk())
				.andExpect(model().attribute("numeroPage", 1)).andExpect(model().attribute("indexDebut", 1))
				.andExpect(model().attribute("indexFin", 2)).andExpect(model().attribute("lang", "en"))
				.andExpect(model().attribute("countComputer", 13)).andExpect(model().attribute("pageMax", 2))
				.andExpect(model().attributeExists("listeComputers"))
				.andExpect(model().attribute("listeComputers", hasSize(10)))
				.andExpect(model().attributeDoesNotExist("search"));
	}

	@Test
	public void testGetModelPage2() throws Exception {
		this.mockMvc.perform(get("/ListComputer?pageno=2")).andExpect(status().isOk())
				.andExpect(model().attribute("numeroPage", 2)).andExpect(model().attribute("indexDebut", 1))
				.andExpect(model().attribute("indexFin", 2)).andExpect(model().attribute("lang", "en"))
				.andExpect(model().attribute("countComputer", 13)).andExpect(model().attribute("pageMax", 2))
				.andExpect(model().attribute("listeComputers", hasSize(3)))
				.andExpect(model().attributeDoesNotExist("search"));
	}

	@Test
	public void testPost() throws Exception {
//		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
//		sessionattr.put("test", "test");

		this.mockMvc.perform(post("/ListComputer").param("nbObject", "50")).andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(request().sessionAttribute("sessionAttributes.nbObject", 50));
//				.andExpect(model().attribute("numeroPage", 1)).andExpect(model().attribute("indexDebut", 1))
//				.andExpect(model().attribute("indexFin", 1)).andExpect(model().attribute("lang", "en"))
//				.andExpect(model().attribute("countComputer", 13)).andExpect(model().attribute("pageMax", 1))
//				.andExpect(model().attribute("listeComputers", hasSize(13)))
//				.andExpect(model().attributeDoesNotExist("search"));

	}
}
