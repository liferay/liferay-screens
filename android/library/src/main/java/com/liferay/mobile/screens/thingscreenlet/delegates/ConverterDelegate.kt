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

package com.liferay.mobile.screens.thingscreenlet.delegates

import com.liferay.apio.consumer.delegates.converters
import com.liferay.mobile.screens.ddm.form.model.FormContext
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord
import com.liferay.mobile.screens.thingscreenlet.model.BlogPosting
import com.liferay.mobile.screens.thingscreenlet.model.Collection
import com.liferay.mobile.screens.thingscreenlet.model.Person

class ConverterDelegate {

    companion object {
        @JvmStatic
        fun initializeConverter() {
        
            converters[BlogPosting::class.java.name] = BlogPosting.converter
            converters[Collection::class.java.name] = Collection.converter
            converters[Person::class.java.name] = Person.converter
            converters[FormInstance::class.java.name] = FormInstance.converter
            converters[FormContext::class.java.name] = FormContext.converter
            converters[FormInstanceRecord::class.java.name] = FormInstanceRecord.converter
        }
    }
}