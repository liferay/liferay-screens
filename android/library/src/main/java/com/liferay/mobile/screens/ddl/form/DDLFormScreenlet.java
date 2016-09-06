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
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.ddl.form.interactor.add.DDLFormAddRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.formload.DDLFormLoadInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.recordload.DDLFormLoadRecordNewInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.update.DDLFormUpdateRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.form.interactor.upload.DDLFormDocumentUploadEvent;
import com.liferay.mobile.screens.ddl.form.interactor.upload.DDLFormDocumentUploadInteractorImpl;
import com.liferay.mobile.screens.ddl.form.view.DDLFormViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class DDLFormScreenlet extends BaseScreenlet<DDLFormViewModel, Interactor<DDLFormListener>>
	implements DDLFormListener {

	public static final String LOAD_FORM_ACTION = "loadForm";
	public static final String LOAD_RECORD_ACTION = "loadRecord";
	public static final String ADD_RECORD_ACTION = "addRecord";
	public static final String UPDATE_RECORD_ACTION = "updateRecord";
	public static final String UPLOAD_DOCUMENT_ACTION = "uploadDocument";
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
	private static final String _STATE_OFFLINE_POLICY = "ddlform-offlinePolicy";
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
	private OfflinePolicy _offlinePolicy;

	public DDLFormScreenlet(Context context) {
		super(context);
	}

	public DDLFormScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DDLFormScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DDLFormScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void load() {
		if (_record.getRecordId() == 0) {
			loadForm();
		} else {
			loadRecord();
		}
	}

	public void loadForm() {
		performUserAction(LOAD_FORM_ACTION);
	}

	public void loadRecord() {
		performUserAction(LOAD_RECORD_ACTION);
	}

	public void submitForm() {
		if (_record.getRecordId() == 0) {
			performUserAction(ADD_RECORD_ACTION);
		} else {
			performUserAction(UPDATE_RECORD_ACTION);
		}
	}

	public boolean validateForm() {
		Map<Field, Boolean> fieldResults = new HashMap<>(_record.getFieldCount());
		boolean result = true;

		for (int i = 0; i < _record.getFieldCount(); i++) {
			Field field = _record.getField(i);

			boolean isFieldValid = field.isValid();

			fieldResults.put(field, isFieldValid);

			result &= isFieldValid;
		}

		getViewModel().showValidationResults(fieldResults, _autoScrollOnValidation);

		return result;
	}

	public void startUploadByPosition(int position) {
		startUpload((DocumentField) _record.getField(position));
	}

	public void startUpload(DocumentField field) {
		performUserAction(UPLOAD_DOCUMENT_ACTION, field);
	}

	@Override
	public void onDDLFormLoaded(Record record) {
		getViewModel().showFinishOperation(LOAD_FORM_ACTION, record);

		if (_listener != null) {
			_listener.onDDLFormLoaded(record);
		}

		if (_loadRecordAfterForm) {
			_loadRecordAfterForm = false;
			loadRecord();
		}
	}

	@Override
	public void error(Exception e, String userAction) {

		getViewModel().showFailedOperation(userAction, e);
		switch (userAction) {
			case LOAD_FORM_ACTION:
				_loadRecordAfterForm = false;
				break;

			case ADD_RECORD_ACTION:
			case LOAD_RECORD_ACTION:
			case UPDATE_RECORD_ACTION:
				break;
		}

		if (_listener != null) {
			_listener.error(e, userAction);
		}
	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		getViewModel().showFinishOperation(ADD_RECORD_ACTION, record);

		if (_listener != null) {
			_listener.onDDLFormRecordAdded(record);
		}
	}

	@Override
	public void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes) {

		_record.setValuesAndAttributes(valuesAndAttributes);
		_record.refresh();

		getViewModel().showFinishOperation(LOAD_RECORD_ACTION, _record);

		if (_listener != null) {
			_listener.onDDLFormRecordLoaded(record, valuesAndAttributes);
		}
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		getViewModel().showFinishOperation(UPDATE_RECORD_ACTION, record);

		if (_listener != null) {
			_listener.onDDLFormRecordUpdated(record);
		}
	}

	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {
		//TODO this is confusing. Why can't I use the argument? Change to receive only the name
		DocumentField originalField = (DocumentField) _record.getFieldByName(documentField.getName());

		if (originalField != null) {
			originalField.moveToUploadCompleteState();
			originalField.setCurrentStringValue(jsonObject.toString());

			getViewModel().showFinishOperation(UPLOAD_DOCUMENT_ACTION, originalField);
		}

		if (_listener != null) {
			_listener.onDDLFormDocumentUploaded(documentField, null);
		}
	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {
		DocumentField originalField = (DocumentField) _record.getFieldByName(documentField.getName());

		if (originalField != null) {
			originalField.moveToUploadFailureState();

			getViewModel().showFailedOperation(UPLOAD_DOCUMENT_ACTION, e, originalField);
		}

		if (_listener != null) {
			_listener.onDDLFormDocumentUploadFailed(documentField, e);
		}
	}

	public boolean isAutoLoad() {
		return _autoLoad;
	}

	public void setAutoLoad(boolean value) {
		_autoLoad = value;
	}

	public boolean isAutoScrollOnValidation() {
		return _autoScrollOnValidation;
	}

	public void setAutoScrollOnValidation(boolean value) {
		_autoScrollOnValidation = value;
	}

	public boolean isShowSubmitButton() {
		return _showSubmitButton;
	}

	public void setShowSubmitButton(boolean value) {
		_showSubmitButton = value;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getStructureId() {
		return _structureId;
	}

	public void setStructureId(long value) {
		_structureId = value;
		_record.setStructureId(value);
	}

	public long getRecordSetId() {
		return _recordSetId;
	}

	public void setRecordSetId(long value) {
		_recordSetId = value;
		_record.setRecordSetId(value);
	}

	public long getRecordId() {
		return _recordId;
	}

	public void setRecordId(long value) {
		_recordId = value;
		_record.setRecordId(value);
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long value) {
		_userId = value;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setRepositoryId(long value) {
		_repositoryId = value;
	}

	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long value) {
		_folderId = value;
	}

	public String getFilePrefix() {
		return _filePrefix;
	}

	public void setFilePrefix(String value) {
		_filePrefix = value;
	}

	public Record getRecord() {
		return _record;
	}

	public void setRecord(Record record) {
		_record = record;
	}

	public DDLFormListener getListener() {
		return _listener;
	}

	public void setListener(DDLFormListener listener) {
		_listener = listener;
	}

	public boolean isLoadRecordAfterForm() {
		return _loadRecordAfterForm;
	}

	public void setLoadRecordAfterForm(boolean loadRecordAfterForm) {
		_loadRecordAfterForm = loadRecordAfterForm;
	}

	public OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy offlinePolicy) {
		_offlinePolicy = offlinePolicy;
	}

	public void setCustomFieldLayoutId(String fieldName, int layoutId) {
		getViewModel().setCustomFieldLayoutId(fieldName, layoutId);
	}

	public void resetCustomFieldLayoutId(String fieldName) {
		getViewModel().resetCustomFieldLayoutId(fieldName);
	}

	@Override
	protected Interactor<DDLFormListener> createInteractor(String actionName) {
		switch (actionName) {
			case LOAD_FORM_ACTION:
				return new DDLFormLoadInteractorImpl();
			case LOAD_RECORD_ACTION:
				return new DDLFormLoadRecordNewInteractorImpl();
			case ADD_RECORD_ACTION:
				return new DDLFormAddRecordInteractorImpl();
			case UPDATE_RECORD_ACTION:
				return new DDLFormUpdateRecordInteractorImpl();
			case UPLOAD_DOCUMENT_ACTION:
				return new DDLFormDocumentUploadInteractorImpl();
			default:
				return null;
		}
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.DDLFormScreenlet, 0, 0);

		_autoLoad = typedArray.getBoolean(R.styleable.DDLFormScreenlet_autoLoad, true);

		_autoScrollOnValidation = typedArray.getBoolean(R.styleable.DDLFormScreenlet_autoScrollOnValidation, true);

		_showSubmitButton = typedArray.getBoolean(R.styleable.DDLFormScreenlet_showSubmitButton, true);

		_groupId = castToLongOrUseDefault(typedArray.getString(R.styleable.DDLFormScreenlet_groupId),
			LiferayServerContext.getGroupId());

		_structureId = castToLong(typedArray.getString(R.styleable.DDLFormScreenlet_structureId));
		_recordSetId = castToLong(typedArray.getString(R.styleable.DDLFormScreenlet_recordSetId));
		_recordId = castToLong(typedArray.getString(R.styleable.DDLFormScreenlet_recordId));
		_filePrefix = typedArray.getString(R.styleable.DDLFormScreenlet_filePrefix);
		_repositoryId = castToLong(typedArray.getString(R.styleable.DDLFormScreenlet_repositoryId));
		_folderId = castToLong(typedArray.getString(R.styleable.DDLFormScreenlet_folderId));

		long defaultCreatorUserId =
			SessionContext.getCurrentUser() != null ? SessionContext.getCurrentUser().getId() : 0;

		_userId =
			castToLongOrUseDefault(typedArray.getString(R.styleable.DDLFormScreenlet_userId), defaultCreatorUserId);

		_record = new Record(getResources().getConfiguration().locale);
		_record.setStructureId(_structureId);
		_record.setRecordSetId(_recordSetId);
		_record.setRecordId(_recordId);
		_record.setCreatorUserId(_userId);

		int offlinePolicy =
			typedArray.getInt(R.styleable.DDLFormScreenlet_offlinePolicy, OfflinePolicy.REMOTE_ONLY.ordinal());
		_offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		int layoutId = typedArray.getResourceId(R.styleable.DDLFormScreenlet_layoutId, getDefaultLayoutId());

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		DDLFormViewModel viewModel = (DDLFormViewModel) view;

		setFieldLayoutId(viewModel, typedArray, Field.EditorType.CHECKBOX,
			R.styleable.DDLFormScreenlet_checkboxFieldLayoutId);

		setFieldLayoutId(viewModel, typedArray, Field.EditorType.DATE, R.styleable.DDLFormScreenlet_dateFieldLayoutId);

		setFieldLayoutId(viewModel, typedArray, Field.EditorType.NUMBER,
			R.styleable.DDLFormScreenlet_numberFieldLayoutId);

		setFieldLayoutId(viewModel, typedArray, Field.EditorType.INTEGER,
			R.styleable.DDLFormScreenlet_numberFieldLayoutId);

		setFieldLayoutId(viewModel, typedArray, Field.EditorType.DECIMAL,
			R.styleable.DDLFormScreenlet_numberFieldLayoutId);

		setFieldLayoutId(viewModel, typedArray, Field.EditorType.RADIO,
			R.styleable.DDLFormScreenlet_radioFieldLayoutId);

		setFieldLayoutId(viewModel, typedArray, Field.EditorType.SELECT,
			R.styleable.DDLFormScreenlet_selectFieldLayoutId);

		setFieldLayoutId(viewModel, typedArray, Field.EditorType.TEXT, R.styleable.DDLFormScreenlet_textFieldLayoutId);

		setFieldLayoutId(viewModel, typedArray, Field.EditorType.TEXT_AREA,
			R.styleable.DDLFormScreenlet_textAreaFieldLayoutId);

		setFieldLayoutId(viewModel, typedArray, Field.EditorType.DOCUMENT,
			R.styleable.DDLFormScreenlet_documentFieldLayoutId);

		typedArray.recycle();

		return view;
	}

	@Override
	protected void onUserAction(String userActionName, Interactor<DDLFormListener> interactor, Object... args) {

		switch (userActionName) {
			case LOAD_FORM_ACTION: {
				getViewModel().showStartOperation(LOAD_FORM_ACTION, _record);

				DDLFormLoadInteractorImpl loadInteractor = (DDLFormLoadInteractorImpl) interactor;

				loadInteractor.start(_record);
				break;
			}
			case LOAD_RECORD_ACTION: {
				if (_record.isRecordStructurePresent()) {
					getViewModel().showStartOperation(LOAD_RECORD_ACTION, _record);

					DDLFormLoadRecordNewInteractorImpl loadInteractor = (DDLFormLoadRecordNewInteractorImpl) interactor;

					loadInteractor.start(_record);
				} else {
					// request both structure and data
					_loadRecordAfterForm = true;
					loadForm();
				}
				break;
			}
			case ADD_RECORD_ACTION: {
				getViewModel().showStartOperation(ADD_RECORD_ACTION, _record);
				((DDLFormAddRecordInteractorImpl) interactor).start(new DDLFormEvent(_record, new JSONObject()));
				break;
			}
			case UPDATE_RECORD_ACTION: {
				getViewModel().showStartOperation(UPDATE_RECORD_ACTION, _record);

				DDLFormUpdateRecordInteractorImpl updateInteractor = (DDLFormUpdateRecordInteractorImpl) interactor;

				updateInteractor.start(new DDLFormEvent(_record, new JSONObject()));
				break;
			}
			case UPLOAD_DOCUMENT_ACTION: {
				DocumentField documentField = (DocumentField) args[0];
				documentField.moveToUploadInProgressState();

				getViewModel().showStartOperation(UPLOAD_DOCUMENT_ACTION, documentField);

				((DDLFormDocumentUploadInteractorImpl) interactor).start(
					new DDLFormDocumentUploadEvent(documentField, _repositoryId, _folderId, _filePrefix));
				break;
			}
		}
	}

	protected void setFieldLayoutId(Field.EditorType editorType, TypedArray typedArray, int index) {
		//TODO check if this is needed...
		setFieldLayoutId(getViewModel(), typedArray, editorType, index);
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
		_offlinePolicy = OfflinePolicy.values()[state.getInt(_STATE_OFFLINE_POLICY)];

		Parcelable superState = state.getParcelable(_STATE_SUPER);

		super.onRestoreInstanceState(superState);

		getViewModel().showFormFields(_record);
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
		state.putLong(_STATE_OFFLINE_POLICY, _offlinePolicy.ordinal());

		return state;
	}

	@Override
	protected void onScreenletAttached() {
		if (_autoLoad && _record.getFieldCount() == 0) {
			load();
		}
	}

	private void setFieldLayoutId(DDLFormViewModel viewModel, TypedArray typedArray, Field.EditorType editorType,
		Integer id) {

		int resourceId = typedArray.getResourceId(id, 0);

		if (resourceId == 0) {
			viewModel.resetFieldLayoutId(editorType);
		} else {
			viewModel.setFieldLayoutId(editorType, resourceId);
		}
	}
}