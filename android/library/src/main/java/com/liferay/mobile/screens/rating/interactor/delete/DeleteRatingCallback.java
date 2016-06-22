package com.liferay.mobile.screens.rating.interactor.delete;

import com.liferay.mobile.android.callback.typed.GenericCallback;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class DeleteRatingCallback extends InteractorAsyncTaskCallback<Object> {

  public DeleteRatingCallback(int targetScreenletId) {
    super(targetScreenletId);
  }

  @Override protected BasicEvent createEvent(int targetScreenletId, Exception e) {
    return new DeleteRatingEvent(targetScreenletId, e);
  }

  @Override protected BasicEvent createEvent(int targetScreenletId, Object result) {
    return new DeleteRatingEvent(targetScreenletId);
  }

  @Override public Object transform(Object obj) throws Exception {
    return null;
  }
}
