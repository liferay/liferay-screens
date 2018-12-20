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
import com.liferay.mobile.screens.ddl.model.DocumentField
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.RepeatableField
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLDocumentFieldView
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.DDMFormView
import org.jetbrains.anko.childrenSequence
import rx.Observable
import rx.Subscriber
import rx.Subscription
import java.util.*

/**
 * @author Paulo Cruz
 */
open class DDMFieldRepeatableView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
	: LinearLayout(context, attrs, defStyleAttr), DDLFieldViewModel<RepeatableField>,
	RepeatableActionListener, DDLDocumentFieldView.UploadListener {

	private lateinit var field: RepeatableField
	private lateinit var parentView: View
	private lateinit var layoutIds: Map<Field.EditorType, Int>

	private val fieldViewsMap: MutableMap<UUID, DDLFieldViewModel<*>> = mutableMapOf()
	private val repeatableContainer: LinearLayout by bindNonNull(R.id.container)
	private val inflater = LayoutInflater.from(context)

	private var fieldLayoutId: Int = 0
	private var containerSubscriber: Subscriber<in RepeatableField>? = null
	private var subscriptionsMap = mutableMapOf<DDMFieldRepeatableItemView, Subscription>()
	private var onChangedValueObservable = Observable.create<RepeatableField> { containerSubscriber = it }

	open fun getRepeatableItemLayoutId(): Int {
		return R.layout.ddmfield_repeatable_item
	}

	fun setLayoutIds(layoutIds: Map<Field.EditorType, Int>) {
		this.layoutIds = layoutIds
	}

	override fun getOnChangedValueObservable(): Observable<RepeatableField> {
		return onChangedValueObservable
	}

	override fun onRepeatableFieldAdded(callerFieldView: DDMFieldRepeatableItemView) {
		val newFieldIndex = repeatableContainer.indexOfChild(callerFieldView) + 1
		val repeatedField = this.field.repeatField()
		val fieldView = createFieldView(newFieldIndex, repeatedField)

		requestLayout()
		fieldView.requestFocus()

		if (!this.field.lastValidationResult) {
			fieldView.onPostValidation(this.field.isValid)
		}
	}

	override fun onRepeatableFieldRemoved(fieldView: DDMFieldRepeatableItemView) {
		val previousField = focusSearch(fieldView, FOCUS_UP)
		val removedField = fieldView.field

		this.field.removeField(removedField)
		removeFieldView(fieldView)

		requestLayout()
		previousField?.requestFocus()
	}

	override fun getField(): RepeatableField {
		return this.field
	}

	override fun setField(field: RepeatableField) {
		this.field = field
		fieldLayoutId = layoutIds[field.baseField.editorType]!!

		setupRepeatableFields()
	}

	override fun refresh() {
		val hasFieldChanges = repeatableContainer.childCount != field.repeatedFields.count()

		if (hasFieldChanges) {
			repeatableContainer.removeAllViews()
			setupRepeatableFields()
		} else {
			repeatableContainer.childrenSequence().forEach {
				(it as? DDLFieldViewModel<*>)?.refresh()
			}
		}
	}

	override fun onPostValidation(valid: Boolean) {
		repeatableContainer.childrenSequence().forEach {
			(it as? DDLFieldViewModel<*>)?.onPostValidation(valid)
		}
	}

	override fun getParentView(): View {
		return parentView
	}

	override fun setParentView(view: View) {
		parentView = view
	}

	override fun setUpdateMode(enabled: Boolean) {
		this.isEnabled = enabled

		repeatableContainer.childrenSequence().forEach {
			(it as? DDLFieldViewModel<*>)?.setUpdateMode(enabled)
		}
	}

	override fun startUpload(field: DocumentField?) {
		field?.let {
			fieldViewsMap[it.uuid]
		}?.let {
			it as? DDMFieldRepeatableItemView
		}?.let {
			it.fieldView as? DDLDocumentFieldView
		}?.also {
			(parentView as? DDMFormView)?.startUpload(it)
		}
	}

	private fun setupRepeatableFields() {
		field.repeatedFields.forEachIndexed { index, repeatedField ->
			createFieldView(index, repeatedField)
		}
		requestLayout()
	}

	private fun createFieldView(fieldIndex: Int, repeatedField: Field<*>): DDMFieldRepeatableItemView {
		val fieldView = inflater.inflate(getRepeatableItemLayoutId(), this, false)

		(fieldView as DDMFieldRepeatableItemView).let {
			val isFirstField = fieldIndex == 0

			fieldView.parentView = this
			fieldView.uploadListener = this
			fieldView.setLabelSettings(field.isShowLabel, field.label)
			fieldView.setRepeatableItemSettings(isFirstField, fieldLayoutId, this)

			fieldView.field = repeatedField

			val subscription = fieldView
				.onChangedValueObservable
				.map { field }
				.doOnNext {
					containerSubscriber?.let {
						if (!it.isUnsubscribed) {
							it.onNext(field)
						}
					}
				}
				.map { field.isValid }
				.filter { it }
				.distinctUntilChanged()
				.doOnNext { onPostValidation(field.isValid) }
				.subscribe()

			subscriptionsMap[fieldView] = subscription

			fieldViewsMap[fieldView.field.uuid] = fieldView
			repeatableContainer.addView(fieldView, fieldIndex)
		}

		return fieldView
	}

	private fun removeFieldView(fieldView: DDMFieldRepeatableItemView) {
		val subscription = subscriptionsMap.remove(fieldView)
		subscription?.unsubscribe()

		fieldViewsMap.remove(fieldView.field.uuid)
		repeatableContainer.removeView(fieldView)
	}
}