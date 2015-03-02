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

package com.liferay.mobile.screens.themes.ddl.form;

import android.content.Context;

import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.form.view.DDLFormViewModel;
import com.liferay.mobile.screens.themes.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class DDLFormScreenletView
	extends ScrollView implements DDLFormViewModel, View.OnClickListener {

	public DDLFormScreenletView(Context context) {
		super(context, null);
	}

	public DDLFormScreenletView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public DDLFormScreenletView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public int getFieldLayoutId(Field.EditorType editorType) {
		return _layoutIds.get(editorType);
	}


	@Override
	public void setFieldLayoutId(Field.EditorType editorType, int layoutId) {
		_layoutIds.put(editorType, layoutId);
	}

	@Override
	public int getCustomFieldLayoutId(String fieldName) {
		return _customLayoutIds.get(fieldName);
	}

	@Override
	public void setCustomFieldLayoutId(String fieldName, int layoutId) {
		_customLayoutIds.put(fieldName, layoutId);
	}

	@Override
	public void setRecordFields(Record record) {

		if (record == null) {
			return;
		}
		_fieldsContainerView.removeAllViews();

		for (int i = 0; i < record.getFieldCount(); ++i) {
			addFieldView(record.getField(i), i);
		}

		DDLFormScreenlet screenlet = getDDLFormScreenlet();

		if (record.getFieldCount() > 0 && screenlet.isShowSubmitButton()) {
			_submitButton.setVisibility(VISIBLE);
		}
	}

	@Override
	public void setRecordValues(Record record) {
		for (int i = 0; i < _fieldsContainerView.getChildCount(); i++) {
			DDLFieldViewModel viewModel = (DDLFieldViewModel) _fieldsContainerView.getChildAt(i);
			viewModel.refresh();
		}
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.submit) {
			if (validateForm()) {
				getDDLFormScreenlet().submitForm();
			}
		} else {
			getDDLFormScreenlet().startUpload((DocumentField) view.getTag());
		}
	}

	@Override
	public void showStartDocumentUpload(DocumentField documentField) {
		findFieldView(documentField).refresh();
	}

	@Override
	public void showDocumentUploaded(DocumentField documentField) {
		findFieldView(documentField).refresh();
	}

	@Override
	public void showDocumentUploadFailed(DocumentField documentField) {
		findFieldView(documentField).refresh();
	}

	protected DDLFormScreenlet getDDLFormScreenlet() {
		return (DDLFormScreenlet) getParent();
	}

	public boolean validateForm() {
		boolean validForm = true;
		View firstInvalidFieldView = null;

		DDLFormScreenlet screenlet = getDDLFormScreenlet();
		Record record = screenlet.getRecord();

		for (int i = 0; i < _fieldsContainerView.getChildCount(); i++) {
			Field field = record.getField(i);

			View fieldView = _fieldsContainerView.getChildAt(i);
			boolean validField = field.isValid();

			if (!validField) {
				if (firstInvalidFieldView == null) {
					firstInvalidFieldView = fieldView;
				}

				validForm = false;
			}

			fieldView.clearFocus();

			DDLFieldViewModel viewModel = (DDLFieldViewModel)fieldView;
			viewModel.onPostValidation(validField);
		}

		boolean autoScroll = screenlet.isAutoScrollOnValidation();

		if (autoScroll && (firstInvalidFieldView != null)) {
			firstInvalidFieldView.requestFocus();

			smoothScrollTo(0, firstInvalidFieldView.getTop());
		}

		return validForm;
	}

	protected void addFieldView(Field field, int position) {
		int layoutId;

		if (_customLayoutIds.containsKey(field.getName())) {
			layoutId = getCustomFieldLayoutId(field.getName());
		}
		else {
			layoutId = getFieldLayoutId(field.getEditorType());
		}

		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(layoutId, this, false);

		DDLFieldViewModel viewModel = (DDLFieldViewModel)view;
		viewModel.setField(field);
		viewModel.setParentView(this);
		viewModel.setPositionInParent(position);

		_fieldsContainerView.addView(view);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_fieldsContainerView = (ViewGroup) findViewById(R.id.ddlfields_container);

		_submitButton = (Button) findViewById(R.id.submit);
		_submitButton.setOnClickListener(this);
	}

	private DDLFieldViewModel findFieldView(Field field) {
		for (int i = 0; i < _fieldsContainerView.getChildCount(); i++) {
			DDLFieldViewModel viewModel = (DDLFieldViewModel) _fieldsContainerView.getChildAt(i);
			if (field.equals(viewModel.getField())) {
				return viewModel;
			}
		}
		return null;
	}

	private ViewGroup _fieldsContainerView;
	private Button _submitButton;
	private Map<Field.EditorType, Integer> _layoutIds = new HashMap<>();
	private Map<String, Integer> _customLayoutIds = new HashMap<>();

}