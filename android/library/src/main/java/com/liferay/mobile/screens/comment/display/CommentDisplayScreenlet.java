package com.liferay.mobile.screens.comment.display;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.comment.display.interactor.delete.CommentDeleteInteractor;
import com.liferay.mobile.screens.comment.display.interactor.load.CommentLoadInteractor;
import com.liferay.mobile.screens.comment.display.interactor.update.CommentUpdateInteractor;
import com.liferay.mobile.screens.comment.display.view.CommentDisplayViewModel;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Alejandro Hernández
 */
public class CommentDisplayScreenlet extends BaseScreenlet<CommentDisplayViewModel, Interactor>
	implements CommentDisplayInteractorListener {

	public static final String DELETE_COMMENT_ACTION = "DELETE_COMMENT";
	public static final String UPDATE_COMMENT_ACTION = "UPDATE_COMMENT";
	public static final String LOAD_COMMENT_ACTION = "LOAD_COMMENT";
	private CommentDisplayListener listener;
	private CommentEntry commentEntry;
	private long commentId;
	private CachePolicy cachePolicy;
	private boolean autoLoad;
	private boolean editable;

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

		commentId = castToLong(typedArray.getString(R.styleable.CommentDisplayScreenlet_commentId));

		editable = typedArray.getBoolean(R.styleable.CommentDisplayScreenlet_editable, true);

		int cachePolicy =
			typedArray.getInt(R.styleable.CommentDisplayScreenlet_cachePolicy, CachePolicy.REMOTE_ONLY.ordinal());
		this.cachePolicy = CachePolicy.values()[cachePolicy];

		int layoutId = typedArray.getResourceId(R.styleable.CommentDisplayScreenlet_layoutId, getDefaultLayoutId());

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected Interactor createInteractor(String actionName) {
		switch (actionName) {
			case DELETE_COMMENT_ACTION:
				return new CommentDeleteInteractor();
			case UPDATE_COMMENT_ACTION:
				return new CommentUpdateInteractor();
			case LOAD_COMMENT_ACTION:
			default:
				return new CommentLoadInteractor();
		}
	}

	@Override
	protected void onUserAction(String actionName, Interactor interactor, Object... args) {
		switch (actionName) {
			case DELETE_COMMENT_ACTION:
				((CommentDeleteInteractor) interactor).start(new CommentEvent(commentId, null));
				break;
			case UPDATE_COMMENT_ACTION:
				String body = (String) args[0];
				((CommentUpdateInteractor) interactor).start(new CommentEvent(commentId, body));
				break;
			case LOAD_COMMENT_ACTION:
			default:
				((CommentLoadInteractor) interactor).start(commentId);
				break;
		}
	}

	@Override
	public void onLoadCommentSuccess(CommentEntry commentEntry) {
		this.commentEntry = commentEntry;
		this.commentId = commentEntry.getCommentId();
		commentEntry.setEditable(editable);
		getViewModel().showFinishOperation(LOAD_COMMENT_ACTION, commentEntry);

		if (getListener() != null) {
			getListener().onLoadCommentSuccess(commentEntry);
		}
	}

	public void allowEdition(boolean editable) {
		this.editable = editable;
		if (commentEntry != null) {
			commentEntry.setEditable(editable);
			getViewModel().showFinishOperation(LOAD_COMMENT_ACTION, commentEntry);
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
	public void onUpdateCommentSuccess(CommentEntry commentEntry) {
		getViewModel().showFinishOperation(UPDATE_COMMENT_ACTION, commentEntry);

		if (getListener() != null) {
			getListener().onUpdateCommentSuccess(commentEntry);
		}
	}

	@Override
	public void error(Exception e, String userAction) {
		getViewModel().showFailedOperation(userAction, e);

		if (getListener() != null) {
			getListener().error(e, userAction);
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

	public CachePolicy getCachePolicy() {
		return cachePolicy;
	}

	public void setCachePolicy(CachePolicy cachePolicy) {
		this.cachePolicy = cachePolicy;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
