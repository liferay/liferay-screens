package com.liferay.mobile.screens.ddl.form;

public enum EventType {

	FORM_ENTER("form-enter"), FORM_SUBMIT("form-submit"), FORM_LEAVE("form-leave"), FORM_CANCEL(
		"form-cancel"), FIELD_ENTER("form-field-enter"), FIELD_LEAVE("form-field-leave"), FIELD_EXHAUSTED(
		"form-field-exhaust"), APP_START("app-start"), APP_END("app-end"), FORM_SCROLL("form-scroll"), PAGE_TRANSITION(
		"page-transition");

	private final String name;

	EventType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}