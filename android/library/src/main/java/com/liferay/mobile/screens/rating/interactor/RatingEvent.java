package com.liferay.mobile.screens.rating.interactor;

import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import org.json.JSONObject;

public class RatingEvent extends OfflineEventNew {

	private double score;
	private long classPK;
	private String className;
	private long entryId;
	private int ratingGroupCounts;

	public RatingEvent(long entryId, JSONObject jsonObject) {
		super(jsonObject);
		this.entryId = entryId;
	}

	public RatingEvent(long classPK, String className, int ratingGroupCounts, JSONObject jsonObject) {
		super(jsonObject);
		this.classPK = classPK;
		this.className = className;
		this.ratingGroupCounts = ratingGroupCounts;
	}

	public RatingEvent(long classPK, String className, int ratingGroupCounts, double score) {
		this(classPK, className, ratingGroupCounts, null);
		this.score = score;
	}

	@Override
	public String getId() throws Exception {
		return classPK + className;
	}

	public long getClassPK() {
		return classPK;
	}

	public String getClassName() {
		return className;
	}

	public int getRatingGroupCounts() {
		return ratingGroupCounts;
	}

	public double getScore() {
		return score;
	}
}