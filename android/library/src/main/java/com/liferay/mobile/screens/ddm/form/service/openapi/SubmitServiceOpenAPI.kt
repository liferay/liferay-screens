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

package com.liferay.mobile.screens.ddm.form.service.openapi

import com.liferay.mobile.screens.ddl.form.util.FormConstants
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord
import com.liferay.mobile.screens.ddm.form.serializer.FieldValueSerializer
import com.liferay.mobile.screens.ddm.form.service.BaseService
import com.liferay.mobile.screens.ddm.form.service.SubmitService
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody
import org.json.JSONObject
import rx.Observable

/**
 * @author Victor Oliveira
 */
class SubmitServiceOpenAPI(serverUrl: String) : BaseService<FormInstanceRecord>(serverUrl), SubmitService {
	override fun submit(formInstance: FormInstance, formInstanceRecord: FormInstanceRecord?,
		fields: MutableList<Field<*>>, isDraft: Boolean): Observable<FormInstanceRecord> {

		var method = POST
		var url = "${getBaseUrl()}/forms/${formInstance.id}/form-records"

		formInstanceRecord?.let {
			method = PUT
			url = "${getBaseUrl()}/form-records/${formInstanceRecord.id}"
		}

		val jsonBody = JSONObject()

		jsonBody.put("draft", isDraft)
		jsonBody.put(FormConstants.FIELD_VALUES, FieldValueSerializer.serialize(fields) { !it.isTransient })

		val requestBody = RequestBody.create(JSON_MEDIA_TYPE, jsonBody.toString())

		return execute(url, method, requestBody) {
			val body = it.body().string()
			FormInstanceRecord.converter(JSONObject(body))
		}
	}
}