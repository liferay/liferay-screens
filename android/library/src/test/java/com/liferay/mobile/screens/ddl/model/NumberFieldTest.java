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
import com.liferay.mobile.screens.ddl.XSDParser;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class NumberFieldTest {

    private static final Locale spanishLocale = new Locale("es", "ES");
    private static final Locale usLocale = new Locale("en", "US");

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenConvertingFromString {

        @Test
        public void shouldReturnNullWhenNullStringIsSupplied() throws Exception {
            NumberField field = new NumberField(new HashMap<String, Object>(), spanishLocale, usLocale);

            assertNull(field.convertFromString(null));
        }

        @Test
        public void shouldReturnNullWhenEmptyStringIsSupplied() throws Exception {
            NumberField field = new NumberField(new HashMap<String, Object>(), spanishLocale, usLocale);

            assertNull(field.convertFromString(""));
        }

        @Test
        public void shouldReturnNullWhenInvalidStringIsSupplied() throws Exception {
            NumberField field = new NumberField(new HashMap<String, Object>(), spanishLocale, usLocale);

            assertNull(field.convertFromString("12a3"));
        }

        @Test
        public void shouldReturnValueWhenParsingWithAlternativeLocale() throws Exception {
            NumberField field = new NumberField(new HashMap<String, Object>(), spanishLocale, usLocale);

            assertNotNull(field.convertFromString("12,3"));
            assertNotNull(field.convertFromString("12,000"));
        }

        @Test
        public void shouldReturnLongWhenIntegerStringIsSupplied() throws Exception {
            NumberField field = new NumberField(new HashMap<String, Object>(), spanishLocale, usLocale);

            Number result = field.convertFromString("123");

            assertTrue(result instanceof Long);
            assertEquals(123L, result);
        }

        @Test
        public void shouldReturnDoubleWhenDecimalStringIsSupplied() throws Exception {
            NumberField field = new NumberField(new HashMap<String, Object>(), usLocale, spanishLocale);

            Number result = field.convertFromString("123.4");

            assertTrue(result instanceof Double);
            assertEquals(123.4, result);
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenConvertingToString {

        @Test
        public void shouldReturnNullWhenNullNumberIsSupplied() throws Exception {
            NumberField field = new NumberField(new HashMap<String, Object>(), spanishLocale, usLocale);

            assertNull(field.convertToData(null));
        }

        @Test
        public void shouldReturnIntegerStringWhenIntegerNumberIsSupplied() throws Exception {
            NumberField field = new NumberField(new HashMap<String, Object>(), spanishLocale, usLocale);

            assertEquals("123", field.convertToData(123));
            assertEquals("123", field.convertToData(123L));
        }

        @Test
        public void shouldReturnDecimalStringWhenDecimalNumberIsSupplied() throws Exception {
            NumberField field = new NumberField(new HashMap<String, Object>(), spanishLocale, usLocale);

            assertEquals("123.4", field.convertToData(123.4d));
            assertEquals("123.4", field.convertToData(123.4f));
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenConvertingToFormattedString {

        @Test
        public void shouldReturnEmptyWhenNullNumberIsSupplied() {
            NumberField field = new NumberField(new HashMap<String, Object>(), spanishLocale, usLocale);

            assertEquals("", field.convertToFormattedString(null));
        }

        @Test
        public void shouldReturnSpanishFormattedIntegerNumber() {
            NumberField field = new NumberField(new HashMap<String, Object>(), spanishLocale, usLocale);

            assertEquals("1234", field.convertToData(1234));
            assertEquals("1234", field.convertToData(1234L));
        }

        @Test
        public void shouldReturnUSFormattedIntegerNumber() {
            NumberField field = new NumberField(new HashMap<String, Object>(), usLocale, usLocale);

            assertEquals("1234", field.convertToData(1234));
            assertEquals("1234", field.convertToData(1234L));
        }

        @Test
        public void shouldReturnUSFormattedDecimalNumber() {
            NumberField field = new NumberField(new HashMap<String, Object>(), usLocale, usLocale);

            assertEquals("123.4", field.convertToData(123.4d));
            assertEquals("123.4", field.convertToData(123.4f));
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenParsingXSD {

        @Test
        public void shouldReturnFieldObjectWhenFieldIsNumber() throws Exception {
            String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
                + "<dynamic-element "
                + "dataType=\"number\" "
                + "fieldNamespace=\"ddm\" "
                + "type=\"ddm-number\" "
                + "name=\"A_Number\" > "
                + "<meta-data locale=\"en_US\"> "
                + "<entry name=\"predefinedValue\"><![CDATA[123]]></entry> "
                + "</meta-data> "
                + "</dynamic-element>"
                + "</root>";

            List<Field> resultList = new XSDParser().parse(xsd, usLocale);

            assertNotNull(resultList);
            assertEquals(1, resultList.size());

            Field resultField = resultList.get(0);
            assertTrue(resultField instanceof NumberField);
            NumberField numberField = (NumberField) resultField;

            assertEquals(Field.DataType.NUMBER.getValue(), numberField.getDataType().getValue());
            assertEquals(Field.EditorType.NUMBER.getValue(), numberField.getEditorType().getValue());
            assertEquals("A_Number", numberField.getName());

            Number result = numberField.getCurrentValue();
            assertTrue(result instanceof Long);
            assertEquals(123L, result);

            assertSame(numberField.getCurrentValue(), numberField.getPredefinedValue());
        }

        @Test
        public void shouldReturnFieldObjectWhenFieldIsInteger() throws Exception {
            String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
                + "<dynamic-element "
                + "dataType=\"integer\" "
                + "fieldNamespace=\"ddm\" "
                + "type=\"ddm-integer\" "
                + "name=\"An_Integer\" > "
                + "<meta-data locale=\"en_US\"> "
                + "<entry name=\"predefinedValue\"><![CDATA[123]]></entry> "
                + "</meta-data> "
                + "</dynamic-element>"
                + "</root>";

            List<Field> resultList = new XSDParser().parse(xsd, usLocale);

            assertNotNull(resultList);
            assertEquals(1, resultList.size());

            Field resultField = resultList.get(0);
            assertTrue(resultField instanceof NumberField);
            NumberField numberField = (NumberField) resultField;

            assertEquals(Field.DataType.NUMBER.getValue(), numberField.getDataType().getValue());
            assertEquals(Field.EditorType.INTEGER.getValue(), numberField.getEditorType().getValue());
            assertEquals("An_Integer", numberField.getName());

            Number result = numberField.getCurrentValue();
            assertTrue(result instanceof Long);
            assertEquals(123L, result);

            assertSame(numberField.getCurrentValue(), numberField.getPredefinedValue());
        }

        @Test
        public void shouldReturnFieldObjectWhenFieldIsDecimal() throws Exception {
            String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
                + "<dynamic-element "
                + "dataType=\"double\" "
                + "fieldNamespace=\"ddm\" "
                + "type=\"ddm-decimal\" "
                + "name=\"A_Decimal\" > "
                + "<meta-data locale=\"en_US\"> "
                + "<entry name=\"predefinedValue\"><![CDATA[123.4]]></entry> "
                + "</meta-data> "
                + "</dynamic-element>"
                + "</root>";

            List<Field> resultList = new XSDParser().parse(xsd, usLocale);

            assertNotNull(resultList);
            assertEquals(1, resultList.size());

            Field resultField = resultList.get(0);
            assertTrue(resultField instanceof NumberField);
            NumberField numberField = (NumberField) resultField;

            assertEquals(Field.DataType.NUMBER.getValue(), numberField.getDataType().getValue());
            assertEquals(Field.EditorType.DECIMAL.getValue(), numberField.getEditorType().getValue());
            assertEquals("A_Decimal", numberField.getName());

            Number result = numberField.getCurrentValue();
            assertTrue(result instanceof Double);
            assertEquals(123.4, result);

            assertSame(numberField.getCurrentValue(), numberField.getPredefinedValue());
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenParsingJson {

        @Test
        public void shouldReturnFieldObjectWhenFieldIsNumber() throws Exception {

            String JSON_NUMBER = "{\"availableLanguageIds\": [ \"en_US\"], "
                + "\"defaultLanguageId\": \"en_US\", "
                + "\"fields\": [ "
                + "{ "
                + "            \"label\": {"
                + "                \"en_US\": \"Number\""
                + "            },"
                + "            \"predefinedValue\": {"
                + "                \"en_US\": \"123\""
                + "            },"
                + "            \"style\": {"
                + "                \"en_US\": \"\""
                + "            },"
                + "            \"tip\": {"
                + "                \"en_US\": \"\""
                + "            },"
                + "            \"dataType\": \"number\","
                + "            \"fieldNamespace\": \"ddm\","
                + "            \"indexType\": \"keyword\","
                + "            \"localizable\": true,"
                + "            \"name\": \"A_Number\","
                + "            \"readOnly\": false,"
                + "            \"repeatable\": false,"
                + "            \"required\": false,"
                + "            \"showLabel\": true,"
                + "            \"type\": \"ddm-number\""
                + "        }"
                + "]}";

            List<Field> resultList = new JsonParser().parse(JSON_NUMBER, usLocale);

            assertNotNull(resultList);
            assertEquals(1, resultList.size());

            Field resultField = resultList.get(0);
            assertTrue(resultField instanceof NumberField);
            NumberField numberField = (NumberField) resultField;

            assertEquals(Field.DataType.NUMBER.getValue(), numberField.getDataType().getValue());
            assertEquals(Field.EditorType.NUMBER.getValue(), numberField.getEditorType().getValue());
            assertEquals("A_Number", numberField.getName());

            Number result = numberField.getCurrentValue();
            assertTrue(result instanceof Long);
            assertEquals(123L, result);

            assertSame(numberField.getCurrentValue(), numberField.getPredefinedValue());
        }

        @Test
        public void shouldReturnFieldObjectWhenFieldIsInteger() throws Exception {

            String JSON_NUMBER = "{\"availableLanguageIds\": [ \"en_US\"], "
                + "\"defaultLanguageId\": \"en_US\", "
                + "\"fields\": [ "
                + "{ "
                + "            \"label\": {"
                + "                \"en_US\": \"Number\""
                + "            },"
                + "            \"predefinedValue\": {"
                + "                \"en_US\": \"123\""
                + "            },"
                + "            \"style\": {"
                + "                \"en_US\": \"\""
                + "            },"
                + "            \"tip\": {"
                + "                \"en_US\": \"\""
                + "            },"
                + "            \"dataType\": \"number\","
                + "            \"fieldNamespace\": \"ddm\","
                + "            \"indexType\": \"keyword\","
                + "            \"localizable\": true,"
                + "            \"name\": \"An_Integer\","
                + "            \"readOnly\": false,"
                + "            \"repeatable\": false,"
                + "            \"required\": false,"
                + "            \"showLabel\": true,"
                + "            \"type\": \"ddm-integer\""
                + "        }"
                + "]}";

            List<Field> resultList = new JsonParser().parse(JSON_NUMBER, usLocale);

            assertNotNull(resultList);
            assertEquals(1, resultList.size());

            Field resultField = resultList.get(0);
            assertTrue(resultField instanceof NumberField);
            NumberField numberField = (NumberField) resultField;

            assertEquals(Field.DataType.NUMBER.getValue(), numberField.getDataType().getValue());
            assertEquals(Field.EditorType.INTEGER.getValue(), numberField.getEditorType().getValue());
            assertEquals("An_Integer", numberField.getName());

            Number result = numberField.getCurrentValue();
            assertTrue(result instanceof Long);
            assertEquals(123L, result);

            assertSame(numberField.getCurrentValue(), numberField.getPredefinedValue());
        }

        @Test
        public void shouldReturnFieldObjectWhenFieldIsDecimal() throws Exception {

            String JSON_NUMBER = "{\"availableLanguageIds\": [ \"en_US\"], "
                + "\"defaultLanguageId\": \"en_US\", "
                + "\"fields\": [ "
                + "{ "
                + "\"label\": {\n"
                + "                \"en_US\": \"Decimal\"\n"
                + "            },\n"
                + "            \"predefinedValue\": {\n"
                + "                \"en_US\": \"123.4\"\n"
                + "            },\n"
                + "            \"style\": {\n"
                + "                \"en_US\": \"\"\n"
                + "            },\n"
                + "            \"tip\": {\n"
                + "                \"en_US\": \"\"\n"
                + "            },\n"
                + "            \"dataType\": \"double\",\n"
                + "            \"fieldNamespace\": \"ddm\",\n"
                + "            \"indexType\": \"keyword\",\n"
                + "            \"localizable\": true,\n"
                + "            \"name\": \"A_Decimal\",\n"
                + "            \"readOnly\": false,\n"
                + "            \"repeatable\": false,\n"
                + "            \"required\": false,\n"
                + "            \"showLabel\": true,\n"
                + "            \"type\": \"ddm-decimal\""
                + "        }"
                + "]}";

            List<Field> resultList = new JsonParser().parse(JSON_NUMBER, usLocale);

            assertNotNull(resultList);
            assertEquals(1, resultList.size());

            Field resultField = resultList.get(0);
            assertTrue(resultField instanceof NumberField);
            NumberField numberField = (NumberField) resultField;

            assertEquals(Field.DataType.NUMBER.getValue(), numberField.getDataType().getValue());
            assertEquals(Field.EditorType.DECIMAL.getValue(), numberField.getEditorType().getValue());
            assertEquals("A_Decimal", numberField.getName());

            Number result = numberField.getCurrentValue();
            assertTrue(result instanceof Double);
            assertEquals(123.4, result);

            assertSame(numberField.getCurrentValue(), numberField.getPredefinedValue());
        }
    }
}