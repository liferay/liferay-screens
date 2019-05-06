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
import java.util.List;
import java.util.Locale;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class XSDParserLocalizationTest {

    private static final Locale spanishLocale = new Locale("es", "ES");
    private static final String booleanFieldWithTranslationsXSD =
        "<root available-locales=\"es_ES, es_AR, es, en_US, en_AU\" default-locale=\"es_ES\"> "
            + "<dynamic-element dataType=\"boolean\" "
            + "name=\"Un_booleano\" "
            + "readOnly=\"false\" "
            + "repeatable=\"false\" "
            + "required=\"false\" "
            + "showLabel=\"true\" "
            + "type=\"checkbox\" > "
            + "<meta-data locale=\"en_US\"> "
            + "<entry name=\"label\">"
            + "<![CDATA[A Boolean for 'en_US']]>"
            + "</entry> "
            + "</meta-data> "
            + "<meta-data locale=\"en_AU\"> "
            + "<entry name=\"label\">"
            + "<![CDATA[An austral Boolean for 'en_AU']]>"
            + "</entry> "
            + "</meta-data> "
            + "<meta-data locale=\"es\"> "
            + "<entry name=\"label\">"
            + "<![CDATA[Un Booleano neutro para 'es']]>"
            + "</entry> "
            + "</meta-data> "
            + "<meta-data locale=\"es_ES\"> "
            + "<entry name=\"label\">"
            + "<![CDATA[Un Booleano para 'es_ES']]>"
            + "</entry> "
            + "</meta-data> "
            + "<meta-data locale=\"es_AR\"> "
            + "<entry name=\"label\">"
            + "<![CDATA[Un boludo Booleano para 'es_AR', ché]]>"
            + "</entry> "
            + "</meta-data> "
            + "</dynamic-element>"
            + "</root>";

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenExistingCompleteLocaleIsProvided {
        @Test
        public void shouldFindFullMatch() throws Exception {
            List<Field> fields = new XSDParser().parse(booleanFieldWithTranslationsXSD, spanishLocale);

            assertEquals("Un Booleano para 'es_ES'", fields.get(0).getLabel());
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenNoExistingCompleteLocaleIsProvided {
        @Test
        public void shouldFindNeutralLanguageMatch() throws Exception {
            List<Field> fields = new XSDParser().parse(booleanFieldWithTranslationsXSD, new Locale("es", "MX"));

            assertEquals("Un Booleano para 'es_ES'", fields.get(0).getLabel());
        }

        @Test
        public void shouldFindAnyLanguageMatch() throws Exception {
            List<Field> fields = new XSDParser().parse(booleanFieldWithTranslationsXSD, new Locale("en", "GB"));

            assertEquals("A Boolean for 'en_US'", fields.get(0).getLabel());
        }

        @Test
        public void shouldFindDefault() throws Exception {
            List<Field> fields = new XSDParser().parse(booleanFieldWithTranslationsXSD, new Locale("fr", "FR"));

            assertEquals("Un Booleano para 'es_ES'", fields.get(0).getLabel());
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenExistingNeutralLanguageIsProvided {
        @Test
        public void shouldFindNeutralLanguageMatch() throws Exception {
            List<Field> fields = new XSDParser().parse(booleanFieldWithTranslationsXSD, new Locale("es"));

            assertEquals("Un Booleano neutro para 'es'", fields.get(0).getLabel());
        }
    }

    //@Config(constants = BuildConfig.class)
    @RunWith(RobolectricTestRunner.class)
    public static class WhenNoExistingNeutralLanguageIsProvided {
        @Test
        public void shouldFindDefault() throws Exception {
            List<Field> fields = new XSDParser().parse(booleanFieldWithTranslationsXSD, new Locale("fr"));

            String defaultMobileLocale = "es_ES";
            assertEquals("Un Booleano para '" + defaultMobileLocale + "'", fields.get(0).getLabel());
        }

        @Test
        public void shouldFindAnyLanguageMatch() throws Exception {
            List<Field> fields = new XSDParser().parse(booleanFieldWithTranslationsXSD, new Locale("en"));

            assertEquals("A Boolean for 'en_US'", fields.get(0).getLabel());
        }
    }
}