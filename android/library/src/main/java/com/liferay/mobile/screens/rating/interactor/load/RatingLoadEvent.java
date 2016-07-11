package com.liferay.mobile.screens.rating.interactor.load;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingLoadEvent extends JSONObjectEvent {
	public RatingLoadEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public RatingLoadEvent(int targetScreenletId, JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);
	}
}
