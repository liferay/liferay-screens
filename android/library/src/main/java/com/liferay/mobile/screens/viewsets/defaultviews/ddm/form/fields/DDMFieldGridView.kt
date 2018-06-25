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
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel
import com.liferay.mobile.screens.ddl.model.Option
import com.liferay.mobile.screens.ddm.form.model.Grid
import com.liferay.mobile.screens.ddm.form.model.GridField
import java.util.*

/**
 * @author Victor Oliveira
 */
open class DDMFieldGridView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), DDLFieldViewModel<GridField> {
    private lateinit var field: GridField
    private lateinit var parentView: View

    override fun getField(): GridField {
        return field
    }

    override fun setField(field: GridField) {
        this.field = field

        refresh()
    }

    override fun refresh() {
        this.removeAllViews()

        field.rows.forEach { row ->

            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.ddmfield_grid_row_default, null, false)

            this.addView(view)

            val ddmFieldGridRowView = view as DDMFieldGridRowView
            ddmFieldGridRowView.rowLabelEditText.setText(row.label)
            ddmFieldGridRowView.setOptions(field.columns as ArrayList<Option>)

            ddmFieldGridRowView.columnLabelEditText.setOnValueChangedListener { dialog, which ->
                val option = field.columns[which]

                if (field.currentValue == null) {
                    field.currentValue = Grid(mutableMapOf(row.value to option.value))
                } else {
                    field.currentValue.rawValues[row.value] = option.value
                }
            }
        }
    }

    override fun onPostValidation(valid: Boolean) {
    }

    override fun getParentView(): View {
        return parentView
    }

    override fun setParentView(view: View) {
        parentView = view
    }

    override fun setUpdateMode(enabled: Boolean) {
        this.isEnabled = enabled
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }
}