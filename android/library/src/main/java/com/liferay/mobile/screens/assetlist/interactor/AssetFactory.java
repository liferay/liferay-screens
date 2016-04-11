package com.liferay.mobile.screens.assetlist.interactor;

import android.support.annotation.NonNull;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.ddl.ContentParser;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.util.LiferayLocale;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class AssetFactory {

	public static AssetEntry createInstance(Map<String, Object> map) {

		if (map.containsKey("object")) {
			map.putAll((Map) map.get("object"));

			String stringLocale = (String) map.get("locale");
			Locale locale = LiferayLocale.getLocaleWithoutDefault(stringLocale);

			if (map.containsKey("DDMStructure")) {
				return createWebContent(map, locale);
			}
			else if (map.containsKey("modelValues")) {
				return new Record(map, locale);
			}
		}
		return new AssetEntry(map);
	}

	private AssetFactory() {
		super();
	}

	@NonNull
	private static AssetEntry createWebContent(Map<String, Object> map, Locale locale) {
		WebContent webContent = new WebContent(map, locale);

		try {
			HashMap ddmStructure = (HashMap) map.get("DDMStructure");
			webContent.parseDDMStructure(new JSONObject(ddmStructure));

			ContentParser contentParser = new ContentParser();
			List<Field> fields = contentParser.parseContent(webContent.getDDMStructure(),
				(String) map.get("modelValues"));

			webContent.getDDMStructure().setFields(fields);
		}
		catch (JSONException e) {
			LiferayLogger.e("Error parsing structure");
		}

		return webContent;
	}
}
