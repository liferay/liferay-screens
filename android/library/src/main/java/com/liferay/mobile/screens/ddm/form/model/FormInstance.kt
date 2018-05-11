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

package com.liferay.mobile.screens.ddm.form.model

import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.model.*
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.mobile.sdk.apio.model.Thing
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * @author Paulo Cruz
 * @author Sarai Díaz García
 * @author Victor Oliveira
 */
data class FormInstance @JvmOverloads constructor(
    val name: String,
    val description: String,
    val ddmStructure: DDMStructure,
    val isRequiredAuthentication: Boolean = false,
    val isRequiredCaptcha: Boolean = false,
    val redirectURL: String? = null) {

    companion object {
        val DEFAULT_VIEWS: MutableMap<Scenario, Int> =
            mutableMapOf(
                Detail to R.layout.ddm_form_default
            )

        val converter: (Thing) -> Any = { it: Thing ->

            val json = JSONObject(mockedJSONStructure)
            val formName = json["name"] as String
            val formDescription = json["description"] as String
            val jsonPages = json["pages"] as JSONArray

            val pages = mutableListOf<FormPage>()

            for (i in 0 until jsonPages.length()) {
                val page = jsonPages.getJSONObject(i)

                val headlinePage = page["headline"] as String
                val textPage = page["text"] as String
                val jsonArrayFields = page["fields"] as JSONArray

                for (j in 0 until jsonArrayFields.length()) {
                    val field = jsonArrayFields.getJSONObject(i)

                    val isAutocomplete = field["isAutocomplete"] as Boolean
                    val isInline = field["isInline"] as Boolean
                    val isLocalizable = field["isLocalizable"] as Boolean
                    val isMultiple = field["isMultiple"] as Boolean
                    val isReadOnly = field["isReadOnly"] as Boolean
                    val isRepeatable = field["isRepeatable"] as Boolean
                    val isRequired = field["isRequired"] as Boolean
                    val isShowAsSwitcher = field["isShowAsSwitcher"] as Boolean
                    val isShowLabel = field["isShowLabel"] as Boolean
                    val isTransient = field["isTransient"] as Boolean
                    val label = field["label"] as String
                    val predefinedValue = field["predefinedValue"] as String
                    val tip = field["tip"] as String
                    val dataSourceType = field["dataSourceType"] as String
                    //val dataType  = field["dataType"] as String
                    val name = field["name"] as String
                    val placeholder = field["placeholder"] as String
                    val text = field["text"] as String

                    val mock = getMockMapping(isAutocomplete, isInline, isLocalizable, isMultiple, isReadOnly,
                        isRepeatable, isRequired, isShowAsSwitcher, isShowLabel, isTransient, label,
                        predefinedValue, tip, dataSourceType, Field.DataType.STRING.value, Field.EditorType.TEXT.value,
                        name, placeholder, text)

                    val stringField = StringField(mock, Locale.ENGLISH, Locale.ENGLISH)

                    val fields = mutableListOf<Field<*>>(stringField)
                    fields.addAll(getAllMockedFields())

                    pages.add(FormPage(headlinePage, textPage, fields))
                    pages.add(FormPage(headlinePage, textPage, fields))
                }
            }
            val ddmStructure = DDMStructure(formName, formDescription, pages)
            FormInstance(formName, formDescription, ddmStructure)
        }
    }
}


