package com.liferay.mobile.screens.rating.interactor.update;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class UpdateRatingCallback extends JSONObjectCallback {
  public UpdateRatingCallback(int targetScreenletId) {
    super(targetScreenletId);
  }

  @Override protected BasicEvent createEvent(int targetScreenletId, Exception e) {
    return new UpdateRatingEvent(targetScreenletId, e);
  }

  @Override protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
    return new UpdateRatingEvent(targetScreenletId, result);
  }
}
