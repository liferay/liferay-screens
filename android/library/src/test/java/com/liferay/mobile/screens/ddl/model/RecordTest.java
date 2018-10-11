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

import android.os.Parcel;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class RecordTest {

	private static void parse(Record record, String content) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("xsd", content);
		record.parseDDMStructure(jsonObject);
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class AfterCreatingFromXSD {

		@Test
		public void shouldReturnTheFieldsByIndex() throws Exception {
			String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
				+ "<dynamic-element "
				+ "dataType=\"boolean\" "
				+ "type=\"checkbox\" "
				+ "name=\"A_Bool\" > "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"predefinedValue\"><![CDATA[false]]></entry> "
				+ "</meta-data> "
				+ "</dynamic-element>"
				+ "</root>";

			Record record = new Record(new Locale("en", "US"));
			parse(record, xsd);

			assertEquals(1, record.getFieldCount());

			Field field = record.getField(0);

			assertEquals("A_Bool", field.getName());
		}
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenGettingValues {

		@Test
		public void shouldReturnTheValueAsString() throws Exception {
			String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
				+ "<dynamic-element "
				+ "dataType=\"boolean\" "
				+ "type=\"checkbox\" "
				+ "name=\"A_Bool\" > "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"predefinedValue\"><![CDATA[false]]></entry> "
				+ "</meta-data> "
				+ "</dynamic-element>"
				+ "</root>";

			Record record = new Record(new Locale("en", "US"));
			parse(record, xsd);

			BooleanField field = (BooleanField) record.getField(0);

			field.setCurrentValue(true);

			Map<String, String> values = record.getData();

			assertNotNull(values);
			assertEquals(1, values.size());
			assertNotNull(values.get("A_Bool"));
			assertEquals("true", values.get("A_Bool"));
		}

		@Test
		public void shouldIgnoreOneValueIfItIsEmpty() throws Exception {
			String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
				+ "<dynamic-element "
				+ "dataType=\"boolean\" "
				+ "type=\"checkbox\" "
				+ "name=\"A_Bool\" > "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"predefinedValue\"><![CDATA[false]]></entry> "
				+ "</meta-data> "
				+ "</dynamic-element>"
				+ "<dynamic-element "
				+ "dataType=\"string\" "
				+ "type=\"text\" "
				+ "name=\"A_Text\" > "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"predefinedValue\"><![CDATA[abc]]></entry> "
				+ "</meta-data> "
				+ "</dynamic-element>"
				+ "</root>";

			Record record = new Record(new Locale("en", "US"));
			parse(record, xsd);

			assertTrue(record.getField(1) instanceof StringField);
			StringField field = (StringField) record.getField(1);

			field.setCurrentValue("");

			Map<String, String> values = record.getData();

			assertNotNull(values);
			assertEquals(1, values.size());
			assertNotNull(values.get("A_Bool"));
			assertNull(values.get("A_Text"));
		}
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenSettingValues {

		@Test
		public void shouldChangeTheFieldsCurrentValue() throws Exception {
			String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
				+ "<dynamic-element "
				+ "dataType=\"string\" "
				+ "type=\"text\" "
				+ "name=\"A_Text\" > "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"predefinedValue\"><![CDATA[abc]]></entry> "
				+ "</meta-data> "
				+ "</dynamic-element>"
				+ "</root>";

			Record record = new Record(new Locale("en", "US"));
			parse(record, xsd);

			StringField field = (StringField) record.getField(0);

			Map<String, Object> newData = new HashMap<>();
			newData.put("A_Text", "xyz");

			Map<String, Object> newValues = new HashMap<>();
			newValues.put("modelValues", newData);

			record.setValuesAndAttributes(newValues);
			record.refresh();

			assertEquals("xyz", field.getCurrentValue());
		}
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenSerialize {

		@Test
		public void shouldSerializeAndDeserializeTheObject() throws Exception {
			String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
				+ "<dynamic-element "
				+ "dataType=\"string\" "
				+ "type=\"text\" "
				+ "name=\"A_Text\" > "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"predefinedValue\"><![CDATA[abc]]></entry> "
				+ "</meta-data> "
				+ "</dynamic-element>"
				+ "<dynamic-element "
				+ "dataType=\"boolean\" "
				+ "type=\"checkbox\" "
				+ "name=\"A_Bool\" > "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"predefinedValue\"><![CDATA[false]]></entry> "
				+ "</meta-data> "
				+ "</dynamic-element>"
				+ "<dynamic-element "
				+ "dataType=\"date\" "
				+ "fieldNamespace=\"ddm\" "
				+ "type=\"ddm-date\" "
				+ "name=\"A_Date\" > "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"predefinedValue\"><![CDATA[06/19/2004]]></entry> "
				+ "</meta-data> "
				+ "</dynamic-element>"
				+ "<dynamic-element "
				+ "dataType=\"number\" "
				+ "fieldNamespace=\"ddm\" "
				+ "type=\"ddm-number\" "
				+ "name=\"A_Number\" > "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"predefinedValue\"><![CDATA[123]]></entry> "
				+ "</meta-data> "
				+ "</dynamic-element>"
				+ "<dynamic-element dataType=\"string\" "
				+ "multiple=\"true\" "
				+ "name=\"A_Select\" "
				+ "type=\"select\" > "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"label\"><![CDATA[A Select]]></entry> "
				+ "<entry name=\"predefinedValue\">"
				+ "<![CDATA[[\"value 1\",\"value 2\"]]]>"
				+ "</entry>"
				+ "</meta-data> "
				+ "<dynamic-element name=\"option_1\" type=\"option\" value=\"value 1\"> "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"label\"><![CDATA[Option 1]]></entry> "
				+ "</meta-data> "
				+ "</dynamic-element> "
				+ "<dynamic-element name=\"option_2\" type=\"option\" value=\"value 2\"> "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"label\"><![CDATA[Option 2]]></entry> "
				+ "</meta-data>"
				+ "</dynamic-element> "
				+ "<dynamic-element name=\"option_3\" type=\"option\" value=\"value 3\"> "
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"label\"><![CDATA[Option 3]]></entry> "
				+ "</meta-data>"
				+ "</dynamic-element> "
				+ "</dynamic-element>"
				+ "</root>";

			Record record = new Record(new Locale("en", "US"));
			parse(record, xsd);
			record.setCreatorUserId(12);
			record.setRecordId(34);
			record.setRecordSetId(56);
			record.setStructureId(78);

			Map<String, Object> values = new HashMap<>();
			values.put("A_Text", "xyz");
			values.put("A_Bool", "true");
			values.put("A_Date", "06/20/2004");
			values.put("A_Number", "321");
			values.put("A_Select", "[value 2]");

			Map<String, Object> modelValues = new HashMap<>();
			modelValues.put("modelValues", values);
			record.setValuesAndAttributes(modelValues);

			SelectableOptionsField selectableOptionsField =
				(SelectableOptionsField) record.getField(record.getFieldCount() - 1);
			selectableOptionsField.selectOption(selectableOptionsField.getAvailableOptions().get(0));

			Parcel parcel = Parcel.obtain();

			parcel.writeParcelable(record, 0);

			assertTrue(parcel.dataCapacity() > 0);
			assertTrue(parcel.dataSize() > 0);

			parcel.setDataPosition(0);

			Record deserializedRecord = parcel.readParcelable(record.getClass().getClassLoader());

			assertEquals(record.getFieldCount(), deserializedRecord.getFieldCount());
			assertEquals(record.getLocale(), deserializedRecord.getLocale());
			assertEquals(record.getCreatorUserId(), deserializedRecord.getCreatorUserId());
			assertEquals(record.getRecordId(), deserializedRecord.getRecordId());
			assertEquals(record.getRecordSetId(), deserializedRecord.getRecordSetId());
			assertEquals(record.getStructureId(), deserializedRecord.getStructureId());

			for (int i = 0; i < record.getFieldCount(); ++i) {
				Field field = record.getField(i);
				Field deserializedField = deserializedRecord.getField(i);

				assertEquals(field.getClass(), deserializedField.getClass());
				assertEquals(field.getCurrentValue(), deserializedField.getCurrentValue());
			}
			parcel.recycle();
		}
	}
}