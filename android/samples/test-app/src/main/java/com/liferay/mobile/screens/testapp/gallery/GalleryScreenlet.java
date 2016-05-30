package com.liferay.mobile.screens.testapp.gallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.testapp.R;

import org.json.JSONObject;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class GalleryScreenlet extends BaseScreenlet<GalleryViewModel, GalleryInteractor> implements GalleryListener {

	public GalleryScreenlet(Context context) {
		super(context);
	}

	public GalleryScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public GalleryScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onGalleryLoaded(List<JSONObject> images) {
		getViewModel().showImages(images);

		if (_galleryListener != null) {
			_galleryListener.onGalleryLoaded(images);
		}
	}

	@Override
	public void onErrorLoadingGallery(Exception e) {
		getViewModel().showFailedOperation(null, e);

		if (_galleryListener != null) {
			_galleryListener.onErrorLoadingGallery(e);
		}
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.GalleryScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.GalleryScreenlet_layoutId, getDefaultLayoutId());

		_folderId = typedArray.getInt(R.styleable.GalleryScreenlet_folderId, 0);
		_groupId = typedArray.getInt(R.styleable.GalleryScreenlet_groupId,
			(int) LiferayServerContext.getGroupId());
		_autoLoad = typedArray.getBoolean(R.styleable.GalleryScreenlet_autoLoad, true);

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		if (_autoLoad) {
			loadGallery();
		}
	}

	@Override
	protected void onUserAction(String userActionName, GalleryInteractor interactor, Object... args) {
		loadGallery();
	}

	@Override
	protected GalleryInteractor createInteractor(String actionName) {
		return new GalleryInteractor(getScreenletId());
	}

	protected void loadGallery() {
		GalleryInteractor galleryInteractor = getInteractor();
		galleryInteractor.load(_groupId, _folderId);
	}

	protected long _folderId;
	protected long _groupId;
	protected boolean _autoLoad;
	protected GalleryListener _galleryListener;
}
