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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class SelectableOptionsFieldTest {

	private static final Locale spanishLocale = new Locale("es", "ES");
	private static final Locale usLocale = new Locale("en", "US");

	private static Map<String, Object> createParsedData() {
		Map<String, String> optionData1 = new HashMap<>();
		optionData1.put("label", "Option 1");
		optionData1.put("name", "option987");
		optionData1.put("value", "option1");

		Map<String, String> optionData2 = new HashMap<>();
		optionData2.put("label", "Option 2");
		optionData2.put("name", "option989");
		optionData2.put("value", "option2");

		List<Map<String, String>> availableOptionsData = new ArrayList<>(2);

		availableOptionsData.add(optionData1);
		availableOptionsData.add(optionData2);

		Map<String, Object> parsedData = new HashMap<>();

		parsedData.put("options", availableOptionsData);

		return parsedData;
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenClearingOptions {

		@Test
		public void shouldClearOptionWhenOptionWasSelected() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			List<SelectableOptionsField.Option> availableOptions = field.getAvailableOptions();

			field.selectOption(availableOptions.get(0));
			field.clearOption(availableOptions.get(0));

			List<SelectableOptionsField.Option> selectedOptions = field.getCurrentValue();

			assertTrue(selectedOptions.isEmpty());
		}

		@Test
		public void shouldDoNothingWhenNoOptionsWasSelected() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			List<SelectableOptionsField.Option> availableOptions = field.getAvailableOptions();

			field.clearOption(availableOptions.get(0));

			List<SelectableOptionsField.Option> selectedOptions = field.getCurrentValue();

			assertTrue(selectedOptions.isEmpty());
		}

		@Test
		public void shouldDoNothingOptionWhenThatOptionWasNotSelected() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			List<SelectableOptionsField.Option> availableOptions = field.getAvailableOptions();

			field.selectOption(availableOptions.get(0));
			field.clearOption(availableOptions.get(1));

			List<SelectableOptionsField.Option> selectedOptions = field.getCurrentValue();

			assertEquals(1, selectedOptions.size());
		}
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenCreating {

		@Test
		public void shouldStoreEmptyArrayWhenNoAvailableOptions() {
			SelectableOptionsField field =
				new SelectableOptionsField(new HashMap<String, Object>(), spanishLocale, usLocale);

			List<SelectableOptionsField.Option> result = field.getAvailableOptions();

			assertNotNull(result);
			assertEquals(0, result.size());
		}

		@Test
		public void shouldStoreTheAvailableOptions() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			List<SelectableOptionsField.Option> result = field.getAvailableOptions();

			assertNotNull(result);
			assertEquals(2, result.size());

			SelectableOptionsField.Option option1 = result.get(0);
			assertEquals("Option 1", option1.label);
			assertEquals("option987", option1.name);
			assertEquals("option1", option1.value);

			SelectableOptionsField.Option option2 = result.get(1);
			assertEquals("Option 2", option2.label);
			assertEquals("option989", option2.name);
			assertEquals("option2", option2.value);
		}
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenConvertingToString {

		@Test
		public void shouldReturnEmptyListWhenSelectedOptionsIsNull() {
			SelectableOptionsField field =
				new SelectableOptionsField(new HashMap<String, Object>(), spanishLocale, usLocale);

			String result = field.convertToData(null);

			assertNotNull(result);
			assertEquals("[]", result);
		}

		@Test
		public void shouldReturnEmptyListWhenSelectedOptionsIsEmpty() {
			SelectableOptionsField field =
				new SelectableOptionsField(new HashMap<String, Object>(), spanishLocale, usLocale);
			ArrayList<SelectableOptionsField.Option> selected = new ArrayList<>();

			String result = field.convertToData(selected);

			assertNotNull(result);
			assertEquals("[]", result);
		}

		@Test
		public void shouldReturnSingleItemListWhenThereIsOnlyOneSelectedOption() {
			SelectableOptionsField field =
				new SelectableOptionsField(new HashMap<String, Object>(), spanishLocale, usLocale);

			SelectableOptionsField.Option option1 =
				new SelectableOptionsField.Option("Option 1", "option987", "option1");

			ArrayList<SelectableOptionsField.Option> selected = new ArrayList<>();

			selected.add(option1);

			String result = field.convertToData(selected);

			assertNotNull(result);
			assertEquals("[\"option1\"]", result);
		}

		@Test
		public void shouldReturnItemListWhenThereAreSelectedOptions() {
			SelectableOptionsField field =
				new SelectableOptionsField(new HashMap<String, Object>(), spanishLocale, usLocale);

			SelectableOptionsField.Option option1 =
				new SelectableOptionsField.Option("Option 1", "option987", "option1");
			SelectableOptionsField.Option option2 =
				new SelectableOptionsField.Option("Option 2", "option989", "option2");

			ArrayList<SelectableOptionsField.Option> selected = new ArrayList<>();

			selected.add(option1);
			selected.add(option2);

			String result = field.convertToData(selected);

			assertNotNull(result);
			assertEquals("[\"option1\", \"option2\"]", result);
		}
	}

	@RunWith(Enclosed.class)
	public static class WhenConvertingFromString {

		//@Config(constants = BuildConfig.class)
		@RunWith(RobolectricTestRunner.class)
		public static class ShouldReturnNull {
			@Test
			public void whenNullStringIsSupplied() {
				SelectableOptionsField field =
					new SelectableOptionsField(new HashMap<String, Object>(), spanishLocale, usLocale);

				assertNull(field.convertFromString(null));
			}
		}

		//@Config(constants = BuildConfig.class)
		@RunWith(RobolectricTestRunner.class)
		public static class ShouldReturnEmptyList {
			@Test
			public void whenEmptyStringIsSupplied() {
				SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

				List<SelectableOptionsField.Option> result = field.convertFromString("");

				assertNotNull(result);
				assertEquals(0, result.size());
			}

			@Test
			public void whenEmptyListStringIsSupplied() {
				SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

				List<SelectableOptionsField.Option> result = field.convertFromString("[]");

				assertNotNull(result);
				assertEquals(0, result.size());
			}
		}

		//@Config(constants = BuildConfig.class)
		@RunWith(RobolectricTestRunner.class)
		public static class ShouldReturnSingleItemList {

			@Test
			public void whenOneOptionValueIsSupplied() {
				SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

				List<SelectableOptionsField.Option> result = field.convertFromString("option1");

				assertNotNull(result);
				assertEquals(1, result.size());

				SelectableOptionsField.Option option = result.get(0);

				assertEquals("Option 1", option.label);
				assertEquals("option987", option.name);
				assertEquals("option1", option.value);
			}

			@Test
			public void whenOneOptionLabelIsSupplied() {
				SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

				List<SelectableOptionsField.Option> result = field.convertFromString("Option 1");

				assertNotNull(result);
				assertEquals(1, result.size());

				SelectableOptionsField.Option option = result.get(0);

				assertEquals("Option 1", option.label);
				assertEquals("option987", option.name);
				assertEquals("option1", option.value);
			}

			@Test
			public void whenOneOptionValueListIsSupplied() {
				SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

				List<SelectableOptionsField.Option> result = field.convertFromString("[option1]");

				assertNotNull(result);
				assertEquals(1, result.size());

				SelectableOptionsField.Option option = result.get(0);

				assertEquals("Option 1", option.label);
				assertEquals("option987", option.name);
				assertEquals("option1", option.value);
			}

			@Test
			public void whenOneOptionLabelListIsSupplied() {
				SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

				List<SelectableOptionsField.Option> result = field.convertFromString("[Option 1]");

				assertNotNull(result);
				assertEquals(1, result.size());

				SelectableOptionsField.Option option = result.get(0);

				assertEquals("Option 1", option.label);
				assertEquals("option987", option.name);
				assertEquals("option1", option.value);
			}

			@Test
			public void whenOneQuotedOptionLabelListIsSupplied() {
				SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

				List<SelectableOptionsField.Option> result = field.convertFromString("[\"Option 1]\"");

				assertNotNull(result);
				assertEquals(1, result.size());

				SelectableOptionsField.Option option = result.get(0);

				assertEquals("Option 1", option.label);
				assertEquals("option987", option.name);
				assertEquals("option1", option.value);
			}
		}
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenConvertingToFormattedString {

		@Test
		public void shouldReturnEmptyWhenNullSelectedOptions() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			assertEquals("", field.convertToFormattedString(null));
		}

		@Test
		public void shouldReturnEmptyWhenEmptySelectedOptions() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			assertEquals("", field.convertToFormattedString(new ArrayList<SelectableOptionsField.Option>()));
		}

		@Test
		public void shouldReturnTheOptionLabelWhenSelectedOption() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			ArrayList<SelectableOptionsField.Option> selectedOptions = new ArrayList<>();

			selectedOptions.add(field.getAvailableOptions().get(0));

			assertEquals("Option 1", field.convertToFormattedString(selectedOptions));
		}
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenParsingXSD {
		@Test
		public void shouldReturnStringWithOptionsFieldObject() throws Exception {
			String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
				+ "<dynamic-element dataType=\"string\" "
				+ "multiple=\"true\" "
				+ "name=\"A_Select\" "
				+ "type=\"select\" > "
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
				+ "<meta-data locale=\"en_US\"> "
				+ "<entry name=\"label\"><![CDATA[A Select]]></entry> "
				+ "<entry name=\"predefinedValue\">"
				+ "<![CDATA[[\"value 2\"]]]>"
				+ "</entry>"
				+ "</meta-data> "
				+ "</dynamic-element>"
				+ "</root>";

			List<Field> resultList = new XSDParser().parse(xsd, new Locale("en", "US"));

			assertNotNull(resultList);
			assertEquals(1, resultList.size());

			Field resultField = resultList.get(0);
			assertTrue(resultField instanceof SelectableOptionsField);
			SelectableOptionsField optionsField = (SelectableOptionsField) resultField;

			assertEquals(Field.DataType.STRING.getValue(), optionsField.getDataType().getValue());
			assertEquals(Field.EditorType.SELECT.getValue(), optionsField.getEditorType().getValue());

			List<SelectableOptionsField.Option> predefinedOptions = optionsField.getPredefinedValue();
			assertNotNull(predefinedOptions);
			assertEquals(1, predefinedOptions.size());

			List<SelectableOptionsField.Option> selectedOptions = optionsField.getCurrentValue();
			assertNotNull(selectedOptions);
			assertEquals(1, selectedOptions.size());

			SelectableOptionsField.Option selectedOption = selectedOptions.get(0);

			assertEquals("Option 2", selectedOption.label);
			assertEquals("option_2", selectedOption.name);
			assertEquals("value 2", selectedOption.value);

			assertEquals(optionsField.getCurrentValue(), optionsField.getPredefinedValue());

			List<SelectableOptionsField.Option> availableOptions = optionsField.getAvailableOptions();
			assertNotNull(availableOptions);
			assertEquals(3, availableOptions.size());

			SelectableOptionsField.Option option = availableOptions.get(0);
			assertEquals("Option 1", option.label);
			assertEquals("option_1", option.name);
			assertEquals("value 1", option.value);

			option = availableOptions.get(1);
			assertEquals("Option 2", option.label);
			assertEquals("option_2", option.name);
			assertEquals("value 2", option.value);

			option = availableOptions.get(2);
			assertEquals("Option 3", option.label);
			assertEquals("option_3", option.name);
			assertEquals("value 3", option.value);

			assertTrue(optionsField.isMultiple());
		}
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenSelectingOption {

		@Test
		public void shouldStoreOptionWhenOptionIsSelected() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			List<SelectableOptionsField.Option> availableOptions = field.getAvailableOptions();

			field.selectOption(availableOptions.get(0));

			List<SelectableOptionsField.Option> selectedOptions = field.getCurrentValue();

			assertEquals(1, selectedOptions.size());
		}

		@Test
		public void shouldStoreOnlyOneOptionWhenMultipleOptionsAreSelected() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			List<SelectableOptionsField.Option> availableOptions = field.getAvailableOptions();

			field.selectOption(availableOptions.get(0));
			field.selectOption(availableOptions.get(1));

			List<SelectableOptionsField.Option> selectedOptions = field.getCurrentValue();

			assertEquals(1, selectedOptions.size());
		}

		@Test
		public void shouldReturnEmptyListWhenNoOptionsWereSelected() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			List<SelectableOptionsField.Option> selectedOptions = field.getCurrentValue();

			assertTrue(selectedOptions.isEmpty());
		}
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenValidating {

		@Test
		public void shouldReturnFalseWhenNoOptionWasSelected() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			assertFalse(field.isValid());
		}

		@Test
		public void shouldReturnFalseWhenSelectionIsCleared() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			List<SelectableOptionsField.Option> availableOptions = field.getAvailableOptions();

			field.selectOption(availableOptions.get(0));
			field.clearOption(availableOptions.get(0));

			assertFalse(field.isValid());
		}

		@Test
		public void shouldReturnTrueWhenOptionIsSelected() {
			SelectableOptionsField field = new SelectableOptionsField(createParsedData(), spanishLocale, usLocale);

			List<SelectableOptionsField.Option> availableOptions = field.getAvailableOptions();

			field.selectOption(availableOptions.get(0));

			assertTrue(field.isValid());
		}
	}

	//@Config(constants = BuildConfig.class)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenParsingJson {
		@Test
		public void shouldReturnStringWithOptionsFieldObject() throws Exception {

			String json = "{\"availableLanguageIds\": [ \"en_US\"], "
				+ "\"defaultLanguageId\": \"en_US\", "
				+ "\"fields\": [ "
				+ "{\n"
				+ "            \"label\": {"
				+ "                \"en_US\": \"Select\""
				+ "            },"
				+ "            \"options\": ["
				+ "                {"
				+ "                    \"value\": \"value 1\","
				+ "                    \"label\": {"
				+ "                        \"en_US\": \"Option 1\""
				+ "                    }"
				+ "                },"
				+ "                {"
				+ "                    \"value\": \"value 2\","
				+ "                    \"label\": {"
				+ "                        \"en_US\": \"Option 2\""
				+ "                    }"
				+ "                },"
				+ "                {"
				+ "                    \"value\": \"value 3\","
				+ "                    \"label\": {"
				+ "                        \"en_US\": \"Option 3\""
				+ "                    }"
				+ "                }"
				+ "            ],"
				+ "            \"predefinedValue\": {"
				+ "                \"en_US\": ["
				+ "                    \"value 2\""
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
				+ "        }"
				+ "]}";

			List<Field> resultList = new JsonParser().parse(json, new Locale("en", "US"));

			assertNotNull(resultList);
			assertEquals(1, resultList.size());

			Field resultField = resultList.get(0);
			assertTrue(resultField instanceof SelectableOptionsField);
			SelectableOptionsField optionsField = (SelectableOptionsField) resultField;

			assertEquals(Field.DataType.STRING.getValue(), optionsField.getDataType().getValue());
			assertEquals(Field.EditorType.SELECT.getValue(), optionsField.getEditorType().getValue());

			List<SelectableOptionsField.Option> predefinedOptions = optionsField.getPredefinedValue();
			assertNotNull(predefinedOptions);
			assertEquals(1, predefinedOptions.size());

			List<SelectableOptionsField.Option> selectedOptions = optionsField.getCurrentValue();
			assertNotNull(selectedOptions);
			assertEquals(1, selectedOptions.size());

			SelectableOptionsField.Option selectedOption = selectedOptions.get(0);

			assertEquals("Option 2", selectedOption.label);
			assertEquals("value 2", selectedOption.value);

			assertEquals(optionsField.getCurrentValue(), optionsField.getPredefinedValue());

			List<SelectableOptionsField.Option> availableOptions = optionsField.getAvailableOptions();
			assertNotNull(availableOptions);
			assertEquals(3, availableOptions.size());

			SelectableOptionsField.Option option = availableOptions.get(0);
			assertEquals("Option 1", option.label);
			assertEquals("value 1", option.value);

			option = availableOptions.get(1);
			assertEquals("Option 2", option.label);
			assertEquals("value 2", option.value);

			option = availableOptions.get(2);
			assertEquals("Option 3", option.label);
			assertEquals("value 3", option.value);

			// Multiple is not supported yet
			assertFalse(optionsField.isMultiple());
		}
	}
}