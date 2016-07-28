package com.liferay.mobile.screens.filedisplay;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import java.util.Map;

/**
 * @author Sarai Díaz García
 */
public class FileEntry extends AssetEntry {

	public FileEntry(Map<String, Object> map) {
		super(map);
	}

	public String getUrl() {
		String url = (String) _values.get("url");
		int index = url.lastIndexOf("/");
		return url.substring(0, index);
	}
}
