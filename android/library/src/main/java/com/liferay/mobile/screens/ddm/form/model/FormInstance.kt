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

import com.liferay.apio.consumer.model.Relation
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.model.DDMStructure
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import java.util.*

/**
 * @author Paulo Cruz
 * @author Sarai Díaz García
 * @author Victor Oliveira
 */
data class FormInstance @JvmOverloads constructor(
    val name: String,
    val description: String?,
    val defaultLanguage: String,
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

            val name = it["name"] as String

            val description = it["description"] as? String

            val defaultLanguage = it["defaultLanguage"] as String

            val locale = Locale(defaultLanguage)

            val ddmStructure = (it["structure"] as Relation).let {
                getStructure(it, locale)
            }

            FormInstance(name, description, defaultLanguage, ddmStructure)
        }

        private fun getStructure(relation: Relation, locale: Locale): DDMStructure {

            val name = relation["name"] as String

            val description = relation["description"] as? String

            val pages = (relation["formPages"] as Map<String, Any>).let {
                getPages(it, locale)
            }

            val successPage = relation["successPage"]?.let {
                val successMap = it as Map<*, *>
                val enabled = successMap["isEnabled"] as Boolean
                var headline = ""
                var text = ""

                if (enabled) {
                    headline = successMap["headline"] as String
                    text = successMap["text"] as String
                }
                SuccessPage(headline, text, enabled)
            }

            return DDMStructure(name, description, pages, successPage)
        }

        private fun getPages(mapper: Map<String, Any>, locale: Locale): List<FormPage> {

            return (mapper["member"] as List<Map<String, Any>>).mapTo(mutableListOf(), {

                val headlinePage = it["headline"] as? String ?: ""
                val textPage = it["text"] as? String ?: ""
                val fields = (it["fields"] as Map<String, Any>).let {
                    getFields(it, locale)
                }.toMutableList()

                FormPage(headlinePage, textPage, fields)
            })
        }

        private fun getFields(map: Map<String, Any>, locale: Locale): List<Field<*>> {

            if (map["totalItems"] as Double <= 0) {
                return mutableListOf()
            }

            return (map["member"] as List<Map<String, Any>>).mapTo(mutableListOf(), {

                val dataType = it["dataType"] as? String
                val options = (it["options"] as? Map<String, Any>)?.let {
                    it["member"] as? List<Map<String, Any>>
                }

                val attributes = it.toMutableMap()
                attributes.remove("options")

                attributes["isDDM"] = true
                options?.let {
                    attributes["options"] = options
                }

                val fieldDataType = Field.DataType.assignDataTypeFromString(dataType)
                fieldDataType.createField(attributes, locale, locale)
            })
        }
    }
}
