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

package com.liferay.mobile.screens.themes.ddl.form.fields;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.liferay.mobile.screens.ddl.model.StringWithOptionsField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldSelectView extends BaseDDLFieldTextView<StringWithOptionsField>
	implements View.OnClickListener {

	public DDLFieldSelectView(Context context) {
		super(context, null);
	}

	public DDLFieldSelectView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public DDLFieldSelectView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onClick(View view) {
		createAlertDialog();
		_alertDialog.show();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (_alertDialog != null) {
			_alertDialog.dismiss();
			_alertDialog = null;
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		EditText editText = getTextEditText();
		editText.setCursorVisible(false);
		editText.setFocusableInTouchMode(false);
		editText.setOnClickListener(this);
		editText.setInputType(InputType.TYPE_NULL);
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle state = (Bundle)inState;
		Parcelable superState = state.getParcelable(_STATE_SUPER);

		super.onRestoreInstanceState(superState);

		Bundle dialogState = state.getBundle(_STATE_DIALOG);

		if (dialogState != null) {
			createAlertDialog();

			_alertDialog.onRestoreInstanceState(dialogState);
		}
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();

		Bundle state = new Bundle();
		state.putParcelable(_STATE_SUPER, superState);

		if (_alertDialog != null && _alertDialog.isShowing()) {
			state.putBundle(_STATE_DIALOG, _alertDialog.onSaveInstanceState());
		}

		return state;
	}

	@Override
	protected void onTextChanged(String text) {
	}

	protected void createAlertDialog() {
		List<String> labels = getOptionsLabels();

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

		DialogInterface.OnClickListener selectOptionHandler =
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					getField().selectOption(getField().getAvailableOptions().get(which));
					refresh();
				}
			};

		builder.setTitle(getField().getLabel())
			.setItems(labels.toArray(new String[0]), selectOptionHandler);

		_alertDialog = builder.create();
	}

	protected List<String> getOptionsLabels() {
		List<String> result = new ArrayList<>();

		for (StringWithOptionsField.Option opt : getField().getAvailableOptions()) {
			result.add(opt.label);
		}

		return result;
	}

	private static final String _STATE_DIALOG = "dialog";

	private static final String _STATE_SUPER = "super";

	private AlertDialog _alertDialog;

}