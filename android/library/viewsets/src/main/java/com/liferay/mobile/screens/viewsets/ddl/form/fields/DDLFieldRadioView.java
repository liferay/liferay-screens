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

package com.liferay.mobile.screens.viewsets.ddl.form.fields;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.internal.widget.TintRadioButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.StringWithOptionsField;
import com.liferay.mobile.screens.viewsets.R;

import java.util.List;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldRadioView extends RadioGroup
	implements DDLFieldViewModel<StringWithOptionsField>, CompoundButton.OnCheckedChangeListener {

	public DDLFieldRadioView(Context context) {
		super(context);
	}

	public DDLFieldRadioView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	@Override
	public StringWithOptionsField getField() {
		return _field;
	}

	@Override
	public void setField(StringWithOptionsField field) {
		_field = field;

		if (_field.isShowLabel()) {
			TextView label = (TextView) findViewById(R.id.label);

			label.setText(field.getLabel());
			label.setVisibility(VISIBLE);
		}

		LayoutParams layoutParams = new LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		List<StringWithOptionsField.Option> availableOptions = field.getAvailableOptions();

		for (int i = 0; i < availableOptions.size(); ++i) {
			StringWithOptionsField.Option opt = availableOptions.get(i);

			TintRadioButton radioButton = new TintRadioButton(getContext());
			radioButton.setLayoutParams(layoutParams);
			radioButton.setText(opt.label);
			radioButton.setTag(opt);
			radioButton.setOnCheckedChangeListener(this);
			radioButton.setTypeface(_getTypeface());
			radioButton.setSaveEnabled(true);
			addView(radioButton);
		}

		refresh();
	}

	@Override
	public void refresh() {
		List<StringWithOptionsField.Option> selectedOptions = _field.getCurrentValue();

		if (selectedOptions != null) {
			for (StringWithOptionsField.Option opt : selectedOptions) {
				TintRadioButton radioButton = (TintRadioButton) findViewWithTag(opt);

				if (radioButton != null) {
					radioButton.setChecked(true);
				}
			}
		}
	}

	@Override
	public void onPostValidation(boolean valid) {
		String errorText = valid ? null : getContext().getString(R.string.required_value);

		if (_field.isShowLabel()) {
			TextView label = (TextView) findViewById(R.id.label);
			label.setError(errorText);
		}
		else {
			List<StringWithOptionsField.Option> availableOptions = _field.getAvailableOptions();
			StringWithOptionsField.Option opt = availableOptions.get(0);
			TintRadioButton radioButton = (TintRadioButton) findViewWithTag(opt);
			if (radioButton != null) {
				radioButton.setError(errorText);
			}
		}
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
	public void setPositionInParent(int position) {

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		setSaveEnabled(true);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		TintRadioButton radioButton = (TintRadioButton) buttonView;

		StringWithOptionsField.Option opt = (StringWithOptionsField.Option) radioButton.getTag();
		if (isChecked) {
			_field.selectOption(opt);
		} else {
			_field.clearOption(opt);
		}
	}

	private Typeface _getTypeface() {
		//FIXME replace with constructor with styles when we have the drawables
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			return Typeface.DEFAULT;
		}
		return Typeface.create("sans-serif-light", Typeface.NORMAL);
	}

	private View _parentView;
	private StringWithOptionsField _field;

}