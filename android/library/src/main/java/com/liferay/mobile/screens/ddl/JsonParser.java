package com.liferay.mobile.screens.ddl;

import android.support.annotation.NonNull;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.util.LiferayLocale;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class JsonParser implements DDMStructureParser {

	public List<Field> parse(String json, Locale locale) {
		if (json == null || json.isEmpty()) {
			throw new IllegalArgumentException("json cannot be empty");
		}

		try {
			JSONObject jsonObject = new JSONObject(json);
			return processDocument(jsonObject, locale);
		} catch (JSONException e) {
			LiferayLogger.e("Error parsing form", e);
		}

		return null;
	}

	protected List<Field> processDocument(JSONObject jsonObject, Locale locale) throws JSONException {

		List<Field> result = new ArrayList<>();

		String defaultLocaleValue = jsonObject.getString("defaultLanguageId");
		if (defaultLocaleValue == null) {
			return null;
		}

		Locale formLocale = parseLocale(defaultLocaleValue);

		JSONArray fields = jsonObject.getJSONArray("fields");

		for (int i = 0; i < fields.length(); i++) {
			Field formField = createFormField(fields.getJSONObject(i), formLocale, locale);

			if (formField != null) {
				result.add(formField);
			}
		}

		return result;
	}

	protected Field createFormField(JSONObject field, Locale formLocale, Locale locale) throws JSONException {

		String textDataType = field.has("dataType") ? field.getString("dataType") : null;
		Field.DataType dataType = Field.DataType.assignDataTypeFromString(textDataType);

		Map<String, Object> attributes = getAttributes(field);

		Map<String, Object> localizedMetadata = processLocalizedMetadata(field, formLocale, locale);

		Map<String, Object> mergedMap = new HashMap<>();

		mergedMap.putAll(attributes);
		mergedMap.putAll(localizedMetadata);

		Field newField = dataType.createField(mergedMap, locale, formLocale);

		if (attributes.containsKey("nestedFields")) {
			JSONArray jsonArray = (JSONArray) attributes.get("nestedFields");
			for (int i = 0; i < jsonArray.length(); i++) {
				Field formField = createFormField(jsonArray.getJSONObject(i), formLocale, locale);
				if (formField != null) {
					newField.getFields().add(formField);
				}
			}
		}

		return newField;
	}

	protected Map<String, Object> processLocalizedMetadata(JSONObject field, Locale formLocale, Locale locale)
		throws JSONException {

		Map<String, Object> result = new HashMap<>();
		result.put("label", addLocalizedElement(field, "label", formLocale, locale));
		result.put("predefinedValue", addLocalizedElement(field, "predefinedValue", formLocale, locale));
		result.put("tip", addLocalizedElement(field, "tip", formLocale, locale));

		if (field.has("options")) {
			result.put("options", findOptions(field, locale, formLocale));
		}
		return result;
	}

	protected String addLocalizedElement(JSONObject jsonObject, String key, Locale formLocale, Locale locale)
		throws JSONException {
		if (jsonObject.has(key)) {
			return addLocalizedElement(jsonObject.getJSONObject(key), formLocale, locale);
		}
		return "";
	}

	protected String addLocalizedElement(JSONObject field, Locale formLocale, Locale locale) throws JSONException {

		// Locale matching fallback mechanism: it's designed in such a way to return
		// the most suitable locale among the available ones. It minimizes the default
		// locale fallback. It supports input both with one component (language) and
		// two components (language and country).
		//
		// Examples for locale = "es_ES"
		// 	a1. Matches elements with "es_ES" (full match)
		//  a2. Matches elements with "es"
		//  a3. Matches elements for any country: "es_ES", "es_AR"...
		//  a4. Matches elements for default locale

		// Examples for locale = "es"
		// 	b1. Matches elements with "es" (full match)
		//  b2. Matches elements for any country: "es_ES", "es_AR"...
		//  b3. Matches elements for default locale

		if (field.has(locale.toString())) {
			//			 cases 'a1' and 'b1'
			return field.getString(locale.toString());
		}

		String supportedLocale = LiferayLocale.getSupportedLocaleWithNoDefault(locale.getLanguage());
		// Pre-final fallback (a2, a3, b2): find any metadata with the portal supported languages

		if (supportedLocale.isEmpty()) {
			return field.getString(supportedLocale);
		}

		// Final fallback (a4, b3): find default metadata
		return field.getString(formLocale.toString());
	}

	protected List<Map<String, String>> findOptions(JSONObject field, Locale locale, Locale defaultLocale)
		throws JSONException {

		JSONArray options = field.getJSONArray("options");

		List<Map<String, String>> result = new ArrayList<>();

		for (int i = 0; i < options.length(); i++) {
			JSONObject jsonObject = options.getJSONObject(i);

			Map<String, String> optionMap = new HashMap<>();

			optionMap.put("value", jsonObject.getString("value"));
			String label = addLocalizedElement(jsonObject.getJSONObject("label"), locale, defaultLocale);
			optionMap.put("label", label == null ? optionMap.get("value") : label);
			result.add(optionMap);
		}

		return result;
	}

	protected Map<String, Object> getAttributes(JSONObject field) throws JSONException {

		Map<String, Object> result = new HashMap<>();
		Iterator<String> it = field.keys();
		while (it.hasNext()) {
			String key = it.next();
			result.put(key, field.get(key));
		}

		return result;
	}

	@NonNull
	private Locale parseLocale(String defaultLocaleValue) {

		int separator = defaultLocaleValue.indexOf('_');

		if (separator == -1) {
			return new Locale(defaultLocaleValue);
		} else {
			String language = defaultLocaleValue.substring(0, separator);
			String country = defaultLocaleValue.substring(separator + 1);
			return new Locale(language, country);
		}
	}
}