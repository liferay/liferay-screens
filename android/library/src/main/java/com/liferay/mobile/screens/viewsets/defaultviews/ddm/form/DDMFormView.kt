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
import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_SHORT
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.context.LiferayScreensContext
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel
import com.liferay.mobile.screens.ddl.model.DocumentField
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.events.Event
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView

import com.liferay.mobile.screens.util.EventBusUtil
import com.liferay.mobile.screens.util.LiferayLogger
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLDocumentFieldView
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.pager.WrapContentViewPager
import com.liferay.mobile.sdk.apio.delegates.converter
import com.liferay.mobile.sdk.apio.fetch
import com.liferay.mobile.sdk.apio.model.Relation
import com.liferay.mobile.sdk.apio.model.Thing
import com.liferay.mobile.sdk.apio.model.getOperation
import com.liferay.mobile.sdk.apio.performOperation
import com.squareup.okhttp.HttpUrl
import com.squareup.otto.Subscribe
import com.squareup.okhttp.MultipartBuilder
import com.squareup.okhttp.RequestBody
import okhttp3.MultipartBody
import java.io.File
import java.net.URI
import java.util.*

/**
 * @author Paulo Cruz
 * @author Victor Oliveira
 */
class DDMFormView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseView,
    ScrollView(context, attrs, defStyleAttr), DDLDocumentFieldView.UploadListener {

    private val layoutIds = mutableMapOf<Field.EditorType, Int?>()
    private val ddmFieldViewPages by bindNonNull<WrapContentViewPager>(R.id.ddmfields_container)
    private val backButton by bindNonNull<Button>(R.id.liferay_form_back)
    private val nextButton by bindNonNull<Button>(R.id.liferay_form_submit)

    var formInstance: FormInstance? = null

    override var screenlet: ThingScreenlet? = null
    override var thing: Thing? by converter<FormInstance> {
        formInstance = it
        val ddmPagerAdapter = DDMPagerAdapter(it.ddmStructure.pages, this)
        ddmFieldViewPages.adapter = ddmPagerAdapter

        if (it.ddmStructure.pages.size == 1)
            nextButton.text = context.getString(R.string.submit)

    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        backButton.setOnClickListener({
            if (ddmFieldViewPages.currentItem >= 1) {
                ddmFieldViewPages.currentItem -= 1

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
                    ddmFieldViewPages.currentItem += 1

                    backButton.isEnabled = true
                    if (ddmFieldViewPages.currentItem == size) {
                        nextButton.text = context.getString(R.string.submit)
                    }
                } else {
                    submit()
//                    evaluateContext(thing)
                }
            } else {
                highLightInvalidFields(invalidFields, true)

                Snackbar.make(this, "Invalid", LENGTH_SHORT).show()
            }
        })
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

    fun submit() {
        val formInstanceRecords = thing?.attributes?.get("formInstanceRecords") as? Relation

        if (formInstanceRecords != null) {
            fetch(HttpUrl.parse(formInstanceRecords.id)) {
                val thing = it.component1()
                if (thing != null) {
                    performSubmitOperation(thing)
                }
            }
        } else {
            LiferayLogger.e("Can't submit")
        }
    }

    fun evaluateContext(thing: Thing?) {
        val operation = thing!!.getOperation("evaluate-context")

        operation?.let {
            performOperation(thing.id, it.id, {
                val values = mutableMapOf<String, Any>()

                val fieldsList = formInstance!!
                    .fields.map { mapOf("identifier" to "", "name" to it.name, "value" to it.currentValue) }

                val fieldValues = Gson().toJson(fieldsList)

                values["inLanguage"] = "es_ES"
                values["fieldValues"] = fieldValues

                values
            }) {
                val (response, exception) = it

                response?.let {

                    var message = "Evaluating context"

                    if (!it.isSuccessful) {
                        message = exception?.message ?: response.message()

                        if (message.isEmpty()) message = "Unknown Error"
                    }

                    Snackbar.make(this, message, LENGTH_SHORT).show()

                } ?: LiferayLogger.d(exception?.message)
            }
        }
    }

    private fun performSubmitOperation(thing: Thing) {
        val operation = thing.getOperation("create")

        operation?.let {
            performOperation(thing.id, it.id, {
                val values = mutableMapOf<String, Any>()

                if (!it.none { it.name == "isDraft" }) {
                    values["isDraft"] = false
                }

                val fieldsList = formInstance!!
                    .fields.map { mapOf("identifier" to "", "name" to it.name, "value" to it.currentValue.toString()) }

                val fieldValues = Gson().toJson(fieldsList)

                values["fieldValues"] = fieldValues

                values
            }) {
                val (response, exception) = it

                response?.let {

                    var message = "Form Submitted"

                    if (!it.isSuccessful) {
                        message = exception?.message ?: response.message()

                        if (message.isEmpty()) message = "Unknown Error"
                    }

                    Snackbar.make(this, message, LENGTH_SHORT).show()

                } ?: LiferayLogger.d(exception?.message)
            }
        }
    }

    override fun startUploadField(field: DocumentField) {
        val operation = thing?.getOperation("uploadFileToRootFolder")

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
                    val json = Gson().fromJson<Map<String, Any>>(it.body().string(), TypeToken.getParameterized(Map::class.java, String::class.java, Any::class.java).type)

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

        val DEFAULT_LAYOUT_IDS = HashMap<Field.EditorType, Int>(1)

        init {
            DEFAULT_LAYOUT_IDS[Field.EditorType.CHECKBOX] = R.layout.ddlfield_checkbox_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.CHECKBOX_MULTIPLE] = R.layout.ddmfield_checkbox_multiple_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.DATE] = R.layout.ddlfield_date_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.NUMBER] = R.layout.ddlfield_number_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.INTEGER] = R.layout.ddlfield_number_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.DECIMAL] = R.layout.ddlfield_number_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.RADIO] = R.layout.ddlfield_radio_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.TEXT] = R.layout.ddlfield_text_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.SELECT] = R.layout.ddlfield_select_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.TEXT_AREA] = R.layout.ddlfield_text_area_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.PARAGRAPH] = R.layout.ddmfield_paragrah_default
            DEFAULT_LAYOUT_IDS[Field.EditorType.DOCUMENT] = R.layout.ddlfield_document_default
            //DEFAULT_LAYOUT_IDS.put(Field.EditorType.GEO, R.layout.ddlfield_geo_default);
        }
    }
}
