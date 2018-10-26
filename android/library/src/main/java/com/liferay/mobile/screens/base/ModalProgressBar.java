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

package com.liferay.mobile.screens.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Jose Manuel Navarro
 */
public class ModalProgressBar extends ProgressBar {

    private int actionViewId;

    public ModalProgressBar(Context context) {
        super(context);
    }

    public ModalProgressBar(Context context, AttributeSet attributes) {
        super(context, attributes);

        init(context, attributes);
    }

    public ModalProgressBar(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);

        init(context, attributes);
    }

    public void startProgress() {
        setVisibility(VISIBLE);
    }

    public void finishProgress() {
        setVisibility(INVISIBLE);
    }

    @Override
    public void setVisibility(int v) {
        View actionView = findActionView((View) getParent(), actionViewId);

        if (actionView != null) {
            setVisibility(v, actionView);
        } else {
            super.setVisibility(v);
        }
    }

    public void setVisibility(int visibility, View... actionViews) {
        super.setVisibility(visibility);

        int len = (actionViews == null) ? 0 : actionViews.length;

        for (int i = 0; i < len; ++i) {
            actionViews[i].setEnabled(visibility != VISIBLE);
        }
    }

    protected void init(Context context, AttributeSet attributes) {
        for (int i = 0; i < attributes.getAttributeCount(); ++i) {
            LiferayLogger.d(attributes.getAttributeName(i) + " - " + attributes.getAttributeValue(i));
        }
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.ModalProgressBar, 0, 0);

        actionViewId = typedArray.getResourceId(R.styleable.ModalProgressBar_actionViewId, 0);

        typedArray.recycle();
    }

    private View findActionView(View parent, int actionViewId) {
        if (parent instanceof BaseScreenlet) {
            return null;
        }

        View result = parent.findViewById(actionViewId);
        if (result != null) {
            return result;
        }

        return findActionView((View) parent.getParent(), actionViewId);
    }
}