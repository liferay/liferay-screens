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

package com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields

import android.R
import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.liferay.mobile.screens.ddl.model.StringField

/**
 * @author Silvio Santos
 * @author Victor Oliveira
 */
open class DDLFieldTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : BaseDDLFieldTextView<StringField>(context, attrs, defStyleAttr) {

    override fun setField(field: StringField) {
        super.setField(field)

        setupAutocomplete(field)
    }

    private fun setupAutocomplete(field: StringField) {
        if (textEditText is AutoCompleteTextView && field.availableOptions.isNotEmpty()) {

            val stringOptions = field.availableOptions.map {
                it.label
            }

            (textEditText as AutoCompleteTextView).setAdapter(
                    ArrayAdapter<String>(context, R.layout.simple_dropdown_item_1line, stringOptions))
        }
    }

    override fun onTextChanged(text: String) {
        field.currentValue = text
    }

    override fun refresh() {
        super.refresh()

        setupAutocomplete(field)
    }
}