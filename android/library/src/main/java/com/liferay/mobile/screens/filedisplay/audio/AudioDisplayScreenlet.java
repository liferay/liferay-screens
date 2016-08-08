package com.liferay.mobile.screens.filedisplay.audio;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.assetdisplay.interactor.AssetDisplayInteractorImpl;
import com.liferay.mobile.screens.filedisplay.BaseFileDisplayScreenlet;

/**
 * @author Sarai Díaz García
 */
public class AudioDisplayScreenlet extends BaseFileDisplayScreenlet {

	public AudioDisplayScreenlet(Context context) {
		super(context);
	}

	public AudioDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AudioDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public AudioDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor, Object... args) {
	}
}
