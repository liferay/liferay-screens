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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.MediaStoreSelectorDialog;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.userportrait.view.UserPortraitViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import rx.functions.Action1;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public class UserPortraitView extends FrameLayout implements UserPortraitViewModel, View.OnClickListener {

	protected ImageView portraitImage;
	protected ImageButton portraitAddButton;
	protected ProgressBar portraitProgress;
	protected AlertDialog choseOriginDialog;
	private BaseScreenlet screenlet;

	public UserPortraitView(Context context) {
		super(context);
	}

	public UserPortraitView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public UserPortraitView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void showStartOperation(String actionName) {
		setDefaultImagePlaceholder();
		portraitProgress.setVisibility(VISIBLE);
	}

	@Override
	public void showFinishOperation(String actionName) {
		portraitProgress.setVisibility(INVISIBLE);
	}

	@Override
	public void showFinishOperation(Bitmap bitmap) {
		LiferayLogger.i("portrait loaded");
		portraitProgress.setVisibility(INVISIBLE);
		portraitImage.setImageBitmap(transformBitmap(bitmap));
	}

	@Override
	public void showPlaceholder(User user) {
		setDefaultImagePlaceholder();
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		if (UserPortraitScreenlet.LOAD_PORTRAIT.equals(actionName)) {
			LiferayLogger.e("portrait failed to load", e);
			setDefaultImagePlaceholder();
		} else {
			LiferayLogger.e("portrait failed to upload", e);
		}
		portraitProgress.setVisibility(INVISIBLE);
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.liferay_replace_image) {
			Activity activity = LiferayScreensContext.getActivityFromContext(getContext());
			choseOriginDialog =
				new MediaStoreSelectorDialog().createOriginDialog(activity, openCamera(), openGallery(), null);
			choseOriginDialog.show();
		}
	}

	public Action1<Boolean> openCamera() {
		return result -> {
			if (result) {
				((UserPortraitScreenlet) getScreenlet()).openCamera();
			}
			choseOriginDialog.dismiss();
		};
	}

	public Action1<Boolean> openGallery() {
		return result -> {
			if (result) {
				((UserPortraitScreenlet) getScreenlet()).openGallery();
			}
			choseOriginDialog.dismiss();
		};
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		portraitImage = (ImageView) findViewById(R.id.liferay_portrait);
		portraitProgress = (ProgressBar) findViewById(R.id.liferay_portrait_progress);
		portraitAddButton = (ImageButton) findViewById(R.id.liferay_replace_image);

		setDefaultImagePlaceholder();

		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());
		choseOriginDialog =
			new MediaStoreSelectorDialog().createOriginDialog(activity, openCamera(), openGallery(), null);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		UserPortraitScreenlet screenlet = getUserPortraitScreenlet();
		if (screenlet.getEditable()) {
			portraitAddButton.setOnClickListener(this);
			portraitAddButton.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (choseOriginDialog != null) {
			choseOriginDialog.dismiss();
			choseOriginDialog = null;
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

		Bitmap finalBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

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
		borderPaint.setColor(ContextCompat.getColor(getContext(), color));
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
		Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_portrait_placeholder);
		portraitImage.setImageBitmap(transformBitmap(defaultBitmap));
	}
}