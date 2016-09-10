package com.liferay.mobile.screens.gallery.interactor.load;

import com.liferay.mobile.android.v7.dlapp.DLAppService;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.gallery.interactor.GalleryEvent;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractorListener;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import java.util.Map;
import org.json.JSONArray;

/**
 * @author Víctor Galán Grande
 */
public class GalleryLoadInteractor extends BaseListInteractor<GalleryInteractorListener, GalleryEvent> {

	private static final JSONArray DEFAULT_MIME_TYPES =
		new JSONArray().put("image/png").put("image/jpeg").put("image/gif");

	@Override
	protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {
		long folderId = (long) args[0];
		JSONArray mimeTypes = getMimeTypes((String[]) args[1]);

		validate(groupId, folderId);

		return new DLAppService(getSession()).getFileEntries(groupId, folderId, mimeTypes, query.getStartRow(),
			query.getEndRow(), query.getComparatorJSONWrapper());
	}

	@Override
	protected Integer getPageRowCountRequest(Object... args) throws Exception {

		long folderId = (long) args[0];
		JSONArray mimeTypes = getMimeTypes((String[]) args[1]);

		validate(groupId, folderId);

		return new DLAppService(getSession()).getFileEntriesCount(groupId, folderId, mimeTypes);
	}

	@Override
	protected String getIdFromArgs(Object... args) {
		return String.valueOf((long) args[0]);
	}

	@Override
	protected GalleryEvent createEntity(Map<String, Object> stringObjectMap) {
		return new GalleryEvent(new ImageEntry(stringObjectMap));
	}

	protected void validate(long groupId, long folderId) {
		if (groupId <= 0) {
			throw new IllegalArgumentException("groupId cannot be 0 or negative");
		} else if (folderId < 0) {
			throw new IllegalArgumentException("groupId cannot be negative");
		}
	}

	private JSONArray getMimeTypes(String[] mimeTypes) {
		if (mimeTypes == null) {
			return DEFAULT_MIME_TYPES;
		}
		if (mimeTypes.length == 1 && mimeTypes[0].isEmpty()) {
			return DEFAULT_MIME_TYPES;
		}

		JSONArray jsonMimeTypes = new JSONArray();

		for (String mimeType : mimeTypes) {
			jsonMimeTypes.put(mimeType);
		}

		return jsonMimeTypes;
	}
}
