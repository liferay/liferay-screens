package com.liferay.mobile.screens.comment.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.comment.list.interactor.CommentListInteractorListener;
import com.liferay.mobile.screens.comment.list.interactor.add.CommentAddInteractor;
import com.liferay.mobile.screens.comment.list.interactor.add.CommentAddInteractorImpl;
import com.liferay.mobile.screens.comment.list.interactor.list.CommentListInteractor;
import com.liferay.mobile.screens.comment.list.interactor.list.CommentListInteractorImpl;
import com.liferay.mobile.screens.comment.list.view.CommentListViewModel;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.models.CommentEntry;
import java.util.Locale;

/**
 * @author Alejandro Hernández
 */
public class CommentListScreenlet extends BaseListScreenlet<CommentEntry, Interactor>
	implements CommentListInteractorListener {

	public static final String ADD_COMMENT_ACTION = "add_comment";

	public CommentListScreenlet(Context context) {
		super(context);
	}

	public CommentListScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommentListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CommentListScreenlet(Context context, AttributeSet attrs, int defStyleAttr,
		int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void loadRows(Interactor interactor, int startRow, int endRow, Locale locale)
		throws Exception {
		((CommentListInteractor) interactor).loadRows(
			_groupId, _className, _classPK, startRow, endRow);
	}

	@Override protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme()
			.obtainStyledAttributes(attributes, R.styleable.CommentListScreenlet, 0, 0);

		_className = typedArray.getString(R.styleable.CommentListScreenlet_className);

		_classPK = castToLong(typedArray.getString(R.styleable.CommentListScreenlet_classPK));

		Integer offlinePolicy =
			typedArray.getInteger(R.styleable.CommentListScreenlet_offlinePolicy,
				OfflinePolicy.REMOTE_ONLY.ordinal());
		_offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		long groupId = LiferayServerContext.getGroupId();

		_groupId =
			castToLongOrUseDefault(typedArray.getString(R.styleable.CommentListScreenlet_groupId),
				groupId);

		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	@Override
	protected void onUserAction(String actionName, Interactor interactor, Object... args) {
		switch (actionName) {
			case ADD_COMMENT_ACTION:
				String body = (String) args[0];
				try {
					((CommentAddInteractor) interactor).addComment(
						_groupId, _className, _classPK, body);
				} catch (Exception e) {
					onAddCommentFailure(body, e);
				}
			default:
				break;
		}
	}

	@Override protected Interactor createInteractor(String actionName) {
		switch (actionName) {
			case ADD_COMMENT_ACTION:
				return new CommentAddInteractorImpl(getScreenletId());
		}
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

	@Override public void onAddCommentFailure(String body, Exception e) {
		if (getCommentListListener() != null) {
			getCommentListListener().onAddCommentFailure(body, e);
		}
		loadPage(0);
	}

	@Override public void onAddCommentSuccess(CommentEntry commentEntry) {
		if (getCommentListListener() != null) {
			getCommentListListener().onAddCommentSuccess(commentEntry);
		}
		loadPage(0);
	}

	@Override public void onDeleteCommentFailure(long commentId, Exception e) {
		if (getCommentListListener() != null) {
			getCommentListListener().onDeleteCommentFailure(commentId, e);
		}
		loadPage(0);
	}

	@Override public void onDeleteCommentSuccess(long commentId) {
		if (getCommentListListener() != null) {
			getCommentListListener().onDeleteCommentSuccess(commentId);
		}
		loadPage(0);
	}

	@Override public void onUpdateCommentSuccess(long commentId) {
		if (getCommentListListener() != null) {
			getCommentListListener().onUpdateCommentSuccess(commentId);
		}
		loadPage(0);
	}

	@Override public void onUpdateCommentFailure(long commentId, Exception e) {
		if (getCommentListListener() != null) {
			getCommentListListener().onUpdateCommentFailure(commentId, e);
		}
		loadPage(0);
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

	private CommentListListener getCommentListListener() {
		return (CommentListListener) getListener();
	}

	private CommentListViewModel getCommentListViewModel() {
		return (CommentListViewModel) getViewModel();
	}

	private OfflinePolicy _offlinePolicy;
	private String _className;
	private long _classPK;
	private long _groupId;
}
