package com.liferay.mobile.screens.ddl.form.interactor;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.ddl.model.DocumentField;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormFileEvent extends JSONObjectEvent {

	public DDLFormFileEvent(int targetScreenletId, JSONObject jsonObject, DocumentField documentField) {
		super(targetScreenletId, jsonObject);

		_documentField = documentField;
	}

	public DDLFormFileEvent(int targetScreenletId, Exception e, DocumentField documentField) {
		super(targetScreenletId, e);

		_documentField = documentField;
	}

	public DocumentField getFileField() {
		return _documentField;
	}

	private DocumentField _documentField;
}
