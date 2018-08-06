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

package com.liferay.mobile.screens.viewsets.lexicon.ddl.form.fields;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields.DDMFieldRepeatableItemView;
import com.liferay.mobile.screens.viewsets.lexicon.R;
import com.liferay.mobile.screens.viewsets.lexicon.util.FormViewUtil;

/**
 * @author Victor Oliveira
 */
public class DDLFieldTextView
	extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldTextView {

	public DDLFieldTextView(Context context) {
		super(context);
	}

	public DDLFieldTextView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public DDLFieldTextView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onPostValidation(boolean valid) {
		FormViewUtil.setupBackground(getContext(), valid, textEditText);

	    boolean isRepeatableField = getParent() instanceof DDMFieldRepeatableItemView;

	    if (!isRepeatableField) {
            View errorView = findViewById(R.id.error_view);
            if (errorView != null) {
                errorView.setVisibility(valid ? GONE : VISIBLE);
            }
        }
	}
}