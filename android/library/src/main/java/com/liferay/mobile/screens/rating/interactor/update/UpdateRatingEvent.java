package com.liferay.mobile.screens.rating.interactor.update;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class UpdateRatingEvent extends JSONObjectEvent {
	public UpdateRatingEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public UpdateRatingEvent(int targetScreenletId, JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);
	}
}
