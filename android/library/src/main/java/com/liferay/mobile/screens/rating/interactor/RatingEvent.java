package com.liferay.mobile.screens.rating.interactor;

import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import org.json.JSONObject;

public class RatingEvent extends OfflineEventNew {

	private long classPK;
	private String className;
	private long entryId;

	public RatingEvent(long entryId, JSONObject jsonObject) {
		super(jsonObject);
		this.entryId = entryId;
	}

	public RatingEvent(long classPK, String className, JSONObject jsonObject) {
		super(jsonObject);
		this.classPK = classPK;
		this.className = className;
	}

	@Override
	public String getId() throws Exception {
		return classPK + className;
	}
}