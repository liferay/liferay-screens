/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.dlfile.display.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.dlfile.display.BaseFileDisplayScreenlet;
import com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display.ImageDisplayView;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayScreenlet extends BaseFileDisplayScreenlet<ImageDisplayViewModel> {

    private ImageView.ScaleType scaleType;
    private ImageView.ScaleType placeholderScaleType;
    private int placeholder;

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
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.ImageDisplayScreenlet, 0, 0);

        placeholder = typedArray.getResourceId(R.styleable.ImageDisplayScreenlet_placeholder, 0);

        Integer scaleTypeAttribute = typedArray.getInteger(R.styleable.ImageDisplayScreenlet_imageScaleType,
            ImageView.ScaleType.FIT_CENTER.ordinal());
        scaleType = ImageView.ScaleType.values()[scaleTypeAttribute];

        Integer placeholderScaleTypeAttribute =
            typedArray.getInteger(R.styleable.ImageDisplayScreenlet_placeholderScaleType,
                ImageView.ScaleType.FIT_CENTER.ordinal());
        placeholderScaleType = ImageView.ScaleType.values()[placeholderScaleTypeAttribute];

        typedArray.recycle();

        View view = super.createScreenletView(context, attributes);

        ImageDisplayView imageDisplayView = (ImageDisplayView) view;
        imageDisplayView.setPlaceholder(placeholder);
        imageDisplayView.setPlaceholderScaleType(placeholderScaleType);
        imageDisplayView.setScaleType(scaleType);

        return view;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        getViewModel().setScaleType(scaleType);
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(int placeholder) {
        this.placeholder = placeholder;
        getViewModel().setPlaceholder(placeholder);
    }

    public ImageView.ScaleType getPlaceholderScaleType() {
        return placeholderScaleType;
    }

    public void setPlaceholderScaleType(ImageView.ScaleType placeholderScaleType) {
        this.placeholderScaleType = placeholderScaleType;
        getViewModel().setPlaceholderScaleType(placeholderScaleType);
    }
}
