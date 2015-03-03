package com.liferay.mobile.screens.viewsets.defaultviews.auth;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.viewsets.R;

/**
 * @author Javier Gamarra
 */
public class ProgressDefaultView extends LinearLayout {

	public ProgressDefaultView(Context context) {
		super(context, null);
	}

	public ProgressDefaultView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public ProgressDefaultView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		dismisDialog();
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(_STATE_SUPER, super.onSaveInstanceState());
		bundle.putBoolean(_STATE_LOADING, _loading);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		Bundle bundle = (Bundle) state;
		super.onRestoreInstanceState(bundle.getParcelable(_STATE_SUPER));
		_loading = bundle.getBoolean(_STATE_LOADING);
		if (_loading) {
			showDialog();
		}
	}

	protected void showDialog() {
		if (_progressDialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			LayoutInflater factory = LayoutInflater.from(getContext());
			final View customDialogView = factory.inflate(
					R.layout.progress_dialog_default, null);
			_progressDialog = builder.setView(customDialogView).create();
		}
		_progressDialog.show();
	}

	protected void dismisDialog() {
		if (_progressDialog != null) {
			_loading = false;
			_progressDialog.dismiss();
			_progressDialog = null;
		}
	}

	protected AlertDialog _progressDialog;
	protected boolean _loading;

	private static final String _STATE_SUPER = "login-super";
	private static final String _STATE_LOADING = "_STATE_LOADING";
}
