package com.liferay.mobile.screens.ddl.form;

public enum EventType {

	FORM_ENTER("form-enter"), FORM_SUBMIT("form-submit"), FORM_LEAVE("form-leave"), FORM_CANCEL(
		"form-cancel"), FIELD_ENTER("form-field-enter"), FIELD_LEAVE("form-field-leave"), FIELD_EXHAUSTED(
		"form-field-exhaust"), APP_START("app_start"), APP_END("app_end"), FORM_SCROLL("form-scroll");

	private final String name;

	EventType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}