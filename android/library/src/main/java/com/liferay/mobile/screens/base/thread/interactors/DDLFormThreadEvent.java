package com.liferay.mobile.screens.base.thread.interactors;

import com.liferay.mobile.screens.base.thread.event.JSONThreadObjectEvent;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormThreadEvent extends JSONThreadObjectEvent {

	public DDLFormThreadEvent(Exception e) {
		super(e);
	}

	public DDLFormThreadEvent(JSONObject jsonObject) {
		super(jsonObject);
	}

	public DDLFormThreadEvent(JSONObject jsonObject, Record record) {
		super(jsonObject);

		_record = record;
	}

	@Override
	public String getId() {
		return String.valueOf(_record.getStructureId());
	}

	public Record getRecord() {
		return _record;
	}

	private Record _record;
}
