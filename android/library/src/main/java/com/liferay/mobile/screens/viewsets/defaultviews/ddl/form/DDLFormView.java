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
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldSelectView;
import java.util.HashMap;
import java.util.Map;
import rx.Observable;

/**
 * @author Silvio Santos
 */
public class DDLFormView extends ScrollView implements DDLFormViewModel, View.OnClickListener {

	private static final Map<Field.EditorType, Integer> DEFAULT_LAYOUT_IDS = new HashMap<>(16);

	static {
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.CHECKBOX, R.layout.ddlfield_checkbox_default);
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.DATE, R.layout.ddlfield_date_default);
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.NUMBER, R.layout.ddlfield_number_default);
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.INTEGER, R.layout.ddlfield_number_default);
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.DECIMAL, R.layout.ddlfield_number_default);
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.RADIO, R.layout.ddlfield_radio_default);
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.SELECT, R.layout.ddlfield_select_default);
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.TEXT, R.layout.ddlfield_text_default);
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.TEXT_AREA, R.layout.ddlfield_text_area_default);
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.DOCUMENT, R.layout.ddlfield_document_default);
	}

	private final Map<Field.EditorType, Integer> layoutIds = new HashMap<>();
	private final Map<String, Integer> customLayoutIds = new HashMap<>();
	protected ProgressBar progressBar;
	protected ProgressBar loadingFormProgressBar;
	protected ViewGroup fieldsContainerView;
	protected Button submitButton;
	private BaseScreenlet screenlet;

	public DDLFormView(Context context) {
		super(context);
	}

	public DDLFormView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public DDLFormView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public int getFieldLayoutId(Field.EditorType editorType) {
		return layoutIds.get(editorType);
	}

	@Override
	public void setFieldLayoutId(Field.EditorType editorType, int layoutId) {
		layoutIds.put(editorType, layoutId);
	}

	@Override
	public void resetFieldLayoutId(Field.EditorType editorType) {
		layoutIds.put(editorType, DEFAULT_LAYOUT_IDS.get(editorType));
	}

	@Override
	public int getCustomFieldLayoutId(String fieldName) {
		return customLayoutIds.get(fieldName);
	}

	@Override
	public void setCustomFieldLayoutId(String fieldName, int layoutId) {
		customLayoutIds.put(fieldName, layoutId);
	}

	@Override
	public void resetCustomFieldLayoutId(String fieldName) {
		customLayoutIds.remove(fieldName);
	}

	@Override
	public void showValidationResults(Map<Field, Boolean> fieldResults, boolean autoscroll) {
		boolean scrolled = false;

		for (int i = 0; i < fieldsContainerView.getChildCount(); i++) {
			View fieldView = fieldsContainerView.getChildAt(i);
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
	}

	@Override
	public void showStartOperation(String actionName, Object argument) {
		switch (actionName) {
			case DDLFormScreenlet.UPLOAD_DOCUMENT_ACTION:
				DocumentField documentField = (DocumentField) argument;

				findFieldView(documentField).refresh();
				break;
			case DDLFormScreenlet.LOAD_FORM_ACTION:
				LiferayLogger.i("loading DDLForm");
				loadingFormProgressBar.setVisibility(VISIBLE);
				break;
			default:
				progressBar.setVisibility(VISIBLE);
				break;
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
			case DDLFormScreenlet.LOAD_RECORD_ACTION:
				LiferayLogger.i("loaded record");
				showRecordValues();
				break;
			case DDLFormScreenlet.UPLOAD_DOCUMENT_ACTION:
				LiferayLogger.i("uploaded document");
				DocumentField documentField = (DocumentField) argument;

				findFieldView(documentField).refresh();
				break;
			case DDLFormScreenlet.LOAD_FORM_ACTION:
			default:
				LiferayLogger.i("loaded form");
				Record record = (Record) argument;

				showFormFields(record);
				break;
		}
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		showFailedOperation(actionName, e, null);
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}

	@Override
	public void showFailedOperation(String actionName, Exception e, Object argument) {
		hideProgressBar(actionName);
		if (actionName.equals(DDLFormScreenlet.LOAD_FORM_ACTION)) {
			LiferayLogger.e("error loading DDLForm", e);

			clearFormFields();
		} else if (actionName.equals(DDLFormScreenlet.UPLOAD_DOCUMENT_ACTION)) {
			LiferayLogger.e("error uploading", e);

			DocumentField documentField = (DocumentField) argument;

			findFieldView(documentField).refresh();
		}
	}

	@Override
	public Observable getEventsObservable() {
		return null;
	}

	@Override
	public void showFormFields(Record record) {
		fieldsContainerView.removeAllViews();
		fieldsContainerView.setVisibility(INVISIBLE);

		for (int i = 0; i < record.getFieldCount(); ++i) {
			addFieldView(record.getField(i), i);
		}

		int visibility = getDDLFormScreenlet().isShowSubmitButton() ? VISIBLE : INVISIBLE;
		submitButton.setVisibility(visibility);

		DefaultAnimation.showViewWithReveal(fieldsContainerView);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.liferay_form_submit) {
			if (getDDLFormScreenlet().validateForm()) {
				getDDLFormScreenlet().submitForm();
			}
		} else {
			getDDLFormScreenlet().startUpload((DocumentField) view.getTag());
		}
	}

	protected void clearFormFields() {
		fieldsContainerView.removeAllViews();
		submitButton.setVisibility(INVISIBLE);
	}

	protected void hideProgressBar(String actionName) {
		if (actionName.equals(DDLFormScreenlet.LOAD_FORM_ACTION)) {
			loadingFormProgressBar.setVisibility(INVISIBLE);
		} else {
			progressBar.setVisibility(INVISIBLE);
		}
	}

	protected void showRecordValues() {
		for (int i = 0; i < fieldsContainerView.getChildCount(); i++) {
			DDLFieldViewModel viewModel = (DDLFieldViewModel) fieldsContainerView.getChildAt(i);
			viewModel.refresh();
		}
	}

	protected DDLFormScreenlet getDDLFormScreenlet() {
		return (DDLFormScreenlet) getScreenlet();
	}

	protected void addFieldView(Field field, int position) {

		boolean isACustomLayout = customLayoutIds.containsKey(field.getName());
		int layoutId =
			isACustomLayout ? getCustomFieldLayoutId(field.getName()) : getFieldLayoutId(field.getEditorType());

		View view = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
		DDLFieldViewModel viewModel = (DDLFieldViewModel) view;

		viewModel.setField(field);
		viewModel.setParentView(this);

		fieldsContainerView.addView(view);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		fieldsContainerView = (ViewGroup) findViewById(R.id.ddlfields_container);

		submitButton = (Button) findViewById(R.id.liferay_form_submit);
		submitButton.setOnClickListener(this);

		progressBar = (ProgressBar) findViewById(R.id.ddlform_progress_bar);
		loadingFormProgressBar = (ProgressBar) findViewById(R.id.ddlform_loading_screen_progress_bar);
	}

	private DDLFieldViewModel findFieldView(Field field) {
		for (int i = 0; i < fieldsContainerView.getChildCount(); i++) {
			DDLFieldViewModel viewModel = (DDLFieldViewModel) fieldsContainerView.getChildAt(i);
			if (field.equals(viewModel.getField())) {
				return viewModel;
			}
		}
		return null;
	}

	public void clearFocusOfFields(DDLFieldViewModel ddlFieldViewModel) {

	}
}