package com.liferay.mobile.screens.asset.display.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.asset.list.interactor.AssetFactory;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.ScreensassetentryService;
import com.liferay.mobile.screens.util.JSONUtil;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayInteractorImpl extends BaseRemoteInteractor<AssetDisplayListener>
	implements AssetDisplayInteractor {

	public AssetDisplayInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void getAssetEntry(long entryId) {
		try {
			Session session = SessionContext.createSessionFromCurrentSession();
			session.setCallback(new JSONObjectCallback(getTargetScreenletId()));
			ScreensassetentryService service = new ScreensassetentryService(session);
			service.getAssetEntry(entryId, Locale.getDefault().getLanguage());
		} catch (Exception e) {
			getListener().onRetrieveAssetFailure(e);
		}
	}

	@Override
	public void getAssetEntry(String className, long classPK) {
		try {
			Session session = SessionContext.createSessionFromCurrentSession();
			session.setCallback(new JSONObjectCallback(getTargetScreenletId()));
			ScreensassetentryService service = new ScreensassetentryService(session);
			service.getAssetEntry(className, classPK, Locale.getDefault().getLanguage());
		} catch (Exception e) {
			getListener().onRetrieveAssetFailure(e);
		}
	}

	public void onEvent(JSONObjectEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onRetrieveAssetFailure(event.getException());
		} else {
			try {
				Map<String, Object> map = JSONUtil.toMap(event.getJSONObject());
				AssetEntry assetEntry = AssetFactory.createInstance(map);
				getListener().onRetrieveAssetSuccess(assetEntry);
			} catch (JSONException e) {
				getListener().onRetrieveAssetFailure(e);
			}
		}
	}
}
