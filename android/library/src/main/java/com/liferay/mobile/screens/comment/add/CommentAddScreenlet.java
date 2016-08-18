package com.liferay.mobile.screens.comment.add;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.add.interactor.CommentAddInteractorImpl;
import com.liferay.mobile.screens.comment.add.view.CommentAddViewModel;

/**
 * @author Alejandro Hernández
 */
public class CommentAddScreenlet extends BaseScreenlet<CommentAddViewModel, CommentAddInteractorImpl>
	implements CommentAddListener {

	public CommentAddScreenlet(Context context) {
		super(context);
	}

	public CommentAddScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommentAddScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CommentAddScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.CommentAddScreenlet, 0, 0);

		className = typedArray.getString(R.styleable.CommentAddScreenlet_className);

		classPK = castToLong(typedArray.getString(R.styleable.CommentAddScreenlet_classPK));

		int layoutId = typedArray.getResourceId(R.styleable.CommentAddScreenlet_layoutId, getDefaultLayoutId());

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected CommentAddInteractorImpl createInteractor(String actionName) {
		return new CommentAddInteractorImpl();
	}

	@Override
	protected void onUserAction(String userActionName, CommentAddInteractorImpl interactor, Object... args) {
		String body = (String) args[0];
		interactor.start(className, classPK, body);
	}

	@Override
	public void error(Exception e, String userAction) {

		getViewModel().showFailedOperation(null, e);

		if (getListener() != null) {
			getListener().error(e, userAction);
		}
	}

	@Override
	public void onAddCommentSuccess(CommentEntry commentEntry) {

		getViewModel().showFinishOperation(null);

		if (getListener() != null) {
			getListener().onAddCommentSuccess(commentEntry);
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

	public CommentAddListener getListener() {
		return listener;
	}

	public void setListener(CommentAddListener listener) {
		this.listener = listener;
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

	private CommentAddListener listener;
	private String className;
	private long classPK;
}
