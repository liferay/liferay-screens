package com.liferay.mobile.screens.comment.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.CommentDisplayListener;
import com.liferay.mobile.screens.comment.list.interactor.CommentListInteractorImpl;
import com.liferay.mobile.screens.comment.list.interactor.CommentListInteractorListener;
import com.liferay.mobile.screens.comment.list.view.CommentListViewModel;
import com.liferay.mobile.screens.context.LiferayServerContext;

/**
 * @author Alejandro Hernández
 */
public class CommentListScreenlet extends BaseListScreenlet<CommentEntry, CommentListInteractorImpl>
	implements CommentListInteractorListener, CommentDisplayListener {

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

	public void addNewCommentEntry(CommentEntry commentEntry) {
		getViewModel().addNewCommentEntry(commentEntry);
	}

	public void removeCommentEntry(CommentEntry commentEntry) {
		getViewModel().removeCommentEntry(commentEntry);
	}

	@Override
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		if (!isInEditMode()) {
			getViewModel().allowEdition(editable);
		}
	}

	@Override
	protected void loadRows(CommentListInteractorImpl interactor) throws Exception {
		interactor.start(className, classPK);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.CommentListScreenlet, 0, 0);

		className = typedArray.getString(R.styleable.CommentListScreenlet_className);

		classPK = castToLong(typedArray.getString(R.styleable.CommentListScreenlet_classPK));

		editable = typedArray.getBoolean(R.styleable.CommentListScreenlet_editable, true);

		Integer offlinePolicy =
			typedArray.getInteger(R.styleable.CommentListScreenlet_offlinePolicy, OfflinePolicy.REMOTE_ONLY.ordinal());
		this.offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		long groupId = LiferayServerContext.getGroupId();

		this.groupId = castToLongOrUseDefault(typedArray.getString(R.styleable.CommentListScreenlet_groupId), groupId);

		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	@Override
	protected void onUserAction(String actionName, CommentListInteractorImpl interactor, Object... args) {
	}

	@Override
	protected CommentListInteractorImpl createInteractor(String actionName) {
		return new CommentListInteractorImpl();
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

	@Override
	public void error(Exception e, String userAction) {

	}

	@Override
	public void onLoadCommentFailure(long commentId, Exception e) {
	}

	@Override
	public void onLoadCommentSuccess(CommentEntry commentEntry) {
	}

	@Override
	public void onDeleteCommentFailure(CommentEntry commentEntry, Exception e) {
		if (getCommentListListener() != null) {
			getCommentListListener().onDeleteCommentFailure(commentEntry, e);
		}
	}

	@Override
	public void onDeleteCommentSuccess(CommentEntry commentEntry) {
		removeCommentEntry(commentEntry);

		if (getCommentListListener() != null) {
			getCommentListListener().onDeleteCommentSuccess(commentEntry);
		}
	}

	@Override
	public void onUpdateCommentFailure(CommentEntry commentEntry, Exception e) {
		if (getCommentListListener() != null) {
			getCommentListListener().onUpdateCommentFailure(commentEntry, e);
		}
	}

	@Override
	public void onUpdateCommentSuccess(CommentEntry commentEntry) {
		if (getCommentListListener() != null) {
			getCommentListListener().onUpdateCommentSuccess(commentEntry);
		}
	}

	public OfflinePolicy getOfflinePolicy() {
		return offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy offlinePolicy) {
		this.offlinePolicy = offlinePolicy;
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

	private CommentListListener getCommentListListener() {
		return (CommentListListener) getListener();
	}

	public boolean isEditable() {
		return editable;
	}

	public void allowEdition(boolean editable) {
		this.editable = editable;

		getViewModel().allowEdition(editable);
	}

	protected CommentListViewModel getViewModel() {
		return (CommentListViewModel) super.getViewModel();
	}

	private OfflinePolicy offlinePolicy;
	private String className;
	private long classPK;
	private long groupId;
	private boolean editable;
}
