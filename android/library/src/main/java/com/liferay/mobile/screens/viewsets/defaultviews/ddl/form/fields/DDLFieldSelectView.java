/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.model.Option;
import com.liferay.mobile.screens.ddl.model.SelectableOptionsField;
import com.liferay.mobile.screens.thingscreenlet.screens.events.Event;
import com.liferay.mobile.screens.util.EventBusUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldSelectView extends BaseDDLFieldTextView<SelectableOptionsField> implements View.OnClickListener {

	protected AlertDialog alertDialog;

	public void setOnValueChangedListener(DialogInterface.OnClickListener onValueChangedListener) {
		this.onValueChangedListener = onValueChangedListener;
	}

	public DialogInterface.OnClickListener getOnValueChangedListener() {
		return onValueChangedListener;
	}

	private DialogInterface.OnClickListener onValueChangedListener;

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
	public void onClick(View view) {
		createAlertDialog();
		alertDialog.show();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (alertDialog != null) {
			alertDialog.dismiss();
			alertDialog = null;
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
	protected void onTextChanged(String text) {
	}

	protected void createAlertDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), getAlertDialogStyle());

		LayoutInflater factory = LayoutInflater.from(getContext());
		final View customDialogView = factory.inflate(getAlertDialogLayout(), null);
		TextView title = customDialogView.findViewById(R.id.liferay_dialog_title);
		title.setText(getField().getLabel());

		DialogInterface.OnClickListener selectOptionHandler = getAlertDialogListener();

		builder.setCustomTitle(customDialogView);

		final List<Option> availableOptions = getField().getAvailableOptions();
		String[] labels = getOptionsLabels().toArray(new String[availableOptions.size()]);

		if (getField().isMultiple()) {
			setupMultipleChoice(builder, availableOptions, labels);
		} else {
			setupSingleChoice(builder, selectOptionHandler, labels);
		}
		alertDialog = builder.create();
	}

	protected int getAlertDialogLayout() {
		return R.layout.ddlfield_select_dialog_default;
	}

	protected DialogInterface.OnClickListener getAlertDialogListener() {
		return new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (which >= 0) {
					getField().selectOption(getField().getAvailableOptions().get(which));
					refresh();

					if (onValueChangedListener != null) {
						onValueChangedListener.onClick(dialog, which);
					}

					if (getField().hasFormRules()) {
						EventBusUtil.post(new Event.RequestEvaluationEvent());
					}
				} else {
					dialog.dismiss();
				}
			}
		};
	}

	protected int getAlertDialogStyle() {
		return R.style.default_theme_dialog;
	}

	protected List<String> getOptionsLabels() {
		List<String> result = new ArrayList<>();
		for (Option opt : getField().getAvailableOptions()) {
			result.add(opt.label);
		}
		return result;
	}

	private boolean[] getCheckedOptions() {
		List<Option> availableOptions = getField().getAvailableOptions();
		boolean[] checked = new boolean[availableOptions.size()];
		for (int i = 0; i < availableOptions.size(); i++) {
			Option availableOption = availableOptions.get(i);
			checked[i] = getField().isSelected(availableOption);
		}
		return checked;
	}

	protected void setupMultipleChoice(AlertDialog.Builder builder,
		final List<Option> availableOptions, String[] labels) {

		final boolean[] checked = getCheckedOptions();
		builder.setMultiChoiceItems(labels, checked, new DialogInterface.OnMultiChoiceClickListener() {
			public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
				checked[whichButton] = isChecked;
			}
		});
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				checkField(checked, availableOptions);
				refresh();
				alertDialog.dismiss();
			}
		});

		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
			}
		});
	}

	protected void setupSingleChoice(AlertDialog.Builder builder,
		DialogInterface.OnClickListener selectOptionHandler, String[] labels) {

		builder.setItems(labels, selectOptionHandler);
	}

	private void checkField(boolean[] checked, List<Option> availableOptions) {
		for (int i = 0; i < checked.length; i++) {
			Option option = availableOptions.get(i);
			if (checked[i]) {
				getField().selectOption(option);
			} else {
				getField().clearOption(option);
			}
		}
	}
}