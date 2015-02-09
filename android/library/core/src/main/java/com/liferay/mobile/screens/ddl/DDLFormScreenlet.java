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

package com.liferay.mobile.screens.ddl;

import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormLoadInteractorImpl;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.view.DDLFormViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class DDLFormScreenlet
	extends BaseScreenlet<DDLFormViewModel, Interactor> {

	public DDLFormScreenlet(Context context) {
		this(context, null);
	}

	public DDLFormScreenlet(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public DDLFormScreenlet(
		Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);
	}

	@Override
	protected Interactor createInteractor(String actionName) {
		return new DDLFormLoadInteractorImpl(getScreenletId());
	}

	@Override
	protected View createScreenletView(
		Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.DDLFormScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(
			R.styleable.DDLFormScreenlet_layoutId, 0);

		View view = LayoutInflater.from(getContext()).inflate(layoutId, null);

		DDLFormViewModel viewModel = (DDLFormViewModel)view;

		setFieldLayoutId(
			viewModel, Field.EditorType.CHECKBOX, typedArray,
			R.styleable.DDLFormScreenlet_checkboxFieldLayoutId);

		setFieldLayoutId(
			viewModel, Field.EditorType.DATE, typedArray,
			R.styleable.DDLFormScreenlet_dateFieldLayoutId);

		setFieldLayoutId(
			viewModel, Field.EditorType.NUMBER, typedArray,
			R.styleable.DDLFormScreenlet_numberFieldLayoutId);

		setFieldLayoutId(
			viewModel, Field.EditorType.RADIO, typedArray,
			R.styleable.DDLFormScreenlet_radioFieldLayoutId);

		setFieldLayoutId(
			viewModel, Field.EditorType.SELECT, typedArray,
			R.styleable.DDLFormScreenlet_selectFieldLayoutId);

		setFieldLayoutId(
			viewModel, Field.EditorType.TEXT, typedArray,
			R.styleable.DDLFormScreenlet_textFieldLayoutId);

		typedArray.recycle();

		return view;
	}

	protected int getDefaultLayoutId(Field.EditorType type) {
		Context context = getContext();
		String layoutName = _defaultLayoutNames.get(type);
		String packageName = context.getPackageName();

		int layoutId = context.getResources().getIdentifier(
			layoutName, "layout", packageName);

		return layoutId;
	}

	@Override
	protected void onUserAction(String userActionName, Interactor interactor) {
	}

	protected void setFieldLayoutId(
		DDLFormViewModel viewModel, Field.EditorType editorType,
		TypedArray typedArray, int index) {

		int layoutId = typedArray.getResourceId(
			index, getDefaultLayoutId(editorType));

		viewModel.setFieldLayoutId(editorType, layoutId);
	}

	private static Map<Field.EditorType, String> _defaultLayoutNames;

	static {
		_defaultLayoutNames = new HashMap<>();
		_defaultLayoutNames.put(
			Field.EditorType.CHECKBOX, "ddlfield_checkbox_default");

		_defaultLayoutNames.put(Field.EditorType.DATE, "ddlfield_date_default");

		_defaultLayoutNames.put(
			Field.EditorType.NUMBER, "ddlfield_number_default");

		_defaultLayoutNames.put(
			Field.EditorType.RADIO, "ddlfield_radio_default");

		_defaultLayoutNames.put(
			Field.EditorType.SELECT, "ddlfield_select_default");

		_defaultLayoutNames.put(Field.EditorType.TEXT, "ddlfield_text_default");
	}

}