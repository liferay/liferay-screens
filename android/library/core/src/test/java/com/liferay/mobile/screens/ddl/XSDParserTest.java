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

import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.interactor.AuthMethod;
import com.liferay.mobile.screens.auth.login.interactor.LoginInteractorImpl;
import com.liferay.mobile.screens.ddl.field.Field;
import com.liferay.mobile.screens.ddl.field.StringField;
import com.liferay.mobile.screens.util.LiferayServerContext;
import com.liferay.mobile.screens.util.MockFactory;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.xml.sax.SAXParseException;

import java.util.List;
import java.util.Locale;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.doReturn;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class XSDParserTest {

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenMalformedXML {

		@Test(expected = SAXParseException.class)
		public void shouldRaiseParserException() throws Exception {
			String malformedXML = "<root available-locales=\"en_US>";

			new XSDParser().parse(malformedXML, _spanishLocale);
		}
	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenEmptyStringXML {

		@Test(expected = IllegalArgumentException.class)
		public void shouldRaiseIllegalArgument() throws Exception {
			new XSDParser().parse("", _spanishLocale);
		}
	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenNullXML {

		@Test(expected = IllegalArgumentException.class)
		public void shouldRaiseIllegalArgument() throws Exception {
			new XSDParser().parse(null, _spanishLocale);
		}
	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenEmptyXML {

		@Test
		public void shouldReturnEmptyResult() throws Exception {
			String malformedXML = "<root available-locales=\"en_US\"></root>";

			List<Field> resultList = new XSDParser().parse(malformedXML, _spanishLocale);

			assertNotNull(resultList);
			assertEquals(0, resultList.size());
		}
	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenStringFieldExists {

		@Test
		public void shouldReturnStringFieldObject() throws Exception {
			String xsd =
				"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
					"<dynamic-element dataType=\"string\" " +
							"indexType=\"keyword\" " +
							"name=\"A_Text\" " +
							"readOnly=\"false\" " +
							"repeatable=\"true\" " +
							"required=\"false\" " +
							"showLabel=\"true\" " +
							"type=\"text\" " +
							"width=\"small\"> " +
						"<meta-data locale=\"en_US\"> " +
							"<entry name=\"label\">" +
								"<![CDATA[A Text]]>" +
							"</entry> " +
							"<entry name=\"predefinedValue\">" +
								"<![CDATA[predefined text]]>" +
							"</entry> " +
							"<entry name=\"tip\">" +
								"<![CDATA[The tip]]>" +
							"</entry> " +
						"</meta-data> " +
					"</dynamic-element>" +
				"</root>";

			List<Field> resultList = new XSDParser().parse(xsd, new Locale("en", "US"));

			assertNotNull(resultList);
			assertEquals(1, resultList.size());

			Field resultField = resultList.get(0);
			assertTrue(resultField instanceof StringField);
			StringField stringField = (StringField) resultField;

			assertEquals(Field.DataType.STRING.getValue(), stringField.getDataType().getValue());
			assertEquals(Field.EditorType.TEXT.getValue(), stringField.getEditorType().getValue());
			assertEquals("A_Text", stringField.getName());
			assertFalse(stringField.isReadOnly());
			assertTrue(stringField.isRepeatable());
			assertFalse(stringField.isRequired());
			assertTrue(stringField.isShowLabel());
			assertEquals("A Text", stringField.getLabel());
			assertEquals("The tip", stringField.getTip());
			assertEquals("predefined text", stringField.getPredefinedValue());
			assertEquals("predefined text", stringField.getCurrentValue());
		}
	}



	private static final Locale _spanishLocale = new Locale("es", "ES");

}