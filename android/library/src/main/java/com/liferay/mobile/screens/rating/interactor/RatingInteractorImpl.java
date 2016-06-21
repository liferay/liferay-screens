package com.liferay.mobile.screens.rating.interactor;

import android.support.annotation.NonNull;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONArrayEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import com.liferay.mobile.screens.util.LiferayLogger;
import org.json.JSONException;

/**
 * @author Alejandro Hernández
 */
public class RatingInteractorImpl extends BaseRemoteInteractor<RatingListener> implements RatingInteractor {

  public RatingInteractorImpl(int targetScreenletId) {
    super(targetScreenletId);
    _screensratingsentryService = getScreensratingsentryService();
  }

  @Override public void load(long assetId) throws Exception {
    _screensratingsentryService.getRatingsEntries(assetId);
  }

  @NonNull private ScreensratingsentryService getScreensratingsentryService() {
    Session session = SessionContext.createSessionFromCurrentSession();
    session.setCallback(new RatingEntryCallback(getTargetScreenletId()));
    return new ScreensratingsentryService(session);
  }

  public void onEvent(JSONArrayEvent event) {
    if (!isValidEvent(event)) {
      return;
    }

    if (event.isFailed()) {
      getListener().onRetrieveRatingEntriesFailure(event.getException());
    } else {
      try {
        getListener().onRetrieveRatingEntriesSuccess(RatingEntryFactory.createEntryList(event.getJsonArray()));
      } catch (JSONException e) {
        LiferayLogger.e(e.getMessage());
      }
    }
  }

  private final ScreensratingsentryService _screensratingsentryService;
}
