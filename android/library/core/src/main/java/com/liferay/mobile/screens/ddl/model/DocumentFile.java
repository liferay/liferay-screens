package com.liferay.mobile.screens.ddl.model;

import java.io.Serializable;

/**
* @author Javier Gamarra
*/
public abstract class DocumentFile implements Serializable {

	public String toData() {
		return _name;
	}

	@Override
	public String toString() {
		return _name;
	}

	public abstract boolean isValid();

	protected String _name;

}
