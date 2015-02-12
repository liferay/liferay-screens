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

package com.liferay.mobile.screens.themes.ddl;

import android.content.Context;

import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.ddl.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.view.DDLFormViewModel;
import com.liferay.mobile.screens.themes.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class DDLFormScreenletView
	extends ScrollView implements DDLFormViewModel, DDLFormListener {

	public DDLFormScreenletView(Context context) {
		super(context, null);
	}

	public DDLFormScreenletView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public DDLFormScreenletView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	/**
	 * The layout associated with each form field.
	 *
	 * @return a layout resource id associated with the field editor type
	 */

	@Override
	public int getFieldLayoutId(Field.EditorType editorType) {
		return _layoutIds.get(editorType);
	}

	/**
	 * Sets the layout associated a field
	 * You should use this method if you want to change the layout of your fields
	 *
	 * @param editorType EditorType associated with this layout
	 * @param layoutId the layout resource id for this editor type
	 */

	@Override
	public void setFieldLayoutId(Field.EditorType editorType, int layoutId) {
		_layoutIds.put(editorType, layoutId);
	}

	@Override
	public void onDDLFormLoaded(Record record) {
		for (int i = 0; i < record.getFieldCount(); ++i) {
			// We have to assign ids to onSave/onRestore methods be fired
			//TODO Assign ids by position should not be a problem, but
			// we have to check if it will conflict with other views
			addFieldView(record.getField(i), i);
		}
	}

	@Override
	public void onDDLFormRecordLoaded(Record record) {
		for (int i = 0; i < record.getFieldCount(); ++i) {
			DDLFieldViewModel viewModel = (DDLFieldViewModel) findViewById(i);
			viewModel.refresh();
		}
	}

	@Override
	public void onDDLFormRecordAdded(Record record) {

	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {

	}

	@Override
	public void onDDLFormLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormAddRecordFailed(Exception e) {

	}

	@Override
	public void onDDLFormUpdateRecordFailed(Exception e) {

	}

	protected void addFieldView(Field field, int id) {
		int layoutId = getFieldLayoutId(field.getEditorType());

		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(layoutId, this, false);
		view.setId(id);

		DDLFieldViewModel viewModel = (DDLFieldViewModel)view;
		viewModel.setField(field);

		_fieldsContainerView.addView(view);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_fieldsContainerView = (ViewGroup)
			findViewById(R.id.ddlfields_container);
	}

	private ViewGroup _fieldsContainerView;
	private Map<Field.EditorType, Integer> _layoutIds = new HashMap<>();

}