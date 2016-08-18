package com.liferay.mobile.screens.comment.add.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.thread.BaseCachedWriteThreadRemoteInteractor;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.add.CommentAddListener;
import com.liferay.mobile.screens.comment.add.CommentAddScreenlet;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;
import com.liferay.mobile.screens.util.JSONUtil;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentAddInteractorImpl
	extends BaseCachedWriteThreadRemoteInteractor<CommentAddListener, CommentEvent> {

	@Override
	public CommentEvent execute(CommentEvent event) throws Exception {

		String className = event.getClassName();
		long classPK = event.getClassPK();
		String body = event.getBody();

		validate(groupId, className, classPK, body);

		Session session = SessionContext.createSessionFromCurrentSession();
		CommentmanagerjsonwsService service = new CommentmanagerjsonwsService(session);

		JSONObject jsonObject = service.addComment(groupId, className, classPK, body);
		CommentEntry commentEntry = new CommentEntry(JSONUtil.toMap(jsonObject));
		return new CommentEvent(commentEntry.getCommentId(), className, classPK, body, commentEntry);
	}

	@Override
	protected CommentEvent createEvent(Object[] args) throws Exception {
		long commentId = (long) args[0];
		String className = (String) args[1];
		long classPK = (long) args[2];
		String body = (String) args[3];
		return new CommentEvent(commentId, className, classPK, body);
	}

	@Override
	public void onSuccess(CommentEvent event) throws Exception {
		getListener().onAddCommentSuccess(event.getCommentEntry());
	}

	@Override
	protected void onFailure(CommentEvent event) {
		getListener().error(event.getException(), CommentAddScreenlet.DEFAULT_ACTION);
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