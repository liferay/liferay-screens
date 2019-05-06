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

package com.liferay.mobile.screens.ddl;

import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.StringField;
import com.liferay.mobile.screens.ddm.form.model.RepeatableField;
import java.util.List;
import java.util.Locale;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.xml.sax.SAXParseException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class XSDParserTest {

    private static final Locale spanishLocale = new Locale("es", "ES");
    private static final Locale usLocale = new Locale("en", "US");

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class ShouldRaiseException {

        @Test(expected = SAXParseException.class)
        public void whenParseMalformedXML() throws Exception {
            String malformedXML = "<root available-locales=\"en_US>";

            new XSDParser().parse(malformedXML, spanishLocale);
        }

        @Test(expected = IllegalArgumentException.class)
        public void whenParseEmptyStringXML() throws Exception {
            new XSDParser().parse("", spanishLocale);
        }

        @Test(expected = IllegalArgumentException.class)
        public void whenParseNullXML() throws Exception {
            new XSDParser().parse(null, spanishLocale);
        }

        @Test
        public void whenParseEmptyXML() throws Exception {
            String malformedXML = "<root available-locales=\"en_US\"></root>";

            List<Field> resultList = new XSDParser().parse(malformedXML, spanishLocale);

            assertNotNull(resultList);
            assertEquals(0, resultList.size());
        }
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
                + "name=\"A_Text\" "
                + "readOnly=\"false\" "
                + "repeatable=\"true\" "
                + "required=\"false\" "
                + "showLabel=\"true\" > "
                + "<meta-data locale=\"en_US\"> "
                + "<entry name=\"label\"><![CDATA[A Text]]></entry> "
                + "<entry name=\"predefinedValue\"><![CDATA[default text]]></entry> "
                + "<entry name=\"tip\"><![CDATA[The tip]]></entry> "
                + "</meta-data> "
                + "</dynamic-element>"
                + "</root>";

            List<Field> resultList = new XSDParser().parse(xsd, usLocale);

            assertNotNull(resultList);
            assertEquals(1, resultList.size());

            Field resultField = resultList.get(0);
            assertTrue(resultField instanceof RepeatableField);
            RepeatableField repeatableField = (RepeatableField) resultList.get(0);
            StringField stringField = (StringField) repeatableField.getBaseField();

            assertEquals(Field.DataType.STRING.getValue(), stringField.getDataType().getValue());
            assertEquals(Field.EditorType.TEXT.getValue(), stringField.getEditorType().getValue());
            assertEquals("A_Text", stringField.getName());
            assertEquals("A Text", stringField.getLabel());
            assertEquals("The tip", stringField.getTip());
            assertFalse(stringField.isReadOnly());
            assertTrue(stringField.isRepeatable());
            assertFalse(stringField.isRequired());
            assertFalse(stringField.isShowLabel());
            assertEquals("default text", stringField.getCurrentValue());
            assertEquals(stringField.getCurrentValue(), stringField.getPredefinedValue());
        }

        @Test
        public void shouldUseEmptyStringWhenCDATAIsEmpty() throws Exception {
            String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
                + "<dynamic-element "
                + "dataType=\"string\" "
                + "type=\"text\" "
                + "name=\"A_Text\" > "
                + "<meta-data locale=\"en_US\"> "
                + "<entry name=\"label\"><![CDATA[A Text]]></entry> "
                + "<entry name=\"predefinedValue\"><![CDATA[]]></entry> "
                + "<entry name=\"tip\"><![CDATA[]]></entry> "
                + "</meta-data> "
                + "</dynamic-element>"
                + "</root>";

            List<Field> resultList = new XSDParser().parse(xsd, usLocale);
            Field resultField = resultList.get(0);

            assertEquals("", resultField.getTip());
        }

        @Test
        public void shouldUseEmptyStringWhenEntryIsEmpty() throws Exception {
            String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
                + "<dynamic-element "
                + "dataType=\"string\" "
                + "type=\"text\" "
                + "name=\"A_Text\" > "
                + "<meta-data locale=\"en_US\"> "
                + "<entry name=\"label\"><![CDATA[A Text]]></entry> "
                + "<entry name=\"predefinedValue\"></entry> "
                + "<entry name=\"tip\"></entry> "
                + "</meta-data> "
                + "</dynamic-element>"
                + "</root>";

            List<Field> resultList = new XSDParser().parse(xsd, usLocale);
            Field resultField = resultList.get(0);

            assertEquals("", resultField.getTip());
        }

        @Test
        public void shouldUseEmptyStringWhenEntryIsNotPresent() throws Exception {
            String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
                + "<dynamic-element "
                + "dataType=\"string\" "
                + "type=\"text\" "
                + "name=\"A_Text\" > "
                + "<meta-data locale=\"en_US\"> "
                + "<entry name=\"label\"><![CDATA[A Text]]></entry> "
                + "</meta-data> "
                + "</dynamic-element>"
                + "</root>";

            List<Field> resultList = new XSDParser().parse(xsd, usLocale);
            Field resultField = resultList.get(0);

            assertEquals("", resultField.getTip());
        }

        @Test
        public void shouldUseEmptyStringWhenMetaDataIsNotPresent() throws Exception {
            String xsd = "<root available-locales=\"en_US\" default-locale=\"en_US\"> "
                + "<dynamic-element "
                + "dataType=\"string\" "
                + "type=\"text\" "
                + "name=\"A_Text\" > "
                + "</dynamic-element>"
                + "</root>";

            List<Field> resultList = new XSDParser().parse(xsd, usLocale);
            Field resultField = resultList.get(0);

            assertEquals("", resultField.getTip());
        }
    }
}