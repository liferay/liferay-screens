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
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.RepeatableField
import rx.Observable
import rx.Subscriber
import rx.Subscription

/**
 * @author Paulo Cruz
 */
class DDMFieldRepeatableView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr), DDLFieldViewModel<RepeatableField>, RepeatableActionListener {

    private lateinit var field: RepeatableField
    private lateinit var parentView: View
    private lateinit var layoutIds: Map<Field.EditorType, Int>

    private val inflater = LayoutInflater.from(context)
    private val repeatableLayoutId = com.liferay.mobile.screens.R.layout.ddmfield_repeatable_item
    private var fieldLayoutId: Int = 0

    private var containerSubscriber: Subscriber<in RepeatableField>? = null
    private var subscriptionsMap = mutableMapOf<DDMFieldRepeatableItemView, Subscription>()
    private var onChangedValueObservable = Observable.create<RepeatableField> { containerSubscriber = it }

    fun setLayoutIds(layoutIds: Map<Field.EditorType, Int>) {
        this.layoutIds = layoutIds
    }

    override fun getOnChangedValueObservable(): Observable<RepeatableField> {
        return onChangedValueObservable
    }

    private fun setupRepeatableFields() {
        field.repeatedFields.forEachIndexed { index, repeatedField ->
            createFieldView(index, repeatedField)
        }
        requestLayout()
    }

    private fun createFieldView(fieldIndex: Int, repeatedField: Field<*>): DDMFieldRepeatableItemView {
        val fieldView = inflater.inflate(repeatableLayoutId, this, false)

        (fieldView as DDMFieldRepeatableItemView).let {
            fieldView.setRepeatableItemSettings(fieldIndex, fieldLayoutId, this)

            fieldView.field = repeatedField
            addView(fieldView, fieldIndex)

            val subscription = fieldView.onChangedValueObservable.map { field }.subscribe({
                containerSubscriber?.let {
                    if (!it.isUnsubscribed) {
                        it.onNext(field)
                    }
                }
            })

            subscriptionsMap[fieldView] = subscription
        }

        return fieldView
    }

    override fun onRepeatableFieldAdded(newFieldIndex: Int) {
        val repeatedField = this.field.repeat()
        createFieldView(newFieldIndex, repeatedField)
        requestLayout()
    }

    override fun onRepeatableFieldRemoved(fieldView: DDMFieldRepeatableItemView) {
        val removedField = fieldView.field

        val subscription = subscriptionsMap.remove(fieldView)
        subscription?.unsubscribe()

        this.field.remove(removedField)

        removeView(fieldView)
    }

    override fun getField(): RepeatableField {
        return this.field
    }

    override fun setField(field: RepeatableField) {
        this.field = field
        fieldLayoutId = layoutIds[field.baseField.editorType]!!

        setupRepeatableFields()
    }

    override fun refresh() {}

    override fun onPostValidation(valid: Boolean) {}

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