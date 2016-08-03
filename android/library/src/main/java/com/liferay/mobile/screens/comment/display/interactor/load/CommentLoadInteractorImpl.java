package com.liferay.mobile.screens.comment.display.interactor.load;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseCachedRemoteInteractor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;

/**
 * @author Alejandro Hernández
 */
public class CommentLoadInteractorImpl extends BaseCachedRemoteInteractor<CommentDisplayInteractorListener, CommentLoadEvent>
	implements CommentLoadInteractor {

	public CommentLoadInteractorImpl(int screenletId, OfflinePolicy offlinePolicy) {
		super(screenletId, offlinePolicy);
	}

	@Override public void load(long groupId, long commentId) throws Exception {
		validate(groupId, commentId);

		processWithCache(groupId, commentId);
	}

	public void onEvent(CommentLoadEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onLoadCommentFailure(event.getException());
		}
		else {
			getListener().onLoadCommentSuccess(event.getCommentEntry());
		}
	}

	@Override
	protected void online(Object[] args) throws Exception {

		long groupId = (long) args[0];
		long commentId = (long) args[1];

		getCommentService().getComment(groupId, commentId);
	}

	@Override protected boolean cached(Object... args) throws Exception {
		return false;
	}

	@Override protected void storeToCache(CommentLoadEvent event, Object... args) {

	}

	@Override protected void notifyError(CommentLoadEvent event) {
		getListener().onLoadCommentFailure(event.getException());
	}

	protected CommentmanagerjsonwsService getCommentService() {
		Session session = SessionContext.createSessionFromCurrentSession();

		session.setCallback(new CommentLoadCallback(getTargetScreenletId()));

		return new CommentmanagerjsonwsService(session);
	}

	private void validate(long groupId, long commentId) {
		if (groupId <= 0) {
			throw new IllegalArgumentException("groupId must be greater than 0");
		} else if (commentId <= 0) {
			throw new IllegalArgumentException("commentId cannot be 0 or negative");
		}
	}
}
