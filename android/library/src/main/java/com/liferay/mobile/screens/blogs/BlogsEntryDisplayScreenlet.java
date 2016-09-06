package com.liferay.mobile.screens.blogs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.interactor.AssetDisplayInteractorImpl;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Sarai Díaz García
 */

public class BlogsEntryDisplayScreenlet extends BaseScreenlet<BlogsEntryDisplayViewModel, AssetDisplayInteractorImpl>
	implements AssetDisplayListener {

	public static final String LOAD_BLOGS_ACTION = "LOAD_BLOGS_ACTION";

	public BlogsEntryDisplayScreenlet(Context context) {
		super(context);
	}

	public BlogsEntryDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BlogsEntryDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public BlogsEntryDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void load() {
		performUserAction(LOAD_BLOGS_ACTION);
	}

	public void loadBlogsEntry() {
		onRetrieveAssetSuccess(blogsEntry);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.BlogsEntryDisplayScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.BlogsEntryDisplayScreenlet_layoutId, getDefaultLayoutId());

		autoLoad = typedArray.getBoolean(R.styleable.BlogsEntryDisplayScreenlet_autoLoad, true);
		entryId = typedArray.getInt(R.styleable.BlogsEntryDisplayScreenlet_entryId, 0);

		className = typedArray.getString(R.styleable.AssetDisplayScreenlet_className);
		classPK = typedArray.getInt(R.styleable.AssetDisplayScreenlet_classPK, 0);

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	@Override
	protected AssetDisplayInteractorImpl createInteractor(String actionName) {
		return new AssetDisplayInteractorImpl();
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor, Object... args) {
		if (entryId != 0) {
			interactor.start(entryId);
		} else {
			interactor.start(className, classPK);
		}
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		blogsEntry = (BlogsEntry) assetEntry;

		getViewModel().showFinishOperation(blogsEntry);

		if (listener != null) {
			listener.onRetrieveAssetSuccess(assetEntry);
		}
	}

	@Override
	public void error(Exception e, String userAction) {
		getViewModel().showFailedOperation(userAction, e);

		if (listener != null) {
			listener.error(e, userAction);
		}
	}

	@Override
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		if (autoLoad) {
			autoLoad();
		}
	}

	protected void autoLoad() {
		if (SessionContext.isLoggedIn()) {
			if (blogsEntry == null || (className != null && classPK != 0)) {
				load();
			} else {
				loadBlogsEntry();
			}
		}
	}

	public long getEntryId() {
		return entryId;
	}

	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}

	public BlogsEntry getBlogsEntry() {
		return blogsEntry;
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

	public void setBlogsEntry(BlogsEntry blogsEntry) {
		this.blogsEntry = blogsEntry;
	}

	public boolean getAutoLoad() {
		return autoLoad;
	}

	public void setAutoLoad(boolean autoLoad) {
		this.autoLoad = autoLoad;
	}

	public AssetDisplayListener getListener() {
		return listener;
	}

	public void setListener(AssetDisplayListener listener) {
		this.listener = listener;
	}

	private long entryId;
	private String className;
	private long classPK;
	private boolean autoLoad;
	private AssetDisplayListener listener;
	private BlogsEntry blogsEntry;
}
