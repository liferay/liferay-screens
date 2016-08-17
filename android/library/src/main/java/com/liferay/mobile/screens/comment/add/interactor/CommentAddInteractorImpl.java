package com.liferay.mobile.screens.comment.add.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.thread.BaseRemoteInteractorNew;
import com.liferay.mobile.screens.comment.add.CommentAddListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;
import com.liferay.mobile.screens.util.JSONUtil;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentAddInteractorImpl extends BaseRemoteInteractorNew<CommentAddListener, CommentAddEvent> {

	@Override
	public CommentAddEvent execute(Object... args) throws Exception {

		long groupId = (long) args[0];
		String className = (String) args[1];
		long classPK = (long) args[2];
		String body = (String) args[3];

		validate(groupId, className, classPK, body);

		Session session = SessionContext.createSessionFromCurrentSession();
		CommentmanagerjsonwsService service = new CommentmanagerjsonwsService(session);

		JSONObject jsonObject = service.addComment(groupId, className, classPK, body);
		return new CommentAddEvent(body, new CommentEntry(JSONUtil.toMap(jsonObject)));
	}

	@Override
	public void onSuccess(CommentAddEvent event) throws Exception {
		getListener().onAddCommentSuccess(event.getCommentEntry());
	}

	@Override
	public void onFailure(Exception e) {
		getListener().onAddCommentFailure(e);
	}

	protected void validate(long groupId, String className, long classPK, String body) {

		if (body.isEmpty()) {
			throw new IllegalArgumentException("comment body cannot be empty");
		} else if (groupId <= 0) {
			throw new IllegalArgumentException("groupId must be greater than 0");
		} else if (className.isEmpty()) {
			throw new IllegalArgumentException("className cannot be empty");
		} else if (classPK <= 0) {
			throw new IllegalArgumentException("classPK must be greater than 0");
		}
	}
}