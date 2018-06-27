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

package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields

import android.content.Context
import android.util.AttributeSet
import android.widget.*
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.model.Option
import com.liferay.mobile.screens.ddl.model.SelectableOptionsField
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldSelectView
import java.util.ArrayList

/**
 * @author Victor Oliveira
 */
open class DDMFieldGridRowView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit var rowLabelEditText: EditText
    lateinit var columnLabelEditText: DDLFieldSelectView

    override fun onFinishInflate() {
        super.onFinishInflate()

        rowLabelEditText = findViewById(R.id.row_label_edit_text)
        columnLabelEditText = findViewById(R.id.column_label_edit_text)
    }

    fun setOptions(dialogLabel: String, options: ArrayList<Option>) {
        val selectableOptionsField = SelectableOptionsField(options)
        selectableOptionsField.label = dialogLabel
        columnLabelEditText.field = selectableOptionsField
    }
}