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

package com.liferay.mobile.screens.viewsets.westeros.ddl.form;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.viewsets.westeros.R;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFieldSelectView
    extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldSelectView
    implements DialogInterface.OnClickListener {

    public DDLFieldSelectView(Context context) {
        super(context);
    }

    public DDLFieldSelectView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public DDLFieldSelectView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    public void refresh() {
        if (!getField().toFormattedString().isEmpty()) {
            findViewById(R.id.liferay_ddl_label).setVisibility(View.VISIBLE);
        }
        getTextEditText().setText(getField().toFormattedString());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        getTextEditText().setHint("");
        findViewById(R.id.liferay_ddl_label).setVisibility(View.VISIBLE);
        getField().selectOption(getField().getAvailableOptions().get(which));
        refresh();
        getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        getTextEditText().setBackgroundResource(R.drawable.westeros_dark_edit_text_drawable);
    }

    @Override
    public void onPostValidation(boolean valid) {
        if (!valid) {
            getTextEditText().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.westeros_icon_warning_white, 0);
            getTextEditText().setBackgroundResource(R.drawable.westeros_warning_edit_text_drawable);
        }
    }

    @Override
    protected DialogInterface.OnClickListener getAlertDialogListener() {
        return this;
    }
}