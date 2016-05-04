package com.liferay.mobile.screens.base.thread.interactors;

import com.liferay.mobile.screens.base.thread.event.JSONThreadObjectEvent;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormLoadRecordThreadEvent extends JSONThreadObjectEvent {

	public DDLFormLoadRecordThreadEvent(Exception e) {
		super(e);
	}

	public DDLFormLoadRecordThreadEvent(JSONObject jsonObject, Record record) {
		super(jsonObject);
		_record = record;
	}

	@Override
	public String getId() {
		return null;
	}

	public Record getRecord() {
		return _record;
	}

	private Record _record;
}
