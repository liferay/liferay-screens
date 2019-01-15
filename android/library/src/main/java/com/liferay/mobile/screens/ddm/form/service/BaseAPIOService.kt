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

package com.liferay.mobile.screens.ddm.form.service

import com.liferay.apio.consumer.ApioConsumer
import com.liferay.apio.consumer.configuration.AcceptLanguage
import com.liferay.apio.consumer.configuration.Authorization
import com.liferay.mobile.screens.context.SessionContext
import com.liferay.mobile.screens.util.ServiceUtil
import java.util.*

/**
 * @author Paulo Cruz
 */
abstract class BaseAPIOService(locale: Locale = Locale.getDefault()) {

	@JvmField
	val apioConsumer: ApioConsumer = ApioConsumer(*ServiceUtil.getAuthHeadersArray(), AcceptLanguage(locale))

}