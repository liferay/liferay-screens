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

package com.liferay.mobile.screens.themes.ddl.fields;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.StringField;
import com.liferay.mobile.screens.ddl.view.DDLFieldViewModel;
import com.liferay.mobile.screens.themes.R;

/**
 * @author Silvio Santos
 */
public class DDLFieldTextView extends LinearLayout
	implements DDLFieldViewModel {

	public DDLFieldTextView(Context context) {
		this(context, null);
	}

	public DDLFieldTextView(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public DDLFieldTextView(
		Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);
	}

	@Override
	public Field getField() {
		return null;
	}

	@Override
	public void setField(Field field) {
		StringField stringField = (StringField)field;

		if (stringField.isShowLabel()) {
			_labelTextView.setVisibility(VISIBLE);
			_labelTextView.setText(stringField.getLabel());
		}
		else {
			_textEditText.setHint(stringField.getLabel());
			_labelTextView.setVisibility(GONE);
		}

		_textEditText.setText(stringField.getCurrentValue());
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_labelTextView = (TextView)findViewById(R.id.label);
		_textEditText = (EditText)findViewById(R.id.text);
	}

	private EditText _textEditText;
	private TextView _labelTextView;

}
