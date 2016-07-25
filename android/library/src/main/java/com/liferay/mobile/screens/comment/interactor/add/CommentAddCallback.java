package com.liferay.mobile.screens.comment.interactor.add;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

/**
 * @author Alejandro Hernández
 */
public class CommentAddCallback extends InteractorAsyncTaskCallback<Long> {

	public CommentAddCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public Long transform(Object obj) throws Exception {
		return Long.valueOf((String) obj);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new CommentAddEvent(targetScreenletId, e);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Long result) {
		return new CommentAddEvent(targetScreenletId, result);
	}

}
