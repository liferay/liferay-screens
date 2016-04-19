package com.liferay.mobile.screens.assetlist.interactor;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.LiferayLocale;
import com.liferay.mobile.screens.webcontent.WebContent;

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
				return new WebContent(map, locale);
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
}
