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

package com.liferay.mobile.screens.viewsets.events.ddl.form;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.model.StringWithOptionsField;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldRadioView
	extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldRadioView {

	private View requiredAsteriskView;
	private TextView errorMessageView;

	public DDLFieldRadioView(Context context) {
		super(context);
	}

	public DDLFieldRadioView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		requiredAsteriskView = findViewById(R.id.required_asterisk);
		errorMessageView = (TextView) findViewById(R.id.error_message);
	}

	@Override
	public void setField(StringWithOptionsField field) {
		super.setField(field);

		requiredAsteriskView.setVisibility(field.isRequired() ? VISIBLE : GONE);

		onPostValidation(true);
	}

	@Override
	public void onPostValidation(boolean valid) {

		int color = getResources().getColor(valid ? R.color.colorPrimary_default : R.color.clear_red_default);
		int textColor = getResources().getColor(valid ? R.color.textColorPrimary : R.color.clear_red_default);
		ColorStateList colorStateList = new ColorStateList(new int[][] {
			new int[] { -android.R.attr.state_checked }, // unchecked
			new int[] { android.R.attr.state_checked },  // checked
			new int[] {}
		}, new int[] { color, color, color });

		for (StringWithOptionsField.Option opt : getField().getAvailableOptions()) {
			AppCompatRadioButton radioButton = (AppCompatRadioButton) findViewWithTag(opt);

			if (radioButton != null) {
				CompoundButtonCompat.setButtonTintList(radioButton, colorStateList);
				radioButton.setTextColor(textColor);
				radioButton.setChecked(getField().isSelected(opt));
			}
		}
	}
}
