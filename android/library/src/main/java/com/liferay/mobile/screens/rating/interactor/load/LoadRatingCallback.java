package com.liferay.mobile.screens.rating.interactor.load;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class LoadRatingCallback extends JSONObjectCallback {
  public LoadRatingCallback(int targetScreenletId) {
    super(targetScreenletId);
  }

  @Override protected BasicEvent createEvent(int targetScreenletId, Exception e) {
    return new LoadRatingEvent(targetScreenletId, e);
  }

  @Override protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
    return new LoadRatingEvent(targetScreenletId, result);
  }
}
