package com.liferay.mobile.screens.filedisplay;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import java.util.Map;

/**
 * @author Sarai Díaz García
 */
public class FileEntry extends AssetEntry {

	public FileEntry(Map<String, Object> map) {
		super(map);
		_map = (Map<String, Object>) map.get("object");
		_values = (Map<String, Object>) _map.get("fileEntry");
	}

	public String getTitle() {
		return (String) _values.get("title");
	}

	public String getUrl() {
		String url = (String) _map.get("url");
		int index = url.lastIndexOf("/");
		String result = url.substring(0, index);
		return result;
	}

	private final Map<String, Object> _values;
	private final Map<String, Object> _map;
}
