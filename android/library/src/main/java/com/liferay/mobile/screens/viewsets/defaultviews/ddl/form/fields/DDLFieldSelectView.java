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
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.StringWithOptionsField;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldSelectView extends BaseDDLFieldTextView<StringWithOptionsField> implements View.OnClickListener {

	protected AlertDialog alertDialog;
	private Boolean shown = false;

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

		shown = true;
		timer = System.currentTimeMillis();
		alertDialog.show();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (alertDialog != null) {
			shown = false;
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

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.default_theme_dialog);

		LayoutInflater factory = LayoutInflater.from(getContext());
		final View customDialogView = factory.inflate(R.layout.ddlfield_select_dialog_default, null);
		TextView title = (TextView) customDialogView.findViewById(R.id.liferay_dialog_title);
		title.setText(getField().getLabel());

		DialogInterface.OnClickListener selectOptionHandler = getAlertDialogListener();

		builder.setCustomTitle(customDialogView);

		final List<StringWithOptionsField.Option> availableOptions = getField().getAvailableOptions();
		String[] labels = getOptionsLabels().toArray(new String[availableOptions.size()]);

		if (getField().isMultiple()) {
			final boolean[] checked = getCheckedOptions();
			builder.setMultiChoiceItems(labels, checked, new DialogInterface.OnMultiChoiceClickListener() {
				public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
					checked[whichButton] = isChecked;
					shown = false;
				}
			});
			builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					shown = false;
					checkField(checked, availableOptions);
					refresh();
					alertDialog.dismiss();
				}
			});

			builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					alertDialog.dismiss();
					shown = false;
				}
			});
		} else {
			builder.setItems(labels, selectOptionHandler);
		}
		alertDialog = builder.create();
	}

	@Override
	public Observable getObservable() {
		return Observable.interval(100, TimeUnit.MILLISECONDS).filter(new Func1<Long, Boolean>() {
			@Override
			public Boolean call(Long aLong) {
				return System.currentTimeMillis() - timer > Field.RATE_FIELD;
			}
		}).filter(new Func1<Long, Boolean>() {
			@Override
			public Boolean call(Long aLong) {
				return shown;
			}
		}).
			map(new Func1() {
				@Override
				public Object call(Object o) {
					return getField();
				}
			}).distinctUntilChanged().map(new Func1() {
			@Override
			public Object[] call(Object o) {
				return new Object[] { getField(), System.currentTimeMillis() - timer };
			}
		});
	}

	protected DialogInterface.OnClickListener getAlertDialogListener() {
		return new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				shown = false;
				getField().selectOption(getField().getAvailableOptions().get(which));
				refresh();
			}
		};
	}

	protected List<String> getOptionsLabels() {
		List<String> result = new ArrayList<>();
		for (StringWithOptionsField.Option opt : getField().getAvailableOptions()) {
			result.add(opt.label);
		}
		return result;
	}

	private boolean[] getCheckedOptions() {
		List<StringWithOptionsField.Option> availableOptions = getField().getAvailableOptions();
		boolean[] checked = new boolean[availableOptions.size()];
		for (int i = 0; i < availableOptions.size(); i++) {
			StringWithOptionsField.Option availableOption = availableOptions.get(i);
			checked[i] = getField().isSelected(availableOption);
		}
		return checked;
	}

	private void checkField(boolean[] checked, List<StringWithOptionsField.Option> availableOptions) {
		for (int i = 0; i < checked.length; i++) {
			StringWithOptionsField.Option option = availableOptions.get(i);
			if (checked[i]) {
				getField().selectOption(option);
			} else {
				getField().clearOption(option);
			}
		}
	}
}