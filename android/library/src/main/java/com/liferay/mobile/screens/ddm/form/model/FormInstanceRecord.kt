package com.liferay.mobile.screens.ddm.form.model

import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get

/**
 * @author Paulo Cruz
 */
typealias FieldValue = MutableMap<String, String>

class FormInstanceRecord @JvmOverloads constructor(
        val id: String,
        val fieldValues: Map<String, String>) {

    companion object {
        val converter: (Thing) -> FormInstanceRecord = { it: Thing ->

            val id = it.id

            val fieldValues =
                (it["fieldValues"] as? List<FieldValue>)?.reduce { acc, fieldValue ->
                        acc.apply { putFieldValue(fieldValue) }
                    } ?: emptyMap<String, String>()

            FormInstanceRecord(id, fieldValues)
        }

        private fun MutableMap<String, String>.putFieldValue(
                mutableMap: FieldValue) {

            val name = mutableMap["name"] ?: ""
            val value = mutableMap["value"] ?: ""

            this[name] = value
        }
    }
}