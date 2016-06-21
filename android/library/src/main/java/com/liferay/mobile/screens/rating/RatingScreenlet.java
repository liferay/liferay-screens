package com.liferay.mobile.screens.rating;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.rating.interactor.RatingInteractor;
import com.liferay.mobile.screens.rating.interactor.RatingInteractorImpl;
import com.liferay.mobile.screens.rating.view.RatingViewModel;
import java.util.List;

/**
 * @author Alejandro Hernández
 */

public class RatingScreenlet extends BaseScreenlet<RatingViewModel, RatingInteractor> implements RatingListener {

  public static final String LOAD_RATINGS_ACTION = "loadRatings";

  public RatingScreenlet(Context context) {
    super(context);
  }

  public RatingScreenlet(Context context, AttributeSet attributes) {
    super(context, attributes);
  }

  public RatingScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
    super(context, attributes, defaultStyle);
  }

  @Override protected View createScreenletView(Context context, AttributeSet attributes) {
    TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributes, R.styleable.RatingScreenlet, 0, 0);

    int layoutId = typedArray.getResourceId(R.styleable.RatingScreenlet_layoutId, 0);

    View view = LayoutInflater.from(context).inflate(layoutId, null);

    _entryId = castToLong(typedArray.getString(R.styleable.RatingScreenlet_entryId));

    typedArray.recycle();

    return view;
  }

  @Override protected RatingInteractor createInteractor(String actionName) {
    return new RatingInteractorImpl(getScreenletId());
  }

  @Override protected void onScreenletAttached() {
    super.onScreenletAttached();

    try {
      getInteractor().load(_entryId);
    } catch (Exception e) {
      this.onRetrieveRatingEntriesFailure(e);
    }
  }

  @Override
  protected void onUserAction(String userActionName, RatingInteractor interactor, Object... args) {

  }

  @Override public void onRetrieveRatingEntriesFailure(Exception exception) {
    getViewModel().showFailedOperation(null, exception);

    if (_listener != null) {
      _listener.onRetrieveRatingEntriesFailure(exception);
    }
  }

  @Override public void onRetrieveRatingEntriesSuccess(List<RatingEntry> ratings) {
    getViewModel().showFinishOperation(LOAD_RATINGS_ACTION, ratings);

    if (_listener != null) {
      _listener.onRetrieveRatingEntriesSuccess(ratings);
    }
  }

  public RatingListener getListener() {
    return _listener;
  }

  public void setListener(RatingListener listener) {
    this._listener = listener;
  }

  private long _entryId;

  private RatingListener _listener;
}
