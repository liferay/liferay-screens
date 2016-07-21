package com.liferay.mobile.screens.comment.interactor.update;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentUpdateEvent extends JSONObjectEvent {

	public CommentUpdateEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public CommentUpdateEvent(int targetScreenletId, JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);
	}
}
