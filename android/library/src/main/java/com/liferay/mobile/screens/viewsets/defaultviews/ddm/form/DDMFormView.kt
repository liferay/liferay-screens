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
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.liferay.apio.consumer.delegates.converter
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.getOperation
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.context.LiferayScreensContext
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel
import com.liferay.mobile.screens.ddl.model.DocumentField
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddl.model.Option
import com.liferay.mobile.screens.ddl.model.OptionsField
import com.liferay.mobile.screens.ddm.form.model.*
import com.liferay.mobile.screens.ddm.form.service.APIOEvaluateService
import com.liferay.mobile.screens.ddm.form.service.APIOFetchLatestDraftService
import com.liferay.mobile.screens.ddm.form.service.APIOSubmitService
import com.liferay.mobile.screens.ddm.form.service.APIOUploadService
import com.liferay.mobile.screens.ddm.form.view.SuccessPageActivity
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.screens.util.AndroidUtil
import com.liferay.mobile.screens.util.LiferayLogger
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.BaseDDLFieldTextView
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLDocumentFieldView
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields.DDMFieldRepeatableView
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.pager.WrapContentViewPager
import com.liferay.mobile.screens.viewsets.defaultviews.util.ThemeUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rx.Observable
import rx.Subscription
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author Paulo Cruz
 * @author Victor Oliveira
 */
class DDMFormView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseView,
    RelativeLayout(context, attrs, defStyleAttr), DDLDocumentFieldView.UploadListener, IDDMFormView {

    val scrollView by bindNonNull<ScrollView>(R.id.multipage_scroll_view)
    private val ddmFieldViewPages by bindNonNull<WrapContentViewPager>(R.id.ddmfields_container)
    private val multipageProgress by bindNonNull<ProgressBar>(R.id.liferay_multipage_progress)
    private val backButton by bindNonNull<Button>(R.id.liferay_form_back)
    private val nextButton by bindNonNull<Button>(R.id.liferay_form_submit)
    private val layoutIds = mutableMapOf<Field.EditorType, Int>()
    private val dirtyFieldNames: MutableList<String> = mutableListOf()

    private val evaluateService = APIOEvaluateService()
    private val fetchLatestDraftService = APIOFetchLatestDraftService()
    private val submitService = APIOSubmitService()
    private val uploadService = APIOUploadService()

    private var subscription: Subscription? = null

    private var formInstanceRecord: FormInstanceRecord? = null
    private lateinit var formInstance: FormInstance

    private var currentRecordThing: Thing? by converter<FormInstanceRecord> {
        formInstanceRecord = it
    }

    override var screenlet: ThingScreenlet? = null

    override var thing: Thing? by converter<FormInstance> {
        formInstance = it

        onFormLoaded(formInstance)
    }

    init {
        val themeName = ThemeUtil.getLayoutTheme(context)

        for (pair in availableFields) {
            val fieldType = pair.first
            val fieldNamePrefix = pair.second

            layoutIds[fieldType] = ThemeUtil.getLayoutIdentifier(context, fieldNamePrefix, themeName)
        }
    }

    override fun subscribeToValueChanged(observable: Observable<Field<*>>) {
        subscription = observable
            .skip(3)
            .debounce(2, TimeUnit.SECONDS)
            .subscribe { field ->
                onFieldValueChanged(field)
                formInstanceRecord?.let {
                    it.fieldValues[field.name] = field.toData()
                }
            }
    }

    override fun scrollToTop() {
        scrollView.scrollTo(0, 0)
    }

    override fun inflateField(inflater: LayoutInflater, parentView: ViewGroup, field: Field<*>): View {
        val layoutId = layoutIds[field.editorType]
        val view = inflater.inflate(layoutId!!, parentView, false)

        if (view is DDLDocumentFieldView) {
            view.setUploadListener(this)
        } else if (view is DDMFieldRepeatableView) {
            view.setLayoutIds(layoutIds)
        }

        val viewModel = view as DDLFieldViewModel<*>
        viewModel.field = field
        view.tag = field

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription?.unsubscribe()
    }

    private fun getIdentifier(fieldNamePrefix: String, themeName: String): Int {
        return context.resources.getIdentifier(
            "${fieldNamePrefix}_$themeName", "layout", context.packageName)
    }

    private fun onFormLoaded(formInstance: FormInstance) {
        val thing = thing ?: throw Exception("No thing found")

        setActivityTitle(formInstance)
        initPageAdapter(formInstance.ddmStructure.pages)

        fetchLatestDraftService.fetchLatestDraft(thing, {
            currentRecordThing = it

            formInstanceRecord?.let {
                updateFields(it.fieldValues)
            }

            evaluateContext()
        }, {
            evaluateContext()
        })
    }

    private fun initPageAdapter(pages: List<FormPage>) {
        val ddmPagerAdapter = DDMPagerAdapter(pages, this)
        ddmFieldViewPages.adapter = ddmPagerAdapter

        if (pages.size > 1) {
            multipageProgress.visibility = View.VISIBLE
            multipageProgress.progress = getFormProgress()
        } else {
            multipageProgress.visibility = View.GONE
        }

        if (pages.size == 1)
            nextButton.text = context.getString(R.string.submit)
    }

    private fun setActivityTitle(formInstance: FormInstance) {
        val activityFromContext = LiferayScreensContext.getActivityFromContext(context)
        activityFromContext?.title = formInstance.name
    }

    override fun startUploadField(field: DocumentField) {
        val fieldView = findViewWithTag<DDLDocumentFieldView>(field)

        fieldView.let {
            field.moveToUploadInProgressState()
            fieldView.refresh()

            val thing = thing ?: throw Exception("No thing found")

            uploadService.uploadFileToRootFolder(context, thing, field, {
                field.currentValue = it

                field.moveToUploadCompleteState()
                fieldView.refresh()
            }, {
                field.moveToUploadFailureState()
                fieldView.refresh()
            })
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())

        formInstanceRecord.let {
            bundle.putParcelable("formInstanceRecord", it)
        }

        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            formInstanceRecord = state.getParcelable("formInstanceRecord")

            formInstanceRecord?.let {
                updateFields(it.fieldValues)
            }

            super.onRestoreInstanceState(state.getParcelable("superState"))
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        isSaveEnabled = true

        backButton.setOnClickListener {
            if (ddmFieldViewPages.currentItem >= 1) {
                ddmFieldViewPages.currentItem = getPreviousEnabledPage().toInt()

                nextButton.text = context.getString(R.string.next)
                multipageProgress.progress = getFormProgress()

                if (ddmFieldViewPages.currentItem == 0) {
                    backButton.visibility = View.GONE
                }
            }
        }

        nextButton.setOnClickListener {
            val size = ddmFieldViewPages.adapter!!.count - 1
            val invalidFields = getInvalidFields()

            if (invalidFields.isEmpty()) {
                if (ddmFieldViewPages.currentItem < size) {
                    ddmFieldViewPages.currentItem = getNextEnabledPage().toInt()

                    backButton.visibility = View.VISIBLE
                    multipageProgress.progress = getFormProgress()

                    if (ddmFieldViewPages.currentItem == size) {
                        nextButton.text = context.getString(R.string.submit)
                    }
                } else {
                    submit()
                }
            } else {
                highLightInvalidFields(invalidFields, true)
            }
        }

        ReactiveNetwork
            .observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToInternet ->
                if (!isConnectedToInternet) {
                    showConnectivityErrorMessage(R.color.orange, R.string.cant_load_some_fields_offline)
                }
            }
    }

    private fun getPreviousEnabledPage(): Number {
        (ddmFieldViewPages.adapter as DDMPagerAdapter).let {
            val dropPages = it.pages.size - ddmFieldViewPages.currentItem

            return it.pages.dropLast(dropPages).indexOfLast { it.isEnabled }
        }
    }

    private fun getNextEnabledPage(): Number {
        (ddmFieldViewPages.adapter as DDMPagerAdapter).let {
            val dropPages = ddmFieldViewPages.currentItem + 1

            return it.pages.drop(dropPages).indexOfFirst { it.isEnabled } + dropPages
        }
    }

    private fun getInvalidFields(): Map<Field<*>, String> {
        val page = formInstance.ddmStructure.pages[ddmFieldViewPages.currentItem]

        return page.fields.filter { !it.isValid }.associateBy({ it }, { "Error Msg Goes Here" })
    }

    /*
     * XXX Copied code
     */
    private fun highLightInvalidFields(fieldResults: Map<Field<*>, String>, autoscroll: Boolean) {
        var scrolled = false

        val fieldsContainerView = ddmFieldViewPages.findViewWithTag<LinearLayout>(ddmFieldViewPages.currentItem)

        for (i in 0 until fieldsContainerView.childCount) {
            val fieldView = fieldsContainerView.getChildAt(i)
            val fieldViewModel = fieldView as? DDLFieldViewModel<*>

            fieldViewModel?.let {

                fieldResults[fieldViewModel.field]?.let {

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

    fun submit(isDraft: Boolean = false) {
        if (!AndroidUtil.isConnected(context.applicationContext) && !isDraft) {
            showConnectivityErrorMessage()
            return
        }

        val thing = thing ?: throw Exception("No thing found")
        val fields = formInstance.ddmStructure.fields

        submitService.submit(thing, currentRecordThing, fields, isDraft, { recordThing ->
            currentRecordThing = recordThing

            if (!isDraft) {
                formInstance.ddmStructure.successPage?.let {
                    showSuccessPage(formInstance.ddmStructure.successPage)
                } ?: run {
                    showSuccessMessage()
                }
            }
        }, { exception ->
            LiferayLogger.e(exception.message)

            if (!isDraft) {
                showErrorMessage(exception)
            }
        })
    }

    private fun getFormProgress(): Int {
        return (ddmFieldViewPages.currentItem + 1) * 100 / formInstance.ddmStructure.pages.size
    }

    private fun showConnectivityErrorMessage(@ColorRes backgroundColorResource: Int = R.color.midGray,
        @StringRes messageStringRes: Int = R.string.no_internet_connection) {

        val icon = R.drawable.default_error_icon
        val message = context.getString(messageStringRes)
        val backgroundColor = ContextCompat.getColor(context, backgroundColorResource)
        val textColor = ContextCompat.getColor(context, android.R.color.white)

        AndroidUtil.showCustomSnackbar(this, message, Snackbar.LENGTH_LONG, backgroundColor, textColor, icon)
    }

    private fun showErrorMessage(exception: Exception?) {
        val icon = R.drawable.default_error_icon
        val backgroundColor = ContextCompat.getColor(context, R.color.lightRed)
        val textColor = ContextCompat.getColor(context, android.R.color.white)
        val message =
            exception?.message ?: context.getString(R.string.submit_failed_contact_administrator)

        AndroidUtil.showCustomSnackbar(
            this, message, Snackbar.LENGTH_LONG, backgroundColor, textColor, icon)
    }

    private fun showSuccessMessage() {
        val icon = R.drawable.default_check_icon
        val backgroundColor = ContextCompat.getColor(context, R.color.success_green_default)
        val textColor = ContextCompat.getColor(context, android.R.color.white)
        val message = context.getString(R.string.information_successfully_received)

        AndroidUtil.showCustomSnackbar(
            this, message, Snackbar.LENGTH_LONG, backgroundColor, textColor, icon)
    }

    private fun showSuccessPage(successPage: SuccessPage) {
        val intent = Intent(context, SuccessPageActivity::class.java)
        intent.putExtra("successPage", successPage)
        context.startActivity(intent)
    }

    private fun evaluateContext() {
        val thing = thing ?: throw Exception("No thing found")
        val fields = formInstance.ddmStructure.fields

        evaluateService.evaluateContext(thing, fields, {
            val formContext = FormContext.converter(it)

            updatePages(formContext)
            updateFields(formContext)
        }, {
            showErrorMessage(it)
        })
    }

    private fun updateFields(fieldValues: List<FieldValue>) {
        val fieldsContainerView =
                ddmFieldViewPages.findViewWithTag<LinearLayout>(ddmFieldViewPages.currentItem)

        val fieldsMap = formInstance.ddmStructure.fields.map {
            Pair(it.name, it)
        }.toMap()

        fieldValues.forEach { fieldValue ->
            val field = fieldsMap[fieldValue.name]

            field?.also {
                field.setCurrentStringValue(fieldValue.value as String)

                val fieldView = fieldsContainerView?.findViewWithTag<View>(field)
                val fieldViewModel = fieldView as? DDLFieldViewModel<*>

                fieldViewModel?.refresh()
            }
        }
    }

    private fun updatePages(formContext: FormContext) {
        (ddmFieldViewPages.adapter as DDMPagerAdapter).let {
            for ((index, page) in it.pages.withIndex()) {
                page.isEnabled = formContext.pages[index].isEnabled
            }
        }
    }

    private fun updateFields(formContext: FormContext) {
        val fieldContexts = formContext.pages.flatMap(FormContextPage::fields)

        val fieldsMap = formInstance.ddmStructure.fields.map {
            Pair(it.name, it)
        }.toMap()

        fieldContexts.forEach { fieldContext ->
            val field = fieldsMap[fieldContext.name]

            field?.let {
                updateFieldModel(fieldContext, field)
                updateFieldView(fieldContext, field)
            }
        }
    }

    private fun updateFieldModel(fieldContext: FieldContext, field: Field<*>) {
        (field as? OptionsField<*>)?.let { optionsField ->
            setOptions(fieldContext, optionsField)
        }

        setValue(fieldContext, field)

        field.isReadOnly = fieldContext.isReadOnly ?: field.isReadOnly
        field.isRequired = fieldContext.isRequired ?: field.isRequired
    }

    private fun updateFieldView(fieldContext: FieldContext, field: Field<*>) {
        val fieldsContainerView =
            ddmFieldViewPages.findViewWithTag<LinearLayout>(ddmFieldViewPages.currentItem)

        val fieldView = fieldsContainerView?.findViewWithTag<View>(field)

        fieldView?.let {
            val fieldViewModel = fieldView as? DDLFieldViewModel<*>
            val fieldTextView = fieldView as? BaseDDLFieldTextView<*>

            setVisibility(fieldContext, fieldView)

            fieldTextView?.setupFieldLayout()
            fieldViewModel?.refresh()

            if (dirtyFieldNames.contains(field.name)) {
                val isValid = fieldContext.isValid ?: true

                field.lastValidationResult = isValid
                fieldViewModel?.onPostValidation(isValid)
            }
        }
    }

    private fun setOptions(fieldContext: FieldContext, optionsField: OptionsField<*>) {
        val availableOptions = fieldContext.options as? List<Map<String, String>>

        availableOptions?.let {
            optionsField.availableOptions = ArrayList(availableOptions.map { Option(it) })
        }
    }

    private fun setValue(fieldContext: FieldContext, field: Field<*>) {
        if (fieldContext.isValueChanged == true) {
            addToDirtyFields(field)

            fieldContext.value?.toString()?.let {
                field.setCurrentStringValue(it)
            }
        }
    }

    private fun setVisibility(fieldContext: FieldContext, fieldView: View) {
        if (fieldContext.isVisible != false) {
            fieldView.visibility = View.VISIBLE
        } else {
            fieldView.visibility = View.GONE
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscription?.unsubscribe()
    }

    private fun onFieldValueChanged(field: Field<*>) {
        addToDirtyFields(field)

        if (!AndroidUtil.isConnected(context.applicationContext)) {
            showConnectivityErrorMessage(R.color.orange, R.string.cant_load_some_fields_offline)

            return
        }

        submit(true)

        if (field.hasFormRules()) {
            evaluateContext()
        }
    }

    private fun addToDirtyFields(field: Field<*>) {
        if (!dirtyFieldNames.contains(field.name)) {
            dirtyFieldNames.add(field.name)
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
            Field.EditorType.PARAGRAPH to "ddmfield_paragrah",
            Field.EditorType.DOCUMENT to "ddlfield_document",
            Field.EditorType.GRID to "ddmfield_grid",
            Field.EditorType.GEO to "ddlfield_geo",
            Field.EditorType.REPEATABLE to "ddmfield_repeatable"
        )
    }
}
