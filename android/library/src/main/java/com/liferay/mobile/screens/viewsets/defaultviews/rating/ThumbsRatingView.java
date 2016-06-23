package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
public class ThumbsRatingView extends BaseRatingView implements View.OnClickListener {

  public ThumbsRatingView(Context context) {
    super(context);
  }

  public ThumbsRatingView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ThumbsRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
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
    updateButtons();
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    _negativeButton = (ImageButton) findViewById(R.id.negativeRatingButton);
    _possitiveButton = (ImageButton) findViewById(R.id.positiveRatingButton);

    _negativeCountLabel = (TextView) findViewById(R.id.negativeRatingCount);
    _possitiveCountLabel = (TextView) findViewById(R.id.positiveRatingCount);

    _negativeButton.setOnClickListener(this);
    _possitiveButton.setOnClickListener(this);
  }

  private void updateButtons() {
    if (_userScore == 1) {
      _negativeButton.setImageResource(R.drawable.default_thumb_down_outline);
      _possitiveButton.setImageResource(R.drawable.default_thumb_up);
    } else if (_userScore == 0) {
      _negativeButton.setImageResource(R.drawable.default_thumb_down);
      _possitiveButton.setImageResource(R.drawable.default_thumb_up_outline);
    } else {
      _negativeButton.setImageResource(R.drawable.default_thumb_down_outline);
      _possitiveButton.setImageResource(R.drawable.default_thumb_up_outline);
    }
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

  private ImageButton _negativeButton;
  private ImageButton _possitiveButton;
  private TextView _negativeCountLabel;
  private TextView _possitiveCountLabel;
  private int _negativeCount;
  private int _possitiveCount;

  private double _userScore;
}
