package com.liferay.mobile.screens.ddl.form.interactor;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.ddl.model.FileField;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormFileEvent extends JSONObjectEvent {

	public DDLFormFileEvent(int targetScreenletId, JSONObject jsonObject, FileField FileField) {
		super(targetScreenletId, jsonObject);

		_FileField = FileField;
	}

	public FileField getFileField() {
		return _FileField;
	}

	private FileField _FileField;
}
