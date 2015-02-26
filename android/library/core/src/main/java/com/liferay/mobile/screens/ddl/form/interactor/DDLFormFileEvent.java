package com.liferay.mobile.screens.ddl.form.interactor;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.ddl.model.FileField;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormFileEvent extends JSONObjectEvent {

	public DDLFormFileEvent(int targetScreenletId, JSONObject jsonObject, FileField fileField) {
		super(targetScreenletId, jsonObject);

		_fileField = fileField;
	}

	public DDLFormFileEvent(int targetScreenletId, Exception e, FileField fileField) {
		super(targetScreenletId, e);

		_fileField = fileField;
	}

	public FileField getFileField() {
		return _fileField;
	}

	private FileField _fileField;
}
