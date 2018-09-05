package com.liferay.mobile.screens.ddm.form.service

import com.liferay.apio.consumer.model.Thing


/**
 * @author Paulo Cruz
 */
interface IFetchLatestDraftService {

    fun fetchLatestDraft(formThing: Thing, onSuccess: (Thing) -> Unit,
                         onError: (Exception) -> Unit = {})

}