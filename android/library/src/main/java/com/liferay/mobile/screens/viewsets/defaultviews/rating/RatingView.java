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
import com.liferay.mobile.screens.rating.view.RatingViewModel;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class RatingView extends LinearLayout implements RatingViewModel, View.OnClickListener {

    public RatingView(Context context) {
        super(context);
    }

    public RatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public void showStartOperation(String actionName) {

    }

    @Override public void showFinishOperation(String actionName) {

    }

    @Override public void showFailedOperation(String actionName, Exception e) {

    }

    @Override public void showFinishOperation(List<RatingEntry> ratings) {
      int negativeCount = 0;
      int possitiveCount = 0;

      for (RatingEntry entry : ratings) {
        if (entry.getScore() == 0) {
          negativeCount++;
        } else {
          possitiveCount++;
        }
      }

      _possitiveCount.setText(Integer.toString(possitiveCount));
      _negativeCount.setText(Integer.toString(negativeCount));
    }

    @Override public void onClick(View v) {

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

      _negativeCount = (TextView) findViewById(R.id.negativeRatingCount);
      _possitiveCount = (TextView) findViewById(R.id.positiveRatingCount);

      _negativeButton.setOnClickListener(this);
      _possitiveButton.setOnClickListener(this);
    }

    private Button _negativeButton;
    private Button _possitiveButton;
    private TextView _negativeCount;
    private TextView _possitiveCount;

    private BaseScreenlet _screenlet;
}
