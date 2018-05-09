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
import com.liferay.mobile.sdk.apio.model.Relation
import com.liferay.mobile.sdk.apio.model.Thing
import com.liferay.mobile.sdk.apio.model.get
import java.util.*

/**
 * @author Paulo Cruz
 * @author Sarai Díaz García
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

            val name = it["name"] as String
            val description = it["description"] as String

            val structureRelation = it.attributes["structure"] as Relation

            val structureDescription = structureRelation["description"] as String
            val structureName = structureRelation["name"] as String

            val textFieldAttributes = mapOf(
                "dataType" to Field.DataType.STRING.value,
                "readOnly" to false,
                "type" to Field.EditorType.TEXT.value,
                "required" to false,
                "showLabel" to true,
                "repeatable" to false,
                "label" to "TextField single",
                "name" to "TextFieldSingle",
                "tip" to "TextField hint",
                "placeHolder" to ""
            )

            val textField = StringField(textFieldAttributes, Locale.ENGLISH, Locale.ENGLISH)

            val textAreaAttributes = mapOf(
                "dataType" to Field.DataType.STRING.value,
                "readOnly" to false,
                "type" to Field.EditorType.TEXT_AREA.value,
                "required" to false,
                "showLabel" to true,
                "repeatable" to false,
                "label" to "TextField multiple",
                "name" to "TextFieldMultiple",
                "tip" to "TextField multiple hint",
                "placeHolder" to ""
            )

            val textAreaField = StringField(textAreaAttributes, Locale.ENGLISH, Locale.ENGLISH)

            val checkBoxMultipleAttributes = mapOf(
                "dataType" to Field.DataType.STRING.value,
                "readOnly" to false,
                "type" to Field.EditorType.CHECKBOX_MULTIPLE.value,
                "required" to false,
                "showLabel" to true,
                "repeatable" to false,
                "label" to "CheckboxField multiple",
                "name" to "CheckboxFieldMultiple",
                "tip" to "Checkbox multiple hint",
                "showAsSwitcher" to false,
                "placeHolder" to ""
            )

            val checkBoxMultipleField = CheckboxMultipleField(checkBoxMultipleAttributes, Locale.ENGLISH, Locale.ENGLISH)

            val dateAttributes = mapOf(
                "dataType" to Field.DataType.DATE.value,
                "readOnly" to false,
                "type" to Field.EditorType.DATE.value,
                "required" to false,
                "showLabel" to true,
                "repeatable" to false,
                "label" to "DateField",
                "name" to "DateField",
                "tip" to "DateField hint",
                "placeHolder" to ""
            )

            val dateField = DateField(dateAttributes, Locale.ENGLISH, Locale.ENGLISH)

            val numberAttributes = mapOf(
                "dataType" to Field.DataType.NUMBER.value,
                "readOnly" to false,
                "type" to Field.EditorType.INTEGER.value,
                "required" to false,
                "showLabel" to true,
                "repeatable" to false,
                "label" to "Number",
                "name" to "Number",
                "tip" to "Number hint",
                "placeHolder" to ""
            )

            val numberField = NumberField(numberAttributes, Locale.ENGLISH, Locale.ENGLISH)

            val fields = ArrayList<Field<*>>()
            fields.add(textField)
            fields.add(textAreaField)
            fields.add(checkBoxMultipleField)
            fields.add(dateField)
            fields.add(numberField)

            val ddmStructure = DDMStructure(structureName, structureDescription, fields)

            FormInstance(name, description, ddmStructure)
        }
    }
}
