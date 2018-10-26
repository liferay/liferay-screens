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

import com.liferay.mobile.screens.ddl.JsonParser;
import java.util.List;
import java.util.Locale;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.xml.sax.SAXException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class StringField70Test {

    private static StringField createStringField(Boolean required) {

        String json = "{\"availableLanguageIds\": [ \"en_US\"], "
            + "\"defaultLanguageId\": \"en_US\", "
            + "\"fields\": [ "
            + "{"
            + "            \"label\": {"
            + "                \"en_US\": \"Title\""
            + "            },"
            + "            \"predefinedValue\": {"
            + "                \"en_US\": \"\""
            + "            },"
            + "            \"style\": {"
            + "                \"en_US\": \"\""
            + "            },"
            + "            \"tip\": {"
            + "                \"en_US\": \"\""
            + "            },"
            + "            \"dataType\": \"string\","
            + "            \"indexType\": \"keyword\","
            + "            \"localizable\": true,"
            + "            \"name\": \"Title\","
            + "            \"readOnly\": false,"
            + "            \"repeatable\": false,"
            + "            \"required\": "
            + required
            + ","
            + "            \"showLabel\": true,"
            + "            \"type\": \"text\""
            + "        }"
            + "]}";

        List<Field> resultList = new JsonParser().parse(json, new Locale("en", "US"));

        return (StringField) resultList.get(0);
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenParsingXSD {
        @Test
        public void shouldReturnStringFieldObject() throws Exception {

            String json = "{\"availableLanguageIds\": [ \"en_US\"], "
                + "\"defaultLanguageId\": \"en_US\", "
                + "\"fields\": [ "
                + "{"
                + "            \"label\": {"
                + "                \"en_US\": \"Title\""
                + "            },"
                + "            \"predefinedValue\": {"
                + "                \"en_US\": \"default text\""
                + "            },"
                + "            \"style\": {"
                + "                \"en_US\": \"\""
                + "            },"
                + "            \"tip\": {"
                + "                \"en_US\": \"\""
                + "            },"
                + "            \"dataType\": \"string\","
                + "            \"indexType\": \"keyword\","
                + "            \"localizable\": true,"
                + "            \"name\": \"A_Text\","
                + "            \"readOnly\": false,"
                + "            \"repeatable\": false,"
                + "            \"required\": true,"
                + "            \"showLabel\": true,"
                + "            \"type\": \"text\""
                + "        }"
                + "]}";

            List<Field> resultList = new JsonParser().parse(json, new Locale("en", "US"));

            assertNotNull(resultList);
            assertEquals(1, resultList.size());

            Field resultField = resultList.get(0);
            assertTrue(resultField instanceof StringField);
            StringField stringField = (StringField) resultField;

            assertEquals(Field.DataType.STRING.getValue(), stringField.getDataType().getValue());
            assertEquals(Field.EditorType.TEXT.getValue(), stringField.getEditorType().getValue());
            assertEquals("A_Text", stringField.getName());
            assertEquals("default text", stringField.getCurrentValue());
            assertEquals(stringField.getCurrentValue(), stringField.getPredefinedValue());
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenValidatingAndRequired {

        @Test
        public void shouldReturnFalseWhenHasOnlyBlankSpaces() throws SAXException {
            StringField field = createStringField(true);

            field.setCurrentValue("   ");

            assertFalse(field.isValid());
        }

        @Test
        public void shouldReturnFalseWhenValueIsNull() throws SAXException {
            StringField field = createStringField(true);

            field.setCurrentValue(null);

            assertFalse(field.isValid());
        }

        @Test
        public void shouldReturnFalseWhenValueIsEmpty() throws SAXException {
            StringField field = createStringField(true);

            field.setCurrentValue("");

            assertFalse(field.isValid());
        }

        @Test
        public void shouldReturnTrueWhenValueIsSet() throws SAXException {
            StringField field = createStringField(true);

            field.setCurrentValue("abc");

            assertTrue(field.isValid());
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenValidatingAndNotRequired {

        @Test
        public void shouldReturnTrueWhenHasOnlyBlankSpaces() throws SAXException {
            StringField field = createStringField(false);

            field.setCurrentValue("   ");

            assertTrue(field.isValid());
        }

        @Test
        public void shouldReturnTrueWhenValueIsNull() throws SAXException {
            StringField field = createStringField(false);

            field.setCurrentValue(null);

            assertTrue(field.isValid());
        }

        @Test
        public void shouldReturnTrueWhenValueIsEmpty() throws SAXException {
            StringField field = createStringField(false);

            field.setCurrentValue("");

            assertTrue(field.isValid());
        }

        @Test
        public void shouldReturnTrueWhenValueIsSet() throws SAXException {
            StringField field = createStringField(false);

            field.setCurrentValue("abc");

            assertTrue(field.isValid());
        }
    }
}