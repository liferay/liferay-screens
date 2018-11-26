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
import android.graphics.Typeface
import android.support.v7.widget.TooltipCompat
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

	lateinit var columnSelectView: DDLFieldSelectView
	lateinit var rowLabelEditText: EditText

	lateinit var rowOption: Option

	override fun onFinishInflate() {
		super.onFinishInflate()

		columnSelectView = findViewById(R.id.column_label_edit_text)
		rowLabelEditText = findViewById(R.id.row_label_edit_text)
	}

	fun refresh() {
		columnSelectView.refresh()

		val columnEditText = columnSelectView.textEditText
		columnEditText.setTypeface(columnEditText.typeface, Typeface.BOLD)
	}

	fun selectOption(option: Option) {
		columnSelectView.field.selectOption(option)
		refresh()
	}

	fun setOptions(rowOption: Option, columnOptions: List<Option>) {
		this.rowOption = rowOption

		val selectableOptionsField = SelectableOptionsField(columnOptions as ArrayList<Option>?)
		selectableOptionsField.label = rowOption.label

		columnSelectView.field = selectableOptionsField

		rowLabelEditText.setText(rowOption.label)
		TooltipCompat.setTooltipText(rowLabelEditText, rowOption.label)
	}
}