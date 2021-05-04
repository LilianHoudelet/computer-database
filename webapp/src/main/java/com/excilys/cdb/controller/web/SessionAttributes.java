package com.excilys.cdb.controller.web;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionAttributes {
	private String orderAttribute;
	private String orderSort;
	private String nbObject;
	private String pageno;

	public String getPageno() {
		return pageno;
	}

	public void setPageno(String pageno) {
		this.pageno = pageno;
	}

	public String getNbObject() {
		return nbObject;
	}

	public void setNbObject(String nbObject) {
		this.nbObject = nbObject;
	}

	public String getOrderAttribute() {
		return orderAttribute;
	}

	public void setOrderAttribute(String orderAttribute) {
		this.orderAttribute = orderAttribute;
	}

	public String getOrderSort() {
		return orderSort;
	}

	public void setOrderSort(String orderSort) {
		this.orderSort = orderSort;
	}

}
