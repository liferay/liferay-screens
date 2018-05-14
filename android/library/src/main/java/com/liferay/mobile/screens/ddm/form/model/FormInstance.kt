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
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.mobile.sdk.apio.model.Relation
import com.liferay.mobile.sdk.apio.model.Thing
import com.liferay.mobile.sdk.apio.model.get
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

        val converter: (Thing) -> FormInstance = { it: Thing ->

            val formName = it.get("name") as String
            val formDescription = it.get("description") as String
            val structure = it.get("structure") as Relation
            val jsonPages = structure.get("pages") as Map<String, Any>
            val pagesList = jsonPages["member"] as List<Map<String, Any>>
            val language = it.get("defaultLanguage") as String

            val pages = mutableListOf<FormPage>()

            for (i in 0 until pagesList.size) {
                val page = pagesList[i]

                val headlinePage = page["headline"] as String
                val textPage = page["text"] as String
                val fieldsMap = page["fields"] as Map<String, Any>
                val fieldsList = fieldsMap["member"] as List<Map<String, Any>>

                val fields = mutableListOf<Field<*>>()

                for (j in 0 until fieldsList.size) {
                    val fieldValues = fieldsList[j]

                    val isAutocomplete = fieldValues["isAutocomplete"] as? Boolean
                    val isInline = fieldValues["isInline"] as? Boolean
                    val isLocalizable = fieldValues["isLocalizable"] as? Boolean
                    val isMultiple = fieldValues["isMultiple"] as? Boolean
                    val isReadOnly = fieldValues["isReadOnly"] as? Boolean
                    val isRepeatable = fieldValues["isRepeatable"] as? Boolean
                    val isRequired = fieldValues["isRequired"] as? Boolean
                    val isShowAsSwitcher = fieldValues["isShowAsSwitcher"] as? Boolean
                    val isShowLabel = fieldValues["isShowLabel"] as? Boolean
                    val isTransient = fieldValues["isTransient"] as? Boolean
                    val label = fieldValues["label"] as? String
                    val predefinedValue = fieldValues["predefinedValue"] as? String
                    val tip = fieldValues["tip"] as? String
                    val dataType = fieldValues["dataType"] as? String
                    val additionalType = fieldValues["additionalType"] as? String
                    val name = fieldValues["name"] as? String
                    val placeholder = fieldValues["placeholder"] as? String
                    val text = fieldValues["text"] as? String

                    val attributes = mapKeysToAllValues(isAutocomplete, isInline, isLocalizable, isMultiple, isReadOnly,
                        isRepeatable, isRequired, isShowAsSwitcher, isShowLabel, isTransient, label, predefinedValue,
                        tip, dataType, additionalType, name, placeholder, text)

                    val locale = Locale(language)
                    val fieldDataType = Field.DataType.assignDataTypeFromString(dataType)
                    val field = fieldDataType.createField(attributes, locale, locale)
                    fields.add(field)
                }
                pages.add(FormPage(headlinePage, textPage, fields))
            }
            val ddmStructure = DDMStructure(formName, formDescription, pages)
            FormInstance(formName, formDescription, ddmStructure)
        }

        fun mapKeysToAllValues(isAutocomplete: Boolean?, isInline: Boolean?, isLocalizable: Boolean?,
            isMultiple: Boolean?, isReadOnly: Boolean?, isRepeatable: Boolean?, isRequired: Boolean?,
            isShowAsSwitcher: Boolean?, isShowLabel: Boolean?, isTransient: Boolean?, label: String?,
            predefinedValue: String?, tip: String?, dataType: String?, additionalType: String?,
            name: String?, placeholder: String?, text: String?): Map<String, Any?> {

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
                "dataType" to dataType,
                "type" to additionalType,
                FormFieldKeys.NAME to name,
                FormFieldKeys.PLACEHOLDER to placeholder,
                FormFieldKeys.TEXT to text
            )
        }
    }
}

