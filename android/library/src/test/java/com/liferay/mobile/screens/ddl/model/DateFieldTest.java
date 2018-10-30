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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.xml.sax.SAXException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class DateFieldTest {

    private static final Locale SPANISH_LOCALE = new Locale("es", "ES");
    private static final Locale US_LOCALE = new Locale("en", "US");

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenConvertingFromString {

        @Test
        public void shouldReturnNullWhenNullStringIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), SPANISH_LOCALE, US_LOCALE);

            assertNull(field.convertFromString(null));
        }

        @Test
        public void shouldReturnNullWhenTooShortStringIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), SPANISH_LOCALE, US_LOCALE);

            assertNull(field.convertFromString("01/05"));
        }

        @Test
        public void shouldReturnNullWhenInvalidStringIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), SPANISH_LOCALE, US_LOCALE);

            assertNull(field.convertFromString("ab/01/2001"));
        }

        @Test
        public void shouldReturnDateWhenShortStringIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), SPANISH_LOCALE, US_LOCALE);

            Date result = field.convertFromString("12/31/00");

            assertNotNull(result);

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), SPANISH_LOCALE);
            calendar.setTime(result);

            assertEquals(2000, calendar.get(Calendar.YEAR));
            assertEquals(Calendar.DECEMBER, calendar.get(Calendar.MONTH));
            assertEquals(31, calendar.get(Calendar.DAY_OF_MONTH));
        }

        @Test
        public void shouldReturnDateWhenLongStringIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), SPANISH_LOCALE, US_LOCALE);

            Date result = field.convertFromString("12/31/2000");

            assertNotNull(result);

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            calendar.setTime(result);

            assertEquals(2000, calendar.get(Calendar.YEAR));
            assertEquals(Calendar.DECEMBER, calendar.get(Calendar.MONTH));
            assertEquals(31, calendar.get(Calendar.DAY_OF_MONTH));
        }

        @Test
        public void shouldReturnDateWhenStringWithOneLetterDayIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), SPANISH_LOCALE, US_LOCALE);

            Date result = field.convertFromString("12/1/00");

            assertNotNull(result);

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            calendar.setTime(result);

            assertEquals(2000, calendar.get(Calendar.YEAR));
            assertEquals(Calendar.DECEMBER, calendar.get(Calendar.MONTH));
            assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH));
        }

        @Test
        public void shouldReturnDateWhenStringWithOneLetterMonthIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), SPANISH_LOCALE, US_LOCALE);

            Date result = field.convertFromString("1/31/00");

            assertNotNull(result);

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            calendar.setTime(result);

            assertEquals(2000, calendar.get(Calendar.YEAR));
            assertEquals(Calendar.JANUARY, calendar.get(Calendar.MONTH));
            assertEquals(31, calendar.get(Calendar.DAY_OF_MONTH));
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenConvertingToString {

        @Test
        public void shouldReturnNullWhenNullDateIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), SPANISH_LOCALE, US_LOCALE);

            assertNull(field.convertToData(null));
        }

        @Test
        public void shouldReturnEpochWhenDateIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), SPANISH_LOCALE, US_LOCALE);

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            calendar.setTimeInMillis(1087666200000L);

            assertEquals("1087666200000", field.convertToData(calendar.getTime()));
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenConvertingToFormattedString {

        @Test
        public void shouldReturnNullWhenNullDateIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), US_LOCALE, US_LOCALE);

            assertEquals("", field.convertToFormattedString(null));
        }

        @Test
        public void shouldReturnSpanishFormattedStringWhenDateIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), SPANISH_LOCALE, US_LOCALE);

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            calendar.setTimeInMillis(1087666200000L);

            assertEquals("19 de junio de 2004", field.convertToFormattedString(calendar.getTime()));
        }

        @Test
        public void shouldReturnUSFormattedStringWhenDateIsSupplied() {
            DateField field = new DateField(new HashMap<String, Object>(), US_LOCALE, US_LOCALE);

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            calendar.setTimeInMillis(1087666200000L);

            assertEquals("June 19, 2004", field.convertToFormattedString(calendar.getTime()));
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenParsingXSD {

        @Test
        public void shouldReturnDateFieldObject() throws SAXException {
            String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
                + "<dynamic-element "
                + "dataType=\"date\" "
                + "fieldNamespace=\"ddm\" "
                + "type=\"ddm-date\" "
                + "name=\"A_Date\" > "
                + "<meta-data locale=\"en_US\"> "
                + "<entry name=\"predefinedValue\"><![CDATA[06/19/2004]]></entry> "
                + "</meta-data> "
                + "</dynamic-element>"
                + "</root>";

            List<Field> resultList = new XSDParser().parse(xsd, US_LOCALE);

            assertNotNull(resultList);
            assertEquals(1, resultList.size());

            Field resultField = resultList.get(0);
            assertTrue(resultField instanceof DateField);
            DateField dateField = (DateField) resultField;

            assertEquals(Field.DataType.DATE.getValue(), dateField.getDataType().getValue());
            assertEquals(Field.EditorType.DATE.getValue(), dateField.getEditorType().getValue());
            assertEquals("A_Date", dateField.getName());

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            calendar.setTimeInMillis(1087603200000L);

            assertEquals(calendar.getTime(), dateField.getCurrentValue());
            assertEquals(dateField.getCurrentValue(), dateField.getPredefinedValue());
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenParsingJson {

        @Test
        public void shouldReturnDateFieldObject() {

            String JSON_DATE = "{\"availableLanguageIds\": [ \"en_US\"], "
                + "\"defaultLanguageId\": \"en_US\", "
                + "\"fields\": [ "
                + "{ \"label\": { \"en_US\": \"Date\"}, "
                + "\"predefinedValue\": { \"en_US\": \"06/19/2004\"}, "
                + "\"style\": { \"en_US\": \"\"}, "
                + "\"tip\": { \"en_US\": \"\"}, "
                + "\"dataType\": \"date\", "
                + "\"fieldNamespace\": \"ddm\", "
                + "\"indexType\": \"keyword\", "
                + "\"localizable\": true, "
                + "\"name\": \"A_Date\", "
                + "\"readOnly\": false, "
                + "\"repeatable\": false, "
                + "\"required\": false, "
                + "\"showLabel\": true, "
                + "\"type\": \"ddm-date\"}"
                + "]}";

            List<Field> resultList = new JsonParser().parse(JSON_DATE, US_LOCALE);
            assertNotNull(resultList);
            assertEquals(1, resultList.size());

            Field resultField = resultList.get(0);
            assertTrue(resultField instanceof DateField);
            DateField dateField = (DateField) resultField;

            assertEquals(Field.DataType.DATE.getValue(), dateField.getDataType().getValue());
            assertEquals(Field.EditorType.DATE.getValue(), dateField.getEditorType().getValue());
            assertEquals("A_Date", dateField.getName());

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            calendar.setTimeInMillis(1087603200000L);

            assertEquals(calendar.getTime(), dateField.getCurrentValue());
            assertEquals(dateField.getCurrentValue(), dateField.getPredefinedValue());
        }
    }
}