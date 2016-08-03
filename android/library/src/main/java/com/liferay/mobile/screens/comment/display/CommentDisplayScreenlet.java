package com.liferay.mobile.screens.comment.display;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.comment.display.interactor.delete.CommentDeleteInteractor;
import com.liferay.mobile.screens.comment.display.interactor.delete.CommentDeleteInteractorImpl;
import com.liferay.mobile.screens.comment.display.interactor.load.CommentLoadInteractor;
import com.liferay.mobile.screens.comment.display.interactor.load.CommentLoadInteractorImpl;
import com.liferay.mobile.screens.comment.display.interactor.update.CommentUpdateInteractor;
import com.liferay.mobile.screens.comment.display.interactor.update.CommentUpdateInteractorImpl;
import com.liferay.mobile.screens.comment.display.view.CommentDisplayViewModel;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentDisplayScreenlet extends BaseScreenlet<CommentDisplayViewModel, Interactor>
	implements CommentDisplayInteractorListener {

	public static final String DELETE_COMMENT_ACTION = "delete_comment";
	public static final String UPDATE_COMMENT_ACTION = "update_comment";

	public CommentDisplayScreenlet(Context context) {
		super(context);
	}

	public CommentDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void refreshView() {
		getViewModel().refreshView();
	}

	@Override
	protected void onScreenletAttached() {
		if (!isInEditMode()) {
			getViewModel().setEditable(_editable);
		}

		if (_autoLoad) {
			autoLoad();
		}
	}

	protected void autoLoad() {
		if (_commentId != 0 && SessionContext.isLoggedIn()) {
			load();
		}
	}

	public void load() {
		performUserAction(BaseScreenlet.DEFAULT_ACTION);
	}

	@Override protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.CommentDisplayScreenlet, 0, 0);

		_autoLoad = typedArray.getBoolean(R.styleable.CommentDisplayScreenlet_autoLoad, true);

		_className = typedArray.getString(R.styleable.CommentDisplayScreenlet_className);

		_classPK = castToLong(typedArray.getString(R.styleable.CommentDisplayScreenlet_classPK));

		_commentId = castToLong(typedArray.getString(
			R.styleable.CommentDisplayScreenlet_commentId));

		_editable = typedArray.getBoolean(R.styleable.CommentDisplayScreenlet_editable, true);

		_groupId = castToLongOrUseDefault(typedArray.getString(
			R.styleable.CommentDisplayScreenlet_groupId), LiferayServerContext.getGroupId());

		int offlinePolicy = typedArray.getInt(R.styleable.CommentDisplayScreenlet_offlinePolicy,
			OfflinePolicy.REMOTE_ONLY.ordinal());
		_offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		int layoutId = typedArray.getResourceId(
			R.styleable.CommentDisplayScreenlet_layoutId, getDefaultLayoutId());

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override protected Interactor createInteractor(String actionName) {
		switch (actionName) {
			case DELETE_COMMENT_ACTION:
				return new CommentDeleteInteractorImpl(getScreenletId());
			case UPDATE_COMMENT_ACTION:
				return new CommentUpdateInteractorImpl(getScreenletId());
			default:
				return new CommentLoadInteractorImpl(getScreenletId(), _offlinePolicy);
		}
	}

	@Override
	protected void onUserAction(String actionName, Interactor interactor, Object... args) {
		switch (actionName) {
			case DELETE_COMMENT_ACTION:
				try {
					((CommentDeleteInteractor) interactor).deleteComment(_commentId);
				} catch (Exception e) {
					onDeleteCommentFailure(e);
				}
				break;
			case UPDATE_COMMENT_ACTION:
				String body = (String) args[0];
				try {
					((CommentUpdateInteractor) interactor).updateComment(
						_groupId, _className, _classPK, _commentId, body);
				} catch (Exception e) {
					onUpdateCommentFailure(e);
				}
				break;
			default:
				try {
					((CommentLoadInteractor) interactor).load(
						_groupId, _commentId);
				} catch (Exception e) {
					onLoadCommentFailure(e);
				}
				break;
		}
	}

	@Override public void onLoadCommentFailure(Exception e) {
		if (getListener() != null) {
			getListener().onLoadCommentFailure(_commentId, e);
		}

		getViewModel().showFailedOperation(DEFAULT_ACTION, e);
	}

	@Override public void onLoadCommentSuccess(CommentEntry commentEntry) {
		if (getListener() != null) {
			getListener().onLoadCommentSuccess(commentEntry);
		}

		setCommentEntry(commentEntry);
	}

	@Override public void onDeleteCommentFailure(Exception e) {
		if (getListener() != null) {
			getListener().onDeleteCommentFailure(_commentEntry, e);
		}

		getViewModel().showFailedOperation(DELETE_COMMENT_ACTION, e);
	}

	@Override public void onDeleteCommentSuccess() {
		if (getListener() != null) {
			getListener().onDeleteCommentSuccess(_commentEntry);
		}

		getViewModel().showFinishOperation(DELETE_COMMENT_ACTION);
	}

	@Override public void onUpdateCommentFailure(Exception e) {
		if (getListener() != null) {
			getListener().onUpdateCommentFailure(_commentEntry, e);
		}

		getViewModel().showFailedOperation(UPDATE_COMMENT_ACTION, e);
	}

	@Override public void onUpdateCommentSuccess(CommentEntry commentEntry) {
		if (getListener() != null) {
			getListener().onUpdateCommentSuccess(commentEntry);
		}

		setCommentEntry(commentEntry);
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

	public CommentDisplayListener getListener() {
		return _listener;
	}

	public void setListener(CommentDisplayListener listener) {
		_listener = listener;
	}

	public CommentEntry getCommentEntry() {
		return _commentEntry;
	}

	public void setCommentEntry(CommentEntry commentEntry) {
		_commentEntry = commentEntry;
		_commentId = commentEntry == null ? 0 : commentEntry.getCommentId();
		getViewModel().showFinishOperation(_commentEntry);
	}

	public long getCommentId() {
		return _commentId;
	}

	public void setCommentId(long commentId) {
		_commentId = commentId;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy offlinePolicy) {
		this._offlinePolicy = offlinePolicy;
	}

	public boolean isEditable() {
		return _editable;
	}

	public void setEditable(boolean editable) {
		_editable = editable;
		getViewModel().setEditable(_editable);
	}

	private CommentDisplayListener _listener;

	private CommentEntry _commentEntry;
	private long _commentId;
	private String _className;
	private long _classPK;
	private long _groupId;
	private OfflinePolicy _offlinePolicy;
	private boolean _autoLoad;
	private boolean _editable;
}
