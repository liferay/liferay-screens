package com.liferay.mobile.screens.comment.display.interactor.delete;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentDeleteEvent extends JSONObjectEvent {

	public CommentDeleteEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public CommentDeleteEvent(int targetScreenletId, JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);
	}
}
