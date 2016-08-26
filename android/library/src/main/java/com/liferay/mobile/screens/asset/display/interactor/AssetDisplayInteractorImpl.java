package com.liferay.mobile.screens.asset.display.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.asset.list.interactor.AssetFactory;
import com.liferay.mobile.screens.base.thread.BaseRemoteInteractorNew;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.ScreensassetentryService;
import com.liferay.mobile.screens.util.JSONUtil;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayInteractorImpl extends BaseRemoteInteractorNew<AssetDisplayListener, BasicThreadEvent> {

	@Override
	public BasicThreadEvent execute(Object... args) throws Exception {

		long entryId = (long) args[0];

		Session session = SessionContext.createSessionFromCurrentSession();
		ScreensassetentryService service = new ScreensassetentryService(session);
		JSONObject jsonObject = service.getAssetEntry(entryId, Locale.getDefault().getLanguage());
		return new BasicThreadEvent(jsonObject);
	}

	@Override
	public void onSuccess(BasicThreadEvent event) throws Exception {
		Map<String, Object> map = JSONUtil.toMap(event.getJSONObject());
		AssetEntry assetEntry = AssetFactory.createInstance(map);
		getListener().onRetrieveAssetSuccess(assetEntry);
	}

	@Override
	public void onFailure(Exception e) {
		getListener().onRetrieveAssetFailure(e);
	}
}
