package com.liferay.mobile.screens.base.thread.interactors;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.screens.base.thread.event.JSONThreadObjectEvent;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormAddRecordThreadEvent extends JSONThreadObjectEvent {

	public DDLFormAddRecordThreadEvent(Record record, long groupId, JSONObject jsonObject, JSONObjectWrapper serviceContextWrapper) {
		super(jsonObject);
		_record = record;
		setGroupId(groupId);
		_serviceContextWrapper = serviceContextWrapper;

	}

	public DDLFormAddRecordThreadEvent(Record record, long groupId, JSONObject fieldsValues) {
		super(fieldsValues);
		_record = record;
		setGroupId(groupId);
	}

	public DDLFormAddRecordThreadEvent(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	public String getId() {
		return null;
	}

	public Record getRecord() {
		return _record;
	}

	public JSONObjectWrapper getServiceContextWrapper() {
		return _serviceContextWrapper;
	}

	private Record _record;
	private JSONObjectWrapper _serviceContextWrapper;
}
