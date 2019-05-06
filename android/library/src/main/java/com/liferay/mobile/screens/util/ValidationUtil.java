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

package com.liferay.mobile.screens.util;

import com.liferay.mobile.screens.ddl.form.util.FormFieldKeys;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Albertinin Mourato
 */
public class ValidationUtil {

    public static Map<String, String> getValidationFromAttributes(Map<String, Object> attributes) {
        if (attributes.get(FormFieldKeys.VALIDATION_KEY) instanceof Map) {
            return (Map<String, String>) attributes.get(FormFieldKeys.VALIDATION_KEY);
        } else {
            return new HashMap<>();
        }
    }
}
