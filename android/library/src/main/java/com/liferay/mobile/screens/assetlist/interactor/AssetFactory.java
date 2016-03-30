package com.liferay.mobile.screens.assetlist.interactor;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.ddl.model.DDMAssetEntry;
import com.liferay.mobile.screens.ddl.model.Record;

import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class AssetFactory {

	public static AssetEntry createInstance(Map<String, Object> map) {

		if (map.containsKey("object")) {
			map.putAll((Map) map.get("object"));
			if (map.containsKey("DDMStructure")) {
				return new DDMAssetEntry(map, null);
			}
			else if (map.containsKey("modelValues")) {
				return new Record(map, null);
			}
		}
		return new AssetEntry(map);
	}

	private AssetFactory() {
		super();
	}
}
