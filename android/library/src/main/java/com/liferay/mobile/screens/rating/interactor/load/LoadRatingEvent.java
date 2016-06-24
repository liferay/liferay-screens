package com.liferay.mobile.screens.rating.interactor.load;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class LoadRatingEvent extends JSONObjectEvent {
	public LoadRatingEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public LoadRatingEvent(int targetScreenletId, JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);
	}
}
