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
package com.liferay.mobile.screens.ddm;

import androidx.annotation.NonNull;
import com.liferay.mobile.screens.ddl.StringValidator;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Victor Oliveira
 */
public class StringValidatorTest {

    @Test
    public void testNotContains() {
        Map<String, String> map = getValidatorMap("NOT(contains(fieldName, \"myWord\"))");
        Assert.assertTrue(StringValidator.parseStringValidation(map) instanceof StringValidator.NotContainsValidation);
    }

    @Test
    public void testContains() {
        Map<String, String> map = getValidatorMap("contains(fieldName, \"myWord\")");
        Assert.assertTrue(StringValidator.parseStringValidation(map) instanceof StringValidator.ContainsValidation);
    }

    @Test
    public void testIsEmailAddress() {
        Map<String, String> map = getValidatorMap("isEmailAddress(fieldName)");
        Assert.assertTrue(StringValidator.parseStringValidation(map) instanceof StringValidator.IsEmailValidation);
    }

    @Test
    public void testIsURL() {
        Map<String, String> map = getValidatorMap("isURL(fieldName)");
        Assert.assertTrue(StringValidator.parseStringValidation(map) instanceof StringValidator.IsUrlValidation);
    }

    @NonNull
    private Map<String, String> getValidatorMap(String expression) {
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", "Test Error");
        map.put("expression", expression);

        return map;
    }
}