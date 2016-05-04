package com.liferay.mobile.screens.base.thread.interactors;

import com.liferay.mobile.screens.base.thread.IdCache;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class IdCacheImpl implements IdCache {

	public IdCacheImpl(String id) {
		_id = id;
	}

	public IdCacheImpl(String id, String userId, int groupId, Locale locale) {
		_id = id;
		_userId = userId;
		_groupId = groupId;
		_locale = locale;
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public String getUserId() {
		return _userId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	private String _id;
	private String _userId;
	private long _groupId;
	private Locale _locale;
}
