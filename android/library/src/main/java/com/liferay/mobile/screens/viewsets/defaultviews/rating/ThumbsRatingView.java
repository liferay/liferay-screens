package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.RatingEntry;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.rating.view.RatingViewModel;
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

  @Override public void showFinishOperation(String actionName) {

  }

  @Override public void showFailedOperation(String actionName, Exception e) {

  }

  @Override public void showFinishOperation(String action, Object argument) {
    switch (action) {
      case RatingScreenlet.LOAD_RATINGS_ACTION:
        _negativeCount = _possitiveCount = 0;
        _userRatingEntry = null;

        for (RatingEntry entry : (List<RatingEntry>) argument) {
          if (_userRatingEntry == null) {
            checkUserEntry(entry);
          }

          if (entry.getScore() == 0) {
            _negativeCount++;
          } else {
            _possitiveCount++;
          }
        }

        updateCountLabels();
        break;
      default:
        break;
    }
  }

  private void checkUserEntry(RatingEntry entry) {
    if (entry.getUserId() == SessionContext.getCurrentUser().getId()) {
      _userRatingEntry = entry;
      _negativeButton.setEnabled(entry.getScore() > 0);
      _possitiveButton.setEnabled(entry.getScore() == 0);
    }
  }

  private void updateCountLabels() {
    _possitiveCountLabel.setText(Integer.toString(_possitiveCount));
    _negativeCountLabel.setText(Integer.toString(_negativeCount));
  }

  @Override public void onClick(View v) {
    Log.d("ThumbsRatingView", "User click button with id: " + v.getId());
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

  private RatingEntry _userRatingEntry;

  private BaseScreenlet _screenlet;
}
