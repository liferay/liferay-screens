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

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.model.DateField;
import com.liferay.mobile.screens.ddl.model.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Silvio Santos
 */
public class DDLFieldDateView extends BaseDDLFieldTextView<DateField>
	implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

	protected DatePickerDialog pickerDialog;
	private boolean shown;

	public DDLFieldDateView(Context context) {
		super(context);
	}

	public DDLFieldDateView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public DDLFieldDateView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onClick(View view) {
		Date date = getField().getCurrentValue();

		if (date == null) {
			date = new Date();
		}

		Calendar.getInstance().setTime(date);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		pickerDialog = new DatePickerDialog(getContext(), getDatePickerStyle(), this, year, month, day);

		timer = System.currentTimeMillis();
		shown = true;
		pickerDialog.show();
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Locale locale = getResources().getConfiguration().locale;

		Calendar calendar = Calendar.getInstance(locale);
		calendar.set(year, month, day);

		getField().setCurrentValue(calendar.getTime());

		refresh();
		shown = false;
	}

	protected int getDatePickerStyle() {
		return R.style.default_date_picker;
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (pickerDialog != null) {
			pickerDialog.dismiss();
			pickerDialog = null;
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
		//not doing anything at the moment, because field is being set
		//using the DatePickerDialog
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
		}).map(new Func1() {
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
}