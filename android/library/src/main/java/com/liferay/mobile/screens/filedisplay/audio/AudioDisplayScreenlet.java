package com.liferay.mobile.screens.filedisplay.audio;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.assetdisplay.interactor.AssetDisplayInteractorImpl;
import com.liferay.mobile.screens.filedisplay.BaseFileDisplayScreenlet;

/**
 * @author Sarai Díaz García
 */
public class AudioDisplayScreenlet extends BaseFileDisplayScreenlet {

	public AudioDisplayScreenlet(Context context) {
		super(context);
	}

	public AudioDisplayScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AudioDisplayScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.AudioDisplayScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.AudioDisplayScreenlet_layoutId, getDefaultLayoutId());

		_autoLoad = typedArray.getBoolean(R.styleable.AudioDisplayScreenlet_autoLoad, true);
		_entryId = typedArray.getInt(R.styleable.AudioDisplayScreenlet_entryId, 0);

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor, Object... args) {
	}
}
