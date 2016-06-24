package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.rating.RatingEntry;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class LikeRatingView extends BaseRatingView implements View.OnClickListener {
  public LikeRatingView(Context context) {
    super(context);
  }

  public LikeRatingView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public LikeRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public void showFinishOperation(String action, Object argument) {
    if (_progressBar != null) {
      _progressBar.setVisibility(View.GONE);
    }
    if (_content != null) {
      _content.setVisibility(View.VISIBLE);

      switch (action) {
        case RatingScreenlet.LOAD_RATINGS_ACTION:
          _likeCount = 0;
          _hasUserRate = false;
          _likeCount = ((List<RatingEntry>) argument).size();
          break;
        case RatingScreenlet.LOAD_USER_RATING_ACTION:
          _hasUserRate = true;
          break;
        case RatingScreenlet.ADD_RATING_ACTION:
          _hasUserRate = true;
          _likeCount++;
          break;
        case RatingScreenlet.DELETE_RATING_ACTION:
          _hasUserRate = false;
          _likeCount--;
          break;
        default:
          break;
      }

      updateCountLabel();
      updateButton();
    }
  }

  private void updateButton() {
    if (_hasUserRate) {
      _likeButton.setImageResource(R.drawable.default_thumb_up);
    } else {
      _likeButton.setImageResource(R.drawable.default_thumb_up_outline);
    }
  }

  private void updateCountLabel() {
    _likeCountLabel.setText(getContext().getString(R.string.rating_total, _likeCount));
  }

  @Override public void onClick(View v) {
      String action = _hasUserRate ?
          RatingScreenlet.DELETE_RATING_ACTION : RatingScreenlet.ADD_RATING_ACTION;
      getScreenlet().performUserAction(action, 1.0);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    _likeButton = (ImageButton) findViewById(R.id.likeRatingButton);
    _likeCountLabel = (TextView) findViewById(R.id.likeCountLabel);
    _likeButton.setOnClickListener(this);
  }

  private ImageButton _likeButton;
  private TextView _likeCountLabel;

  private int _likeCount;
  private boolean _hasUserRate = false;
}
