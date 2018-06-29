/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.viewsets.lexicon.ddl.form.fields;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import com.liferay.mobile.screens.ddl.model.Option;
import com.liferay.mobile.screens.ddl.model.SelectableOptionsField;
import com.liferay.mobile.screens.viewsets.lexicon.R;
import com.liferay.mobile.screens.viewsets.lexicon.util.FormViewUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor Oliveira
 */
public class DDLFieldSelectView
	extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldSelectView {

	public DDLFieldSelectView(Context context) {
		super(context);
	}

	public DDLFieldSelectView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public DDLFieldSelectView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected int getAlertDialogStyle() {
		return R.style.lexicon_theme_dialog;
	}

	@Override
	protected int getAlertDialogLayout() {
		return R.layout.ddlfield_select_dialog_lexicon;
	}

	@Override
	protected void setupSingleChoice(AlertDialog.Builder builder, DialogInterface.OnClickListener selectOptionHandler,
		String[] labels) {

		List<Option> availableOptions = getField().getAvailableOptions();
		ArrayList<Option> currentValue = getField().getCurrentValue();

		int index = (currentValue.isEmpty()) ? -1 : availableOptions.indexOf(currentValue.get(0));
		builder.setSingleChoiceItems(labels, index, selectOptionHandler);
		builder.setPositiveButton(android.R.string.ok, selectOptionHandler);
		builder.setNegativeButton(android.R.string.cancel, selectOptionHandler);
	}

	@Override
	public void onPostValidation(boolean valid) {
		FormViewUtil.setupTextFieldLayout(getContext(), valid, labelTextView, textEditText);
	}
}