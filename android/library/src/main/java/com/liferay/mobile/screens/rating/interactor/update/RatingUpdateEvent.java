package com.liferay.mobile.screens.rating.interactor.update;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingUpdateEvent extends JSONObjectEvent {
	public RatingUpdateEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public RatingUpdateEvent(int targetScreenletId, JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);
	}
}
