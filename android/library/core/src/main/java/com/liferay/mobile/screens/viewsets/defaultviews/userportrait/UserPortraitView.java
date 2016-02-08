/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.viewsets.defaultviews.userportrait;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.userportrait.view.UserPortraitViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public class UserPortraitView extends FrameLayout implements UserPortraitViewModel, View.OnClickListener {

	public UserPortraitView(Context context) {
		super(context);
	}

	public UserPortraitView(
		Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public UserPortraitView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void showStartOperation(String actionName) {
		_portraitProgress.setVisibility(VISIBLE);
	}

	@Override
	public void showFinishOperation(String actionName) {
		_portraitProgress.setVisibility(INVISIBLE);
	}

	@Override
	public void showFinishOperation(Bitmap bitmap) {
		LiferayLogger.i("portrait loaded");
		_portraitProgress.setVisibility(INVISIBLE);
		_portraitImage.setImageBitmap(transformBitmap(bitmap));
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		if (UserPortraitScreenlet.LOAD_PORTRAIT.equals(actionName)) {
			LiferayLogger.e("portrait failed to load", e);
			setDefaultImagePlaceholder();
		}
		else {
			LiferayLogger.e("portrait failed to upload", e);
		}
		_portraitProgress.setVisibility(INVISIBLE);
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		_screenlet = screenlet;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.liferay_replace_image) {
			_choseOriginDialog = createOriginDialog();
			_choseOriginDialog.show();
		}
		else {
			UserPortraitScreenlet parent = getUserPortraitScreenlet();

			if (v.getId() == R.id.liferay_dialog_select_file) {
				parent.openGallery();
				_choseOriginDialog.dismiss();
			}
			else {
				parent.openCamera();
				_choseOriginDialog.dismiss();
			}
		}
	}

	protected AlertDialog createOriginDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

		LayoutInflater factory = LayoutInflater.from(getContext());
		final View customDialogView = factory.inflate(
			R.layout.user_portrait_chose_origin_default, null);

		customDialogView.findViewById(R.id.liferay_dialog_select_file).setOnClickListener(this);
		customDialogView.findViewById(R.id.liferay_dialog_take_photo).setOnClickListener(this);

		builder.setView(customDialogView);

		return builder.create();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_portraitImage = (ImageView) findViewById(R.id.liferay_portrait);
		_portraitProgress = (ProgressBar) findViewById(R.id.liferay_portrait_progress);
		_portraitAddButton = (ImageButton) findViewById(R.id.liferay_replace_image);

		setDefaultImagePlaceholder();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		UserPortraitScreenlet screenlet = getUserPortraitScreenlet();
		if (screenlet.getEditable()) {
			_portraitAddButton.setOnClickListener(this);
			_portraitAddButton.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (_choseOriginDialog != null) {
			_choseOriginDialog.dismiss();
			_choseOriginDialog = null;
		}
	}

	protected float getBorderRadius() {
		return (float) getResources().getInteger(R.integer.userportrait_default_border_radius);
	}

	protected float getBorderWidth() {
		return (float) getResources().getInteger(R.integer.userportrait_default_border_width);
	}

	protected Bitmap transformBitmap(Bitmap bitmap) {
		float borderRadius = getBorderRadius();
		float borderWidth = getBorderWidth();

		RectF rect = getRectF(bitmap, borderWidth);

		Bitmap finalBitmap = Bitmap.createBitmap(
			bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(finalBitmap);

		canvas.drawRoundRect(rect, borderRadius, borderRadius, getPaint(bitmap));
		canvas.drawRoundRect(rect, borderRadius, borderRadius, getBorderPaint(borderWidth, getDefaultBorderColor()));

		return finalBitmap;
	}

	protected RectF getRectF(Bitmap bitmap, float borderWidth) {
		RectF rect = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
		rect.inset(borderWidth, borderWidth);
		return rect;
	}

	protected Paint getBorderPaint(float borderWidth, int color) {
		Paint borderPaint = new Paint();
		borderPaint.setAntiAlias(true);
		borderPaint.setColor(getResources().getColor(color));
		borderPaint.setStyle(Paint.Style.STROKE);
		borderPaint.setStrokeWidth(borderWidth);
		return borderPaint;
	}

	protected int getDefaultBorderColor() {
		return R.color.textColorPrimary_default;
	}

	protected Paint getPaint(Bitmap bitmap) {
		BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);
		return paint;
	}

	private UserPortraitScreenlet getUserPortraitScreenlet() {
		return (UserPortraitScreenlet) getScreenlet();
	}

	private void setDefaultImagePlaceholder() {
		Bitmap defaultBitmap = BitmapFactory.decodeResource(
			getResources(), R.drawable.default_portrait_placeholder);
		_portraitImage.setImageBitmap(transformBitmap(defaultBitmap));
	}

	protected ImageView _portraitImage;
	protected ImageButton _portraitAddButton;
	protected ProgressBar _portraitProgress;
	protected AlertDialog _choseOriginDialog;

	private BaseScreenlet _screenlet;
}