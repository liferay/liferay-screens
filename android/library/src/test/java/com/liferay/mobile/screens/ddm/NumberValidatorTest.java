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
import com.liferay.mobile.screens.ddl.NumberValidator;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Victor Oliveira
 */
public class NumberValidatorTest {

    @Test
    public void testIsGreaterThan() {
        Map<String, String> map = getValidatorMap(">");
        Assert.assertTrue(NumberValidator.parseNumberValidator(map) instanceof NumberValidator.IsGreaterThan);
    }

    @Test
    public void testIsLessThan() {
        Map<String, String> map = getValidatorMap("<");
        Assert.assertTrue(NumberValidator.parseNumberValidator(map) instanceof NumberValidator.IsLessThan);
    }

    @Test
    public void testIsGreaterThanOrEqualTo() {
        Map<String, String> map = getValidatorMap(">=");
        Assert.assertTrue(NumberValidator.parseNumberValidator(map) instanceof NumberValidator.IsGreaterThanOrEqualTo);
    }

    @Test
    public void testIsLessThanOrEqualTo() {
        Map<String, String> map = getValidatorMap("<=");
        Assert.assertTrue(NumberValidator.parseNumberValidator(map) instanceof NumberValidator.IsLessThanOrEqualTo);
    }

    @Test
    public void testIsEqualTo() {
        Map<String, String> map = getValidatorMap("==");
        Assert.assertTrue(NumberValidator.parseNumberValidator(map) instanceof NumberValidator.IsEqualTo);
    }

    @Test
    public void testUnknown() {
        Map<String, String> map = getValidatorMap("invalidOperator");
        Assert.assertTrue(NumberValidator.parseNumberValidator(map) instanceof NumberValidator.Unknown);
    }

    @NonNull
    private Map<String, String> getValidatorMap(String operator) {
        String expression = "fieldName" + operator + "100";

        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", "Test Error");
        map.put("expression", expression);
        return map;
    }
}
