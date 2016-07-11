package com.liferay.mobile.screens.rating.interactor.delete;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingDeleteEvent extends JSONObjectEvent {
	public RatingDeleteEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public RatingDeleteEvent(int targetScreenletId, JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);
	}
}
