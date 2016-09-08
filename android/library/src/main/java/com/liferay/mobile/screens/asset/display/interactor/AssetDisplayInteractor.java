package com.liferay.mobile.screens.asset.display.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.asset.AssetEvent;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.asset.list.interactor.AssetFactory;
import com.liferay.mobile.screens.base.thread.BaseCacheReadInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.ScreensassetentryService;
import com.liferay.mobile.screens.util.JSONUtil;
import java.util.Locale;
import java.util.Map;
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
		Session session = SessionContext.createSessionFromCurrentSession();
		if (args.length > 1) {
			String className = (String) args[0];
			long classPK = (long) args[1];

			ScreensassetentryService service = new ScreensassetentryService(session);
			return service.getAssetEntry(className, classPK, Locale.getDefault().getLanguage());
		} else {
			long entryId = (long) args[0];

			ScreensassetentryService service = new ScreensassetentryService(getSession());
			return service.getAssetEntry(entryId, Locale.getDefault().getLanguage());
		}
	}

	@Override
	public void onSuccess(AssetEvent event) throws Exception {
		Map<String, Object> map = JSONUtil.toMap(event.getJSONObject());
		AssetEntry assetEntry = AssetFactory.createInstance(map);
		getListener().onRetrieveAssetSuccess(assetEntry);
	}

	@Override
	public void onFailure(Exception e) {
		getListener().error(e, AssetDisplayScreenlet.DEFAULT_ACTION);
	}

	@Override
	protected String getIdFromArgs(Object... args) {
		final long entryId = (long) args[0];
		return String.valueOf(entryId);
	}
}
