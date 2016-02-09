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

import com.liferay.mobile.screens.BuildConfig;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class BooleanFieldTest {

	public static final String JSON_BOOLEAN = "{\"availableLanguageIds\": [ \"en_US\"], " +
		"\"defaultLanguageId\": \"en_US\", " +
		"\"fields\": [ " +
		"{ \"label\": { \"en_US\": \"Boolean\"}, " +
		"\"predefinedValue\": { \"en_US\": false}, " +
		"\"style\": { \"en_US\": \"\"}, " +
		"\"tip\": { \"en_US\": \"\"}, " +
		"\"dataType\": \"boolean\", " +
		"\"indexType\": \"keyword\", " +
		"\"localizable\": true, " +
		"\"name\": \"A_Bool\", " +
		"\"readOnly\": false, " +
		"\"repeatable\": false, " +
		"\"required\": false, " +
		"\"showLabel\": true, " +
		"\"type\": \"checkbox\"}]}";
	private static final Locale _spanishLocale = new Locale("es", "ES");
	private static final Locale _usLocale = new Locale("en", "US");

	@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenConvertingFromString {

		@Test
		public void shouldReturnNullWhenNullStringIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _spanishLocale, _usLocale);

			assertNull(field.convertFromString(null));
		}

		@Test
		public void shouldReturnBooleanWhenTrueStringIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _spanishLocale, _usLocale);

			assertTrue(field.convertFromString("True"));
		}

		@Test
		public void shouldReturnBooleanWhenFalseStringIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _spanishLocale, _usLocale);

			assertFalse(field.convertFromString("false"));
		}
	}

	@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenConvertingToString {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _spanishLocale, _usLocale);

			assertNull(field.convertToData(null));
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _spanishLocale, _usLocale);

			assertEquals("true", field.convertToData(true));
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _spanishLocale, _usLocale);

			assertEquals("false", field.convertToData(false));
		}
	}

	@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenConvertingToFormattedString {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _usLocale, _usLocale);

			assertNull(field.convertToFormattedString(null));
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _usLocale, _usLocale);

			assertEquals("Yes", field.convertToFormattedString(true));
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _usLocale, _usLocale);

			assertEquals("No", field.convertToFormattedString(false));
		}
	}

	public static class WhenToData {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _spanishLocale, _usLocale);
			field.setCurrentValue(null);

			assertNull(field.toData());
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _spanishLocale, _usLocale);
			field.setCurrentValue(true);

			assertEquals("true", field.toData());
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _spanishLocale, _usLocale);
			field.setCurrentValue(false);

			assertEquals("false", field.toData());
		}
	}

	public static class WhenToFormattedString {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _usLocale, _usLocale);
			field.setCurrentValue(null);

			assertNull(field.toFormattedString());
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _usLocale, _usLocale);
			field.setCurrentValue(true);

			assertEquals("Yes", field.toFormattedString());
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, Object>(), _usLocale, _usLocale);
			field.setCurrentValue(false);

			assertEquals("No", field.toFormattedString());
		}
	}

	@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenParsingXSD {
		@Test
		public void shouldReturnStringFieldObject() throws Exception {

			List<Field> resultList = new JsonParser().parse(JSON_BOOLEAN, _usLocale);

			assertNotNull(resultList);
			assertEquals(1, resultList.size());

			Field resultField = resultList.get(0);
			assertTrue(resultField instanceof BooleanField);
			BooleanField booleanField = (BooleanField) resultField;

			assertEquals(Field.DataType.BOOLEAN.getValue(), booleanField.getDataType().getValue());
			assertEquals(Field.EditorType.CHECKBOX.getValue(), booleanField.getEditorType().getValue());
			assertEquals("A_Bool", booleanField.getName());
			assertFalse(booleanField.getCurrentValue());
			assertFalse(booleanField.getPredefinedValue());
		}

		@Test
		public void shouldUseFalseAsDefaultValueWhenNoPredefinedValue() throws Exception {

			List<Field> resultList = new JsonParser().parse(JSON_BOOLEAN, _usLocale);

			BooleanField booleanField = (BooleanField) resultList.get(0);

			assertFalse(booleanField.getCurrentValue());
		}
	}

}