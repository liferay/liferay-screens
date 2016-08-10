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

	public static final String DELETE_COMMENT_ACTION = "DELETE_COMMENT";
	public static final String UPDATE_COMMENT_ACTION = "UPDATE_COMMENT";
	public static final String LOAD_COMMENT_ACTION = "LOAD_COMMENT";

	public CommentDisplayScreenlet(Context context) {
		super(context);
	}

	public CommentDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommentDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CommentDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onScreenletAttached() {
		if (autoLoad) {
			autoLoad();
		}
	}

	protected void autoLoad() {
		if (commentId != 0 && SessionContext.isLoggedIn()) {
			load();
		}
	}

	public void load() {
		performUserAction(BaseScreenlet.DEFAULT_ACTION);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.CommentDisplayScreenlet, 0, 0);

		autoLoad = typedArray.getBoolean(R.styleable.CommentDisplayScreenlet_autoLoad, true);

		className = typedArray.getString(R.styleable.CommentDisplayScreenlet_className);

		classPK = castToLong(typedArray.getString(R.styleable.CommentDisplayScreenlet_classPK));

		commentId = castToLong(typedArray.getString(R.styleable.CommentDisplayScreenlet_commentId));

		editable = typedArray.getBoolean(R.styleable.CommentDisplayScreenlet_editable, true);

		groupId = castToLongOrUseDefault(typedArray.getString(R.styleable.CommentDisplayScreenlet_groupId),
			LiferayServerContext.getGroupId());

		int offlinePolicy =
			typedArray.getInt(R.styleable.CommentDisplayScreenlet_offlinePolicy, OfflinePolicy.REMOTE_ONLY.ordinal());
		this.offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		int layoutId = typedArray.getResourceId(R.styleable.CommentDisplayScreenlet_layoutId, getDefaultLayoutId());

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected Interactor createInteractor(String actionName) {
		switch (actionName) {
			case DELETE_COMMENT_ACTION:
				return new CommentDeleteInteractorImpl(getScreenletId());
			case UPDATE_COMMENT_ACTION:
				return new CommentUpdateInteractorImpl(getScreenletId());
			case LOAD_COMMENT_ACTION:
			default:
				return new CommentLoadInteractorImpl(getScreenletId(), offlinePolicy);
		}
	}

	@Override
	protected void onUserAction(String actionName, Interactor interactor, Object... args) {
		switch (actionName) {
			case DELETE_COMMENT_ACTION:
				((CommentDeleteInteractor) interactor).deleteComment(commentId);
				break;
			case UPDATE_COMMENT_ACTION:
				String body = (String) args[0];
				((CommentUpdateInteractor) interactor).updateComment(groupId, className, classPK, commentId, body);
				break;
			case LOAD_COMMENT_ACTION:
			default:
				((CommentLoadInteractor) interactor).load(groupId, commentId);
				break;
		}
	}

	@Override
	public void onLoadCommentFailure(Exception e) {
		if (getListener() != null) {
			getListener().onLoadCommentFailure(commentId, e);
		}

		getViewModel().showFailedOperation(LOAD_COMMENT_ACTION, e);
	}

	@Override
	public void onLoadCommentSuccess(CommentEntry commentEntry) {
		getViewModel().showFinishOperation(LOAD_COMMENT_ACTION, commentEntry);

		if (getListener() != null) {
			getListener().onLoadCommentSuccess(commentEntry);
		}
	}

	@Override
	public void onDeleteCommentFailure(Exception e) {
		getViewModel().showFailedOperation(DELETE_COMMENT_ACTION, e);

		if (getListener() != null) {
			getListener().onDeleteCommentFailure(commentEntry, e);
		}
	}

	@Override
	public void onDeleteCommentSuccess() {
		getViewModel().showFinishOperation(DELETE_COMMENT_ACTION);

		if (getListener() != null) {
			getListener().onDeleteCommentSuccess(commentEntry);
		}
	}

	@Override
	public void onUpdateCommentFailure(Exception e) {
		getViewModel().showFailedOperation(UPDATE_COMMENT_ACTION, e);

		if (getListener() != null) {
			getListener().onUpdateCommentFailure(commentEntry, e);
		}
	}

	@Override
	public void onUpdateCommentSuccess(CommentEntry commentEntry) {
		getViewModel().showFinishOperation(UPDATE_COMMENT_ACTION, commentEntry);

		if (getListener() != null) {
			getListener().onUpdateCommentSuccess(commentEntry);
		}
	}

	@Override
	public void loadingFromCache(boolean success) {
		if (getListener() != null) {
			getListener().loadingFromCache(success);
		}
	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {
		if (getListener() != null) {
			getListener().retrievingOnline(triedInCache, e);
		}
	}

	@Override
	public void storingToCache(Object object) {
		if (getListener() != null) {
			getListener().storingToCache(object);
		}
	}

	public CommentDisplayListener getListener() {
		return listener;
	}

	public void setListener(CommentDisplayListener listener) {
		this.listener = listener;
	}

	public CommentEntry getCommentEntry() {
		return commentEntry;
	}

	public void setCommentEntry(CommentEntry commentEntry) {
		this.commentEntry = commentEntry;
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public long getClassPK() {
		return classPK;
	}

	public void setClassPK(long classPK) {
		this.classPK = classPK;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public OfflinePolicy getOfflinePolicy() {
		return offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy offlinePolicy) {
		this.offlinePolicy = offlinePolicy;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void allowEdition(boolean editable) {
		this.editable = editable;
		getViewModel().allowEdition(this.editable);
	}

	private CommentDisplayListener listener;

	private CommentEntry commentEntry;
	private long commentId;
	private String className;
	private long classPK;
	private long groupId;
	private OfflinePolicy offlinePolicy;
	private boolean autoLoad;
	private boolean editable;
}
