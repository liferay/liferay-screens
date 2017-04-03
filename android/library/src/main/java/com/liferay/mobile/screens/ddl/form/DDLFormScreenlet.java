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
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.ddl.form.interactor.add.DDLFormAddRecordInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.formload.DDLFormLoadInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.recordload.DDLFormLoadRecordInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.update.DDLFormUpdateRecordInteractor;
import com.liferay.mobile.screens.ddl.form.interactor.upload.DDLFormDocumentUploadEvent;
import com.liferay.mobile.screens.ddl.form.interactor.upload.DDLFormDocumentUploadInteractor;
import com.liferay.mobile.screens.ddl.form.view.DDLFormViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import rx.Observable;

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
	private static final String STATE_SUPER = "ddlform-super";
	private static final String STATE_AUTOSCROLL_ON_VALIDATION = "ddlform-autoScrollOnValidation";
	private static final String STATE_SHOW_SUBMIT_BUTTON = "ddlform-showSubmitButton";
	private static final String STATE_STRUCTURE_ID = "ddlform-structureId";
	private static final String STATE_RECORDSET_ID = "ddlform-recordSetId";
	private static final String STATE_RECORD_ID = "ddlform-recordId";
	private static final String STATE_RECORD = "ddlform-record";
	private static final String STATE_LOAD_RECORD_AFTER_FORM = "ddlform-loadRecordAfterForm";
	private static final String STATE_REPOSITORY_ID = "ddlform-repositoryId";
	private static final String STATE_FOLDER_ID = "ddlform-folderId";
	private static final String STATE_FILE_PREFIX = "ddlform-filePrefixId";
	private boolean autoLoad;
	private boolean autoScrollOnValidation;
	private boolean showSubmitButton;
	private long structureId;
	private long recordSetId;
	private long recordId;
	private long repositoryId;
	private long folderId;
	private String filePrefix;
	private Record record;
	private DDLFormListener listener;
	private boolean loadRecordAfterForm;
	private Integer connectionTimeout;

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

	/**
	 * Loads the form if the record doesn't have `recordId`. In other case,
	 * loads the {@link Record}.
	 */
	public void load() {
		if (record.getRecordId() == 0) {
			loadForm();
		} else {
			loadRecord();
		}
	}

	/**
	 * Loads the form.
	 */
	public void loadForm() {
		performUserAction(LOAD_FORM_ACTION);
	}

	/**
	 * Loads the {@link Record}.
	 */
	public void loadRecord() {
		performUserAction(LOAD_RECORD_ACTION);
	}

	/**
	 * Adds new record if the record doesn't have `recordId`. In other case,
	 * the record is updated.
	 */
	public void submitForm() {
		if (record.getRecordId() == 0) {
			performUserAction(ADD_RECORD_ACTION);
		} else {
			performUserAction(UPDATE_RECORD_ACTION);
		}
	}

	/**
	 * Validates all the fields of the form.
	 *
	 * @return if the form validates or not.
	 */
	public boolean validateForm() {
		Map<Field, Boolean> fieldResults = new HashMap<>(record.getFieldCount());
		boolean result = true;

		for (int i = 0; i < record.getFieldCount(); i++) {
			Field field = record.getField(i);

			boolean isFieldValid = field.isValid();

			fieldResults.put(field, isFieldValid);

			result &= isFieldValid;
		}

		getViewModel().showValidationResults(fieldResults, autoScrollOnValidation);

		return result;
	}

	/**
	 * Starts uploading document from position.
	 */
	public void startUploadByPosition(int position) {
		startUpload((DocumentField) record.getField(position));
	}

	/**
	 * Starts uploading document from field.
	 */
	public void startUpload(DocumentField field) {
		performUserAction(UPLOAD_DOCUMENT_ACTION, field);
	}

	@Override
	public void onDDLFormLoaded(Record record) {
		getViewModel().showFinishOperation(LOAD_FORM_ACTION, record);

		if (listener != null) {
			listener.onDDLFormLoaded(record);
		}

		if (loadRecordAfterForm) {
			loadRecordAfterForm = false;
			loadRecord();
		}
	}

	@Override
	public void error(Exception e, String userAction) {

		getViewModel().showFailedOperation(userAction, e);
		switch (userAction) {
			case LOAD_FORM_ACTION:
				loadRecordAfterForm = false;
				break;

			case ADD_RECORD_ACTION:
			case LOAD_RECORD_ACTION:
			case UPDATE_RECORD_ACTION:
			default:
				break;
		}

		if (listener != null) {
			listener.error(e, userAction);
		}
	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		getViewModel().showFinishOperation(ADD_RECORD_ACTION, record);

		if (listener != null) {
			listener.onDDLFormRecordAdded(record);
		}
	}

	@Override
	public void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes) {

		this.record.setValuesAndAttributes(valuesAndAttributes);
		this.record.refresh();

		getViewModel().showFinishOperation(LOAD_RECORD_ACTION, this.record);

		if (listener != null) {
			listener.onDDLFormRecordLoaded(record, valuesAndAttributes);
		}
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		getViewModel().showFinishOperation(UPDATE_RECORD_ACTION, record);

		if (listener != null) {
			listener.onDDLFormRecordUpdated(record);
		}
	}

	/**
	 * Called when the document has been uploaded successfully.
	 *
	 * @param documentField form document field.
	 * @param jsonObject event data.
	 */
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {
		//TODO this is confusing. Why can't I use the argument? Change to receive only the name
		DocumentField originalField = (DocumentField) record.getFieldByName(documentField.getName());

		if (originalField != null) {
			originalField.moveToUploadCompleteState();
			originalField.setCurrentStringValue(jsonObject.toString());

			getViewModel().showFinishOperation(UPLOAD_DOCUMENT_ACTION, originalField);
		}

		if (listener != null) {
			listener.onDDLFormDocumentUploaded(documentField, null);
		}
	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {
		DocumentField originalField = (DocumentField) record.getFieldByName(documentField.getName());

		if (originalField != null) {
			originalField.moveToUploadFailureState();

			getViewModel().showFailedOperation(UPLOAD_DOCUMENT_ACTION, e, originalField);
		}

		if (listener != null) {
			listener.onDDLFormDocumentUploadFailed(documentField, e);
		}
	}

	public boolean isAutoLoad() {
		return autoLoad;
	}

	public void setAutoLoad(boolean value) {
		autoLoad = value;
	}

	public boolean isAutoScrollOnValidation() {
		return autoScrollOnValidation;
	}

	public void setAutoScrollOnValidation(boolean value) {
		autoScrollOnValidation = value;
	}

	public boolean isShowSubmitButton() {
		return showSubmitButton;
	}

	public void setShowSubmitButton(boolean value) {
		showSubmitButton = value;
	}

	public long getStructureId() {
		return structureId;
	}

	public void setStructureId(long value) {
		structureId = value;
		record.setStructureId(value);
	}

	public long getRecordSetId() {
		return recordSetId;
	}

	public void setRecordSetId(long value) {
		recordSetId = value;
		record.setRecordSetId(value);
	}

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long value) {
		recordId = value;
		record.setRecordId(value);
	}

	public long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(long value) {
		repositoryId = value;
	}

	public long getFolderId() {
		return folderId;
	}

	public void setFolderId(long value) {
		folderId = value;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public void setFilePrefix(String value) {
		filePrefix = value;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public DDLFormListener getListener() {
		return listener;
	}

	public void setListener(DDLFormListener listener) {
		this.listener = listener;
	}

	public boolean isLoadRecordAfterForm() {
		return loadRecordAfterForm;
	}

	public void setLoadRecordAfterForm(boolean loadRecordAfterForm) {
		this.loadRecordAfterForm = loadRecordAfterForm;
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
				return new DDLFormLoadInteractor();
			case LOAD_RECORD_ACTION:
				return new DDLFormLoadRecordInteractor();
			case ADD_RECORD_ACTION:
				return new DDLFormAddRecordInteractor();
			case UPDATE_RECORD_ACTION:
				return new DDLFormUpdateRecordInteractor();
			case UPLOAD_DOCUMENT_ACTION:
				return new DDLFormDocumentUploadInteractor();
			default:
				return null;
		}
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.DDLFormScreenlet, 0, 0);

		autoLoad = typedArray.getBoolean(R.styleable.DDLFormScreenlet_autoLoad, true);

		autoScrollOnValidation = typedArray.getBoolean(R.styleable.DDLFormScreenlet_autoScrollOnValidation, true);

		showSubmitButton = typedArray.getBoolean(R.styleable.DDLFormScreenlet_showSubmitButton, true);

		structureId = castToLong(typedArray.getString(R.styleable.DDLFormScreenlet_structureId));
		recordSetId = castToLong(typedArray.getString(R.styleable.DDLFormScreenlet_recordSetId));
		recordId = castToLong(typedArray.getString(R.styleable.DDLFormScreenlet_recordId));
		filePrefix = typedArray.getString(R.styleable.DDLFormScreenlet_filePrefix);
		repositoryId = castToLong(typedArray.getString(R.styleable.DDLFormScreenlet_repositoryId));
		folderId = castToLong(typedArray.getString(R.styleable.DDLFormScreenlet_folderId));

		record = new Record(getResources().getConfiguration().locale);
		record.setStructureId(structureId);
		record.setRecordSetId(recordSetId);
		record.setRecordId(recordId);
		record.setCreatorUserId(userId);

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
			default:
			case LOAD_FORM_ACTION:
				getViewModel().showStartOperation(LOAD_FORM_ACTION, record);

				DDLFormLoadInteractor loadInteractor = (DDLFormLoadInteractor) interactor;

				loadInteractor.start(record);
				break;
			case LOAD_RECORD_ACTION:
				if (record.isRecordStructurePresent()) {
					getViewModel().showStartOperation(LOAD_RECORD_ACTION, record);
					((DDLFormLoadRecordInteractor) interactor).start(record);
				} else {
					// request both structure and data
					loadRecordAfterForm = true;
					loadForm();
				}
				break;
			case ADD_RECORD_ACTION:
				getViewModel().showStartOperation(ADD_RECORD_ACTION, record);
				((DDLFormAddRecordInteractor) interactor).start(new DDLFormEvent(record, new JSONObject()));
				break;
			case UPDATE_RECORD_ACTION:
				getViewModel().showStartOperation(UPDATE_RECORD_ACTION, record);

				DDLFormUpdateRecordInteractor updateInteractor = (DDLFormUpdateRecordInteractor) interactor;

				updateInteractor.start(new DDLFormEvent(record, new JSONObject()));
				break;
			case UPLOAD_DOCUMENT_ACTION:
				DocumentField documentField = (DocumentField) args[0];
				documentField.moveToUploadInProgressState();

				getViewModel().showStartOperation(UPLOAD_DOCUMENT_ACTION, documentField);

				((DDLFormDocumentUploadInteractor) interactor).start(
					new DDLFormDocumentUploadEvent(documentField, repositoryId, folderId, filePrefix,
						connectionTimeout));
				break;
		}
	}

	protected void setFieldLayoutId(Field.EditorType editorType, TypedArray typedArray, int index) {
		//TODO check if this is needed...
		setFieldLayoutId(getViewModel(), typedArray, editorType, index);
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle state = (Bundle) inState;

		record = state.getParcelable(STATE_RECORD);
		recordId = state.getLong(STATE_RECORD_ID);
		recordSetId = state.getLong(STATE_RECORDSET_ID);
		structureId = state.getLong(STATE_STRUCTURE_ID);
		showSubmitButton = state.getBoolean(STATE_SHOW_SUBMIT_BUTTON);
		autoScrollOnValidation = state.getBoolean(STATE_AUTOSCROLL_ON_VALIDATION);
		loadRecordAfterForm = state.getBoolean(STATE_LOAD_RECORD_AFTER_FORM);
		repositoryId = state.getLong(STATE_REPOSITORY_ID);
		folderId = state.getLong(STATE_FOLDER_ID);
		filePrefix = state.getString(STATE_FILE_PREFIX);

		Parcelable superState = state.getParcelable(STATE_SUPER);

		super.onRestoreInstanceState(superState);

		getViewModel().showFormFields(record);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();

		Bundle state = new Bundle();
		state.putParcelable(STATE_SUPER, superState);
		state.putBoolean(STATE_AUTOSCROLL_ON_VALIDATION, autoScrollOnValidation);
		state.putBoolean(STATE_SHOW_SUBMIT_BUTTON, showSubmitButton);
		state.putLong(STATE_STRUCTURE_ID, structureId);
		state.putLong(STATE_RECORDSET_ID, recordSetId);
		state.putLong(STATE_RECORD_ID, recordId);
		state.putParcelable(STATE_RECORD, record);
		state.putBoolean(STATE_LOAD_RECORD_AFTER_FORM, loadRecordAfterForm);
		state.putLong(STATE_REPOSITORY_ID, repositoryId);
		state.putLong(STATE_FOLDER_ID, folderId);
		state.putString(STATE_FILE_PREFIX, filePrefix);

		return state;
	}

	@Override
	protected void onScreenletAttached() {
		if (autoLoad && record.getFieldCount() == 0) {
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

	public Observable<EventProperty> getEventsObservable() {
		return getViewModel().getEventsObservable();
	}
}