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
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormAddRecordInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormAddRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormBaseInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormLoadInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormLoadInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormLoadRecordInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormLoadRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormUpdateRecordInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormUpdateRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.ddl.view.DDLFormViewModel;
import com.liferay.mobile.screens.util.LiferayServerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class DDLFormScreenlet
	extends BaseScreenlet<DDLFormViewModel, DDLFormBaseInteractor>
	implements DDLFormListener {

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

	public void loadForm() {
		performUserAction(_LOAD_FORM_ACTION);
	}

	public void loadRecord() {
		performUserAction(_LOAD_RECORD_ACTION);
	}

	public void submitForm() {
		if (_record.getRecordId() == 0) {
			performUserAction(_ADD_RECORD_ACTION);
		}
		else {
			performUserAction(_UPDATE_RECORD_ACTION);
		}
	}

	@Override
	public void onDDLFormLoaded(Record record) {
		DDLFormListener view = (DDLFormListener)getScreenletView();
		view.onDDLFormLoaded(record);

		if (_listener != null) {
			_listener.onDDLFormLoaded(record);
		}
	}

	@Override
	public void onDDLFormRecordLoaded(Record record) {
		DDLFormListener view = (DDLFormListener)getScreenletView();
		view.onDDLFormRecordLoaded(record);

		if (_listener != null) {
			_listener.onDDLFormRecordLoaded(record);
		}
	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		DDLFormListener view = (DDLFormListener)getScreenletView();
		view.onDDLFormRecordAdded(record);

		if (_listener != null) {
			_listener.onDDLFormRecordAdded(record);
		}
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		DDLFormListener view = (DDLFormListener)getScreenletView();
		view.onDDLFormRecordUpdated(record);

		if (_listener != null) {
			_listener.onDDLFormRecordUpdated(record);
		}
	}

	@Override
	public void onDDLFormLoadFailed(Exception e) {
		DDLFormListener view = (DDLFormListener)getScreenletView();
		view.onDDLFormLoadFailed(e);

		if (_listener != null) {
			_listener.onDDLFormLoadFailed(e);
		}
	}

	@Override
	public void onDDLFormRecordLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormAddRecordFailed(Exception e) {
		DDLFormListener view = (DDLFormListener)getScreenletView();
		view.onDDLFormAddRecordFailed(e);

		if (_listener != null) {
			_listener.onDDLFormAddRecordFailed(e);
		}
	}

	@Override
	public void onDDLFormUpdateRecordFailed(Exception e) {
		DDLFormListener view = (DDLFormListener)getScreenletView();
		view.onDDLFormUpdateRecordFailed(e);

		if (_listener != null) {
			_listener.onDDLFormUpdateRecordFailed(e);
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public DDLFormListener getListener() {
		return _listener;
	}

	public void setListener(DDLFormListener listener) {
		_listener = listener;
	}

	@Override
	protected DDLFormBaseInteractor createInteractor(String actionName) {
		DDLFormBaseInteractor result = null;

		if (_LOAD_FORM_ACTION.equals(actionName)) {
			result = new DDLFormLoadInteractorImpl(getScreenletId());
		}
		else if (_LOAD_RECORD_ACTION.equals(actionName)) {
			if (_record.isRecordStructurePresent()) {
				result = new DDLFormLoadRecordInteractorImpl(getScreenletId());
			}
			else {
				// TODO request both structure and data
			}
		}
		else if (_ADD_RECORD_ACTION.equals(actionName)) {
			result = new DDLFormAddRecordInteractorImpl(getScreenletId());
		}
		else if (_UPDATE_RECORD_ACTION.equals(actionName)) {
			result = new DDLFormUpdateRecordInteractorImpl(getScreenletId());
		}

		return result;
	}

	@Override
	protected View createScreenletView(
		Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.DDLFormScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(
			R.styleable.DDLFormScreenlet_layoutId, 0);

		_groupId = typedArray.getInteger(
			R.styleable.DDLFormScreenlet_groupId,
			(int) LiferayServerContext.getGroupId());

		_structureId = typedArray.getInteger(
			R.styleable.DDLFormScreenlet_structureId, 0);

		_recordSetId = typedArray.getInteger(
			R.styleable.DDLFormScreenlet_recordSetId, 0);

		_recordId = typedArray.getInteger(
			R.styleable.DDLFormScreenlet_recordId, 0);

		//TODO use current user id from SessionContext as default
		_userId = typedArray.getInteger(
			R.styleable.DDLFormScreenlet_userId, 0);

		_record = new Record(getResources().getConfiguration().locale);
		_record.setStructureId(_structureId);
		_record.setRecordSetId(_recordSetId);
		_record.setRecordId(_recordId);
		_record.setCreatorUserId(_userId);

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
	protected void onUserAction(String userActionName, DDLFormBaseInteractor interactor) {
		if (_LOAD_FORM_ACTION.equals(userActionName)) {
			DDLFormLoadInteractor loadInteractor = (DDLFormLoadInteractor) interactor;

			try {
				loadInteractor.load(_record);
			}
			catch (Exception e) {
				// TODO user message
			}
		}
		else if (_LOAD_RECORD_ACTION.equals(userActionName)) {
			if (_record.isRecordStructurePresent()) {
				DDLFormLoadRecordInteractor loadInteractor = (DDLFormLoadRecordInteractor) interactor;

				try {
					loadInteractor.loadRecord(_record);
				}
				catch (Exception e) {
					// TODO user message
				}
			}
			else {
				// TODO request both structure and data
			}
		}
		else if (_ADD_RECORD_ACTION.equals(userActionName)) {
			DDLFormAddRecordInteractor addInteractor = (DDLFormAddRecordInteractor) interactor;

			try {
				addInteractor.addRecord(_groupId, _record);
			}
			catch (Exception e) {
				// TODO user message
			}
		}
		else if (_UPDATE_RECORD_ACTION.equals(userActionName)) {
			DDLFormUpdateRecordInteractor updateInteractor = (DDLFormUpdateRecordInteractor) interactor;

			try {
				updateInteractor.updateRecord(_groupId, _record);
			}
			catch (Exception e) {
				// TODO user message
			}
		}
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


	private static final String _LOAD_FORM_ACTION = "loadForm";
	private static final String _LOAD_RECORD_ACTION = "loadRecord";
	private static final String _ADD_RECORD_ACTION = "addRecord";
	private static final String _UPDATE_RECORD_ACTION = "updateRecord";

	private long _groupId;
	private long _structureId;
	private long _recordSetId;
	private long _recordId;
	private long _userId;

	// TODO make Record parcelable
	private Record _record;

	private DDLFormListener _listener;

}