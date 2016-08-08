package com.liferay.mobile.screens.filedisplay.image;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.assetdisplay.interactor.AssetDisplayInteractorImpl;
import com.liferay.mobile.screens.filedisplay.BaseFileDisplayScreenlet;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayScreenlet extends BaseFileDisplayScreenlet {

	public ImageDisplayScreenlet(Context context) {
		super(context);
	}

	public ImageDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ImageDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor, Object... args) {
	}
}
