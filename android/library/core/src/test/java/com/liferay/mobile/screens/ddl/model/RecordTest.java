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

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class RecordTest {

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class AfterCreatingFromXSD {

		@Test
		public void shouldReturnTheFiedsByIndex() throws Exception {
			String xsd =
				"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
					"<dynamic-element " +
							"dataType=\"boolean\" " +
							"type=\"checkbox\" " +
							"name=\"A_Bool\" > " +
						"<meta-data locale=\"en_US\"> " +
							"<entry name=\"predefinedValue\"><![CDATA[false]]></entry> " +
						"</meta-data> " +
					"</dynamic-element>" +
				"</root>";

			Record record = new Record(new Locale("en", "US"));
			record.parseXsd(xsd);

			assertEquals(1, record.getFieldCount());

			Field field = record.getField(0);

			assertEquals("A_Bool", field.getName());
		}

	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenGettingValues {

		@Test
		public void shouldReturnTheValueAsString() throws Exception {
			String xsd =
				"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
					"<dynamic-element " +
							"dataType=\"boolean\" " +
							"type=\"checkbox\" " +
							"name=\"A_Bool\" > " +
						"<meta-data locale=\"en_US\"> " +
							"<entry name=\"predefinedValue\"><![CDATA[false]]></entry> " +
						"</meta-data> " +
					"</dynamic-element>" +
				"</root>";

			Record record = new Record(new Locale("en", "US"));
			record.parseXsd(xsd);

			BooleanField field = (BooleanField)record.getField(0);

			field.setCurrentValue(true);

			Map<String, String> values = record.getData();

			assertNotNull(values);
			assertEquals(1, values.size());
			assertNotNull(values.get("A_Bool"));
			assertEquals("true", values.get("A_Bool"));
		}

		@Test
		public void shouldIgnoreOneValueIfItIsEmpty() throws Exception {
			String xsd =
				"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
					"<dynamic-element " +
							"dataType=\"boolean\" " +
							"type=\"checkbox\" " +
							"name=\"A_Bool\" > " +
						"<meta-data locale=\"en_US\"> " +
							"<entry name=\"predefinedValue\"><![CDATA[false]]></entry> " +
						"</meta-data> " +
					"</dynamic-element>" +
					"<dynamic-element " +
							"dataType=\"string\" " +
							"type=\"text\" " +
							"name=\"A_Text\" > " +
						"<meta-data locale=\"en_US\"> " +
							"<entry name=\"predefinedValue\"><![CDATA[abc]]></entry> " +
						"</meta-data> " +
					"</dynamic-element>" +
				"</root>";

			Record record = new Record(new Locale("en", "US"));
			record.parseXsd(xsd);

			assertTrue(record.getField(1) instanceof  StringField);
			StringField field = (StringField)record.getField(1);

			field.setCurrentValue("");

			Map<String, String> values = record.getData();

			assertNotNull(values);
			assertEquals(1, values.size());
			assertNotNull(values.get("A_Bool"));
			assertNull(values.get("A_Text"));
		}

	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenSettingValues {

		@Test
		public void shouldChangeTheFieldsCurrentValue() throws Exception {
			String xsd =
				"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
					"<dynamic-element " +
							"dataType=\"string\" " +
							"type=\"text\" " +
							"name=\"A_Text\" > " +
						"<meta-data locale=\"en_US\"> " +
							"<entry name=\"predefinedValue\"><![CDATA[abc]]></entry> " +
						"</meta-data> " +
					"</dynamic-element>" +
				"</root>";

			Record record = new Record(new Locale("en", "US"));
			record.parseXsd(xsd);

			StringField field = (StringField)record.getField(0);

			Map<String, Object> newValues = new HashMap<>();
			newValues.put("A_Text", "xyz");

			record.setValues(newValues);

			assertEquals("xyz", field.getCurrentValue());
		}

	}

}