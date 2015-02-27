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
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.themes.R;
import com.liferay.mobile.screens.themes.ddl.form.DDLFormScreenletView;

/**
 * @author Javier Gamarra
 */
public class DDLFieldFileView extends BaseDDLFieldTextView<DocumentField>
		implements DDLFieldViewModel<DocumentField>, View.OnClickListener {

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
		_fileDialog = new FileDialog().createDialog(getContext(), new FileDialog.SimpleFileDialogListener() {
			@Override
			public void onFileChosen(String path) {
				_progressBar.setVisibility(View.VISIBLE);
				getTextEditText().setText(path);

				DocumentField field = getField();
				field.getCurrentValue().setName(path);
				field.getCurrentValue().setState(DocumentField.State.PENDING);
				view.setTag(field);
				((DDLFormScreenletView) getParentView()).onClick(view);

			}
		});
		_fileDialog.show();
	}

	@Override
	public void refresh() {
		getTextEditText().setText(getField().toFormattedString());
		if (DocumentField.State.LOADED.equals(getField().getCurrentValue().getState())) {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.default_circle_success, 0);
			_progressBar.setVisibility(View.GONE);
		}
		else if (DocumentField.State.ERROR.equals(getField().getCurrentValue().getState())) {
			getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.default_circle_failed, 0);
			_progressBar.setVisibility(View.GONE);
		}
		else if (DocumentField.State.UPLOADING.equals(getField().getCurrentValue().getState()) ||
				DocumentField.State.PENDING.equals(getField().getCurrentValue().getState())) {
			_progressBar.setVisibility(View.VISIBLE);
		}
		else {
			_progressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public void onPostValidation(boolean valid) {
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (_fileDialog != null) {
			_fileDialog.dismiss();
			_fileDialog = null;
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
	private AlertDialog _fileDialog;
}