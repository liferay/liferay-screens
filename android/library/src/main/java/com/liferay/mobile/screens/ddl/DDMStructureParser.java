package com.liferay.mobile.screens.ddl;

import com.liferay.mobile.screens.ddl.model.Field;
import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public interface DDMStructureParser {

    List<Field> parse(String content, Locale locale) throws Exception;
}
