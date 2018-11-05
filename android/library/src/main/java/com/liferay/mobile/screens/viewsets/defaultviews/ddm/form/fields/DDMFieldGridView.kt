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
import android.support.design.widget.Snackbar
import android.support.v7.widget.TooltipCompat
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
import com.liferay.mobile.screens.ddm.form.model.get
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.viewsets.defaultviews.util.ThemeUtil
import org.jetbrains.anko.childrenSequence
import rx.Observable
import rx.Subscriber
import rx.Subscription

/**
 * @author Victor Oliveira
 */
open class DDMFieldGridView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
	defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), DDLFieldViewModel<GridField> {
	private lateinit var gridField: GridField
	private lateinit var parentView: View
	private val labelTextView: TextView by bindNonNull(R.id.liferay_ddm_label)
	private val hintTextView: TextView by bindNonNull(R.id.liferay_ddm_hint)
	val gridLinearLayout: LinearLayout by bindNonNull(R.id.liferay_ddm_grid)

	private var changeValuesSubscription: Subscription? = null
	private var changeValuesSubscriber: Subscriber<in Boolean>? = null
	private var changeValuesGridSubscriber: Subscriber<in GridField>? = null
	private val changeValuesObservable = Observable.create<Boolean> { changeValuesSubscriber = it }

	override fun getField(): GridField {
		return this.gridField
	}

	override fun setField(field: GridField) {
		this.gridField = field

		setupLabelLayout()
		setupFieldLayout()
	}

	override fun getOnChangedValueObservable(): Observable<GridField> {
		return Observable.create<GridField> {
			changeValuesGridSubscriber = it
		}
	}

	private fun onColumnValueChanged(which: Int, row: Option, ddmFieldGridRowView: DDMFieldGridRowView) {
		val option = this.gridField.columns[which]

		if (this.gridField.currentValue == null) {
			this.gridField.currentValue = Grid(mutableMapOf(row.value to option.value))
		} else {
			this.gridField.currentValue.rawValues[row.value] = option.value
		}

		val columnEditText = ddmFieldGridRowView.columnSelectView.textEditText
		columnEditText.setTypeface(columnEditText.typeface, Typeface.BOLD)

		changeValuesSubscriber?.onNext(field.isValid)
		changeValuesGridSubscriber?.onNext(field)
	}

	private fun setupFieldLayout() {
		val inflater = LayoutInflater.from(context)
		val layoutIdentifier = ThemeUtil.getLayoutIdentifier(context, "ddmfield_grid_row")

		this.gridField.rows.forEach { row ->
			val ddmFieldGridRowView =
				inflater.inflate(layoutIdentifier, gridLinearLayout, false) as DDMFieldGridRowView

			gridLinearLayout.addView(ddmFieldGridRowView)

			ddmFieldGridRowView.setOptions(row, gridField.columns)

			ddmFieldGridRowView.columnSelectView.setOnValueChangedListener { _, which ->
				onColumnValueChanged(which, row, ddmFieldGridRowView)
			}
		}

		changeValuesSubscription = changeValuesObservable
			.filter { it }
			.distinctUntilChanged()
			.subscribe(::onPostValidation)

		refreshGridRows()
	}

	private fun setupLabelLayout() {
		if (gridField.isShowLabel && gridField.label.isNotEmpty()) {
			labelTextView.text = gridField.label
			labelTextView.visibility = View.VISIBLE

			if (this.gridField.isRequired) {
				val requiredAlert = ThemeUtil.getRequiredSpannable(context)
				labelTextView.append(requiredAlert)
			}
		}

		if (gridField.tip.isNotEmpty()) {
			hintTextView.text = gridField.tip
			hintTextView.visibility = View.VISIBLE
		}
	}

	override fun refresh() {
		setupLabelLayout()
		refreshGridRows()
	}

	override fun onPostValidation(valid: Boolean) {
		val errorText = if (valid) null else context.getString(R.string.required_value)

		if (field.isShowLabel) {
			val label = this.findViewById<TextView>(R.id.liferay_ddm_label)
			label?.error = errorText
		} else {
			Snackbar.make(this, errorText.toString(), Snackbar.LENGTH_SHORT).show()
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
	}

	override fun onDetachedFromWindow() {
		super.onDetachedFromWindow()

		changeValuesSubscription?.unsubscribe()
	}

	private fun refreshGridRows() {
		gridLinearLayout.childrenSequence().mapNotNull {
			it as? DDMFieldGridRowView
		}.forEach { view ->
			this.field.currentValue?.let {
				it[view.rowOption.value]
			}?.let { columnValue ->
				gridField.columns[columnValue]
			}?.run {
				view.selectOption(this)
			}
		}
	}

}