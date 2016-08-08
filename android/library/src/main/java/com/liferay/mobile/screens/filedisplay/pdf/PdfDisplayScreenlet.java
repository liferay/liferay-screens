package com.liferay.mobile.screens.filedisplay.pdf;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.assetdisplay.interactor.AssetDisplayInteractorImpl;
import com.liferay.mobile.screens.filedisplay.BaseFileDisplayScreenlet;

/**
 * @author Sarai Díaz García
 */
public class PdfDisplayScreenlet extends BaseFileDisplayScreenlet {

	public PdfDisplayScreenlet(Context context) {
		super(context);
	}

	public PdfDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PdfDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public PdfDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor, Object... args) {
	}
}
