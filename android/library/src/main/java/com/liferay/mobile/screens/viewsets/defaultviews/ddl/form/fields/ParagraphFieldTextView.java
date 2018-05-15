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

package com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import com.liferay.mobile.screens.ddl.model.StringField;

/**
 * @author Victor Oliveira
 */
public class ParagraphFieldTextView extends BaseDDLFieldTextView<StringField> {

	public ParagraphFieldTextView(Context context) {
		super(context);
	}

	public ParagraphFieldTextView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public ParagraphFieldTextView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected void onTextChanged(String text) {
		getField().setCurrentValue(text);
	}

	@Override
	public void setupFieldLayout() {
		super.setupFieldLayout();

		textEditText.setBackground(null);
		textEditText.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public void refresh() {

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			textEditText.setText(Html.fromHtml(getField().getText(), Html.FROM_HTML_MODE_LEGACY));
		} else {
			textEditText.setText(Html.fromHtml(getField().getText()));
		}
	}
}