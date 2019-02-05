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
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormContext
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.ddm.form.service.EvaluateService
import com.liferay.mobile.screens.util.AndroidUtil
import org.json.JSONObject

/**
 * @author Victor Oliveira
 */
class EvaluateServiceOpenAPI : EvaluateService {
	override fun evaluateContext(formInstance: FormInstance, fields: MutableList<Field<*>>,
		onSuccess: (formContext: FormContext) -> Unit, onError: (Throwable) -> Unit) {

		val formContextStr = AndroidUtil.assetJSONFile("formContext.json", LiferayScreensContext.getContext())
		val formContext = FormContext.converter(JSONObject(formContextStr))

		onSuccess(formContext)
	}
}