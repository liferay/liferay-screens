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

package com.liferay.mobile.screens.themes.material.userportrait;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * @author Javier Gamarra
 */
public class UserPortraitScreenletView
	extends com.liferay.mobile.screens.themes.userportrait.UserPortraitScreenletView {


    public UserPortraitScreenletView(Context context) {
        super(context);
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
    protected Bitmap transformBitmap(Bitmap bitmap) {
        RectF rect = getRectF(bitmap);

        Bitmap finalBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(finalBitmap);
        canvas.drawOval(rect, getPaint(bitmap));
        canvas.drawOval(rect, getBorderPaint());

        return finalBitmap;
    }

}