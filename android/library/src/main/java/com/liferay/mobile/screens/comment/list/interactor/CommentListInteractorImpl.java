package com.liferay.mobile.screens.comment.list.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;
import java.util.Map;
import org.json.JSONArray;

/**
 * @author Alejandro Hernández
 */
public class CommentListInteractorImpl extends BaseListInteractor<CommentEntry, CommentListInteractorListener> {

	@Override
	protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {

		String className = (String) args[0];
		long classPK = (long) args[1];

		CommentmanagerjsonwsService service = getCommentsService(getSession());
		return service.getComments(groupId, className, classPK, query.getStartRow(), query.getEndRow());
	}

	@Override
	protected Integer getPageRowCountRequest(Object... args) throws Exception {

		String className = (String) args[0];
		long classPK = (long) args[1];

		CommentmanagerjsonwsService service = getCommentsService(getSession());
		return service.getCommentsCount(groupId, className, classPK);
	}

	@Override
	protected CommentEntry createEntity(Map<String, Object> stringObjectMap) {
		return new CommentEntry(stringObjectMap);
	}

	@Override
	protected String getIdFromArgs(Object... args) throws Exception {
		String className = (String) args[0];
		long classPK = (long) args[1];

		return null;
	}

	private CommentmanagerjsonwsService getCommentsService(Session session) {
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
