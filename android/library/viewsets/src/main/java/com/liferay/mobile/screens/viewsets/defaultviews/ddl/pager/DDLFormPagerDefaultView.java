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

package com.liferay.mobile.screens.viewsets.defaultviews.ddl.pager;

import android.content.Context;

import android.support.v4.view.ViewPager;

import android.util.AttributeSet;

import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.viewsets.R;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.DDLFormDefaultView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class DDLFormPagerDefaultView extends DDLFormDefaultView {

	public DDLFormPagerDefaultView(Context context) {
		super(context, null);
	}

	public DDLFormPagerDefaultView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	@Override
	public void showFormFields(Record record) {
		List<Field.EditorType> editorTypes = Field.EditorType.all();

		Map<Field.EditorType, Integer> layoutIds = new HashMap<>();
		for (Field.EditorType editorType : editorTypes) {
			layoutIds.put(editorType, getFieldLayoutId(editorType));
		}

		List<Field> fields = new ArrayList<>(record.getFieldCount());
		for (int i = 0; i < record.getFieldCount(); ++i) {
			fields.add(record.getField(i));
		}

		_pager.setAdapter(new DDLFormPagerDefaultViewAdapter(fields, layoutIds));
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_pager = (ViewPager)findViewById(R.id.pager);
	}

	private ViewPager _pager;

}