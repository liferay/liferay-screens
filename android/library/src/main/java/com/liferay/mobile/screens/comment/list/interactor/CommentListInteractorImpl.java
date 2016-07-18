package com.liferay.mobile.screens.comment.list.interactor;

import android.support.annotation.NonNull;
import android.util.Pair;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.comment.list.CommentListListener;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.util.LiferayLocale;
import java.util.Locale;
import org.json.JSONException;

/**
 * @author Alejandro Hernández
 */
public class CommentListInteractorImpl
	extends BaseListInteractor<CommentEntry, CommentListListener>
	implements CommentListInteractor {

	public CommentListInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override public void loadRows(long groupId, String className, long classPK, int startRow, int endRow)
		throws Exception {
		this._groupId = groupId;
		this._className = className;
		this._classPK = classPK;

		Locale locale = LiferayLocale.getDefaultLocale();

		processWithCache(startRow, endRow, locale);
	}

	@NonNull @Override protected CommentEntry getElement(TableCache tableCache) throws JSONException {
		return null;
	}

	@Override protected String getContent(CommentEntry object) {
		return null;
	}

	@Override protected BaseListCallback<CommentEntry> getCallback(Pair<Integer, Integer> rowsRange, Locale locale) {
		return null;
	}

	@Override protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale)
		throws Exception {

	}

	@Override protected void getPageRowCountRequest(Session session) throws Exception {

	}

	@Override protected boolean cached(Object... args) throws Exception {
		return false;
	}

	@Override protected void storeToCache(BaseListEvent event, Object... args) {

	}

	private long _groupId;
	private String _className;
	private long _classPK;
}
