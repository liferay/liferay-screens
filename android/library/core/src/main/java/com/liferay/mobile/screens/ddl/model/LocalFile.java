package com.liferay.mobile.screens.ddl.model;

import java.io.Serializable;

/**
 * @author Javier Gamarra
 */
public class LocalFile extends DocumentFile implements Serializable {

	public LocalFile(String name) {
		_name = name;
	}

	@Override
	public String toData() {
		return _name;
	}

}
