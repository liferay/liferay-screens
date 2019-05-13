/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.base.ModalProgressBarWithLabel
import com.liferay.mobile.screens.context.LiferayScreensContext
import com.liferay.mobile.screens.ddl.form.util.FormConstants
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel
import com.liferay.mobile.screens.ddl.model.DocumentField
import com.liferay.mobile.screens.ddl.model.DocumentLocalFile
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.DDMFormListener
import com.liferay.mobile.screens.ddm.form.model.*
import com.liferay.mobile.screens.ddm.form.view.SuccessPageActivity
import com.liferay.mobile.screens.delegates.bindNonNull
import com.liferay.mobile.screens.util.AndroidUtil
import com.liferay.mobile.screens.util.LiferayLogger
import com.liferay.mobile.screens.util.extensions.firstNotNull
import com.liferay.mobile.screens.util.extensions.forEachChild
import com.liferay.mobile.screens.util.extensions.setVisibility
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.BaseDDLFieldTextView
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLDocumentFieldView
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.adapters.DDMPagerAdapter
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields.DDMFieldRepeatableView
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.pager.WrapContentViewPager
import com.liferay.mobile.screens.viewsets.defaultviews.util.ThemeUtil
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.Subscriptions
import java.util.concurrent.TimeUnit

/**
 * @author Paulo Cruz
 * @author Victor Oliveira
 */
class DDMFormView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
	RelativeLayout(context, attrs, defStyleAttr), DDLDocumentFieldView.UploadListener,
	DDMFormViewContract.DDMFormView {

	private val presenter = DDMFormPresenter(this, resources.getString(R.string.liferay_server))
	private val layoutIds = mutableMapOf<Field.EditorType, Int>()

	private val backButton by bindNonNull<Button>(R.id.liferay_form_back)
	private val nextButton by bindNonNull<Button>(R.id.liferay_form_submit)
	private val ddmFieldViewPages by bindNonNull<WrapContentViewPager>(R.id.ddmfields_container)
	private val scrollView by bindNonNull<ScrollView>(R.id.multipage_scroll_view)
	private val multipageProgress by bindNonNull<ProgressBar>(R.id.liferay_multipage_progress)
	private val modalProgress by bindNonNull<ModalProgressBarWithLabel>(R.id.liferay_modal_progress)

	private var subscription: Subscription = Subscriptions.empty()

	override var ddmFormListener: DDMFormListener? = null

	override val config = DDMFormViewConfig()

	private lateinit var _formInstance: FormInstance

	override var formInstance: FormInstance? = null
		get() = _formInstance
		set(value) {
			field = value
			value?.let {
				_formInstance = it
				onFormLoaded(_formInstance)
			}
		}

	init {
		val themeName = ThemeUtil.getLayoutTheme(context)

		for (pair in availableFields) {
			val fieldType = pair.first
			val fieldNamePrefix = pair.second

			layoutIds[fieldType] = ThemeUtil.getLayoutIdentifier(context, fieldNamePrefix, themeName)
		}
	}

	override fun hasConnectivity(): Boolean {
		return AndroidUtil.isConnected(context.applicationContext)
	}

	override fun hideModalLoading() {
		modalProgress.hide()
	}

	override fun inflateFieldView(inflater: LayoutInflater, parentView: ViewGroup, field: Field<*>): View {
		val layoutId = layoutIds[field.editorType]
		val view = inflater.inflate(layoutId!!, parentView, false)

		if (view is DDLDocumentFieldView) {
			view.setUploadListener(this)
		} else if (view is DDMFieldRepeatableView) {
			view.setLayoutIds(layoutIds)
		}

		val viewModel = view as DDLFieldViewModel<*>
		viewModel.field = field
		viewModel.parentView = this
		view.tag = field.name

		return view
	}

	override fun isSubmitEnabled(isEnabled: Boolean) {
		nextButton.isEnabled = isEnabled
	}

	override fun onDetachedFromWindow() {
		super.onDetachedFromWindow()
		subscription.unsubscribe()
		subscription = Subscriptions.empty()
		presenter.onDestroy()
	}

	override fun onFinishInflate() {
		super.onFinishInflate()
		isSaveEnabled = true

		backButton.setOnClickListener { backButtonListener() }
		nextButton.setOnClickListener { nextOrSubmitButtonListener() }
	}

	override fun onRestoreInstanceState(state: Parcelable?) {
		if (state is Bundle) {
			val formInstanceState = FormInstanceState.valueOf(state.getString("formInstanceState"))
			val formInstanceRecord = state.getParcelable<FormInstanceRecord>("formInstanceRecord")
			formInstance = state.getParcelable("formInstance")

			presenter.restore(formInstanceRecord, _formInstance.ddmStructure.fields, formInstanceState)
			super.onRestoreInstanceState(state.getParcelable("superState"))
		} else {
			super.onRestoreInstanceState(state)
		}
	}

	override fun onSaveInstanceState(): Parcelable {
		val bundle = Bundle()
		bundle.putParcelable("superState", super.onSaveInstanceState())
		bundle.putParcelable("formInstance", _formInstance)
		bundle.putString("formInstanceState", presenter.getFormInstanceState().toString())

		presenter.formInstanceRecord?.let {
			bundle.putParcelable("formInstanceRecord", it)
		}

		return bundle
	}

	override fun refreshVisibleFields() {
		getInstantiatedPages().forEach { viewGroup ->
			viewGroup.forEachChild { view ->
				(view as? DDLFieldViewModel<*>).also { ddlFieldViewModel ->
					ddlFieldViewModel?.refresh()
				}
			}
		}

		restoreActionButtonsState()
	}

	override fun setDDMFormListener(listener: DDMFormListener) {
		ddmFormListener = listener
	}

	override fun scrollToTop() {
		scrollView.scrollTo(0, 0)
	}

	override fun showErrorMessage(exception: Throwable?) {
		val icon = R.drawable.default_error_icon
		val backgroundColor = ContextCompat.getColor(context, R.color.lightRed)
		val textColor = ContextCompat.getColor(context, android.R.color.white)
		val message =
			exception?.message ?: context.getString(R.string.submit_failed_contact_administrator)

		AndroidUtil.showCustomSnackbar(
			this, message, Snackbar.LENGTH_LONG, backgroundColor, textColor, icon)
	}

	override fun showModalEvaluateContextLoading() {
		modalProgress.show(context.getString(R.string.validating_rules))
	}

	override fun showModalSyncFormLoading() {
		modalProgress.show(context.getString(R.string.synchronizing_form))
	}

	override fun showOfflineWarningMessage() {
		showConnectivityErrorMessage(R.color.orange, R.string.cant_load_some_fields_offline)
	}

	override fun showSuccessMessage() {
		val icon = R.drawable.default_check_icon
		val backgroundColor = ContextCompat.getColor(context, R.color.success_green_default)
		val textColor = ContextCompat.getColor(context, android.R.color.white)
		val message = context.getString(R.string.information_successfully_received)

		AndroidUtil.showCustomSnackbar(
			this, message, Snackbar.LENGTH_LONG, backgroundColor, textColor, icon)
	}

	override fun showSuccessPage(successPage: SuccessPage) {
		val intent = Intent(context, SuccessPageActivity::class.java)
		intent.putExtra(FormConstants.SUCCESS_PAGE, successPage)
		context.startActivity(intent)
	}

	override fun startUpload(field: DocumentField) {
		findViewWithTag<DDLDocumentFieldView>(field.name)?.also {
			startUpload(it)
		}
	}

	override fun startUpload(documentFieldView: DDLDocumentFieldView) {
		val field = documentFieldView.field

		field.moveToUploadInProgressState()
		documentFieldView.refresh()

		val inputStream = AndroidUtil.openLocalFileInputStream(context, field.currentValue as DocumentLocalFile)

		presenter.uploadFile(_formInstance, field, inputStream, documentFieldView::onUploadCompleted,
			documentFieldView::onUploadError)
	}

	var lastChangedField: Field<*>? = null

	override fun subscribeToValueChanged(observable: Observable<Field<*>>) {
		subscription = observable.doOnNext {
			if (!it.isTransient) {
				presenter.fieldModelsChanged(it)
				lastChangedField = it
			}
		}.doOnError {
			LiferayLogger.e(it.message, it)
		}.debounce(config.syncFormTimeout, TimeUnit.MILLISECONDS)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ field ->
				presenter.syncForm(_formInstance, field, true)
			}, {
				LiferayLogger.e(it.message, it)
			})
	}

	override fun updateFieldView(fieldContext: FieldContext, field: Field<*>) {
		val fieldView = getInstantiatedPages().map {
			findViewWithTag<View>(field.name)
		}.firstNotNull()

		fieldView?.setVisibility(fieldContext.isVisible)

		(fieldView as? DDLFieldViewModel<*>)?.apply {
			refresh()
			setUpdateMode(!field.isReadOnly)

			presenter.checkIsDirty(field, fieldContext, this)
		}
	}

	override fun updatePageEnabled(formContext: FormContext) {
		(ddmFieldViewPages.adapter as DDMPagerAdapter).let {
			for ((index, page) in it.pages.withIndex()) {
				page.isEnabled = formContext.pages[index].isEnabled
			}
		}
	}

	private fun backButtonListener() {
		if (ddmFieldViewPages.currentItem >= 1) {
			ddmFieldViewPages.currentItem = getPreviousEnabledPage()

			nextButton.text = context.getString(R.string.next)
			multipageProgress.progress = getFormProgress()

			if (ddmFieldViewPages.currentItem == 0) {
				backButton.visibility = View.GONE
			}
		}
	}

	private fun getFormProgress(): Int {
		return (ddmFieldViewPages.currentItem + 1) * 100 / _formInstance.ddmStructure.pages.size
	}

	private fun getInstantiatedPages(): List<ViewGroup> {
		val pages = mutableListOf<ViewGroup>()

		val currentPos = ddmFieldViewPages.currentItem
		val offset = ddmFieldViewPages.offscreenPageLimit

		val start = if (currentPos - offset >= 0) currentPos - offset else 0
		val end = currentPos + offset

		for (pos in start..end) {
			val view = ddmFieldViewPages.findViewWithTag<LinearLayout>(pos)

			if (view != null) {
				pages.add(view)
			}
		}

		return pages
	}

	private fun getInvalidFields(): Map<String, Field<*>> {
		val page = _formInstance.ddmStructure.pages[ddmFieldViewPages.currentItem]

		return page.fields.asSequence().filter { !it.isValid }.associateBy({ it.name }, { it })
	}

	private fun getNextEnabledPage(): Int {
		(ddmFieldViewPages.adapter as DDMPagerAdapter).let { ddmPagerAdapter ->
			val currentPage = ddmFieldViewPages.currentItem + 1

			return ddmPagerAdapter.pages.asSequence().drop(currentPage).indexOfFirst { it.isEnabled } + currentPage
		}
	}

	private fun getPreviousEnabledPage(): Int {
		(ddmFieldViewPages.adapter as DDMPagerAdapter).let { ddmPagerAdapter ->
			val dropPages = ddmPagerAdapter.pages.size - ddmFieldViewPages.currentItem

			return ddmPagerAdapter.pages.dropLast(dropPages).indexOfLast { it.isEnabled }
		}
	}

	private fun highLightInvalidFields(fieldResults: Map<String, Field<*>>, autoscroll: Boolean) {
		var scrolled = false

		ddmFieldViewPages.currentView?.let { currentViewPage ->

			for (i in 0 until currentViewPage.childCount) {

				val fieldView = currentViewPage.getChildAt(i)
				val fieldViewModel = fieldView as? DDLFieldViewModel<*>

				fieldViewModel?.let {
					fieldResults[fieldViewModel.field.name]
				}?.let {
					fieldView.clearFocus()
					fieldViewModel.onPostValidation(false)

					if (autoscroll && !scrolled) {
						fieldView.requestFocus()
						scrollView.smoothScrollTo(0, fieldView.top)
						scrolled = true
					}
				}
			}
		}
	}

	private fun initPageAdapter(pages: List<FormPage>) {
		ddmFieldViewPages.adapter = DDMPagerAdapter(pages, this)

		if (pages.size > 1) {
			multipageProgress.visibility = View.VISIBLE
			multipageProgress.progress = getFormProgress()
		} else {
			multipageProgress.visibility = View.GONE
		}

		if (pages.size == 1)
			nextButton.text = context.getString(R.string.submit)
	}

	private fun nextButtonListener(size: Int) {
		ddmFieldViewPages.currentItem = getNextEnabledPage()

		backButton.visibility = View.VISIBLE
		multipageProgress.progress = getFormProgress()

		if (ddmFieldViewPages.currentItem == size) {
			nextButton.text = context.getString(R.string.submit)
		}
	}

	private fun nextOrSubmitButtonListener() {
		var size = 0
		val hasNext = ddmFieldViewPages.adapter?.let {
			size = it.count - 1
			ddmFieldViewPages.currentItem < size
		} ?: false

		presenter.syncForm(_formInstance, lastChangedField, hasNext) {
			ddmFieldViewPages.adapter?.also {
				val invalidFields = getInvalidFields()

				if (invalidFields.isEmpty()) {
					if (hasNext) {
						nextButtonListener(size)
					} else {
						submitButtonListener()
					}
				} else {
					highLightInvalidFields(invalidFields, true)
				}
			}
		}
	}

	private fun onFormLoaded(formInstance: FormInstance) {
		setActivityTitle(formInstance)
		initPageAdapter(formInstance.ddmStructure.pages)

		presenter.loadInitialContext(formInstance)
	}

	private fun restoreActionButtonsState() {
		if (ddmFieldViewPages.currentItem != 0) {
			backButton.visibility = View.VISIBLE
		}

		ddmFieldViewPages.adapter?.let {
			if (ddmFieldViewPages.currentItem == it.count - 1) {
				nextButton.text = context.getString(R.string.submit)
			}
		}
	}

	private fun setActivityTitle(formInstance: FormInstance) {
		val activityFromContext = LiferayScreensContext.getActivityFromContext(context)
		activityFromContext?.title = formInstance.name
	}

	private fun showConnectivityErrorMessage(@ColorRes backgroundColorResource: Int = R.color.midGray,
		@StringRes messageStringRes: Int = R.string.no_internet_connection) {

		val icon = R.drawable.default_error_icon
		val message = context.getString(messageStringRes)
		val backgroundColor = ContextCompat.getColor(context, backgroundColorResource)
		val textColor = ContextCompat.getColor(context, android.R.color.white)

		AndroidUtil.showCustomSnackbar(this, message, Snackbar.LENGTH_LONG, backgroundColor, textColor, icon)
	}

	private fun submitButtonListener() {
		if (!AndroidUtil.isConnected(context.applicationContext)) {
			showConnectivityErrorMessage()
		} else {
			presenter.submit(_formInstance)
		}
	}

	companion object {
		@JvmField
		val availableFields = listOf(
			Field.EditorType.CHECKBOX to "ddlfield_checkbox",
			Field.EditorType.CHECKBOX_MULTIPLE to "ddmfield_checkbox_multiple",
			Field.EditorType.DATE to "ddlfield_date",
			Field.EditorType.NUMBER to "ddlfield_number",
			Field.EditorType.INTEGER to "ddlfield_number",
			Field.EditorType.DECIMAL to "ddlfield_number",
			Field.EditorType.RADIO to "ddlfield_radio",
			Field.EditorType.TEXT to "ddlfield_text",
			Field.EditorType.SELECT to "ddlfield_select",
			Field.EditorType.TEXT_AREA to "ddlfield_text_area",
			Field.EditorType.PARAGRAPH to "ddmfield_paragraph",
			Field.EditorType.DOCUMENT to "ddlfield_document",
			Field.EditorType.GRID to "ddmfield_grid",
			Field.EditorType.GEO to "ddlfield_geo",
			Field.EditorType.REPEATABLE to "ddmfield_repeatable"
		)
	}
}
