package com.liferay.mobile.screens.filedisplay.image;

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
public class ImageDisplayScreenlet extends BaseFileDisplayScreenlet {

	public ImageDisplayScreenlet(Context context) {
		super(context);
	}

	public ImageDisplayScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public ImageDisplayScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.ImageDisplayScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.ImageDisplayScreenlet_layoutId, getDefaultLayoutId());

		_autoLoad = typedArray.getBoolean(R.styleable.ImageDisplayScreenlet_autoLoad, true);
		_entryId = typedArray.getInt(R.styleable.ImageDisplayScreenlet_entryId, 0);

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor,
		Object... args) {
	}
}
