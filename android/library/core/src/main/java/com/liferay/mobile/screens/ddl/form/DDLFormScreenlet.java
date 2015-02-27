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

package com.liferay.mobile.screens.ddl.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormAddRecordInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormAddRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormBaseInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormLoadInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormLoadInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormLoadRecordInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormLoadRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormUpdateRecordInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormUpdateRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormUploadInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormUploadInteractorImpl;
import com.liferay.mobile.screens.ddl.form.view.DDLFormViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class DDLFormScreenlet
	extends BaseScreenlet<DDLFormViewModel, DDLFormBaseInteractor>
	implements DDLFormListener {

	public DDLFormScreenlet(Context context) {
		super(context, null);
	}

	public DDLFormScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public DDLFormScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	public void load() {
		if (_record.getRecordId() == 0) {
			loadForm();
		}
		else {
			loadRecord();
		}
	}

	public void loadForm() {
		performUserAction(_LOAD_FORM_ACTION);
	}

	public void loadRecord() {
		performUserAction(_LOAD_RECORD_ACTION);
	}

	public void submitForm() {
		if (allDocumentsUploaded(_record)) {
			if (_record.getRecordId() == 0) {
				performUserAction(_ADD_RECORD_ACTION);
			} else {
				performUserAction(_UPDATE_RECORD_ACTION);
			}
		}
	}

	public void upload(int position) {
		DocumentField field = (DocumentField) _record.getField(position);
		upload(field);
	}

	public void upload(DocumentField field) {
		field.getCurrentValue().setState(DocumentField.State.PENDING);
		DDLFormViewModel view = (DDLFormViewModel) getScreenletView();
		view.showStartDocumentUpload(field);
		performUserAction(_UPLOAD_DOCUMENT_ACTION, field);
	}

	@Override
	public void onDDLFormLoaded(Record record) {
		DDLFormViewModel view = (DDLFormViewModel) getScreenletView();
		view.setRecordFields(record);

		if (_listener != null) {
			_listener.onDDLFormLoaded(record);
		}

		if (_loadRecordAfterForm) {
			_loadRecordAfterForm = false;
			loadRecord();
		}
	}

	@Override
	public void onDDLFormLoadFailed(Exception e) {
		DDLFormViewModel view = (DDLFormViewModel) getScreenletView();
		view.setRecordFields(null);

		if (_listener != null) {
			_listener.onDDLFormLoadFailed(e);
		}
		_loadRecordAfterForm = false;
	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		if (_listener != null) {
			_listener.onDDLFormRecordAdded(record);
		}
	}

	@Override
	public void onDDLFormRecordAddFailed(Exception e) {
		if (_listener != null) {
			_listener.onDDLFormRecordAddFailed(e);
		}
	}

	@Override
	public void onDDLFormRecordLoaded(Record record) {
		DDLFormViewModel view = (DDLFormViewModel) getScreenletView();
		view.setRecordValues(record);

		if (_listener != null) {
			_listener.onDDLFormRecordLoaded(record);
		}
	}

	@Override
	public void onDDLFormRecordLoadFailed(Exception e) {
		DDLFormViewModel view = (DDLFormViewModel) getScreenletView();
		view.setRecordValues(null);

		if (_listener != null) {
			_listener.onDDLFormRecordLoadFailed(e);
		}
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		if (_listener != null) {
			_listener.onDDLFormRecordUpdated(record);
		}
	}

	@Override
	public void onDDLFormUpdateRecordFailed(Exception e) {
		if (_listener != null) {
			_listener.onDDLFormUpdateRecordFailed(e);
		}
	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField) {
		DocumentField originalField =
			(DocumentField) _record.getFieldByName(documentField.getName());

		if (originalField != null) {
			originalField.getCurrentValue().setState(DocumentField.State.UPLOADED);
			DDLFormViewModel view = (DDLFormViewModel) getScreenletView();
			view.showDocumentUploaded(originalField);
		}

		if (_listener != null) {
			_listener.onDDLFormDocumentUploaded(documentField);
		}
	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {
		DocumentField originalField =
			(DocumentField) _record.getFieldByName(documentField.getName());

		if (originalField != null) {
			originalField.getCurrentValue().setState(DocumentField.State.FAILED);
			DDLFormViewModel view = (DDLFormViewModel) getScreenletView();
			view.showDocumentUploadFailed(originalField);
		}

		if (_listener != null) {
			_listener.onDDLFormDocumentUploadFailed(documentField, e);
		}
	}

	public boolean getAutoLoad() {
		return _autoLoad;
	}

	public void setAutoLoad(boolean autoLoad) {
		_autoLoad = autoLoad;
	}

	public boolean isAutoScrollOnValidation() {
		return _autoScrollOnValidation;
	}

	public boolean isShowSubmitButton() {
		return _showSubmitButton;
	}

	public long getGroupId() {
		return _groupId;
	}

	public Record getRecord() {
		return _record;
	}

	public void setAutoScrollOnValidation(boolean autoScrollOnValidation) {
		_autoScrollOnValidation = autoScrollOnValidation;
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

	public void setCustomFieldLayoutId(String fieldName, int layoutId) {
		DDLFormViewModel view = (DDLFormViewModel) getScreenletView();

		view.setCustomFieldLayoutId(fieldName, layoutId);
	}

	@Override
	protected DDLFormBaseInteractor createInteractor(String actionName) {
		switch (actionName) {
			case _LOAD_FORM_ACTION:
				return new DDLFormLoadInteractorImpl(getScreenletId());
			case _LOAD_RECORD_ACTION:
				return new DDLFormLoadRecordInteractorImpl(getScreenletId());
			case _ADD_RECORD_ACTION:
				return new DDLFormAddRecordInteractorImpl(getScreenletId());
			case _UPDATE_RECORD_ACTION:
				return new DDLFormUpdateRecordInteractorImpl(getScreenletId());
			case _UPLOAD_DOCUMENT_ACTION:
				return new DDLFormUploadInteractorImpl(getScreenletId());
			default:
				return null;
		}
	}

	@Override
	protected View createScreenletView(
		Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.DDLFormScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(
			R.styleable.DDLFormScreenlet_layoutId, 0);

		_autoLoad = typedArray.getBoolean(R.styleable.DDLFormScreenlet_autoLoad, true);

		_autoScrollOnValidation = typedArray.getBoolean(
			R.styleable.DDLFormScreenlet_autoScrollOnValidation, true);

		_showSubmitButton = typedArray.getBoolean(
			R.styleable.DDLFormScreenlet_showSubmitButton, true);

		_groupId = typedArray.getInteger(
			R.styleable.DDLFormScreenlet_groupId,
			(int) LiferayServerContext.getGroupId());

		_structureId = typedArray.getInteger(
			R.styleable.DDLFormScreenlet_structureId, 0);

		_recordSetId = typedArray.getInteger(
			R.styleable.DDLFormScreenlet_recordSetId, 0);

		_recordId = typedArray.getInteger(
			R.styleable.DDLFormScreenlet_recordId, 0);

		_filePrefix = typedArray.getString(R.styleable.DDLFormScreenlet_filePrefix);

		_repositoryId = typedArray.getInteger(R.styleable.DDLFormScreenlet_repositoryId, 0);
		_folderId = typedArray.getInteger(R.styleable.DDLFormScreenlet_folderId, 0);

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
			viewModel, Field.EditorType.INTEGER, typedArray,
			R.styleable.DDLFormScreenlet_numberFieldLayoutId);

		setFieldLayoutId(
			viewModel, Field.EditorType.DECIMAL, typedArray,
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

		setFieldLayoutId(
			viewModel, Field.EditorType.TEXT_AREA, typedArray,
			R.styleable.DDLFormScreenlet_textAreaFieldLayoutId);

		setFieldLayoutId(
				viewModel, Field.EditorType.DOCUMENT, typedArray,
				R.styleable.DDLFormScreenlet_documentFieldLayoutId);

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
	protected void onUserAction(String userActionName, DDLFormBaseInteractor interactor, Object... args) {
		if (_LOAD_FORM_ACTION.equals(userActionName)) {
			DDLFormLoadInteractor loadInteractor = (DDLFormLoadInteractor) interactor;

			try {
				loadInteractor.load(_record);
			}
			catch (Exception e) {
				onDDLFormLoadFailed(e);
			}
		}
		else if (_LOAD_RECORD_ACTION.equals(userActionName)) {
			if (_record.isRecordStructurePresent()) {
				DDLFormLoadRecordInteractor loadInteractor = (DDLFormLoadRecordInteractor) interactor;

				try {
					loadInteractor.loadRecord(_record);
				}
				catch (Exception e) {
					onDDLFormRecordLoadFailed(e);
				}
			}
			else {
				// request both structure and data
				_loadRecordAfterForm = true;
				loadForm();
			}
		}
		else if (_ADD_RECORD_ACTION.equals(userActionName)) {
			DDLFormAddRecordInteractor addInteractor = (DDLFormAddRecordInteractor) interactor;

			try {
				addInteractor.addRecord(_groupId, _record);
			}
			catch (Exception e) {
				onDDLFormRecordAddFailed(e);
			}
		}
		else if (_UPDATE_RECORD_ACTION.equals(userActionName)) {
			DDLFormUpdateRecordInteractor updateInteractor = (DDLFormUpdateRecordInteractor) interactor;

			try {
				updateInteractor.updateRecord(_groupId, _record);
			}
			catch (Exception e) {
				onDDLFormUpdateRecordFailed(e);
			}
		}
		else if (_UPLOAD_DOCUMENT_ACTION.equals(userActionName)) {
			DDLFormUploadInteractor uploadInteractor = (DDLFormUploadInteractor) interactor;

			DocumentField documentToUpload = (DocumentField) args[0];
			try {
				uploadInteractor.upload(
					_groupId, _userId, _repositoryId, _folderId, _filePrefix, documentToUpload);
			}
			catch (Exception e) {
				onDDLFormDocumentUploadFailed(documentToUpload, e);
			}
		}
	}

	private boolean allDocumentsUploaded(Record record) {
		boolean result = true;
		for (int i = 0; i < record.getFieldCount(); i++) {
			Field field = record.getField(i);
			if (field instanceof DocumentField) {
				DocumentField file = (DocumentField) field;
				DocumentField.State state = file.getCurrentValue().getState();
				if (DocumentField.State.UPLOADING.equals(state) ||  DocumentField.State.PENDING.equals(state)) {
					result = false;
				}
				else {
					if (DocumentField.State.FAILED.equals(state)) {
						upload(file);
						result = false;
					}
				}
			}
		}
		return result;
	}

	protected void setFieldLayoutId(
		DDLFormViewModel viewModel, Field.EditorType editorType,
		TypedArray typedArray, int index) {

		int layoutId = typedArray.getResourceId(
			index, getDefaultLayoutId(editorType));

		viewModel.setFieldLayoutId(editorType, layoutId);
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle state = (Bundle) inState;

		_record = state.getParcelable(_STATE_RECORD);
		_userId = state.getLong(_STATE_USER_ID);
		_recordId = state.getLong(_STATE_RECORD_ID);
		_recordSetId = state.getLong(_STATE_RECORDSET_ID);
		_structureId = state.getLong(_STATE_STRUCTURE_ID);
		_groupId = state.getLong(_STATE_GROUP_ID);
		_showSubmitButton = state.getBoolean(_STATE_SHOW_SUBMIT_BUTTON);
		_autoScrollOnValidation = state.getBoolean(_STATE_AUTOSCROLL_ON_VALIDATION);
		_loadRecordAfterForm = state.getBoolean(_STATE_LOAD_RECORD_AFTER_FORM);
		_repositoryId = state.getLong(_STATE_REPOSITORY_ID);
		_folderId = state.getLong(_STATE_FOLDER_ID);
		_filePrefix = state.getString(_STATE_FILE_PREFIX);

		Parcelable superState = state.getParcelable(_STATE_SUPER);

		super.onRestoreInstanceState(superState);

		DDLFormViewModel view = (DDLFormViewModel) getScreenletView();
		view.setRecordFields(_record);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();

		Bundle state = new Bundle();
		state.putParcelable(_STATE_SUPER, superState);
		state.putBoolean(_STATE_AUTOSCROLL_ON_VALIDATION, _autoScrollOnValidation);
		state.putBoolean(_STATE_SHOW_SUBMIT_BUTTON, _showSubmitButton);
		state.putLong(_STATE_GROUP_ID, _groupId);
		state.putLong(_STATE_STRUCTURE_ID, _structureId);
		state.putLong(_STATE_RECORDSET_ID, _recordSetId);
		state.putLong(_STATE_RECORD_ID, _recordId);
		state.putLong(_STATE_USER_ID, _userId);
		state.putParcelable(_STATE_RECORD, _record);
		state.putBoolean(_STATE_LOAD_RECORD_AFTER_FORM, _loadRecordAfterForm);
		state.putLong(_STATE_REPOSITORY_ID, _repositoryId);
		state.putLong(_STATE_FOLDER_ID, _folderId);
		state.putString(_STATE_FILE_PREFIX, _filePrefix);

		return state;
	}

	@Override
	protected void onScreenletAttached() {
		if (_autoLoad && _record.getFieldCount() == 0) {
			load();
		}
	}

	private static Map<Field.EditorType, String> _defaultLayoutNames;

	static {
		_defaultLayoutNames = new HashMap<>();
		_defaultLayoutNames.put(Field.EditorType.CHECKBOX, "ddlfield_checkbox_default");

		_defaultLayoutNames.put(Field.EditorType.DATE, "ddlfield_date_default");

		_defaultLayoutNames.put(Field.EditorType.NUMBER, "ddlfield_number_default");

		_defaultLayoutNames.put(Field.EditorType.INTEGER, "ddlfield_number_default");

		_defaultLayoutNames.put(Field.EditorType.DECIMAL, "ddlfield_number_default");

		_defaultLayoutNames.put(Field.EditorType.RADIO, "ddlfield_radio_default");

		_defaultLayoutNames.put(Field.EditorType.SELECT, "ddlfield_select_default");

		_defaultLayoutNames.put(Field.EditorType.TEXT, "ddlfield_text_default");

		_defaultLayoutNames.put(Field.EditorType.TEXT_AREA, "ddlfield_text_area_default");

		_defaultLayoutNames.put(Field.EditorType.DOCUMENT, "ddlfield_document_default");
	}


	private static final String _LOAD_FORM_ACTION = "loadForm";
	private static final String _LOAD_RECORD_ACTION = "loadRecord";
	private static final String _ADD_RECORD_ACTION = "addRecord";
	private static final String _UPDATE_RECORD_ACTION = "updateRecord";
	private static final String _UPLOAD_DOCUMENT_ACTION = "uploadDocument";


	private static final String _STATE_SUPER = "ddlform-super";
	private static final String _STATE_AUTOSCROLL_ON_VALIDATION = "ddlform-autoScrollOnValidation";
	private static final String _STATE_SHOW_SUBMIT_BUTTON = "ddlform-showSubmitButton";
	private static final String _STATE_GROUP_ID = "ddlform-groupId";
	private static final String _STATE_STRUCTURE_ID = "ddlform-structureId";
	private static final String _STATE_RECORDSET_ID = "ddlform-recordSetId";
	private static final String _STATE_RECORD_ID = "ddlform-recordId";
	private static final String _STATE_USER_ID = "ddlform-userId";
	private static final String _STATE_RECORD = "ddlform-record";
	private static final String _STATE_LOAD_RECORD_AFTER_FORM = "ddlform-loadRecordAfterForm";
	private static final String _STATE_REPOSITORY_ID = "ddlform-repositoryId";
	private static final String _STATE_FOLDER_ID = "ddlform-folderId";
	private static final String _STATE_FILE_PREFIX = "ddlform-filePrefixId";

	private boolean _autoLoad;
	private boolean _autoScrollOnValidation;
	private boolean _showSubmitButton;
	private long _groupId;
	private long _structureId;
	private long _recordSetId;
	private long _recordId;
	private long _userId;
	private long _repositoryId;
	private long _folderId;
	private String _filePrefix;

	private Record _record;

	private DDLFormListener _listener;

	private boolean _loadRecordAfterForm;

}