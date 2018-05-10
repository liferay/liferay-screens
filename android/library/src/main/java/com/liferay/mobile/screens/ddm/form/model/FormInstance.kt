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

            val textField = StringField(
                getMockMapping(Field.DataType.STRING.value, Field.EditorType.TEXT.value),
                Locale.ENGLISH, Locale.ENGLISH)

            val textAreaField = StringField(
                getMockMapping(Field.DataType.STRING.value, Field.EditorType.TEXT_AREA.value),
                Locale.ENGLISH, Locale.ENGLISH)

            val optionData1 = mapOf("label" to "Option 1", "value" to "option1")
            val optionData2 = mapOf("label" to "Option 2", "value" to "option2")
            val availableOptionsData = listOf(optionData1, optionData2)

            val checkBoxMultipleField = CheckboxMultipleField(
                getMockMapping(Field.DataType.STRING.value, Field.EditorType.CHECKBOX_MULTIPLE.value,
                    availableOptionsData),
                Locale.ENGLISH, Locale.ENGLISH)


            val checkBoxShowAsSwitcherField = CheckboxMultipleField(
                getMockMapping(Field.DataType.STRING.value, Field.EditorType.CHECKBOX_MULTIPLE.value,
                    availableOptionsData, true),
                Locale.ENGLISH, Locale.ENGLISH)


            val dateField = DateField(
                getMockMapping(Field.DataType.DATE.value, Field.EditorType.DATE.value),
                Locale.ENGLISH, Locale.ENGLISH)

            val numberField = NumberField(
                getMockMapping(Field.DataType.NUMBER.value, Field.EditorType.INTEGER.value),
                Locale.ENGLISH, Locale.ENGLISH)

            val fields = ArrayList<Field<*>>()
            fields.add(textField)
            fields.add(textAreaField)
            fields.add(checkBoxMultipleField)
            fields.add(checkBoxShowAsSwitcherField)
            fields.add(dateField)
            fields.add(numberField)

            val ddmStructure = DDMStructure(structureName, structureDescription, fields)

            FormInstance(name, description, ddmStructure)
        }

        private fun getMockMapping(dataType: String, type: String, options: List<Map<String, String>> = listOf(),
            showAsSwitcher: Boolean = false): Map<String, Any> {
            return mapOf(
                "dataType" to dataType,
                "readOnly" to false,
                "type" to type,
                "required" to false,
                "showLabel" to true,
                "repeatable" to false,
                "label" to "Label $type",
                "name" to "Name $type",
                "tip" to "Tip $type",
                "placeHolder" to "",
                "showAsSwitcher" to showAsSwitcher,
                "options" to options
            )
        }
    }
}
