package com.liferay.mobile.screens.comment.list;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.comment.list.interactor.CommentListInteractor;
import com.liferay.mobile.screens.comment.list.interactor.CommentListInteractorImpl;
import com.liferay.mobile.screens.models.CommentEntry;
import java.util.Locale;

/**
 * @author Alejandro Hernández
 */
public class CommentListScreenlet
	extends BaseListScreenlet<CommentEntry, CommentListInteractor>
	implements CommentListListener {

	public CommentListScreenlet(Context context) {
		super(context);
	}

	public CommentListScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommentListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CommentListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override protected void loadRows(CommentListInteractor interactor, int startRow, int endRow, Locale locale)
		throws Exception {
		interactor.loadRows(_groupId, _className, _classPK, startRow, endRow);
	}

	@Override protected CommentListInteractor createInteractor(String actionName) {
		return new CommentListInteractorImpl(getScreenletId(), _offlinePolicy);
	}

	@Override public void loadingFromCache(boolean success) {
		if (getListener() != null) {
			getListener().loadingFromCache(success);
		}
	}

	@Override public void retrievingOnline(boolean triedInCache, Exception e) {
		if (getListener() != null) {
			getListener().retrievingOnline(triedInCache, e);
		}
	}

	@Override public void storingToCache(Object object) {
		if (getListener() != null) {
			getListener().storingToCache(object);
		}
	}

	public OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy offlinePolicy) {
		this._offlinePolicy = offlinePolicy;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		this._className = className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		this._classPK = classPK;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		this._groupId = groupId;
	}

	private OfflinePolicy _offlinePolicy;
	private String _className;
	private long _classPK;
	private long _groupId;
}
