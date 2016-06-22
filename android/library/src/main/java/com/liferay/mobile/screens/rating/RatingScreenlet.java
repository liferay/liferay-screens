package com.liferay.mobile.screens.rating;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractor;
import com.liferay.mobile.screens.rating.interactor.add.AddRatingInteractor;
import com.liferay.mobile.screens.rating.interactor.add.AddRatingInteractorImpl;
import com.liferay.mobile.screens.rating.interactor.load.LoadRatingInteractor;
import com.liferay.mobile.screens.rating.interactor.load.LoadRatingInteractorImpl;
import com.liferay.mobile.screens.rating.view.RatingViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.List;

/**
 * @author Alejandro Hernández
 */

public class RatingScreenlet extends BaseScreenlet<RatingViewModel, BaseRatingInteractor> implements RatingListener {

  public static final String LOAD_RATINGS_ACTION     = "loadRatings";
  public static final String LOAD_USER_RATING_ACTION = "loadUserRating";
  public static final String ADD_RATING_ACTION       = "addRating";
  public static final String UPDATE_RATING_ACTION    = "updateRating";

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

  @Override protected BaseRatingInteractor createInteractor(String actionName) {
    switch (actionName) {
      case LOAD_RATINGS_ACTION:
        return new LoadRatingInteractorImpl(getScreenletId());
      case ADD_RATING_ACTION:
        return new AddRatingInteractorImpl(getScreenletId());
      default:
        return null;
    }
  }

  @Override protected void onScreenletAttached() {
    super.onScreenletAttached();

    try {
      ((LoadRatingInteractor) getInteractor(LOAD_RATINGS_ACTION)).loadRatings(_entryId);
    } catch (Exception e) {
      this.onRetrieveRatingEntriesFailure(e);
    }
  }

  @Override
  protected void onUserAction(String userActionName, BaseRatingInteractor interactor, Object... args) {
    switch (userActionName) {
      case ADD_RATING_ACTION:
        double score = (double) args[0];
        try {
          ((AddRatingInteractor) interactor).addRating(_className, _classPK, score);
        } catch (Exception e) {
          LiferayLogger.e(e.getMessage());
          onAddRatingEntryFailure(e);
        }
        break;
      default:
        break;
    }
  }

  @Override public void onRetrieveRatingEntriesFailure(Exception exception) {
    getViewModel().showFailedOperation(LOAD_RATINGS_ACTION, exception);

    if (_listener != null) {
      _listener.onRetrieveRatingEntriesFailure(exception);
    }
  }

  @Override public void onAddRatingEntryFailure(Exception exception) {
    getViewModel().showFailedOperation(ADD_RATING_ACTION, exception);

    if (_listener != null) {
      _listener.onAddRatingEntryFailure(exception);
    }
  }

  @Override public void onRetrieveRatingEntriesSuccess(long classPK, String className, List<RatingEntry> ratings) {
    getViewModel().showFinishOperation(LOAD_RATINGS_ACTION, ratings);

    _classPK = classPK;
    _className = className;

    if (checkUserEntry(ratings)) {
      getViewModel().showFinishOperation(LOAD_USER_RATING_ACTION, _userRatingEntry);
    }

    if (_listener != null) {
      _listener.onRetrieveRatingEntriesSuccess(classPK, className, ratings);
    }
  }

  @Override public void onAddRatingEntrySuccess(RatingEntry entry) {
    if (_userRatingEntry == null) {
      getViewModel().showFinishOperation(ADD_RATING_ACTION, entry);
    } else {
      getViewModel().showFinishOperation(UPDATE_RATING_ACTION, entry);
    }

    _userRatingEntry = entry;

    if (_listener != null) {
      _listener.onAddRatingEntrySuccess(entry);
    }
  }

  private boolean checkUserEntry(List<RatingEntry> ratings) {
    for (RatingEntry entry : ratings) {
      if (entry.getUserId() == SessionContext.getCurrentUser().getId()) {
        _userRatingEntry = entry;
        return true;
      }
    }
    return false;
  }

  public RatingListener getListener() {
    return _listener;
  }

  public void setListener(RatingListener listener) {
    this._listener = listener;
  }

  private RatingEntry _userRatingEntry;
  private RatingListener _listener;
  private long _entryId;
  private long _classPK;
  private String _className;
}
