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

package com.liferay.mobile.screens.ddm.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddm.form.interactor.FormContextEvent;
import com.liferay.mobile.screens.ddm.form.interactor.FormInstanceRecordEvent;
import com.liferay.mobile.screens.ddm.form.interactor.add.FormInstanceRecordAddInteractor;
import com.liferay.mobile.screens.ddm.form.interactor.evaluate.FormContextEvaluateInteractor;
import com.liferay.mobile.screens.ddm.form.interactor.load.FormInstanceLoadInteractor;
import com.liferay.mobile.screens.ddm.form.interactor.load.FormInstanceRecordLoadInteractor;
import com.liferay.mobile.screens.ddm.form.interactor.update.FormInstanceRecordUpdateInteractor;
import com.liferay.mobile.screens.ddm.form.model.FormContext;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.ddm.form.view.DDMFormViewModel;

/**
 * @author Paulo Cruz
 */
public class DDMFormScreenlet extends BaseScreenlet<DDMFormViewModel, Interactor<DDMFormListener>>
        implements DDMFormListener {

    public static final String EVALUATE_CONTEXT_ACTION = "evaluateContext";
    public static final String LOAD_FORM_ACTION = "loadForm";
    public static final String LOAD_RECORD_ACTION = "loadRecord";
    public static final String ADD_RECORD_ACTION = "addRecord";
    public static final String UPDATE_RECORD_ACTION = "updateRecord";

    private long formInstanceId;
    private DDMFormListener listener;

    private FormInstance formInstance;
    private FormInstanceRecord formInstanceRecord;

    private String currentLanguageId;

    public DDMFormScreenlet(Context context) {
        super(context);
    }

    public DDMFormScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DDMFormScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DDMFormScreenlet(Context context, AttributeSet attrs, int defStyleAttr,
                            int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void load() {
        performUserAction(LOAD_FORM_ACTION);
    }

    public long getFormInstanceId() {
        return formInstanceId;
    }

    public void setFormInstanceId(long formInstanceId) {
        this.formInstanceId = formInstanceId;
    }

    public DDMFormListener getListener() {
        return listener;
    }

    public void setListener(DDMFormListener listener) {
        this.listener = listener;
    }

    @Override
    public void onFormContextEvaluated(FormContext formContext) {
        if (listener != null) {
            listener.onFormContextEvaluated(formContext);
        }
    }

    @Override
    public void onFormInstanceLoaded(FormInstance formInstance) {
        getViewModel().showFinishOperation(LOAD_FORM_ACTION, formInstance);

        if (listener != null) {
            listener.onFormInstanceLoaded(formInstance);
        }
    }

    @Override
    public void onFormInstanceRecordLoaded(FormInstanceRecord formInstanceRecord) {
        if (listener != null) {
            listener.onFormInstanceRecordLoaded(formInstanceRecord);
        }
    }

    @Override
    public void onFormInstanceRecordAdded(FormInstanceRecord formInstanceRecord) {
        if (listener != null) {
            listener.onFormInstanceRecordAdded(formInstanceRecord);
        }
    }

    @Override
    public void onFormInstanceRecordUpdated(FormInstanceRecord formInstanceRecord) {
        if (listener != null) {
            listener.onFormInstanceRecordUpdated(formInstanceRecord);
        }
    }

    @Override
    public void error(Exception e, String userAction) {
        if (listener != null) {
            listener.error(e, userAction);
        }
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
                context.getTheme().obtainStyledAttributes(attributes, R.styleable.DDMFormScreenlet, 0, 0);

        formInstanceId = castToLong(typedArray.getString(R.styleable.DDMFormScreenlet_formInstanceId));

        int layoutId = typedArray.getResourceId(R.styleable.DDMFormScreenlet_layoutId,
                getDefaultLayoutId());

        View view = LayoutInflater.from(context).inflate(layoutId, null);

        DDMFormViewModel viewModel = (DDMFormViewModel) view;

        setFieldLayoutId(viewModel, typedArray, Field.EditorType.CHECKBOX,
                R.styleable.DDMFormScreenlet_checkboxFieldLayoutId);

        setFieldLayoutId(viewModel, typedArray, Field.EditorType.DATE,
                R.styleable.DDMFormScreenlet_dateFieldLayoutId);

        setFieldLayoutId(viewModel, typedArray, Field.EditorType.NUMBER,
                R.styleable.DDMFormScreenlet_numberFieldLayoutId);

        setFieldLayoutId(viewModel, typedArray, Field.EditorType.INTEGER,
                R.styleable.DDMFormScreenlet_numberFieldLayoutId);

        setFieldLayoutId(viewModel, typedArray, Field.EditorType.DECIMAL,
                R.styleable.DDMFormScreenlet_numberFieldLayoutId);

        setFieldLayoutId(viewModel, typedArray, Field.EditorType.RADIO,
                R.styleable.DDMFormScreenlet_radioFieldLayoutId);

        setFieldLayoutId(viewModel, typedArray, Field.EditorType.SELECT,
                R.styleable.DDMFormScreenlet_selectFieldLayoutId);

        setFieldLayoutId(viewModel, typedArray, Field.EditorType.TEXT,
                R.styleable.DDMFormScreenlet_textFieldLayoutId);

        setFieldLayoutId(viewModel, typedArray, Field.EditorType.TEXT_AREA,
                R.styleable.DDMFormScreenlet_textAreaFieldLayoutId);

        setFieldLayoutId(viewModel, typedArray, Field.EditorType.DOCUMENT,
                R.styleable.DDMFormScreenlet_documentFieldLayoutId);

        setFieldLayoutId(viewModel, typedArray, Field.EditorType.GEO,
                R.styleable.DDMFormScreenlet_geoFieldLayoutId);
        typedArray.recycle();

        return view;
    }

    @Override
    protected Interactor<DDMFormListener> createInteractor(String actionName) {
        switch (actionName) {
            case EVALUATE_CONTEXT_ACTION:
                return new FormContextEvaluateInteractor();
            case LOAD_FORM_ACTION:
                return new FormInstanceLoadInteractor();
            case LOAD_RECORD_ACTION:
                return new FormInstanceRecordLoadInteractor();
            case ADD_RECORD_ACTION:
                return new FormInstanceRecordAddInteractor();
            case UPDATE_RECORD_ACTION:
                return new FormInstanceRecordUpdateInteractor();
            default:
                return null;
        }
    }

    @Override
    protected void onUserAction(String userActionName, Interactor<DDMFormListener> interactor,
                                Object... args) {

        switch (userActionName) {
            case EVALUATE_CONTEXT_ACTION:
                FormContextEvent evaluateContextEvent = new FormContextEvent(formInstance,

                        formInstanceRecord, currentLanguageId);

                ((FormContextEvaluateInteractor) interactor).start(evaluateContextEvent);
                break;
            case LOAD_FORM_ACTION:
                ((FormInstanceLoadInteractor) interactor).start(getFormInstanceId());
                break;
            case LOAD_RECORD_ACTION:
                long formInstanceRecordId = formInstanceRecord.getFormInstanceRecordId();
                ((FormInstanceRecordLoadInteractor) interactor).start(formInstanceRecordId);
                break;
            case ADD_RECORD_ACTION:
                FormInstanceRecordEvent addRecordEvent = new FormInstanceRecordEvent(formInstance,
                        formInstanceRecord);

                ((FormInstanceRecordAddInteractor) interactor).start(addRecordEvent);
                break;
            case UPDATE_RECORD_ACTION:
                FormInstanceRecordEvent updateRecordEvent = new FormInstanceRecordEvent(
                        formInstance, formInstanceRecord);

                ((FormInstanceRecordUpdateInteractor) interactor).start(updateRecordEvent);
                break;
        }
    }

    private void setFieldLayoutId(DDMFormViewModel viewModel, TypedArray typedArray, Field.EditorType editorType,
                                  Integer id) {

        int resourceId = typedArray.getResourceId(id, 0);

        if (resourceId == 0) {
            viewModel.resetFieldLayoutId(editorType);
        } else {
            viewModel.setFieldLayoutId(editorType, resourceId);
        }
    }
}
