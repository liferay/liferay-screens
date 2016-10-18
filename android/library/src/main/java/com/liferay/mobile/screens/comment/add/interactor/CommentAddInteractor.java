package com.liferay.mobile.screens.comment.add.interactor;

import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.add.CommentAddListener;
import com.liferay.mobile.screens.comment.add.CommentAddScreenlet;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.service.v70.ScreenscommentService;
import com.liferay.mobile.screens.util.JSONUtil;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentAddInteractor extends BaseCacheWriteInteractor<CommentAddListener, CommentEvent> {

	@Override
	public CommentEvent execute(CommentEvent event) throws Exception {

		String className = event.getClassName();
		long classPK = event.getClassPK();
		String body = event.getBody();

		validate(className, classPK, body);

		JSONObject jsonObject = new ScreenscommentService(getSession()).addComment(className, classPK, body);

		event.setCommentEntry(new CommentEntry(JSONUtil.toMap(jsonObject)));
		return event;
	}

	@Override
	public void onSuccess(CommentEvent event) throws Exception {
		getListener().onAddCommentSuccess(event.getCommentEntry());
	}

	@Override
	public void onFailure(CommentEvent event) {
		getListener().error(event.getException(), CommentAddScreenlet.DEFAULT_ACTION);
	}

	protected void validate(String className, long classPK, String body) {
		if (body.isEmpty()) {
			throw new IllegalArgumentException("comment body cannot be empty");
		} else if (className.isEmpty()) {
			throw new IllegalArgumentException("className cannot be empty");
		} else if (classPK <= 0) {
			throw new IllegalArgumentException("classPK must be greater than 0");
		}
	}
}