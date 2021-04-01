package com.excilys.cdb.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Page<E> {

	private List<E> contentPage;

	private int pageInt;
	private int pageInitial;
	private int objetPerPage;
	private int indexDebut;
	private int indexFin;
	private String orderAttribute;
	private String orderSort;

	public Page() {
		this.pageInt = 0;
		this.pageInitial = 0;
		this.contentPage = new ArrayList<E>();
		this.objetPerPage = 10;
		this.indexDebut = 0;
		this.indexFin = 0;
		this.orderAttribute = "id";
		this.orderSort = "asc";
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

	public List<E> getContentPage() {
		return contentPage;
	}

	public void setContentPage(List<E> contentPage) {
		this.contentPage = contentPage;
	}

	public int getPageInt() {
		return pageInt;
	}

	public void setPageInt(int page) {
		this.pageInt = page;
	}

	public int getPageInitial() {
		return pageInitial;
	}

	public void setPageInitial(int pageInitial) {
		this.pageInitial = pageInitial;
	}

	public int getObjetPerPage() {
		return objetPerPage;
	}

	public void setObjetPerPage(int objetPerPage) {
		this.objetPerPage = objetPerPage;
	}

	public void setIndex(int max) {
		int pageIndex = this.pageInt + 1;
		if (max > 5) {

			if (pageIndex <= 3) {
				indexDebut = 1;
				indexFin = 5;
			} else if (pageIndex > max - 3) {
				indexDebut = max - 4;
				indexFin = max;
			} else {
				indexDebut = pageIndex - 2;
				indexFin = pageIndex + 2;
			}
		} else {
			indexDebut = 1;
			indexFin = max;
		}
	}

	public int getIndexDebut() {
		return indexDebut;
	}

	public void setIndexDebut(int indexDebut) {
		this.indexDebut = indexDebut;
	}

	public int getIndexFin() {
		return indexFin;
	}

	public void setIndexFin(int indexFin) {
		this.indexFin = indexFin;
	}
}
