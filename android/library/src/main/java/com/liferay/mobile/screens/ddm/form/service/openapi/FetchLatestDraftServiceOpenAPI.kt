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

import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord
import com.liferay.mobile.screens.ddm.form.service.BaseService
import com.liferay.mobile.screens.ddm.form.service.FetchLatestDraftService
import org.json.JSONObject
import rx.Observable

/**
 * @author Victor Oliveira
 */
class FetchLatestDraftServiceOpenAPI(
	private val serverUrl: String) : BaseService<FormInstanceRecord>(), FetchLatestDraftService {

	override fun fetchLatestDraft(formInstanceId: Long): Observable<FormInstanceRecord> {
		val url = (if (!serverUrl.endsWith('/')) "$serverUrl/" else serverUrl)
			.plus(FormServiceOpenAPI.OPEN_API_FORM_PATH)
			.plus("/forms/")
			.plus(formInstanceId)
			.plus("/form-records/by-latest-draft")

		return execute(url) {
			val body = it.body().string()
			FormInstanceRecord.converter(JSONObject(body))
		}
	}
}