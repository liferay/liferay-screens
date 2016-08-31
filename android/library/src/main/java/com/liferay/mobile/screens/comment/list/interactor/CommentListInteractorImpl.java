package com.liferay.mobile.screens.comment.list.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;
import java.util.Map;
import org.json.JSONArray;

import static com.liferay.mobile.screens.cache.Cache.SEPARATOR;

/**
 * @author Alejandro Hernández
 */
public class CommentListInteractorImpl extends BaseListInteractor<CommentListInteractorListener, CommentEvent> {

	@Override
	protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {

		String className = (String) args[0];
		long classPK = (long) args[1];

		return getService(getSession()).getComments(groupId, className, classPK, query.getStartRow(),
			query.getEndRow());
	}

	@Override
	protected Integer getPageRowCountRequest(Object... args) throws Exception {

		String className = (String) args[0];
		long classPK = (long) args[1];

		return getService(getSession()).getCommentsCount(groupId, className, classPK);
	}

	@Override
	protected CommentEvent createEntity(Map<String, Object> stringObjectMap) {
		CommentEntry commentEntry = new CommentEntry(stringObjectMap);

		return new CommentEvent(commentEntry.getCommentId(), commentEntry.getClassName(), commentEntry.getClassPK(),
			commentEntry.getBody(), commentEntry);
	}

	@Override
	protected String getIdFromArgs(Object... args) {
		String className = (String) args[0];
		long classPK = (long) args[1];

		return className + SEPARATOR + classPK;
	}

	private CommentmanagerjsonwsService getService(Session session) {
		return new CommentmanagerjsonwsService(session);
	}

	protected void validate(long groupId, String className, long classPK) {
		if (groupId <= 0) {
			throw new IllegalArgumentException("groupId must be greater than 0");
		} else if (className.isEmpty()) {
			throw new IllegalArgumentException("className cannot be empty");
		} else if (classPK <= 0) {
			throw new IllegalArgumentException("classPK must be greater than 0");
		}
	}
}
