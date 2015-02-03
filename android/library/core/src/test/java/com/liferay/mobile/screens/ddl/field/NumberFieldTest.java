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

package com.liferay.mobile.screens.ddl.field;

import com.liferay.mobile.screens.ddl.XSDParser;

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
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class NumberFieldTest {

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenConvertingFromString {

		@Test
		public void shouldReturnNullWhenNullStringIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _spanishLocale);

			assertNull(field.convertFromString(null));
		}

		@Test
		public void shouldReturnNullWhenEmptyStringIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _spanishLocale);

			assertNull(field.convertFromString(""));
		}

		@Test
		public void shouldReturnNullWhenInvalidStringIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _spanishLocale);

			assertNull(field.convertFromString("12a3"));
			assertNull(field.convertFromString("12,3"));
			assertNull(field.convertFromString("12,000"));
		}

		@Test
		public void shouldReturnLongWhenIntegerStringIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _spanishLocale);

			Number result = field.convertFromString("123");

			assertTrue(result instanceof Long);
			assertEquals(123L, result);
		}

		@Test
		public void shouldReturnDoubleWhenDecimalStringIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _spanishLocale);

			Number result = field.convertFromString("123.4");

			assertTrue(result instanceof Double);
			assertEquals(123.4, result);
		}
	}

	/*
	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenConvertingToString {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _spanishLocale);

			assertNull(field.convertToString(null));
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _spanishLocale);

			assertEquals("true", field.convertToString(true));
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _spanishLocale);

			assertEquals("false", field.convertToString(false));
		}
	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenConvertingToLabel {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _usLocale);

			assertNull(field.convertToLabel(null));
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _usLocale);

			assertEquals("Yes", field.convertToLabel(true));
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _usLocale);

			assertEquals("No", field.convertToLabel(false));
		}
	}

	public static class WhenToString {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _spanishLocale);
			field.setCurrentValue(null);

			assertNull(field.toString());
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _spanishLocale);
			field.setCurrentValue(true);

			assertEquals("true", field.toString());
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _spanishLocale);
			field.setCurrentValue(false);

			assertEquals("false", field.toString());
		}
	}

	public static class WhenToLabel {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _usLocale);
			field.setCurrentValue(null);

			assertNull(field.toLabel());
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _usLocale);
			field.setCurrentValue(true);

			assertEquals("Yes", field.toLabel());
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			NumberField field = new NumberField(new HashMap<String, String>(), _usLocale);
			field.setCurrentValue(false);

			assertEquals("No", field.toLabel());
		}
	}
*/
	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenParsingXSD {

		@Test
		public void shouldReturnFieldObjectWhenFieldIsNumber() throws Exception {
			String xsd =
				"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
					"<dynamic-element " +
							"dataType=\"number\" " +
							"fieldNamespace=\"ddm\" " +
							"type=\"ddm-number\" " +
							"name=\"A_Number\" > " +
						"<meta-data locale=\"en_US\"> " +
							"<entry name=\"predefinedValue\"><![CDATA[123]]></entry> " +
						"</meta-data> " +
					"</dynamic-element>" +
				"</root>";

			List<Field> resultList = new XSDParser().parse(xsd, _usLocale);

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
			String xsd =
				"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
					"<dynamic-element " +
							"dataType=\"integer\" " +
							"fieldNamespace=\"ddm\" " +
							"type=\"ddm-integer\" " +
							"name=\"An_Integer\" > " +
						"<meta-data locale=\"en_US\"> " +
							"<entry name=\"predefinedValue\"><![CDATA[123]]></entry> " +
						"</meta-data> " +
					"</dynamic-element>" +
				"</root>";

			List<Field> resultList = new XSDParser().parse(xsd, _usLocale);

			assertNotNull(resultList);
			assertEquals(1, resultList.size());

			Field resultField = resultList.get(0);
			assertTrue(resultField instanceof NumberField);
			NumberField numberField = (NumberField) resultField;

			assertEquals(Field.DataType.NUMBER.getValue(), numberField.getDataType().getValue());
			assertEquals(Field.EditorType.NUMBER.getValue(), numberField.getEditorType().getValue());
			assertEquals("An_Integer", numberField.getName());

			Number result = numberField.getCurrentValue();
			assertTrue(result instanceof Long);
			assertEquals(123L, result);

			assertSame(numberField.getCurrentValue(), numberField.getPredefinedValue());
		}

		@Test
		public void shouldReturnFieldObjectWhenFieldIsDecimal() throws Exception {
			String xsd =
				"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
					"<dynamic-element " +
							"dataType=\"double\" " +
							"fieldNamespace=\"ddm\" " +
							"type=\"ddm-decimal\" " +
							"name=\"A_Decimal\" > " +
						"<meta-data locale=\"en_US\"> " +
							"<entry name=\"predefinedValue\"><![CDATA[123.4]]></entry> " +
						"</meta-data> " +
					"</dynamic-element>" +
				"</root>";

			List<Field> resultList = new XSDParser().parse(xsd, _usLocale);

			assertNotNull(resultList);
			assertEquals(1, resultList.size());

			Field resultField = resultList.get(0);
			assertTrue(resultField instanceof NumberField);
			NumberField numberField = (NumberField) resultField;

			assertEquals(Field.DataType.NUMBER.getValue(), numberField.getDataType().getValue());
			assertEquals(Field.EditorType.NUMBER.getValue(), numberField.getEditorType().getValue());
			assertEquals("A_Decimal", numberField.getName());

			Number result = numberField.getCurrentValue();
			assertTrue(result instanceof Double);
			assertEquals(123.4, result);

			assertSame(numberField.getCurrentValue(), numberField.getPredefinedValue());
		}

	}

	private static final Locale _spanishLocale = new Locale("es", "ES");
	private static final Locale _usLocale = new Locale("en", "US");

}