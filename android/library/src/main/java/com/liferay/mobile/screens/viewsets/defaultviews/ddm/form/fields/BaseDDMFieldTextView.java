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

package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddm.form.model.DDMField;
import com.liferay.mobile.screens.ddm.form.view.DDMFieldViewModel;

/**
 * @author Paulo Cruz
 */
public abstract class BaseDDMFieldTextView<T extends DDMField> extends LinearLayout
        implements DDMFieldViewModel<T>, TextWatcher {

    protected TextView labelTextView;
    protected EditText textEditText;
    protected View parentView;
    private T field;

    public BaseDDMFieldTextView(Context context) {
        super(context);
    }

    public BaseDDMFieldTextView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public BaseDDMFieldTextView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (field != null) {
            if (!field.getLastValidationResult()) {
                field.setLastValidationResult(true);

                onPostValidation(true);
            }

            onTextChanged(editable.toString());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence text, int start, int count, int after) {
    }

    @Override
    public T getField() {
        return field;
    }

    @Override
    public void setField(T field) {
        this.field = field;

        if (this.field.isShowLabel()) {
            textEditText.setHint("");
            if (labelTextView != null) {
                labelTextView.setText(this.field.getLabel());
                labelTextView.setVisibility(VISIBLE);
            }
        } else {
            textEditText.setHint(this.field.getLabel());
            if (labelTextView != null) {
                labelTextView.setVisibility(GONE);
            }
        }

        refresh();
    }

    public TextView getLabelTextView() {
        return labelTextView;
    }

    public EditText getTextEditText() {
        return textEditText;
    }

    @Override
    public View getParentView() {
        return parentView;
    }

    @Override
    public void setParentView(View view) {
        parentView = view;
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
    }

    @Override
    public void refresh() {
        textEditText.setText(field.toFormattedString());
    }

    @Override
    public void onPostValidation(boolean valid) {
        String errorText = valid ? null : getResources().getString(R.string.invalid);

        if (labelTextView == null) {
            textEditText.setError(errorText);
        } else {
            labelTextView.setError(errorText);
        }
    }

    @Override
    public void setUpdateMode(boolean enabled) {
        textEditText.setEnabled(enabled);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        labelTextView = findViewById(R.id.liferay_ddm_label);
        textEditText = findViewById(R.id.liferay_ddm_edit_text);

        textEditText.addTextChangedListener(this);

        //We are not saving the text view state because when state is restored,
        //the ids of other DDLFields are conflicting.
        //It is not a problem because all state is stored in Field objects.
        textEditText.setSaveEnabled(false);
    }

    protected abstract void onTextChanged(String text);
}
