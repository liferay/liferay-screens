package com.liferay.mobile.screens.ddl.form.interactor;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.ddl.model.DocumentField;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormDocumentEvent extends JSONObjectEvent {

	public DDLFormDocumentEvent(
		int targetScreenletId, JSONObject jsonObject, DocumentField documentField) {

		super(targetScreenletId, jsonObject);

		_documentField = documentField;
	}

	public DDLFormDocumentEvent(int targetScreenletId, Exception e, DocumentField documentField) {
		super(targetScreenletId, e);

		_documentField = documentField;
	}

	public DocumentField getDocumentField() {
		return _documentField;
	}

	private DocumentField _documentField;

}
