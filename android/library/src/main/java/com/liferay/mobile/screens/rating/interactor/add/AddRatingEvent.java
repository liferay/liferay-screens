package com.liferay.mobile.screens.rating.interactor.add;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class AddRatingEvent extends JSONObjectEvent {
  public AddRatingEvent(int targetScreenletId, Exception e) {
    super(targetScreenletId, e);
  }

  public AddRatingEvent(int targetScreenletId, JSONObject jsonObject) {
    super(targetScreenletId, jsonObject);
  }
}
