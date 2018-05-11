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
import com.liferay.mobile.screens.ddl.model.DDMStructure
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddl.model.FormFieldKeys
import com.liferay.mobile.screens.ddl.model.StringField
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.mobile.sdk.apio.model.Thing
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

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

            val jsonString = "{\n" +
                "  \"description\": \"description\",\n" +
                "  \"name\": \"name\",\n" +
                "  \"pages\": [{\n" +
                "    \"headline\": \"headline\",\n" +
                "    \"text\": \"text\",\n" +
                "    \"fields\": [{\n" +
                "      \"isAutocomplete\": false,\n" +
                "      \"isInline\": false,\n" +
                "      \"isLocalizable\": false,\n" +
                "      \"isMultiple\": false,\n" +
                "      \"isReadOnly\": false,\n" +
                "      \"isRepeatable\": false,\n" +
                "      \"isRequired\": false,\n" +
                "      \"isShowAsSwitcher\": false,\n" +
                "      \"isShowLabel\": true,\n" +
                "      \"isTransient\": false,\n" +
                "      \"label\": \"label\",\n" +
                "      \"predefinedValue\": \"\",\n" +
                "      \"tip\": \"tip\",\n" +
                "      \"dataSourceType\": \"text\",\n" +
                "      \"dataType\": \"string\",\n" +
                "      \"name\": \"fields name\",\n" +
                "      \"placeholder\": \"placeholder\",\n" +
                "      \"text\": \"field text\"\n" +
                "      }]\n" +
                "  }]\n" +
                "}"

            val json = JSONObject(jsonString)
            val formName = json["name"] as String
            val formDescription = json["description"] as String
            val jsonPages = json["pages"] as JSONArray

            val pages = mutableListOf<FormPage>()

            for (i in 0..(jsonPages.length() - 1)) {
                val page = jsonPages.getJSONObject(i)

                val headlinePage = page["headline"] as String
                val textPage = page["text"] as String
                val fields = page["fields"] as JSONArray

                for (j in 0..(fields.length() - 1)) {
                    val field = fields.getJSONObject(i)

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
                    val fields2 = listOf(stringField)

                    pages.add(FormPage(headlinePage, textPage, fields2))
                }
            }
            val ddmStructure = DDMStructure(formName,formDescription, pages)
            FormInstance(formName, formDescription, ddmStructure)
        }

        private fun getMockMapping(isAutocomplete: Boolean, isInline: Boolean, isLocalizable: Boolean,
            isMultiple: Boolean, isReadOnly: Boolean, isRepeatable: Boolean, isRequired: Boolean,
            isShowAsSwitcher: Boolean, isShowLabel: Boolean, isTransient: Boolean, label: String,
            predefinedValue: String, tip: String, dataSourceType: String, dataType: String,
            type: String, name: String, placeholder: String, text: String): Map<String, Any> {

            return mapOf(
                "isAutocomplete" to isAutocomplete,
                "isInline" to isInline,
                "isLocalizable" to isLocalizable,
                "isMultiple" to isMultiple,
                FormFieldKeys.READ_ONLY to isReadOnly,
                FormFieldKeys.REPEATABLE to isRepeatable,
                FormFieldKeys.REQUIRED to isRequired,
                "isShowAsSwitcher" to isShowAsSwitcher,
                FormFieldKeys.SHOW_LABEL to isShowLabel,
                "isTransient" to isTransient,
                FormFieldKeys.LABEL to label,
                FormFieldKeys.PREDEFINED_VALUE to predefinedValue,
                FormFieldKeys.TIP to tip,
                "dataSourceType" to dataSourceType,
                "dataType" to dataType,
                "type" to type,
                FormFieldKeys.NAME to name,
                FormFieldKeys.PLACEHOLDER to placeholder,
                FormFieldKeys.TEXT to text
            )
        }
    }
}
