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
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel
import com.liferay.mobile.screens.ddl.model.Option
import com.liferay.mobile.screens.ddm.form.model.Grid
import com.liferay.mobile.screens.ddm.form.model.GridField
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.viewsets.defaultviews.util.ThemeUtil
import java.util.*

/**
 * @author Victor Oliveira
 */
open class DDMFieldGridView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), DDLFieldViewModel<GridField> {
    private lateinit var field: GridField
    private lateinit var parentView: View
    private val labelTextView: TextView by bindNonNull(R.id.liferay_ddm_label)
    private val hintTextView: TextView by bindNonNull(R.id.liferay_ddm_hint)
    private val gridLinearLayout: LinearLayout by bindNonNull(R.id.liferay_ddm_grid)

    override fun getField(): GridField {
        return field
    }

    override fun setField(field: GridField) {
        this.field = field

        setupLabelLayout()
        refresh()
    }

    private fun setupLabelLayout() {
        if (field.isShowLabel && field.label.isNotEmpty()) {
            labelTextView.text = field.label
            labelTextView.visibility = View.VISIBLE

            if (this.field.isRequired) {
                val requiredAlert = ThemeUtil.getRequiredSpannable(context)
                labelTextView.append(requiredAlert)
            }
        }

        if (field.tip.isNotEmpty()) {
            hintTextView.text = field.tip
            hintTextView.visibility = View.VISIBLE
        }
    }

    override fun refresh() {
        gridLinearLayout.removeAllViews()

        field.rows.forEach { row ->

            val inflater = LayoutInflater.from(context)
            val layoutIdentifier = ThemeUtil.getLayoutIdentifier(context, "ddmfield_grid_row")

            val view = inflater.inflate(layoutIdentifier, gridLinearLayout, false)

            gridLinearLayout.addView(view)

            val ddmFieldGridRowView = view as DDMFieldGridRowView
            ddmFieldGridRowView.rowLabelEditText.setText(row.label)
            ddmFieldGridRowView.setOptions(row.label, field.columns as ArrayList<Option>)

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