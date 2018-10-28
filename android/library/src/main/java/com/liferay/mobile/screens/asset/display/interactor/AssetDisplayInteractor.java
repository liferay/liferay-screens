package com.liferay.mobile.screens.asset.display.interactor;

import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.AssetEvent;
import com.liferay.mobile.screens.asset.AssetFactory;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.asset.list.connector.ScreensAssetEntryConnector;
import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayInteractor extends BaseCacheReadInteractor<AssetDisplayListener, AssetEvent> {

    @Override
    public AssetEvent execute(Object... args) throws Exception {

        JSONObject jsonObject = getAsset(args);
        return new AssetEvent(jsonObject);
    }

    private JSONObject getAsset(Object... args) throws Exception {
        ScreensAssetEntryConnector connector =
            ServiceProvider.getInstance().getScreensAssetEntryConnector(getSession());
        if (args.length > 1) {
            String className = (String) args[0];
            long classPK = (long) args[1];
            return connector.getAssetEntry(className, classPK, Locale.getDefault().getLanguage());
        } else if (args[0] instanceof Long) {
            long entryId = (long) args[0];

            return connector.getAssetEntry(entryId, Locale.getDefault().getLanguage());
        } else {
            String portletItemName = (String) args[0];

            JSONArray assetEntry =
                connector.getAssetEntries(LiferayServerContext.getCompanyId(), groupId, portletItemName,
                    locale.toString(), 1);

            if (assetEntry.length() == 0) {
                throw new NoSuchElementException();
            }

            return assetEntry.getJSONObject(0);
        }
    }

    @Override
    public void onSuccess(AssetEvent event) {
        AssetEntry assetEntry;
        try {
            Map<String, Object> map = JSONUtil.toMap(event.getJSONObject());
            assetEntry = AssetFactory.createInstance(map);
        } catch (JSONException ex) {
            event.setException(ex);
            onFailure(event);
            return;
        }
        getListener().onRetrieveAssetSuccess(assetEntry);
    }

    @Override
    public void onFailure(AssetEvent event) {
        getListener().error(event.getException(), AssetDisplayScreenlet.DEFAULT_ACTION);
    }

    @Override
    protected String getIdFromArgs(Object... args) {
        if (args.length > 1) {
            return String.valueOf((long) args[1]);
        } else if (args[0] instanceof String) {
            return (String) args[0];
        }
        return String.valueOf((long) args[0]);
    }
}
