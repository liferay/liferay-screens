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
import android.text.InputType;
import android.util.AttributeSet;
import com.liferay.mobile.screens.ddl.model.NumberField;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldNumberView extends BaseDDLFieldTextView<NumberField> {

	public DDLFieldNumberView(Context context) {
		super(context);
	}

	public DDLFieldNumberView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public DDLFieldNumberView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void setField(NumberField field) {
		super.setField(field);

		switch (getField().getEditorType()) {
			case INTEGER:
				getTextEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
				break;

			case NUMBER:
			case DECIMAL:
			default:
				getTextEditText().setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_NUMBER_FLAG_SIGNED
					| InputType.TYPE_NUMBER_FLAG_DECIMAL);
				break;
		}
	}

	@Override
	protected void onTextChanged(String text) {
		getField().setCurrentStringValue(text);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		textEditText.setOnFocusChangeListener(this);
	}
}