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
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.FileField;
import com.liferay.mobile.screens.themes.R;
import com.liferay.mobile.screens.themes.ddl.form.DDLFormScreenletView;

/**
 * @author Javier Gamarra
 */
public class DDLFieldFileView extends BaseDDLFieldTextView<FileField>
		implements DDLFieldViewModel<FileField>, View.OnClickListener {

	public DDLFieldFileView(Context context) {
		super(context, null);
	}

	public DDLFieldFileView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public DDLFieldFileView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onClick(final View view) {
		_dialog = new SimpleFileDialog().createDialog(getContext(), new SimpleFileDialog.SimpleFileDialogListener() {
			@Override
			public void onFileChosen(String path) {
				_progressBar.setVisibility(View.VISIBLE);
				getTextEditText().setText(path);

				FileField field = getField();
				field.getCurrentValue().setName(path);
				field.getCurrentValue().setState(FileField.State.PENDING);
				view.setTag(field);
				((DDLFormScreenletView) getParentView()).onClick(view);

			}
		});
		_dialog.show();
	}

	@Override
	public void refresh() {
		_textEditText.setText(_field.toFormattedString());
		if (FileField.State.LOADED.equals(_field.getCurrentValue().getState())) {
			_textEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.abc_btn_radio_material, 0);
		} else if (FileField.State.ERROR.equals(_field.getCurrentValue().getState())) {
			_textEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.abc_btn_check_material, 0);
		} else if (_field.getCurrentValue().getState() != null) {
			_progressBar.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onPostValidation(boolean valid) {
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (_dialog != null) {
			_dialog.dismiss();
			_dialog = null;
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		_progressBar = (ProgressBar) findViewById(R.id.fileProgress);
		getTextEditText().setOnClickListener(this);
	}

	@Override
	protected void onTextChanged(String text) {

	}

	private ProgressBar _progressBar;
	private AlertDialog _dialog;
}