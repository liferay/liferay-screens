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

package com.liferay.mobile.screens.viewsets.material.ddl.form;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.form.view.DDLFormViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.R;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import java.util.HashMap;
import java.util.Map;

import static com.liferay.mobile.screens.ddl.model.Field.EditorType.CHECKBOX;
import static com.liferay.mobile.screens.ddl.model.Field.EditorType.DATE;
import static com.liferay.mobile.screens.ddl.model.Field.EditorType.DECIMAL;
import static com.liferay.mobile.screens.ddl.model.Field.EditorType.DOCUMENT;
import static com.liferay.mobile.screens.ddl.model.Field.EditorType.INTEGER;
import static com.liferay.mobile.screens.ddl.model.Field.EditorType.NUMBER;
import static com.liferay.mobile.screens.ddl.model.Field.EditorType.RADIO;
import static com.liferay.mobile.screens.ddl.model.Field.EditorType.SELECT;
import static com.liferay.mobile.screens.ddl.model.Field.EditorType.TEXT;
import static com.liferay.mobile.screens.ddl.model.Field.EditorType.TEXT_AREA;

/**
 * @author Silvio Santos
 */
public class ViewPagerDDLFormView extends RecyclerViewPager
    implements DDLFormViewModel, View.OnClickListener, RecyclerViewPager.OnPageChangedListener {

    private static final Map<Field.EditorType, Integer> DEFAULT_LAYOUT_IDS = new HashMap<>(16);

    static {
        DEFAULT_LAYOUT_IDS.put(CHECKBOX, R.layout.ddlfield_checkbox_default);
        DEFAULT_LAYOUT_IDS.put(DATE, R.layout.ddlfield_date_default);
        DEFAULT_LAYOUT_IDS.put(NUMBER, R.layout.ddlfield_number_default);
        DEFAULT_LAYOUT_IDS.put(INTEGER, R.layout.ddlfield_number_default);
        DEFAULT_LAYOUT_IDS.put(DECIMAL, R.layout.ddlfield_number_default);
        DEFAULT_LAYOUT_IDS.put(RADIO, R.layout.ddlfield_radio_default);
        DEFAULT_LAYOUT_IDS.put(SELECT, R.layout.ddlfield_select_default);
        DEFAULT_LAYOUT_IDS.put(TEXT, R.layout.ddlfield_text_default);
        DEFAULT_LAYOUT_IDS.put(TEXT_AREA, R.layout.ddlfield_text_area_default);
        DEFAULT_LAYOUT_IDS.put(DOCUMENT, R.layout.ddlfield_document_default);
    }

    private final Map<Field.EditorType, Integer> layoutIds = new HashMap<>();
    private final Map<String, Integer> customLayoutIds = new HashMap<>();
    protected ProgressBar progressBar;
    protected ProgressBar loadingFormProgressBar;
    protected ViewGroup fieldsContainerView;
    protected Button submitButton;
    private BaseScreenlet screenlet;
    private int position;

    public ViewPagerDDLFormView(Context context) {
        super(context);
    }

    public ViewPagerDDLFormView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public ViewPagerDDLFormView(Context context, AttributeSet attributes, int defaultStyle) {
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
    public void showValidationResults(final Map<Field, Boolean> fieldResults, final boolean autoscroll) {
        checkPage(fieldResults);
    }

    private boolean checkPage(Map<Field, Boolean> fieldResults) {

        LinearLayout container = findViewById(com.liferay.mobile.screens.viewsets.R.id.ddlfields_container);

        boolean pageInvalid = false;

        for (int i = 0; i < container.getChildCount(); i++) {
            View fieldView = container.getChildAt(i);
            DDLFieldViewModel fieldViewModel = (DDLFieldViewModel) fieldView;

            if (fieldViewModel != null && fieldViewModel.getField() != null && fieldResults.containsKey(
                fieldViewModel.getField())) {

                boolean isFieldValid = fieldResults.get(fieldViewModel.getField());

                fieldView.clearFocus();

                fieldViewModel.onPostValidation(isFieldValid);

                if (!isFieldValid) {
                    fieldView.requestFocus();

                    ((ScrollView) findViewById(com.liferay.mobile.screens.viewsets.R.id.scroll_view)).smoothScrollTo(0,
                        fieldView.getTop() - 100);
                    //scrollBy(0, fieldView.getTop() - getScrollY());
                    //smoothScrollTo(0, fieldView.getTop());
                    pageInvalid = true;
                }
            }
        }
        return pageInvalid;
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
        switch (actionName) {
            case DDLFormScreenlet.LOAD_RECORD_ACTION:
                LiferayLogger.i("loaded record");
                showRecordValues();
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
        if (actionName.equals(DDLFormScreenlet.LOAD_FORM_ACTION)) {
            LiferayLogger.e("error loading DDLForm", e);
            clearFormFields();
        }
    }

    @Override
    public void showFormFields(Record record) {
        setAdapter(new HorizontalViewPagerAdapter(record));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == com.liferay.mobile.screens.R.id.liferay_form_submit) {
            if (getDDLFormScreenlet().validateForm()) {
                getDDLFormScreenlet().submitForm();
            }
        } else if (view.getId() == com.liferay.mobile.screens.viewsets.R.id.next_page_button) {

            boolean validPage = checkPage(getCurrentPosition());

            if (validPage) {
                smoothScrollToPosition(getCurrentPosition() + 1);
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));
        addOnPageChangedListener(this);
        setSinglePageFling(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setDescendantFocusability(FOCUS_BEFORE_DESCENDANTS);
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

    @Override
    protected void adjustPositionX(int velocityX) {

        boolean validPage = checkPage(position);

        if (validPage || getCurrentPosition() < position) {
            super.adjustPositionX(velocityX);
        } else {
            this.smoothScrollToPosition(position);
        }
    }

    private boolean checkPage(int currentPosition) {

        Record record = getDDLFormScreenlet().getRecord();

        Map<Field, Boolean> fieldResults = new HashMap<>(record.getFieldCount());
        boolean result = true;

        for (int i = 0; i < record.getFieldCount(); i++) {
            Field field = record.getField(i);

            if (record.getPages().get(currentPosition).getFields().contains(field)) {
                boolean isFieldValid = field.isValid();
                fieldResults.put(field, isFieldValid);
                result &= isFieldValid;
            }
        }

        showValidationResults(fieldResults, true);

        return result;
    }

    public boolean quickCheck() {
        Record record = getDDLFormScreenlet().getRecord();
        for (Field field : record.getFields()) {
            if (record.getPages().get(getCurrentPosition()).getFields().contains(field)) {
                boolean isFieldValid = field.isValid();
                if (!isFieldValid) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void OnPageChanged(int oldPosition, int newPosition) {
        position = newPosition;
    }

    private class HorizontalViewPagerAdapter
        extends RecyclerView.Adapter<HorizontalViewPagerAdapter.HorizontalViewHolder> {

        private final Record record;

        HorizontalViewPagerAdapter(Record record) {
            this.record = record;
        }

        @Override
        public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext())
                .inflate(com.liferay.mobile.screens.viewsets.R.layout.ddl_page_layout, parent, false);
            return new HorizontalViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HorizontalViewHolder holder, int position) {
            holder.paint(record.getPages().get(position));
        }

        @Override
        public int getItemCount() {
            return record.getPages().size();
        }

        class HorizontalViewHolder extends RecyclerView.ViewHolder {

            private final LinearLayout container;
            private final TextView pageTitleTextView;
            private final TextView pageNumberTextView;
            private final Button nextPageButton;

            HorizontalViewHolder(View itemView) {
                super(itemView);

                container = itemView.findViewById(R.id.ddlfields_container);
                submitButton = itemView.findViewById(R.id.liferay_form_submit);
                submitButton.setOnClickListener(ViewPagerDDLFormView.this);
                nextPageButton = itemView.findViewById(R.id.next_page_button);
                nextPageButton.setOnClickListener(ViewPagerDDLFormView.this);
                pageTitleTextView = itemView.findViewById(R.id.page_title);
                pageNumberTextView = itemView.findViewById(R.id.page_number);
            }

            void paint(final Record.Page page) {

                for (Field field : page.getFields()) {

                    int fieldLayoutId = getFieldLayoutId(field.getEditorType());
                    View view =
                        LayoutInflater.from(getContext()).inflate(fieldLayoutId, ViewPagerDDLFormView.this, false);
                    DDLFieldViewModel viewModel = (DDLFieldViewModel) view;

                    viewModel.setField(field);
                    viewModel.setParentView(ViewPagerDDLFormView.this);

                    container.addView(view);
                }

                pageTitleTextView.setText(page.getTitle());
                if (getItemCount() > 1) {
                    String text = page.getNumber() + 1 + "/" + getItemCount();
                    pageNumberTextView.setText(text);
                }

                boolean isLastPage = page.getNumber() == record.getPages().size() - 1;
                nextPageButton.setVisibility(!isLastPage ? VISIBLE : INVISIBLE);
                submitButton.setVisibility(isLastPage ? VISIBLE : INVISIBLE);
            }
        }
    }
}
