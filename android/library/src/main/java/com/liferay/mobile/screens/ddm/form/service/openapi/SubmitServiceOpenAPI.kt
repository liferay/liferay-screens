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

import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord
import com.liferay.mobile.screens.ddm.form.service.SubmitService

/**
 * @author Victor Oliveira
 */
class SubmitServiceOpenAPI : SubmitService {
	override fun submit(formInstanceId: String, operationId: String, fields: MutableList<Field<*>>, isDraft: Boolean,
		onSuccess: (FormInstanceRecord) -> Unit, onError: (Throwable) -> Unit) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun submit(formInstance: FormInstance, currentRecordThing: FormInstanceRecord,
		fields: MutableList<Field<*>>, isDraft: Boolean, onSuccess: (FormInstanceRecord) -> Unit,
		onError: (Throwable) -> Unit) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}