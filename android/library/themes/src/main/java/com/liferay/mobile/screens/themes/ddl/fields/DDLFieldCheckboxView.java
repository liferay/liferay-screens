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
import android.widget.CompoundButton;
import android.widget.Switch;

import com.liferay.mobile.screens.ddl.model.BooleanField;
import com.liferay.mobile.screens.ddl.view.DDLFieldViewModel;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldCheckboxView extends Switch
	implements DDLFieldViewModel<BooleanField>, CompoundButton.OnCheckedChangeListener {

	public DDLFieldCheckboxView(Context context) {
		super(context);
	}

	public DDLFieldCheckboxView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public DDLFieldCheckboxView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public BooleanField getField() {
		return _field;
	}

	@Override
	public void setField(BooleanField field) {
		_field = field;

		if (_field.isShowLabel()) {
			setHint("");
			setText(_field.getLabel());
		}
		else {
			setHint(_field.getLabel());
			setText("");
		}

		refresh();
	}

	@Override
	public void refresh() {
		this.setChecked(_field.getCurrentValue());
	}

	@Override
	public void onPostValidation(boolean valid) {
		//This field is always valid because it has always a value
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		_field.setCurrentValue(isChecked);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		setSaveEnabled(false);

		setOnCheckedChangeListener(this);
	}

	private BooleanField _field;

}