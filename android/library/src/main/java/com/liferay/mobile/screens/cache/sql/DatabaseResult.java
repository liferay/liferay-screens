package com.liferay.mobile.screens.cache.sql;

/**
 * @author Javier Gamarra
 */
public class DatabaseResult {

	public DatabaseResult(Object object, Long insertedId) {
		_object = object;
		_insertedId = insertedId;
		_error = false;
	}

	public DatabaseResult(boolean error) {
		_error = error;
	}

	public Long getInsertedId() {
		return _insertedId;
	}

	public void setInsertedId(Long insertedId) {
		_insertedId = insertedId;
	}

	public Object getObject() {
		return _object;
	}

	public void setObject(Object object) {
		_object = object;
	}

	public boolean hasError() {
		return _error;
	}

	public void setError(boolean error) {
		_error = error;
	}

	private Long _insertedId;
	private Object _object;
	private boolean _error;
}
