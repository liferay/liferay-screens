/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.themes.userportrait;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.themes.R;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorListener;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public class UserPortraitScreenletView extends FrameLayout
        implements BaseViewModel, UserPortraitInteractorListener {

    public static final float BORDER_WIDTH = 3f;
    public static final float ROUNDED_BORDER = 10f;

    public UserPortraitScreenletView(Context context) {
        this(context, null);
    }

    public UserPortraitScreenletView(
            Context context, AttributeSet attributes) {
        this(context, attributes, 0);
    }

    public UserPortraitScreenletView(
            Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        _portraitImage = (ImageView) findViewById(R.id.portrait_image);
        _portraitProgress = (ProgressBar) findViewById(R.id.portrait_progress);
        setDefaultImagePlaceholder();
    }

	@Override
	public void onStartUserPortraitRequest() {
		_portraitProgress.setVisibility(VISIBLE);
	}

	@Override
	public Bitmap onEndUserPortraitRequest(Bitmap bitmap) {
		_portraitProgress.setVisibility(INVISIBLE);
		_portraitImage.setImageBitmap(transformBitmap(bitmap));
		return bitmap;
	}

	@Override
    public void onUserPortraitFailure(Exception e) {
        _portraitProgress.setVisibility(INVISIBLE);
        setDefaultImagePlaceholder();
    }

    private void setDefaultImagePlaceholder() {
        Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_portrait_placeholder);
        _portraitImage.setImageBitmap(transformBitmap(defaultBitmap));
    }

    protected Bitmap transformBitmap(Bitmap bitmap) {

        RectF rect = getRectF(bitmap);

        Bitmap finalBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(finalBitmap);
        canvas.drawRoundRect(rect, ROUNDED_BORDER, ROUNDED_BORDER, getPaint(bitmap));
        canvas.drawRoundRect(rect, ROUNDED_BORDER, ROUNDED_BORDER, getBorderPaint());

        return finalBitmap;
    }

    protected RectF getRectF(Bitmap bitmap) {
        RectF rect = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
        rect.inset(BORDER_WIDTH, BORDER_WIDTH);
        return rect;
    }

    protected Paint getBorderPaint() {
        Paint borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(Color.DKGRAY);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(BORDER_WIDTH);
        return borderPaint;
    }

    protected Paint getPaint(Bitmap bitmap) {
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        return paint;
    }

    private ImageView _portraitImage;
    private ProgressBar _portraitProgress;

}