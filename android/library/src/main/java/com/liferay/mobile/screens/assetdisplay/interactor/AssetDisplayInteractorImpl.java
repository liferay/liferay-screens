package com.liferay.mobile.screens.assetdisplay.interactor;

import android.support.annotation.NonNull;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.assetdisplay.AssetDisplayListener;
import com.liferay.mobile.screens.assetlist.interactor.AssetFactory;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.ScreensassetentryService;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.Locale;
import org.json.JSONException;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayInteractorImpl extends BaseRemoteInteractor<AssetDisplayListener> implements AssetDisplayInteractor {

  public AssetDisplayInteractorImpl(int targetScreenletId) {
    super(targetScreenletId);
    _service = getScreensAssetEntryService();
  }

  @Override public void getAssetEntryExtended(long entryId) throws Exception {
    _service.getAssetEntryExtended(entryId, Locale.getDefault().getLanguage());
  }

  public void onEvent(JSONObjectEvent event) {
    if (!isValidEvent(event)) {
      return;
    }

    if (event.isFailed()) {
      getListener().onRetrieveAssetFailure(event.getException());
    } else {
      try {
        getListener().onRetrieveAssetSuccess(AssetFactory.createInstance(JSONUtil.toMap(event.getJSONObject())));
      } catch (JSONException e) {
        LiferayLogger.e(e.getMessage());
      }
    }
  }

  @NonNull private ScreensassetentryService getScreensAssetEntryService() {
    Session session = SessionContext.createSessionFromCurrentSession();
    session.setCallback(new JSONObjectCallback(getTargetScreenletId()));
    return new ScreensassetentryService(session);
  }

  private final ScreensassetentryService _service;
}
