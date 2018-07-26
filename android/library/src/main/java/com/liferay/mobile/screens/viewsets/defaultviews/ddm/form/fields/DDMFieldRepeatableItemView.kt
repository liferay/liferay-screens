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
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.viewsets.defaultviews.util.ThemeUtil
import rx.Observable

/**
 * @author Paulo Cruz
 */
class DDMFieldRepeatableItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr), DDLFieldViewModel<Field<*>> {

    private val repeatableLabel
            by bindNonNull<TextView>(com.liferay.mobile.screens.R.id.liferay_repeatable_field_label)

    private val addRepeatableButton
        by bindNonNull<ImageButton>(com.liferay.mobile.screens.R.id.liferay_repeatable_field_add)

    private val removeRepeatableButton
        by bindNonNull<ImageButton>(com.liferay.mobile.screens.R.id.liferay_repeatable_field_remove)

    private lateinit var field: Field<*>
    private lateinit var parentView: View

    private var isFirstField: Boolean = false
    private var isShowLabel: Boolean = false
    private lateinit var label: String

    private var fieldLayoutId: Int = 0
    private lateinit var fieldView: DDLFieldViewModel<*>
    private lateinit var listener: RepeatableActionListener
    private var onChangedValueObservable = Observable.empty<Field<*>>()

    override fun getOnChangedValueObservable(): Observable<Field<*>> {
        return onChangedValueObservable
    }

    fun setRepeatableItemSettings(
            isFirstField: Boolean, fieldLayoutId: Int, listener: RepeatableActionListener) {

        this.isFirstField = isFirstField
        this.fieldLayoutId = fieldLayoutId
        this.listener = listener
    }

    fun setLabelSettings(isShowLabel: Boolean, label: String) {
        this.isShowLabel = isShowLabel
        this.label = label
    }

    private fun setupRepeatableActions() {
        if (!isFirstField) {
            removeRepeatableButton.visibility = View.VISIBLE

            removeRepeatableButton.setOnClickListener {
                (parent as? LinearLayout)?.let {
                    listener.onRepeatableFieldRemoved(this)
                }
            }
        }

        addRepeatableButton.setOnClickListener {
            listener.onRepeatableFieldAdded(this)
        }
    }

    private fun setupRepeatableLabel() {
        if(isFirstField && isShowLabel) {
            repeatableLabel.text = label
            repeatableLabel.visibility = View.VISIBLE

            if (field.isRequired) {
                val requiredAlert = ThemeUtil.getRequiredSpannable(context)
                repeatableLabel.append(requiredAlert)
            }
        }
    }

    private fun setupRepeatableField() {
        val inflater = LayoutInflater.from(context)

        val fieldView = inflater.inflate(fieldLayoutId, this, false)

        (fieldView as? DDLFieldViewModel<*>)?.let {
            it.field = field

            this.fieldView = fieldView
            addView(fieldView)

            onChangedValueObservable = onChangedValueObservable
                .mergeWith(fieldView.onChangedValueObservable.map { field })
        }
    }

    override fun getField(): Field<*> {
        return field
    }

    override fun setField(field: Field<*>) {
        this.field = field

        refresh()
    }

    override fun refresh() {
        setupRepeatableLabel()
        setupRepeatableActions()
        setupRepeatableField()
        fieldView?.refresh()
    }

    override fun onPostValidation(valid: Boolean) {
        fieldView.onPostValidation(valid)
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

}

