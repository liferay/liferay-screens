package com.liferay.mobile.screens.ddl.model;

import java.io.Serializable;

/**
 * @author Javier Gamarra
 */
public class DocumentLocalFile extends DocumentFile {

	public DocumentLocalFile(String path) {
		_path = path;
	}

	@Override
	public String toData() {
		// it's not possible to sent the local file, so fast-fail
		assert false;
		return null;
	}

	@Override
	public String toString() {
		return _path;
	}

	@Override
	public boolean isValid() {
		return _path != null && !_path.isEmpty();
	}

	private String _path;

}