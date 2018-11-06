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

package com.liferay.mobile.screens.viewsets.lexicon.ddl.form.fields;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.liferay.mobile.screens.ddl.model.Option;
import com.liferay.mobile.screens.ddl.model.SelectableOptionsField;
import com.liferay.mobile.screens.viewsets.lexicon.R;
import com.liferay.mobile.screens.viewsets.lexicon.util.FormViewUtil;
import java.util.List;

/**
 * @author Victor Oliveira
 */
public class DDLFieldRadioView
    extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldRadioView {

    public DDLFieldRadioView(Context context) {
        super(context);
    }

    public DDLFieldRadioView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    private RadioGroup radioGroup;

    @Override
    public void renderOptions(SelectableOptionsField field) {
        LayoutParams layoutParams =
            new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int margin = FormViewUtil.convertDpToPx(getContext(), 18);
        layoutParams.setMargins(0, margin, 0, margin);

        List<Option> availableOptions = field.getAvailableOptions();

        for (int i = 0; i < availableOptions.size(); ++i) {
            Option opt = availableOptions.get(i);

            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setLayoutParams(layoutParams);
            radioButton.setText(opt.label);
            radioButton.setTag(opt);
            radioButton.setOnCheckedChangeListener(this);
            radioButton.setSaveEnabled(true);
            radioGroup.addView(radioButton);

            if (field.isInline()) {
                radioButton.setGravity(Gravity.CENTER_VERTICAL);
            } else {
                boolean isLast = availableOptions.size() - 1 == i;
                if (!isLast) {
                    LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        FormViewUtil.convertDpToPx(getContext(), 1));

                    View separator = new View(getContext());
                    separator.setLayoutParams(params);
                    separator.setBackgroundColor(
                        ResourcesCompat.getColor(getResources(), R.color.textColorTertiary_lexicon,
                            getContext().getTheme()));

                    radioGroup.addView(separator);
                }
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        radioGroup = findViewById(R.id.radio_group);
    }

    @Override
    public void onPostValidation(boolean valid) {
        View errorContainer = findViewById(R.id.errorContainer);

        if (errorContainer != null) {
            errorContainer.setBackground(
                valid ? null : getResources().getDrawable(R.drawable.lexicon_error_view_shape));
        }

        View errorView = findViewById(R.id.error_view);

        if (errorView != null) {
            errorView.setVisibility(valid ? GONE : VISIBLE);
        }
    }
}