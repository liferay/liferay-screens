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

package com.liferay.mobile.screens.ddl.model;

import java.util.HashMap;
import java.util.Locale;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * @author Marcelo Mello
 */
@RunWith(Enclosed.class)
public class DocumentFieldTest {

    private static final Locale brazilianLocale = new Locale("pt", "BR");
    private static final Locale usLocale = new Locale("en", "US");

    private static final String JSON_STRING =
        "{\"groupId\":18936,\"uuid\":\"uuid\",\"title\":\"title\"," + "\"version\":\"version\"}";

    private static final String URL = "http://someurl/with/paths";

    @RunWith(RobolectricTestRunner.class)
    public static class WhenConvertingFromString {

        @Test
        public void shouldReturnNullWhenNullStringIsSupplied() {
            DocumentField field = new DocumentField(new HashMap<String, Object>(), brazilianLocale, usLocale);

            assertNull(field.convertFromString(null));
        }

        @Test
        public void shouldReturnNullWhenEmptyStringIsSupplied() {
            DocumentField field = new DocumentField(new HashMap<String, Object>(), brazilianLocale, usLocale);

            assertNull(field.convertFromString(""));
        }

        @Test
        public void shouldReturnNullWhenEmptyJsonStringIsSupplied() {
            DocumentField field = new DocumentField(new HashMap<String, Object>(), brazilianLocale, usLocale);

            assertNull(field.convertFromString("{}"));
        }

        @Test
        public void shouldReturnNullWhenInvalidJsonStringIsSupplied() {
            DocumentField field = new DocumentField(new HashMap<String, Object>(), brazilianLocale, usLocale);

            assertNull(field.convertFromString("{;:-/}"));
        }

        @Test
        public void shouldReturnValueWhenUrlStringIsSupplied() {
            DocumentField field = new DocumentField(new HashMap<String, Object>(), brazilianLocale, usLocale);

            DocumentFile documentFile = field.convertFromString(URL);

            assertNotNull(field);
            assertNotNull(documentFile);
            assertEquals(documentFile.toData(), URL);
        }

        @Test
        public void shouldReturnValueWhenValidJsonStringIsSupplied() {
            DocumentField field = new DocumentField(new HashMap<String, Object>(), brazilianLocale, usLocale);

            DocumentFile documentFile = field.convertFromString(JSON_STRING);

            assertNotNull(field);
            assertNotNull(documentFile);
            assertEquals(documentFile.toData(), JSON_STRING);
        }
    }
}
