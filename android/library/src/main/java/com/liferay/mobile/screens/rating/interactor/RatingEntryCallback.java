package com.liferay.mobile.screens.rating.interactor;

import com.liferay.mobile.android.callback.BaseCallback;
import com.liferay.mobile.screens.base.interactor.JSONArrayEvent;
import com.liferay.mobile.screens.util.EventBusUtil;
import org.json.JSONArray;

/**
 * @author Alejandro Hernández
 */

public class RatingEntryCallback extends BaseCallback<JSONArray> {

  public RatingEntryCallback(int targetScreenletId) {
    _targetScreenletId = targetScreenletId;
  }

  @Override public JSONArray inBackground(JSONArray result) throws Exception {
    return result.getJSONArray(0);
  }

  @Override
  public void onFailure(Exception e) {
    EventBusUtil.post(new JSONArrayEvent(getTargetScreenletId(), e));
  }

  @Override
  public void onSuccess(JSONArray result) {
    EventBusUtil.post(new JSONArrayEvent(getTargetScreenletId(), result));
  }

  public int getTargetScreenletId() {
    return _targetScreenletId;
  }

  private int _targetScreenletId;
}
