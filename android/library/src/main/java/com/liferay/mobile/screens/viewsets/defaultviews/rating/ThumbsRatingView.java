package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.rating.RatingEntry;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.rating.view.RatingViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class ThumbsRatingView extends LinearLayout implements RatingViewModel, View.OnClickListener {

  public ThumbsRatingView(Context context) {
      super(context);
  }

  public ThumbsRatingView(Context context, AttributeSet attrs) {
      super(context, attrs);
  }

  public ThumbsRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
  }

  @Override public void showStartOperation(String actionName) {

  }

  @Override public void showFailedOperation(String actionName, Exception e) {
    switch (actionName) {
      case RatingScreenlet.LOAD_RATINGS_ACTION:
        LiferayLogger.e(getContext().getString(R.string.loading_ratings_error), e);
        break;
      case RatingScreenlet.LOAD_USER_RATING_ACTION:
        LiferayLogger.e(getContext().getString(R.string.loading_user_rating_error), e);
        break;
      case RatingScreenlet.ADD_RATING_ACTION:
        LiferayLogger.e(getContext().getString(R.string.adding_error_rating), e);
        break;
      case RatingScreenlet.UPDATE_RATING_ACTION:
        LiferayLogger.e(getContext().getString(R.string.updating_rating_error), e);
        break;
      default:
        LiferayLogger.e(getContext().getString(R.string.ratings_error), e);
        break;
    }
  }

  @Override public void showFinishOperation(String actionName) {
    showFinishOperation(actionName, null);
  }

  @Override public void showFinishOperation(String action, Object argument) {
    switch (action) {
      case RatingScreenlet.LOAD_RATINGS_ACTION:
        _negativeCount = _possitiveCount = 0;
        _userScore = -1;

        for (RatingEntry entry : (List<RatingEntry>) argument) {
          updateGlobalScore(entry.getScore());
        }

        break;
      case RatingScreenlet.LOAD_USER_RATING_ACTION:
        _userScore = ((RatingEntry) argument).getScore();
        break;
      case RatingScreenlet.ADD_RATING_ACTION:
        _userScore = ((RatingEntry) argument).getScore();
        updateGlobalScore(_userScore);
        break;
      case RatingScreenlet.UPDATE_RATING_ACTION:
        _userScore = ((RatingEntry) argument).getScore();
        double change = (_userScore == 0) ? 1 : -1;
        _negativeCount += change;
        _possitiveCount += -change;
        break;
      case RatingScreenlet.DELETE_RATING_ACTION:
        if (_userScore == 0) {
          _negativeCount--;
        } else if (_userScore == 1) {
          _possitiveCount--;
        }
        _userScore = -1;
        break;
      default:
        break;
    }
    updateCountLabels();
  }

  private void updateGlobalScore(double score) {
    if (score == 0) {
      _negativeCount++;
    } else {
      _possitiveCount++;
    }
  }

  private void updateCountLabels() {
    _possitiveCountLabel.setText(getContext().getString(R.string.rating_total) + Integer.toString(_possitiveCount));
    _negativeCountLabel.setText(getContext().getString(R.string.rating_total) + Integer.toString(_negativeCount));
  }

  @Override public void onClick(View v) {
    final int id = v.getId();

    double score = -1;

    if (id == R.id.positiveRatingButton) {
      score = 1.0;
    } else if (id == R.id.negativeRatingButton) {
      score = 0.0;
    }

    if (score != -1) {
      String action = score == _userScore ?
          RatingScreenlet.DELETE_RATING_ACTION : RatingScreenlet.ADD_RATING_ACTION;
      getScreenlet().performUserAction(action, score);
    }
  }

  @Override public BaseScreenlet getScreenlet() {
      return _screenlet;
  }

  @Override public void setScreenlet(BaseScreenlet screenlet) {
      _screenlet = screenlet;
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    _negativeButton = (Button) findViewById(R.id.negativeRatingButton);
    _possitiveButton = (Button) findViewById(R.id.positiveRatingButton);

    _negativeCountLabel = (TextView) findViewById(R.id.negativeRatingCount);
    _possitiveCountLabel = (TextView) findViewById(R.id.positiveRatingCount);

    _negativeButton.setOnClickListener(this);
    _possitiveButton.setOnClickListener(this);
  }

  private Button _negativeButton;
  private Button _possitiveButton;
  private TextView _negativeCountLabel;
  private TextView _possitiveCountLabel;
  private int _negativeCount;
  private int _possitiveCount;

  private double _userScore;

  private BaseScreenlet _screenlet;
}
