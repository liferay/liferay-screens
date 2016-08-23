package com.liferay.mobile.screens.blogs;

import com.liferay.mobile.screens.asset.list.AssetEntry;
import java.util.Map;

/**
 * @author Sarai Díaz García
 */

public class BlogsEntry extends AssetEntry {

	public BlogsEntry(Map<String, Object> map) {
		super(map);
	}

	@Override
	public String getTitle() {
		return super.getTitle();
	}

	public Map<String, Object> getBlogsEntry() {
		return (Map<String, Object>) _values.get("blogsEntry");
	}

	public String getSubtitle() {
		return (String) getBlogsEntry().get("subtitle");
	}

	public String getContent() {
		return (String) getBlogsEntry().get("content");
	}
}
