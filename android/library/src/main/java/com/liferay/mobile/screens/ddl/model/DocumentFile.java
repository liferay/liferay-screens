package com.liferay.mobile.screens.ddl.model;

import com.liferay.mobile.screens.util.AndroidUtil;
import java.io.Serializable;

/**
 * @author Javier Gamarra
 */
public abstract class DocumentFile implements Serializable {

	public abstract String toData();

	public abstract boolean isValid();

	public abstract String getFileName();
}
