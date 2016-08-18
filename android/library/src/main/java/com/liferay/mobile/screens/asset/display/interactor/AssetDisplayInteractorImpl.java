package com.liferay.mobile.screens.asset.display.interactor;

import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.asset.list.interactor.AssetFactory;
import com.liferay.mobile.screens.base.thread.BaseCachedThreadRemoteInteractor;
import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import com.liferay.mobile.screens.service.v70.ScreensassetentryService;
import com.liferay.mobile.screens.util.JSONUtil;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayInteractorImpl
	extends BaseCachedThreadRemoteInteractor<AssetDisplayListener, AssetDisplayInteractorImpl.AssetEvent> {

	@Override
	public AssetEvent execute(Object... args) throws Exception {

		final long entryId = (long) args[0];

		ScreensassetentryService service = new ScreensassetentryService(getSession());
		JSONObject jsonObject = service.getAssetEntry(entryId, Locale.getDefault().getLanguage());
		return new AssetEvent(jsonObject);
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
	protected String getIdFromArgs(Object... args) throws Exception {
		final long entryId = (long) args[0];
		return String.valueOf(entryId);
	}

	public class AssetEvent extends OfflineEventNew {

		public AssetEvent() {
			super();
		}

		public AssetEvent(JSONObject jsonObject) {
			super(jsonObject);
		}
	}
}
