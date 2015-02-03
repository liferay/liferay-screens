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

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class BooleanFieldTest {

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenConvertingFromString {

		@Test
		public void shouldReturnNullWhenNullStringIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _spanishLocale);

			assertNull(field.convertFromString(null));
		}

		@Test
		public void shouldReturnBooleanWhenTrueStringIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _spanishLocale);

			assertTrue(field.convertFromString("True"));
		}

		@Test
		public void shouldReturnBooleanWhenFalseStringIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _spanishLocale);

			assertFalse(field.convertFromString("false"));
		}
	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenConvertingToString {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _spanishLocale);

			assertNull(field.convertToString(null));
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _spanishLocale);

			assertEquals("true", field.convertToString(true));
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _spanishLocale);

			assertEquals("false", field.convertToString(false));
		}
	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenConvertingToLabel {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _usLocale);

			assertNull(field.convertToLabel(null));
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _usLocale);

			assertEquals("Yes", field.convertToLabel(true));
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _usLocale);

			assertEquals("No", field.convertToLabel(false));
		}
	}

	public static class WhenToString {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _spanishLocale);
			field.setCurrentValue(null);

			assertNull(field.toString());
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _spanishLocale);
			field.setCurrentValue(true);

			assertEquals("true", field.toString());
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _spanishLocale);
			field.setCurrentValue(false);

			assertEquals("false", field.toString());
		}
	}

	public static class WhenToLabel {

		@Test
		public void shouldReturnNullWhenNullBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _usLocale);
			field.setCurrentValue(null);

			assertNull(field.toLabel());
		}

		@Test
		public void shouldReturnTrueStringWhenTrueBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _usLocale);
			field.setCurrentValue(true);

			assertEquals("Yes", field.toLabel());
		}

		@Test
		public void shouldReturnFalseStringWhenFalseBooleanIsSupplied() throws Exception {
			BooleanField field = new BooleanField(new HashMap<String, String>(), _usLocale);
			field.setCurrentValue(false);

			assertEquals("No", field.toLabel());
		}
	}

	private static final Locale _spanishLocale = new Locale("es", "ES");
	private static final Locale _usLocale = new Locale("en", "US");

}