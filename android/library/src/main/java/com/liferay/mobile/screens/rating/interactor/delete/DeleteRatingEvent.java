package com.liferay.mobile.screens.rating.interactor.delete;

import com.liferay.mobile.screens.base.interactor.BasicEvent;

/**
 * @author Alejandro Hernández
 */
public class DeleteRatingEvent extends BasicEvent {

  public DeleteRatingEvent(int targetScreenletId) {
    super(targetScreenletId);
  }

  public DeleteRatingEvent(int targetScreenletId, Exception exception) {
    super(targetScreenletId, exception);
  }
}
