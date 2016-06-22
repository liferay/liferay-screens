package com.liferay.mobile.screens.rating.interactor.add;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class AddRatingCallback extends JSONObjectCallback {
  public AddRatingCallback(int targetScreenletId) {
    super(targetScreenletId);
  }

  @Override protected BasicEvent createEvent(int targetScreenletId, Exception e) {
    return new AddRatingEvent(targetScreenletId, e);
  }

  @Override protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
    return new AddRatingEvent(targetScreenletId, result);
  }
}
