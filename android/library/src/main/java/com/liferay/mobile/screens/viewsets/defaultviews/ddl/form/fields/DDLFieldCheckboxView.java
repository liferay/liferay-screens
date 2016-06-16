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
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.BooleanField;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldCheckboxView extends LinearLayout
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
			_switch.setHint("");
			_switch.setText(_field.getLabel());
		}
		else {
			_switch.setHint(_field.getLabel());
			_switch.setText("");
		}

		refresh();
	}

	@Override
	public void refresh() {
		_switch.setChecked(_field.getCurrentValue());
	}

	@Override
	public void onPostValidation(boolean valid) {
		//This field is always valid because it has always a value
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
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		_field.setCurrentValue(isChecked);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		setSaveEnabled(false);

		_switch = (SwitchCompat) findViewById(R.id.liferay_ddl_switch);

		_switch.setOnCheckedChangeListener(this);
	}

	protected BooleanField _field;
	protected SwitchCompat _switch;
	protected View _parentView;

}