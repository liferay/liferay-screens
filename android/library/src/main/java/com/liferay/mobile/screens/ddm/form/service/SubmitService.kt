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

package com.liferay.mobile.screens.ddm.form.service

import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord

/**
 * @author Paulo Cruz
 */
interface SubmitService {

	fun submit(formInstance: FormInstance, currentRecordThing: FormInstanceRecord, fields: MutableList<Field<*>>,
		isDraft: Boolean = false, onSuccess: (FormInstanceRecord) -> Unit, onError: (Throwable) -> Unit)

	fun submit(formInstanceId: String, operationId: String,
		fields: MutableList<Field<*>>, isDraft: Boolean,
		onSuccess: (FormInstanceRecord) -> Unit, onError: (Throwable) -> Unit)

}
