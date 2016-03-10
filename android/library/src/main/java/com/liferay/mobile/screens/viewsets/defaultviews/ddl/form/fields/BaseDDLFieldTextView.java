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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.Field;

/**
 * @author Silvio Santos
 */
public abstract class BaseDDLFieldTextView<T extends Field> extends LinearLayout
	implements DDLFieldViewModel<T>, TextWatcher {

	public BaseDDLFieldTextView(Context context) {
		super(context);
	}

	public BaseDDLFieldTextView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public BaseDDLFieldTextView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void afterTextChanged(Editable editable) {
		if (!_field.getLastValidationResult()) {
			_field.setLastValidationResult(true);

			onPostValidation(true);
		}

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

	@Override
	public void setField(T field) {
		_field = field;

		if (_field.isShowLabel()) {
			_textEditText.setHint("");
			if (_labelTextView != null) {
				_labelTextView.setText(_field.getLabel());
				_labelTextView.setVisibility(VISIBLE);
			}
		}
		else {
			_textEditText.setHint(_field.getLabel());
			if (_labelTextView != null) {
				_labelTextView.setVisibility(GONE);
			}
		}

		refresh();
	}

	public TextView getLabelTextView() {
		return _labelTextView;
	}

	public EditText getTextEditText() {
		return _textEditText;
	}

	@Override
	public View getParentView() {
		return _parentView;
	}

	@Override
	public void setParentView(View view) {
		_parentView = view;
	}

	@Override
	public void onTextChanged(
		CharSequence text, int start, int before, int count) {
	}

	@Override
	public void refresh() {
		_textEditText.setText(_field.toFormattedString());
	}

	@Override
	public void onPostValidation(boolean valid) {
		String errorText = valid ? null : getResources().getString(R.string.invalid);

		if (_labelTextView == null) {
			_textEditText.setError(errorText);
		}
		else {
			_labelTextView.setError(errorText);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_labelTextView = (TextView) findViewById(R.id.liferay_ddl_label);
		_textEditText = (EditText) findViewById(R.id.liferay_ddl_edit_text);

		_textEditText.addTextChangedListener(this);

		//We are not saving the text view state because when state is restored,
		//the ids of other DDLFields are conflicting.
		//It is not a problem because all state is stored in Field objects.
		_textEditText.setSaveEnabled(false);
	}

	protected abstract void onTextChanged(String text);

	protected TextView _labelTextView;
	protected EditText _textEditText;
	protected View _parentView;

	private T _field;

}