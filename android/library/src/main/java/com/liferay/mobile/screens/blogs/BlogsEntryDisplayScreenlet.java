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

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		return null;
	}

	@Override
	protected AssetDisplayInteractorImpl createInteractor(String actionName) {
		return null;
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor, Object... args) {
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
	}

	@Override
	public void onRetrieveAssetFailure(Exception e) {
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

	protected long entryId;
	protected boolean autoLoad;
	protected AssetDisplayListener listener;
	protected BlogsEntry blogsEntry;
}
