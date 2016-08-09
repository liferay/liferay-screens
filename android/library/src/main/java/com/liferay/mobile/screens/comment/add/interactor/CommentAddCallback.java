package com.liferay.mobile.screens.comment.add.interactor;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.util.JSONUtil;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentAddCallback extends InteractorAsyncTaskCallback<CommentEntry> {

	private final String body;

	public CommentAddCallback(String body, int targetScreenletId) {
		super(targetScreenletId);

		this.body = body;
	}

	@Override
	public CommentEntry transform(Object obj) throws Exception {
		return new CommentEntry(JSONUtil.toMap((JSONObject) obj));
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new CommentAddEvent(targetScreenletId, body, e);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, CommentEntry result) {
		return new CommentAddEvent(targetScreenletId, body, result);
	}
}
