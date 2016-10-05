package com.liferay.mobile.screens.comment.display.interactor.load;

import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.service.v70.ScreenscommentService;
import com.liferay.mobile.screens.util.JSONUtil;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentLoadInteractor extends BaseCacheReadInteractor<CommentDisplayInteractorListener, CommentEvent> {

	@Override
	public CommentEvent execute(Object... args) throws Exception {

		long commentId = (long) args[0];

		validate(commentId);

		ScreenscommentService commentService = new ScreenscommentService(getSession());
		JSONObject jsonObject = commentService.getComment(commentId);
		return new CommentEvent(jsonObject);
	}

	@Override
	public void onSuccess(CommentEvent event) throws Exception {
		CommentEntry commentEntry = new CommentEntry(JSONUtil.toMap(event.getJSONObject()));
		getListener().onLoadCommentSuccess(commentEntry);
	}

	@Override
	public void onFailure(CommentEvent event) {
		getListener().error(event.getException(), CommentDisplayScreenlet.LOAD_COMMENT_ACTION);
	}

	@Override
	protected String getIdFromArgs(Object... args) {
		long commentId = (long) args[0];
		return String.valueOf(commentId);
	}

	private void validate(long commentId) {
		if (commentId <= 0) {
			throw new IllegalArgumentException("commentId cannot be 0 or negative");
		}
	}
}
