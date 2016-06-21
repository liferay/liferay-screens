package com.liferay.mobile.screens.rating.interactor;

import android.support.annotation.NonNull;
import com.liferay.mobile.android.callback.typed.JSONArrayCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.RatingEntry;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import com.liferay.mobile.screens.util.JSONUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    session.setCallback(new JSONArrayCallback() {
      @Override public void onFailure(Exception exception) {
        getListener().onRetrieveRatingEntriesFailure(exception);
      }

      @Override public void onSuccess(JSONArray result) {
        List<RatingEntry> ratings = new ArrayList();
        try {
          for (int i = 0; i < result.length(); i++) {
            ratings.add(new RatingEntry(JSONUtil.toMap(result.getJSONObject(i))));
          }
          getListener().onRetrieveRatingEntriesSuccess(ratings);
        } catch (JSONException e) {
          onFailure(e);
        }
      }
    });
    return new ScreensratingsentryService(session);
  }

  private final ScreensratingsentryService _screensratingsentryService;
}
