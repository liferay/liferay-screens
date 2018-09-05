package com.liferay.mobile.screens.ddm.form.service

import com.liferay.apio.consumer.model.Operation
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.getOperation
import com.liferay.apio.consumer.performParseOperation
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord

/**
 * @author Paulo Cruz
 */
class APIOFetchLatestDraftService : IFetchLatestDraftService {

    override fun fetchLatestDraft(formThing: Thing, onSuccess: (Thing) -> Unit,
                                  onError: (Exception) -> Unit) {

        formThing.getOperation("fetch-latest-draft")?.let {
            performFetch(formThing, it, onSuccess, onError)
        }
    }

    private fun performFetch(thing: Thing, operation: Operation, onSuccess: (Thing) -> Unit,
                             onError: (Exception) -> Unit) {

        performParseOperation(thing.id, operation.id) {
            val (resultThing, exception) = it

            resultThing?.let(onSuccess) ?: exception?.let(onError)
        }

    }

}