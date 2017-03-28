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
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.BooleanField;
import com.liferay.mobile.screens.ddl.model.Field;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldCheckboxView extends LinearLayout
	implements DDLFieldViewModel<BooleanField>, CompoundButton.OnCheckedChangeListener {

	protected BooleanField field;
	protected SwitchCompat switchCompat;
	protected View parentView;
	private boolean shown;
	private long timer = System.currentTimeMillis();

	public DDLFieldCheckboxView(Context context) {
		super(context);
	}

	public DDLFieldCheckboxView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public DDLFieldCheckboxView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public BooleanField getField() {
		return field;
	}

	@Override
	public void setField(BooleanField field) {
		this.field = field;

		if (this.field.isShowLabel()) {
			switchCompat.setHint("");
			switchCompat.setText(this.field.getLabel());
		} else {
			switchCompat.setHint(this.field.getLabel());
			switchCompat.setText("");
		}

		refresh();
	}

	@Override
	public void refresh() {
		switchCompat.setChecked(field.getCurrentValue());
	}

	@Override
	public void onPostValidation(boolean valid) {
		//This field is always valid because it has always a value
	}

	@Override
	public View getParentView() {
		return parentView;
	}

	@Override
	public void setParentView(View view) {
		parentView = view;
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
				return field;
			}
		}).distinctUntilChanged();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		field.setCurrentValue(isChecked);
		shown = true;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		setSaveEnabled(false);

		switchCompat = (SwitchCompat) findViewById(R.id.liferay_ddl_switch);

		switchCompat.setOnCheckedChangeListener(this);
	}
}