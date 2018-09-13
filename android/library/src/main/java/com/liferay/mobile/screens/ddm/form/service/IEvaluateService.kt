package com.liferay.mobile.screens.ddm.form.service

import com.liferay.apio.consumer.model.Thing
import com.liferay.mobile.screens.ddl.model.Field


/**
 * @author Paulo Cruz
 */
interface IEvaluateService {

     fun evaluateContext(thingId: String, operationId: String,
                         fields: MutableList<Field<*>>, onSuccess: (Thing) -> Unit,
                         onError: (Exception) -> Unit)

}