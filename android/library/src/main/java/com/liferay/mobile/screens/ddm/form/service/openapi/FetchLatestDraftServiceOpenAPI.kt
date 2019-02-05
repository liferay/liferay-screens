/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddm.form.service.openapi

import com.liferay.mobile.screens.context.LiferayScreensContext
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord
import com.liferay.mobile.screens.ddm.form.service.FetchLatestDraftService
import com.liferay.mobile.screens.util.AndroidUtil
import org.json.JSONObject

/**
 * @author Victor Oliveira
 */
class FetchLatestDraftServiceOpenAPI : FetchLatestDraftService {
	override fun fetchLatestDraft(formInstance: FormInstance, onSuccess: (FormInstanceRecord) -> Unit,
		onError: (Throwable) -> Unit) {
		val formInstanceJSONStr = AndroidUtil.assetJSONFile("formInstanceRecord.json",
			LiferayScreensContext.getContext())
		val formInstanceRecord = FormInstanceRecord.converter(JSONObject(formInstanceJSONStr))

		onSuccess(formInstanceRecord)
	}
}