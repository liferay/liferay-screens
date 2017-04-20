package com.liferay.mobile.screens.ddl.model;

import java.util.List;

public class Page {

	private String title;
	private String description;
	private List<Field> fields;
	private int number;

	public Page() {
		super();
	}

	public Page(int number, String title, String description, List<Field> fields) {
		this.number = number;
		this.title = title;
		this.description = description;
		this.fields = fields;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<Field> getFields() {
		return fields;
	}

	public int getNumber() {
		return number;
	}

	public String getTitle() {
		return title;
	}
}