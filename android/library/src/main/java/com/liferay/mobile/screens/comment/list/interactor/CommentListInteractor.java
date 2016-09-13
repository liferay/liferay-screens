package com.liferay.mobile.screens.comment.list.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.service.v70.ScreenscommentService;
import java.util.Map;
import org.json.JSONArray;

import static com.liferay.mobile.screens.cache.Cache.SEPARATOR;

/**
 * @author Alejandro Hernández
 */
public class CommentListInteractor extends BaseListInteractor<BaseListInteractorListener<CommentEntry>, CommentEvent> {

	@Override
	protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {

		String className = (String) args[0];
		long classPK = (long) args[1];

		validate(className, classPK);

		return getService(getSession()).getComments(className, classPK, query.getStartRow(), query.getEndRow());
	}

	@Override
	protected Integer getPageRowCountRequest(Object... args) throws Exception {

		String className = (String) args[0];
		long classPK = (long) args[1];

		validate(className, classPK);

		return getService(getSession()).getCommentsCount(className, classPK);
	}

	@Override
	protected CommentEvent createEntity(Map<String, Object> stringObjectMap) {
		CommentEntry commentEntry = new CommentEntry(stringObjectMap);

		return new CommentEvent(commentEntry.getCommentId(), commentEntry.getBody(), commentEntry);
	}

	@Override
	protected String getIdFromArgs(Object... args) {
		String className = (String) args[0];
		long classPK = (long) args[1];

		return className + SEPARATOR + classPK;
	}

	protected void validate(String className, long classPK) {
		if (className.isEmpty()) {
			throw new IllegalArgumentException("className cannot be empty");
		} else if (classPK <= 0) {
			throw new IllegalArgumentException("classPK must be greater than 0");
		}
	}

	private ScreenscommentService getService(Session session) {
		return new ScreenscommentService(session);
	}
}
