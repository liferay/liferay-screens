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
public class Record70Test {

	public static final String JSON_BOOLEAN = "{\"availableLanguageIds\": [ \"en_US\"], "
		+ "\"defaultLanguageId\": \"en_US\", "
		+ "\"fields\": [ "
		+ "{ \"label\": { \"en_US\": \"Boolean\"}, "
		+ "\"predefinedValue\": { \"en_US\": false}, "
		+ "\"style\": { \"en_US\": \"\"}, "
		+ "\"tip\": { \"en_US\": \"\"}, "
		+ "\"dataType\": \"boolean\", "
		+ "\"indexType\": \"keyword\", "
		+ "\"localizable\": true, "
		+ "\"name\": \"A_Bool\", "
		+ "\"readOnly\": false, "
		+ "\"repeatable\": false, "
		+ "\"required\": false, "
		+ "\"showLabel\": true, "
		+ "\"type\": \"checkbox\"}]}";

	private static void parse(Record record, String content) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("definition", new JSONObject(content));
		record.parseDDMStructure(jsonObject);
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class AfterCreatingFromXSD {

		@Test
		public void shouldReturnTheFieldsByIndex() throws Exception {

			Record record = new Record(new Locale("en", "US"));
			parse(record, JSON_BOOLEAN);

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

			Record record = new Record(new Locale("en", "US"));
			parse(record, JSON_BOOLEAN);

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

			String json = "{\"availableLanguageIds\": [ \"en_US\"], "
				+ "\"defaultLanguageId\": \"en_US\", "
				+ "\"fields\": [ "
				+ "{ \"label\": { \"en_US\": \"Boolean\"}, "
				+ "\"predefinedValue\": { \"en_US\": false}, "
				+ "\"style\": { \"en_US\": \"\"}, "
				+ "\"tip\": { \"en_US\": \"\"}, "
				+ "\"dataType\": \"boolean\", "
				+ "\"indexType\": \"keyword\", "
				+ "\"localizable\": true, "
				+ "\"name\": \"A_Bool\", "
				+ "\"readOnly\": false, "
				+ "\"repeatable\": false, "
				+ "\"required\": false, "
				+ "\"showLabel\": true, "
				+ "\"type\": \"checkbox\"},"
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
				+ "            \"required\": \"false\","
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"text\""
				+ "        }"
				+ "]}";

			Record record = new Record(new Locale("en", "US"));
			parse(record, json);

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
				+ "            \"name\": \"A_Text\","
				+ "            \"readOnly\": false,"
				+ "            \"repeatable\": false,"
				+ "            \"required\": \"false\","
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"text\""
				+ "        }"
				+ "]}";

			Record record = new Record(new Locale("en", "US"));
			parse(record, json);

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

			String json = "{"
				+ "    \"availableLanguageIds\": ["
				+ "        \"en_US\""
				+ "    ],"
				+ "    \"defaultLanguageId\": \"en_US\","
				+ "    \"fields\": ["
				+ "        {"
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
				+ "            \"required\": false,"
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"text\""
				+ "        },"
				+ "        {"
				+ "            \"label\": {"
				+ "                \"en_US\": \"Boolean\""
				+ "            },"
				+ "            \"predefinedValue\": {"
				+ "                \"en_US\": false"
				+ "            },"
				+ "            \"style\": {"
				+ "                \"en_US\": \"\""
				+ "            },"
				+ "            \"tip\": {"
				+ "                \"en_US\": \"\""
				+ "            },"
				+ "            \"dataType\": \"boolean\","
				+ "            \"indexType\": \"keyword\","
				+ "            \"localizable\": true,"
				+ "            \"name\": \"Boolean88ug\","
				+ "            \"readOnly\": false,"
				+ "            \"repeatable\": false,"
				+ "            \"required\": false,"
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"checkbox\""
				+ "        },"
				+ "        {"
				+ "            \"label\": {"
				+ "                \"en_US\": \"Date\""
				+ "            },"
				+ "            \"predefinedValue\": {"
				+ "                \"en_US\": \"02/26/2016\""
				+ "            },"
				+ "            \"style\": {"
				+ "                \"en_US\": \"\""
				+ "            },"
				+ "            \"tip\": {"
				+ "                \"en_US\": \"\""
				+ "            },"
				+ "            \"dataType\": \"date\","
				+ "            \"fieldNamespace\": \"ddm\","
				+ "            \"indexType\": \"keyword\","
				+ "            \"localizable\": true,"
				+ "            \"name\": \"Date2klr\","
				+ "            \"readOnly\": false,"
				+ "            \"repeatable\": false,"
				+ "            \"required\": false,"
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"ddm-date\""
				+ "        },"
				+ "        {"
				+ "            \"label\": {"
				+ "                \"en_US\": \"Decimal\""
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
				+ "            \"dataType\": \"double\","
				+ "            \"fieldNamespace\": \"ddm\","
				+ "            \"indexType\": \"keyword\","
				+ "            \"localizable\": true,"
				+ "            \"name\": \"Decimal75fb\","
				+ "            \"readOnly\": false,"
				+ "            \"repeatable\": false,"
				+ "            \"required\": false,"
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"ddm-decimal\""
				+ "        },"
				+ "        {"
				+ "            \"label\": {"
				+ "                \"en_US\": \"Text Box\""
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
				+ "            \"name\": \"TextBoxe2h4\","
				+ "            \"readOnly\": false,"
				+ "            \"repeatable\": false,"
				+ "            \"required\": false,"
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"textarea\""
				+ "        },"
				+ "        {"
				+ "            \"label\": {"
				+ "                \"en_US\": \"Number\""
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
				+ "            \"dataType\": \"number\","
				+ "            \"fieldNamespace\": \"ddm\","
				+ "            \"indexType\": \"keyword\","
				+ "            \"localizable\": true,"
				+ "            \"name\": \"Number6zw6\","
				+ "            \"readOnly\": false,"
				+ "            \"repeatable\": false,"
				+ "            \"required\": false,"
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"ddm-number\""
				+ "        },"
				+ "        {"
				+ "            \"label\": {"
				+ "                \"en_US\": \"Integer\""
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
				+ "            \"dataType\": \"integer\","
				+ "            \"fieldNamespace\": \"ddm\","
				+ "            \"indexType\": \"keyword\","
				+ "            \"localizable\": true,"
				+ "            \"name\": \"Integer2si7\","
				+ "            \"readOnly\": false,"
				+ "            \"repeatable\": false,"
				+ "            \"required\": false,"
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"ddm-integer\""
				+ "        },"
				+ "        {"
				+ "            \"label\": {"
				+ "                \"en_US\": \"Documents and Media\""
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
				+ "            \"dataType\": \"document-library\","
				+ "            \"fieldNamespace\": \"ddm\","
				+ "            \"indexType\": \"keyword\","
				+ "            \"localizable\": true,"
				+ "            \"name\": \"DocumentsAndMediadlq5\","
				+ "            \"readOnly\": false,"
				+ "            \"repeatable\": false,"
				+ "            \"required\": false,"
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"ddm-documentlibrary\""
				+ "        },"
				+ "        {"
				+ "            \"label\": {"
				+ "                \"en_US\": \"Select\""
				+ "            },"
				+ "            \"options\": ["
				+ "                {"
				+ "                    \"value\": \"value 1\","
				+ "                    \"label\": {"
				+ "                        \"en_US\": \"option 1\""
				+ "                    }"
				+ "                },"
				+ "                {"
				+ "                    \"value\": \"value 2\","
				+ "                    \"label\": {"
				+ "                        \"en_US\": \"option 2\""
				+ "                    }"
				+ "                },"
				+ "                {"
				+ "                    \"value\": \"value 3\","
				+ "                    \"label\": {"
				+ "                        \"en_US\": \"option 3\""
				+ "                    }"
				+ "                }"
				+ "            ],"
				+ "            \"predefinedValue\": {"
				+ "                \"en_US\": ["
				+ "                    \"value 1\""
				+ "                ]"
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
				+ "            \"multiple\": false,"
				+ "            \"name\": \"Select54e6\","
				+ "            \"readOnly\": false,"
				+ "            \"repeatable\": false,"
				+ "            \"required\": false,"
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"select\""
				+ "        },"
				+ "        {"
				+ "            \"label\": {"
				+ "                \"en_US\": \"Radio\""
				+ "            },"
				+ "            \"options\": ["
				+ "                {"
				+ "                    \"value\": \"value 1\","
				+ "                    \"label\": {"
				+ "                        \"en_US\": \"option 1\""
				+ "                    }"
				+ "                },"
				+ "                {"
				+ "                    \"value\": \"value 2\","
				+ "                    \"label\": {"
				+ "                        \"en_US\": \"option 2\""
				+ "                    }"
				+ "                },"
				+ "                {"
				+ "                    \"value\": \"value 3\","
				+ "                    \"label\": {"
				+ "                        \"en_US\": \"option 3\""
				+ "                    }"
				+ "                }"
				+ "            ],"
				+ "            \"predefinedValue\": {"
				+ "                \"en_US\": ["
				+ "                    \"\""
				+ "                ]"
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
				+ "            \"name\": \"Radio6zup\","
				+ "            \"readOnly\": false,"
				+ "            \"repeatable\": false,"
				+ "            \"required\": false,"
				+ "            \"showLabel\": true,"
				+ "            \"type\": \"radio\""
				+ "        }"
				+ "    ]"
				+ "}";

			Record record = new Record(new Locale("en", "US"));
			parse(record, json);

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

		@Test
		public void shouldSerializeAndDeserializeAStructure() {

			DDMStructure structure = new DDMStructure(Locale.US);

			Parcel parcel = Parcel.obtain();

			parcel.writeParcelable(structure, 0);

			assertTrue(parcel.dataCapacity() > 0);
			assertTrue(parcel.dataSize() > 0);

			parcel.setDataPosition(0);

			DDMStructure deserializedStructure = parcel.readParcelable(structure.getClass().getClassLoader());
			assertEquals(structure.getLocale(), deserializedStructure.getLocale());
			parcel.recycle();
		}
	}
}