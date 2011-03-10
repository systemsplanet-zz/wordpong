package com.wordpong.cmn.db.objectify;

import java.io.Serializable;

public class Filter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String filterCondition;
	private Serializable filterValue;

	@SuppressWarnings("unused")
	private Filter() {
	}

	public Filter(String filterCondition, Serializable filterValue) {
		this.filterCondition = filterCondition;
		this.filterValue = filterValue;
	}

	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}

	public void setFilterValue(Serializable filterValue) {
		this.filterValue = filterValue;
	}

	public String getFilterCondition() {
		return filterCondition;
	}

	public Serializable getFilterValue() {
		return filterValue;
	}

}
