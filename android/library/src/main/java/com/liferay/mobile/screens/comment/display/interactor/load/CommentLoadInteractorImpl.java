package com.liferay.mobile.screens.comment.display.interactor.load;

import com.liferay.mobile.screens.base.thread.BaseCachedThreadRemoteInteractor;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;
import com.liferay.mobile.screens.util.JSONUtil;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentLoadInteractorImpl
	extends BaseCachedThreadRemoteInteractor<CommentDisplayInteractorListener, CommentEvent> {

	@Override
	public CommentEvent execute(Object... args) throws Exception {

		long commentId = (long) args[0];

		validate(groupId, commentId);

		CommentmanagerjsonwsService commentService = new CommentmanagerjsonwsService(getSession());
		JSONObject jsonObject = commentService.getComment(groupId, commentId);
		return new CommentEvent(jsonObject);
	}

	@Override
	public void onSuccess(CommentEvent event) throws Exception {
		CommentEntry commentEntry = new CommentEntry(JSONUtil.toMap(event.getJSONObject()));
		getListener().onLoadCommentSuccess(commentEntry);
	}

	@Override
	public void onFailure(Exception e) {
		getListener().error(e, CommentDisplayScreenlet.LOAD_COMMENT_ACTION);
	}

	@Override
	protected String getIdFromArgs(Object... args) throws Exception {
		long commentId = (long) args[0];
		return String.valueOf(commentId);
	}

	private void validate(long groupId, long commentId) {
		if (groupId <= 0) {
			throw new IllegalArgumentException("groupId must be greater than 0");
		} else if (commentId <= 0) {
			throw new IllegalArgumentException("commentId cannot be 0 or negative");
		}
	}
}
