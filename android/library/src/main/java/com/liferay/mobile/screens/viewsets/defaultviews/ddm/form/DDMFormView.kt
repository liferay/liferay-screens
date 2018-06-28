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
import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_SHORT
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.liferay.apio.consumer.delegates.converter
import com.liferay.apio.consumer.fetch
import com.liferay.apio.consumer.model.Relation
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.getOperation
import com.liferay.apio.consumer.performOperation
import com.liferay.apio.consumer.performParseOperation
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel
import com.liferay.mobile.screens.ddl.model.*
import com.liferay.mobile.screens.ddm.form.OptionSerializer
import com.liferay.mobile.screens.ddm.form.model.*
import com.liferay.mobile.screens.ddm.form.view.SuccessPageActivity
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.events.Event
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.screens.util.EventBusUtil
import com.liferay.mobile.screens.util.LiferayLogger
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.BaseDDLFieldTextView
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLDocumentFieldView
import com.liferay.mobile.screens.viewsets.defaultviews.util.ThemeUtil
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.pager.WrapContentViewPager
import com.squareup.otto.Subscribe
import okhttp3.HttpUrl
import org.jetbrains.anko.childrenSequence
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Paulo Cruz
 * @author Victor Oliveira
 */
class DDMFormView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseView,
    ScrollView(context, attrs, defStyleAttr), DDLDocumentFieldView.UploadListener {

    private val ddmFieldViewPages by bindNonNull<WrapContentViewPager>(R.id.ddmfields_container)
    private val backButton by bindNonNull<Button>(R.id.liferay_form_back)
    private val nextButton by bindNonNull<Button>(R.id.liferay_form_submit)
    private var gson = GsonBuilder().registerTypeAdapter(Option::class.java, OptionSerializer()).create()
    private var formInstance: FormInstance? = null

    val layoutIds = mutableMapOf<Field.EditorType, Int>()

    override var screenlet: ThingScreenlet? = null
    override var thing: Thing? by converter<FormInstance> {
        formInstance = it
        val ddmPagerAdapter = DDMPagerAdapter(it.ddmStructure.pages, this)
        ddmFieldViewPages.adapter = ddmPagerAdapter

        if (it.ddmStructure.pages.size == 1)
            nextButton.text = context.getString(R.string.submit)

        formInstance?.fields

        evaluateContext(thing)
    }

    init {
        val themeName = ThemeUtil.getLayoutTheme(context)

        for (pair in availableFields) {
            val fieldType = pair.first
            val fieldNamePrefix = pair.second

            layoutIds[fieldType] = ThemeUtil.getLayoutIdentifier(context, fieldNamePrefix, themeName)
        }
    }

    private fun getIdentifier(fieldNamePrefix: String, themeName: String): Int {
        return context.resources.getIdentifier(
            "${fieldNamePrefix}_$themeName", "layout", context.packageName)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        backButton.setOnClickListener({
            if (ddmFieldViewPages.currentItem >= 1) {
                ddmFieldViewPages.currentItem = getPreviousEnabledPage().toInt()

                nextButton.text = context.getString(R.string.next)
                if (ddmFieldViewPages.currentItem == 0) {
                    backButton.isEnabled = false
                }
            }
        })

        nextButton.setOnClickListener({
            val size = ddmFieldViewPages.adapter.count - 1
            val invalidFields = getInvalidFields()

            if (invalidFields.isEmpty()) {
                if (ddmFieldViewPages.currentItem < size) {
                    ddmFieldViewPages.currentItem = getNextEnabledPage().toInt()

                    backButton.isEnabled = true
                    if (ddmFieldViewPages.currentItem == size) {
                        nextButton.text = context.getString(R.string.submit)
                    }
                } else {
                    submit()
                }
            } else {
                highLightInvalidFields(invalidFields, true)

                Snackbar.make(this, "Invalid", LENGTH_SHORT).show()
            }
        })
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
        var invalidResults = mapOf<Field<*>, String>()
        val page = formInstance?.ddmStructure?.pages?.get(ddmFieldViewPages.currentItem)

        page?.let {
            invalidResults = it.fields.filter { !it.isValid }.associateBy({ it }, { "Error Msg Goes Here" })
        }

        return invalidResults
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
                        smoothScrollTo(0, fieldView.top)
                        scrolled = true
                    }
                }
            }
        }
    }

    fun submit(isDraft: Boolean = false) {
        val formInstanceRecords = thing?.attributes?.get("formInstanceRecords") as? Relation

        if (formInstanceRecords != null) {
            HttpUrl.parse(formInstanceRecords.id)?.let {
                fetch(it) {
                    val thing = it.component1()
                    if (thing != null) {
                        performSubmitOperation(thing, isDraft)
                    }
                }
            }
        } else {
            LiferayLogger.e("Can't submit")
        }
    }

    private fun evaluateContext(thing: Thing?) {
        val operation = thing!!.getOperation("evaluate-context")

        operation?.let {
            performParseOperation(thing.id, it.id, {
                val values = mutableMapOf<String, Any>()

                val fieldsList = formInstance!!.fields.map {
                    mapOf("name" to it.name, "value" to it.currentValue)
                }

                val fieldValues = gson.toJson(fieldsList)

                values["fieldValues"] = fieldValues

                values
            }) {
                val (thing, exception) = it
                val message = ""

                thing?.let {
                    val formContext = FormContext.converter(it)

                    updatePages(formContext)
                    updateFields(formContext)
                } ?: exception?.let {
                    val message = it.message ?: "Unknown Error"

                    Snackbar.make(this, message, LENGTH_SHORT).show()
                } ?: LiferayLogger.d(message)
            }
        }
    }

    private fun updatePages(formContext: FormContext) {
        (ddmFieldViewPages.adapter as DDMPagerAdapter)?.let {
            for ((index, page) in it.pages.withIndex()) {
                page.isEnabled = formContext.pages[index].isEnabled
            }
        }
    }

    private fun updateFields(formContext: FormContext) {
        val fieldsContainerView =
            ddmFieldViewPages.findViewWithTag<LinearLayout>(ddmFieldViewPages.currentItem)

        val fieldContexts =
            formContext.pages.flatMap(FormContextPage::fields).map { Pair(it.name, it) }.toMap()

        fieldsContainerView.childrenSequence().forEach {
            val fieldView = it
            val fieldViewModel = fieldView as? DDLFieldViewModel<*>
            val fieldTextView = fieldView as? BaseDDLFieldTextView<*>

            fieldViewModel?.let {
                val field = it.field

                fieldContexts[field.name]?.let {
                    setOptions(it, fieldView)
                    setValue(it, field)
                    setVisibility(it, fieldView)

                    field.isReadOnly = it.isReadOnly ?: field.isReadOnly
                    field.isRequired = it.isRequired ?: field.isRequired

                    fieldTextView?.setupFieldLayout()
                    fieldViewModel.onPostValidation(it.isValid ?: true)
                    fieldViewModel.refresh()
                }
            }
        }
    }

    private fun setOptions(fieldContext: FieldContext, fieldViewModel: DDLFieldViewModel<*>) {
        val optionsField = fieldViewModel.field as? OptionsField<*>

        optionsField?.let {
            val availableOptions = fieldContext.options as? List<Map<String, String>>

            availableOptions?.let {
                optionsField.availableOptions = ArrayList(availableOptions.map { Option(it) })
            }
        }
    }

    private fun setValue(fieldContext: FieldContext, field: Field<*>) {
        if (fieldContext.isValueChanged == true) {
            fieldContext.value?.toString()?.let {
                when (field) {
                    is SelectableOptionsField -> setSelectableFieldValues(it, field)
                    else -> setBasicFieldValue(it, field)
                }
            }
        }
    }

    private fun setBasicFieldValue(stringValue: String, field: Field<*>) {
        field.currentValue = when (field.dataType) {
            Field.DataType.BOOLEAN -> stringValue.toBoolean()
            Field.DataType.NUMBER -> stringValue.toDouble()
            Field.DataType.DATE -> SimpleDateFormat("MMMM dd, yyyy hh:mm:ss", Locale.US).parse(stringValue)
            else -> stringValue
        }
    }

    private fun setSelectableFieldValues(stringValue: String, field: SelectableOptionsField) {
        if (stringValue.isNotEmpty()) {
            val optionValues = stringValue
                .removePrefix("[")
                .removeSuffix("]")
                .split(',')

            field.availableOptions.filter {
                optionValues.contains(it.value)
            }.forEach {
                    field.selectOption(it)
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

    private fun performSubmitOperation(thing: Thing, isDraft: Boolean = false) {
        val operation = thing.getOperation("create")

        operation?.let {
            performOperation(thing.id, it.id, {
                val values = mutableMapOf<String, Any>()

                if (!it.none { it.name == "isDraft" }) {
                    values["isDraft"] = isDraft
                }

                val fieldsList = formInstance!!
                    .fields.map {
                    val currentValue = when (it.editorType) {
                        Field.EditorType.RADIO -> (it.currentValue as? List<*>)?.get(0)
                        Field.EditorType.GRID -> (it.currentValue as? Grid)?.rawValues
                        else -> it.currentValue
                    }

                    mapOf("name" to it.name, "value" to currentValue)
                }

                val fieldValues = gson.toJson(fieldsList)

                values["fieldValues"] = fieldValues

                values
            }) {
                val (response, exception) = it

                response?.let {

                    var message = "Form Submitted"

                    if (it.isSuccessful && !isDraft) {

                        formInstance?.ddmStructure?.successPage?.let {

                            if (it.enabled) {
                                val intent = Intent(context, SuccessPageActivity::class.java)
                                intent.putExtra("successPage", it)
                                context.startActivity(intent)
                            }
                        }

                    } else {
                        message = exception?.message ?: response.message()

                        if (message.isEmpty()) message = "Unknown Error"
                    }

                    Snackbar.make(this, message, LENGTH_SHORT).show()

                } ?: LiferayLogger.d(exception?.message)
            }
        }
    }

    override fun startUploadField(field: DocumentField) {
        val operation = thing?.getOperation("upload-file-to-root-folder")

        val filePath = field.currentValue.toString()
        val fileUri = Uri.parse(filePath)

        val inputStream = context.contentResolver.openInputStream(fileUri)

        operation?.let {
            performOperation(thing!!.id, it.id, {
                val values = mutableMapOf<String, Any>()

                val randomName = UUID.randomUUID().toString()
                values["changeLog"] = ""
                values["description"] = ""
                values["name"] = randomName
                values["title"] = randomName
                values["binaryFile"] = inputStream

                values
            }) {
                val (response, exception) = it

                response?.let {
                    val json = Gson().fromJson<Map<String, Any>>(it.body()?.string(),
                        TypeToken.getParameterized(Map::class.java, String::class.java, Any::class.java).type)

                    field.setCurrentStringValue(json["@id"] as String)
                    field.moveToUploadCompleteState()
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        EventBusUtil.register(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        EventBusUtil.unregister(this)
    }

    @Subscribe
    fun onEvent(event: Event.RequestEvaluationEvent) {
        evaluateContext(thing)
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
            Field.EditorType.GEO to "ddlfield_geo"
        )
    }
}
