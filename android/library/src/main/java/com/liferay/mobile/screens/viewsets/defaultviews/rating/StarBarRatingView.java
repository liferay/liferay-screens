package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RatingBar;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.rating.RatingEntry;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class StarBarRatingView extends BaseRatingView implements RatingBar.OnRatingBarChangeListener {
  public StarBarRatingView(Context context) {
    super(context);
  }

  public StarBarRatingView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public StarBarRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public void showFinishOperation(String actionName, Object argument) {
    switch (actionName) {
      case RatingScreenlet.LOAD_RATINGS_ACTION:
        _userScore = -1;
        _totalScore = _totalCount = 0;
        final List<RatingEntry> ratings = (List<RatingEntry>) argument;
        for (RatingEntry rating : ratings) {
          addToTotalScore(rating.getScore());
          _totalCount++;
        }
        break;
      case RatingScreenlet.LOAD_USER_RATING_ACTION:
        final RatingEntry rating = (RatingEntry) argument;
        _userScore = rating.getScore();
        setUserRating();
        break;
      case RatingScreenlet.ADD_RATING_ACTION:
        _userScore = ((RatingEntry) argument).getScore();
        setUserRating();
        _totalCount++;
        addToTotalScore(_userScore);
        break;
      case RatingScreenlet.UPDATE_RATING_ACTION:
        addToTotalScore(-_userScore);
        _userScore = ((RatingEntry) argument).getScore();
        addToTotalScore(_userScore);
        break;
      default:
        break;
    }

    setRatingBarRate(_averageRatingBar, getRating());
    _totalCountTextView.setText(getContext().getString(R.string.rating_count, _totalCount));
  }

  private void addToTotalScore(double score) {
    _totalScore += score;
  }

  @Override public void onRatingChanged(RatingBar ratingBar, float score, boolean fromUser) {
    if (fromUser) {
      double normalizedScore = score / ratingBar.getNumStars();
      getScreenlet().performUserAction(RatingScreenlet.ADD_RATING_ACTION, (double) normalizedScore);
    }
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    _userRatingBar = (RatingBar) findViewById(R.id.userRatingBar);
    _averageRatingBar = (RatingBar) findViewById(R.id.averageRatingBar);
    _totalCountTextView = (TextView) findViewById(R.id.totalCountTextView);

    _userRatingBar.setOnRatingBarChangeListener(this);
  }

  private float getRating() {
    return (float) _totalScore / _totalCount;
  }

  private void setUserRating() {
    setRatingBarRate(_userRatingBar, (float) _userScore);
  }

  private void setRatingBarRate(RatingBar ratingBar, float rating) {
    ratingBar.setRating(rating * ratingBar.getNumStars());
  }

  private TextView _totalCountTextView;
  private RatingBar _averageRatingBar;
  private RatingBar _userRatingBar;

  private int _totalCount;
  private double _totalScore;
  private double _userScore;
}
