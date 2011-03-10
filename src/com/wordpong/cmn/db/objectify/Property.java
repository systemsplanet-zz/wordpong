package com.wordpong.cmn.db.objectify;

import java.io.Serializable;

public class Property implements Serializable {

	private static final long serialVersionUID = 1L;
	private String propertyName;
	private Serializable propertyValue;

	@SuppressWarnings("unused")
	private Property() {
	}

	public Property(String propertyName, Serializable propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setPropertyValue(Serializable propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Serializable getPropertyValue() {
		return propertyValue;
	}
}
