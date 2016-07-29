package com.liferay.mobile.screens.comment.list.interactor.update;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentUpdateCallback extends JSONObjectCallback {

	public CommentUpdateCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override public JSONObject transform(Object obj) throws Exception {
		return new JSONObject();
	}

	@Override protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new CommentUpdateEvent(targetScreenletId, e);
	}

	@Override protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
		return new CommentUpdateEvent(targetScreenletId, result);
	}
}
