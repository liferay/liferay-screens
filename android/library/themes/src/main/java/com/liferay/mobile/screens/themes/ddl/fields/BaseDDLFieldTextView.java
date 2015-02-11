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

import android.text.Editable;
import android.text.TextWatcher;

import android.util.AttributeSet;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.view.DDLFieldViewModel;
import com.liferay.mobile.screens.themes.R;

/**
 * @author Silvio Santos
 */
public abstract class BaseDDLFieldTextView<T extends Field> extends LinearLayout
	implements DDLFieldViewModel<T>, TextWatcher {

	public BaseDDLFieldTextView(Context context) {
		super(context, null);
	}

	public BaseDDLFieldTextView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public BaseDDLFieldTextView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void afterTextChanged(Editable editable) {
		onTextChanged(editable.toString());
	}

	@Override
	public void beforeTextChanged(
		CharSequence text, int start, int count, int after) {
	}

	@Override
	public T getField() {
		return _field;
	}

	public TextView getLabelTextView() {
		return _labelTextView;
	}

	public EditText getTextEditText() {
		return _textEditText;
	}

	@Override
	public void onTextChanged(
		CharSequence text, int start, int before, int count) {
	}

	@Override
	public void setField(T field) {
		_field = field;

		if (_field.isShowLabel()) {
			_textEditText.setHint("");
			_labelTextView.setText(_field.getLabel());
			_labelTextView.setVisibility(VISIBLE);
		}
		else {
			_textEditText.setHint(_field.getLabel());
			_labelTextView.setVisibility(GONE);
		}

		refresh();
	}

	@Override
	public void refresh() {
		_textEditText.setText(_field.toFormattedString());
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_labelTextView = (TextView)findViewById(R.id.label);
		_textEditText = (EditText)findViewById(R.id.text);

		_textEditText.addTextChangedListener(this);

		//We are not saving the text view state because when state is restored,
		//the ids of other DDLFields are conflicting.
		//It is not a problem because all state is stored in Field objects.
		_textEditText.setSaveEnabled(false);
	}

	protected abstract void onTextChanged(String text);

	private T _field;
	private TextView _labelTextView;
	private EditText _textEditText;

}