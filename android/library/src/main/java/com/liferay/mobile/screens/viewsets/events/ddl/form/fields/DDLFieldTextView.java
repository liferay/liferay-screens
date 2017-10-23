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

package com.liferay.mobile.screens.viewsets.events.ddl.form.fields;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.model.StringField;

;

/**
 * @author Silvio Santos
 */
public class DDLFieldTextView
	extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldTextView {

	private View requiredAsteriskView;
	private TextView errorMessageView;

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
	protected void onFinishInflate() {
		super.onFinishInflate();

		requiredAsteriskView = findViewById(R.id.required_asterisk);
		errorMessageView = (TextView) findViewById(R.id.error_message);
	}

	@Override
	public void setField(StringField field) {
		super.setField(field);

		requiredAsteriskView.setVisibility(field.isRequired() ? VISIBLE : GONE);
	}

	@Override
	public void onPostValidation(boolean valid) {

		textEditText.setBackgroundResource(
			valid ? R.drawable.default_edit_text_selector : R.drawable.error_edit_text_selector);

		String errorText = valid ? null : getResources().getString(com.liferay.mobile.screens.R.string.required_value);

		errorMessageView.setVisibility(valid ? GONE : VISIBLE);
		errorMessageView.setText(errorText);
	}
}
