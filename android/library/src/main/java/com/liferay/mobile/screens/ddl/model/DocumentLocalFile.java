package com.liferay.mobile.screens.ddl.model;

/**
 * @author Javier Gamarra
 */
public class DocumentLocalFile extends DocumentFile {

	private final String path;

	public DocumentLocalFile(String path) {
		this.path = path;
	}

	@Override
	public String toData() {
		throw new AssertionError("it's not possible to sent the local file, so fast-fail");
	}

	@Override
	public String toString() {
		return path;
	}

	@Override
	public boolean isValid() {
		return path != null && !path.isEmpty();
	}
}