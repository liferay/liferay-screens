package com.liferay.mobile.screens.ddm.form.util

import com.liferay.apio.consumer.ApioException
import com.liferay.apio.consumer.fetch
import com.liferay.apio.consumer.model.Operation
import com.liferay.apio.consumer.model.Relation
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.getOperation
import com.liferay.apio.consumer.performOperation
import com.liferay.apio.consumer.performParseOperation
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.extension.toJsonMap
import com.liferay.mobile.screens.ddm.form.extension.toThing
import com.liferay.mobile.screens.ddm.form.serializer.FieldValueSerializer
import okhttp3.HttpUrl

/**
 * @author Paulo Cruz
 */
fun submitForm(
        formThing: Thing, fields: MutableList<Field<*>>,
        currentRecordThing: Thing?, isDraft: Boolean = false,
        onSuccess: (Thing) -> Unit, onError: (Exception) -> Unit) {

    val recordsRelation = formThing.attributes["formInstanceRecords"] as? Relation

    currentRecordThing?.let {
        getThing(it.id, { thing ->
            thing.getOperation("update")?.let { operation ->
                performSubmit(thing, operation, fields, isDraft, onSuccess, onError)
            }
        }, onError)
    } ?: recordsRelation?.let {
        getThing(it.id, { thing ->
            thing.getOperation("create")?.let { operation ->
                performSubmit(thing, operation, fields, isDraft, onSuccess, onError)
            }
        }, onError)
    }
}

private fun getThing(thingId: String, onSuccess: (Thing) -> Unit, onError: (Exception) -> Unit) {
    HttpUrl.parse(thingId)?.let {
        fetch(it) {
            val (thing, exception) = it

            thing?.let {
                onSuccess(it)
            } ?: run {
                onError(exception ?: ApioException("No thing found"))
            }
        }
    } ?: onError(ApioException("No thing found"))
}

private fun performSubmit(
        thing: Thing, operation: Operation, fields: MutableList<Field<*>>, isDraft: Boolean = false,
        onSuccess: (Thing) -> Unit, onError: (Exception) -> Unit) {

    performParseOperation(thing.id, operation.id, {
        mapOf(
            Pair("isDraft", isDraft),
            Pair("fieldValues", FieldValueSerializer.serialize(fields))
        )
    }) {
        val (resultThing, exception) = it

        resultThing?.let(onSuccess) ?: exception?.let(onError)
    }
}