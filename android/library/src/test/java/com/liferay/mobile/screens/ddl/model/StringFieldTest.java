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

import com.liferay.mobile.screens.ddl.XSDParser;
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
public class StringFieldTest {

    private static StringField createStringField(Boolean required) throws SAXException {
        String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
            + "<dynamic-element "
            + "dataType=\"string\" "
            + "type=\"text\" "
            + "required=\""
            + required.toString()
            + "\" "
            + "name=\"A_Text\" > "
            + "<meta-data locale=\"en_US\"> "
            + "<entry name=\"predefinedValue\"><![CDATA[default text]]></entry> "
            + "</meta-data> "
            + "</dynamic-element>"
            + "</root>";

        List<Field> resultList = new XSDParser().parse(xsd, new Locale("en", "US"));

        return (StringField) resultList.get(0);
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenParsingXSD {
        @Test
        public void shouldReturnStringFieldObject() throws Exception {
            String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
                + "<dynamic-element "
                + "dataType=\"string\" "
                + "type=\"text\" "
                + "name=\"A_Text\" > "
                + "<meta-data locale=\"en_US\"> "
                + "<entry name=\"predefinedValue\"><![CDATA[default text]]></entry> "
                + "</meta-data> "
                + "</dynamic-element>"
                + "</root>";

            List<Field> resultList = new XSDParser().parse(xsd, new Locale("en", "US"));

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