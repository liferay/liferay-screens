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

package com.liferay.mobile.screens.themes.ddl.pager;

import android.content.Context;

import android.support.v4.view.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;

import java.util.List;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class DDLFormViewPagerAdapter extends PagerAdapter {

	public DDLFormViewPagerAdapter(
		List<Field> fields, Map<Field.EditorType, Integer> layoutIds) {

		_fields = fields;
		_layoutIds = layoutIds;
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Context context = container.getContext();
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);

		int nFields = 2;

		for (int i = 0; i < nFields; i++) {
			Field field = _fields.get(i);
			Field.EditorType type = field.getEditorType();
			int layoutId = _layoutIds.get(type);

			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(layoutId, layout, false);

			DDLFieldViewModel viewModel = (DDLFieldViewModel)view;
			viewModel.setField(field);

			layout.addView(view);
		}

		container.addView(layout);

		return layout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (view == object);
	}

	private List<Field> _fields;
	private Map<Field.EditorType, Integer> _layoutIds;
}