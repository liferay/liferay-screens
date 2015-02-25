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

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.FileField;
import com.liferay.mobile.screens.ddl.model.StringField;
import com.liferay.mobile.screens.themes.R;
import com.liferay.mobile.screens.themes.crouton.LiferayCroutonStyle;
import com.liferay.mobile.screens.themes.ddl.form.DDLFormScreenletView;

import de.keyboardsurfer.android.widget.crouton.Crouton;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldFileView extends BaseDDLFieldTextView<FileField>
		implements View.OnClickListener {

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
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
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
	public void onClick(final View view) {
		SimpleFileDialog dialog = new SimpleFileDialog(getContext(), new SimpleFileDialog.SimpleFileDialogListener() {
			@Override
			public void onFileChosen(String path) {
				//FIXME referencia
				getTextEditText().setText(path);
				getField().setCurrentStringValue(path);
				((DDLFormScreenletView) getParent().getParent().getParent()).onClick(view);

			}
		});
		dialog.chooseFile();
	}

	@Override
	protected void onTextChanged(String text) {
	}

}