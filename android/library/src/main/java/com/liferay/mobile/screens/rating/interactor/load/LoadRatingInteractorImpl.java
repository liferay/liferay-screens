package com.liferay.mobile.screens.rating.interactor.load;

import android.support.annotation.NonNull;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.rating.interactor.RatingEntryFactory;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import com.liferay.mobile.screens.util.LiferayLogger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class LoadRatingInteractorImpl extends BaseRemoteInteractor<RatingListener> implements
    LoadRatingInteractor {

  public LoadRatingInteractorImpl(int targetScreenletId) {
    super(targetScreenletId);
    _screensratingsentryService = getScreensratingsentryService();
  }

  @Override public void loadRatings(long assetId) throws Exception {
    _screensratingsentryService.getRatingsEntries(assetId);
  }

  @NonNull private ScreensratingsentryService getScreensratingsentryService() {
    Session session = SessionContext.createSessionFromCurrentSession();
    session.setCallback(new LoadRatingCallback(getTargetScreenletId()));
    return new ScreensratingsentryService(session);
  }

  public void onEvent(LoadRatingEvent event) {
    if (!isValidEvent(event)) {
      return;
    }

    if (event.isFailed()) {
      getListener().onRetrieveRatingEntriesFailure(event.getException());
    } else {
      try {
        JSONObject result = event.getJSONObject();
        getListener().onRetrieveRatingEntriesSuccess(result.getLong("classPK"),
            result.getString("className"), RatingEntryFactory.createEntryList(result.getJSONArray("entries")));
      } catch (JSONException e) {
        LiferayLogger.e(e.getMessage());
      }
    }
  }

  private final ScreensratingsentryService _screensratingsentryService;
}
