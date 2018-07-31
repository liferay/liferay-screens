package com.liferay.mobile.screens.ddm.form.util

import com.github.kittinunf.result.Result
import com.liferay.apio.consumer.ApioException
import com.liferay.apio.consumer.fetch
import com.liferay.apio.consumer.model.Operation
import com.liferay.apio.consumer.model.Relation
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.getOperation
import com.liferay.apio.consumer.performOperation
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
        onComplete: (Result<Thing, Exception>) -> Unit) {

    val recordsRelation = formThing.attributes["formInstanceRecords"] as? Relation

    currentRecordThing?.let {
        getThing(it.id) {
            val (thing, exception) = it

            exception?.let {
                onComplete(Result.error(exception))
            } ?: thing?.getOperation("update")?.let { operation ->
                performSubmit(thing, operation, fields, isDraft, onComplete)
            }
        }
    } ?: recordsRelation?.let {
        getThing(it.id) {
            val (thing, exception) = it

            exception?.let {
                onComplete(Result.error(exception))
            } ?: thing?.getOperation("create")?.let { operation ->
                performSubmit(thing, operation, fields, isDraft, onComplete)
            }
        }
    } ?: onComplete(Result.error(ApioException("No thing found")))
}

private fun getThing(thingId: String, onComplete: (Result<Thing, Exception>) -> Unit) {
    HttpUrl.parse(thingId)?.let {
        fetch(it) {
            onComplete(it)
        }
    } ?: onComplete(Result.error(ApioException("No thing found")))
}

private fun performSubmit(
        thing: Thing, operation: Operation, fields: MutableList<Field<*>>, isDraft: Boolean = false,
        onComplete: (Result<Thing, Exception>) -> Unit) {

    performOperation(thing.id, operation.id, {
        mapOf(
            Pair("isDraft", isDraft),
            Pair("fieldValues", FieldValueSerializer.serialize(fields))
        )
    }) {
        val (response, exception) = it

        exception?.let {
            onComplete(Result.error(exception))
        } ?:
        response?.let {
            if(response.isSuccessful) {
                response.toThing().let { thing ->
                    onComplete(Result.of(thing))
                }
            }
            else {
                response.toJsonMap()?.let { json ->
                    val error = json["title"] as? String ?: "Unable to submit form"

                    onComplete(Result.error(ApioException(error)))
                }
            }
        }
    }
}