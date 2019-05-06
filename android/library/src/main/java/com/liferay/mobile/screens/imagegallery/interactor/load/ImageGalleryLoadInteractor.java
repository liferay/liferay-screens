package com.liferay.mobile.screens.imagegallery.interactor.load;

import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.ddl.form.connector.DLAppConnector;
import com.liferay.mobile.screens.imagegallery.interactor.ImageGalleryEvent;
import com.liferay.mobile.screens.imagegallery.interactor.ImageGalleryInteractorListener;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.Map;
import org.json.JSONArray;

/**
 * @author Víctor Galán Grande
 */
public class ImageGalleryLoadInteractor extends BaseListInteractor<ImageGalleryInteractorListener, ImageGalleryEvent> {

    private static final JSONArray DEFAULT_MIME_TYPES =
        new JSONArray().put("image/png").put("image/jpeg").put("image/gif");

    @Override
    protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {
        long repositoryId = (long) args[0];
        long folderId = (long) args[1];
        JSONArray mimeTypes = getMimeTypes((String[]) args[2]);

        validate(repositoryId, folderId);

        DLAppConnector connector = ServiceProvider.getInstance().getDLAppConnector(getSession());

        return connector.getFileEntries(repositoryId, folderId, mimeTypes, query.getStartRow(), query.getEndRow(),
            query.getComparatorJSONWrapper());
    }

    @Override
    protected Integer getPageRowCountRequest(Object... args) throws Exception {
        long repositoryId = (long) args[0];
        long folderId = (long) args[1];
        JSONArray mimeTypes = getMimeTypes((String[]) args[2]);

        validate(repositoryId, folderId);

        DLAppConnector connector = ServiceProvider.getInstance().getDLAppConnector(getSession());

        return connector.getFileEntriesCount(repositoryId, folderId, mimeTypes);
    }

    @Override
    protected String getIdFromArgs(Object... args) {
        return String.valueOf((long) args[1]);
    }

    @Override
    protected ImageGalleryEvent createEntity(Map<String, Object> stringObjectMap) {
        return new ImageGalleryEvent(new ImageEntry(stringObjectMap));
    }

    protected void validate(long repositoryId, long folderId) {
        if (repositoryId <= 0) {
            throw new IllegalArgumentException("repository cannot be 0 or negative");
        } else if (folderId < 0) {
            throw new IllegalArgumentException("folderId cannot be negative");
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
