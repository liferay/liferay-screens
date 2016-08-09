package com.liferay.mobile.screens.asset.list.interactor;

import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.dlfile.display.FileEntry;
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
			String className = (String) map.get("className");

			if (stringLocale != null) {
				Locale locale = LiferayLocale.getLocaleWithoutDefault(stringLocale);

				if (className.endsWith("JournalArticle")) {
					return new WebContent(map, locale);
				} else if (className.endsWith("DDLRecord")) {
					return new Record(map, locale);
				} else if (className.endsWith("DLFileEntry")) {
					return new FileEntry(map);
				}
			}
		}
		return new AssetEntry(map);
	}

	private AssetFactory() {
		super();
	}
}
