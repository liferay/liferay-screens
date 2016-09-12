package com.liferay.mobile.screens.webcontent.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.list.interactor.WebContentListInteractorImpl;
import com.liferay.mobile.screens.webcontent.list.interactor.WebContentListInteractorListener;

/**
 * @author Javier Gamarra
 */
public class WebContentListScreenlet extends BaseListScreenlet<WebContent, WebContentListInteractorImpl>
	implements WebContentListInteractorListener {

	private long folderId;

	public WebContentListScreenlet(Context context) {
		super(context);
	}

	public WebContentListScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WebContentListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public WebContentListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void error(Exception e, String userAction) {
		if (getListener() != null) {
			getListener().error(e, userAction);
		}
	}

	public long getFolderId() {
		return folderId;
	}

	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}

	@Override
	protected void loadRows(WebContentListInteractorImpl interactor) {
		interactor.start(folderId);
	}

	@Override
	protected WebContentListInteractorImpl createInteractor(String actionName) {
		return new WebContentListInteractorImpl();
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.WebContentListScreenlet, 0, 0);

		folderId = castToLongOrUseDefault(typedArray.getString(R.styleable.WebContentListScreenlet_folderId), 0);

		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}
}
