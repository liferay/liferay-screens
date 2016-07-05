package com.liferay.mobile.screens.gallery.interactor.load;

import android.util.Pair;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;

/**
 * @author Víctor Galán Grande
 */
public class GalleryLoadCallback extends BaseListCallback<ImageEntry> {

	public GalleryLoadCallback(int targetScreenletId, Pair<Integer, Integer> rowsRange, Locale locale) {
		super(targetScreenletId, rowsRange, locale);
	}

	@Override
	public ImageEntry createEntity(Map<String, Object> stringObjectMap) {
		return new ImageEntry(stringObjectMap);
	}
}
