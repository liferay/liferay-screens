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

package com.liferay.mobile.screens.viewsets.defaultviews.ddl.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.form.view.DDLFormViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class DDLFormView
	extends ScrollView implements DDLFormViewModel, View.OnClickListener {

	public DDLFormView(Context context) {
		super(context);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public DDLFormView(Context context, AttributeSet attributes) {
		super(context, attributes);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public DDLFormView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);

		DefaultTheme.initIfThemeNotPresent(context);
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
	public void resetFieldLayoutId(Field.EditorType editorType) {
		_layoutIds.put(editorType, _defaultLayoutIds.get(editorType));
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
	public void resetCustomFieldLayoutId(String fieldName) {
		_customLayoutIds.remove(fieldName);
	}

	@Override
	public void showValidationResults(Map<Field, Boolean> fieldResults, boolean autoscroll) {
		boolean scrolled = false;

		for (int i = 0; i < _fieldsContainerView.getChildCount(); i++) {
			View fieldView = _fieldsContainerView.getChildAt(i);
			DDLFieldViewModel fieldViewModel = (DDLFieldViewModel) fieldView;
			boolean isFieldValid = fieldResults.get(fieldViewModel.getField());

			fieldView.clearFocus();

			fieldViewModel.onPostValidation(isFieldValid);

			if (!isFieldValid && autoscroll && !scrolled) {
				fieldView.requestFocus();
				smoothScrollTo(0, fieldView.getTop());
				scrolled = true;
			}
		}
	}

	@Override
	public void showStartOperation(String actionName) {
		throw new AssertionError("Use showStartOperation(actionName, argument) instead");
	}

	@Override
	public void showStartOperation(String actionName, Object argument) {
		if (actionName.equals(DDLFormScreenlet.UPLOAD_DOCUMENT_ACTION)) {
			DocumentField documentField = (DocumentField) argument;

			findFieldView(documentField).refresh();
		}
		else {
			LiferayLogger.i("loading DDLForm");
			showProgressBar(actionName);
		}
	}

	@Override
	public void showFinishOperation(String actionName) {
		showFinishOperation(actionName, null);
	}

	@Override
	public void showFinishOperation(String actionName, Object argument) {
		hideProgressBar(actionName);
		switch (actionName) {
			case DDLFormScreenlet.LOAD_FORM_ACTION:
				LiferayLogger.i("loaded form");
				Record record = (Record) argument;

				showFormFields(record);
				break;
			case DDLFormScreenlet.LOAD_RECORD_ACTION:
				LiferayLogger.i("loaded record");
				showRecordValues();
				break;
			case DDLFormScreenlet.UPLOAD_DOCUMENT_ACTION:
				LiferayLogger.i("uploaded document");
				DocumentField documentField = (DocumentField) argument;

				findFieldView(documentField).refresh();
				break;
		}
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		showFailedOperation(actionName, e, null);
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		_screenlet = screenlet;
	}

	@Override
	public void showFailedOperation(String actionName, Exception e, Object argument) {
		hideProgressBar(actionName);
		if (actionName.equals(DDLFormScreenlet.LOAD_FORM_ACTION)) {
			LiferayLogger.e("error loading DDLForm", e);
			LiferayCrouton.error(getContext(), getContext().getString(R.string.loading_form_error), e);

			clearFormFields();
		}
		else if (actionName.equals(DDLFormScreenlet.UPLOAD_DOCUMENT_ACTION)) {
			LiferayLogger.e("error uploading", e);
			LiferayCrouton.error(getContext(), getContext().getString(R.string.uploading_document_error), e);

			DocumentField documentField = (DocumentField) argument;

			findFieldView(documentField).refresh();
		}
	}

	@Override
	public void showFormFields(Record record) {
		_fieldsContainerView.removeAllViews();
		_fieldsContainerView.setVisibility(INVISIBLE);

		for (int i = 0; i < record.getFieldCount(); ++i) {
			addFieldView(record.getField(i), i);
		}

		if (getDDLFormScreenlet().isShowSubmitButton()) {
			_submitButton.setVisibility(VISIBLE);
		}
		else {
			_submitButton.setVisibility(INVISIBLE);
		}

		DefaultAnimation.showViewWithReveal(_fieldsContainerView);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.liferay_form_submit) {
			if (getDDLFormScreenlet().validateForm()) {
				getDDLFormScreenlet().submitForm();
			}
		}
		else {
			getDDLFormScreenlet().startUpload((DocumentField) view.getTag());
		}
	}

	protected void clearFormFields() {
		_fieldsContainerView.removeAllViews();
		_submitButton.setVisibility(INVISIBLE);
	}

	protected void showProgressBar(String actionName) {
		if (actionName.equals(DDLFormScreenlet.LOAD_FORM_ACTION)) {
			_loadingFormProgressBar.setVisibility(VISIBLE);
		}
		else {
			_progressBar.setVisibility(VISIBLE);
		}
	}

	protected void hideProgressBar(String actionName) {
		if (actionName.equals(DDLFormScreenlet.LOAD_FORM_ACTION)) {
			_loadingFormProgressBar.setVisibility(INVISIBLE);
		}
		else {
			_progressBar.setVisibility(INVISIBLE);
		}
	}

	protected void showRecordValues() {
		for (int i = 0; i < _fieldsContainerView.getChildCount(); i++) {
			DDLFieldViewModel viewModel = (DDLFieldViewModel) _fieldsContainerView.getChildAt(i);
			viewModel.refresh();
		}
	}

	protected DDLFormScreenlet getDDLFormScreenlet() {
		return (DDLFormScreenlet) getScreenlet();
	}

	protected void addFieldView(Field field, int position) {
		int layoutId;

		if (_customLayoutIds.containsKey(field.getName())) {
			layoutId = getCustomFieldLayoutId(field.getName());
		}
		else {
			layoutId = getFieldLayoutId(field.getEditorType());
		}

		View view = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
		DDLFieldViewModel viewModel = (DDLFieldViewModel) view;

		viewModel.setField(field);
		viewModel.setParentView(this);
		viewModel.setPositionInParent(position);

		_fieldsContainerView.addView(view);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_fieldsContainerView = (ViewGroup) findViewById(R.id.ddlfields_container);

		_submitButton = (Button) findViewById(R.id.liferay_form_submit);
		_submitButton.setOnClickListener(this);

		_progressBar = (ProgressBar) findViewById(R.id.ddlform_progress_bar);
		_loadingFormProgressBar = (ProgressBar) findViewById(R.id.ddlform_loading_screen_progress_bar);
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
	protected ProgressBar _progressBar;
	protected ProgressBar _loadingFormProgressBar;
	protected ViewGroup _fieldsContainerView;
	protected Button _submitButton;

	private static Map<Field.EditorType, Integer> _defaultLayoutIds = new HashMap<>(16);
	private Map<Field.EditorType, Integer> _layoutIds = new HashMap<>();
	private Map<String, Integer> _customLayoutIds = new HashMap<>();
	private BaseScreenlet _screenlet;

	static {
		_defaultLayoutIds.put(Field.EditorType.CHECKBOX, R.layout.ddlfield_checkbox_default);
		_defaultLayoutIds.put(Field.EditorType.DATE, R.layout.ddlfield_date_default);
		_defaultLayoutIds.put(Field.EditorType.NUMBER, R.layout.ddlfield_number_default);
		_defaultLayoutIds.put(Field.EditorType.INTEGER, R.layout.ddlfield_number_default);
		_defaultLayoutIds.put(Field.EditorType.DECIMAL, R.layout.ddlfield_number_default);
		_defaultLayoutIds.put(Field.EditorType.RADIO, R.layout.ddlfield_radio_default);
		_defaultLayoutIds.put(Field.EditorType.SELECT, R.layout.ddlfield_select_default);
		_defaultLayoutIds.put(Field.EditorType.TEXT, R.layout.ddlfield_text_default);
		_defaultLayoutIds.put(Field.EditorType.TEXT_AREA, R.layout.ddlfield_text_area_default);
		_defaultLayoutIds.put(Field.EditorType.DOCUMENT, R.layout.ddlfield_document_default);
	}

}