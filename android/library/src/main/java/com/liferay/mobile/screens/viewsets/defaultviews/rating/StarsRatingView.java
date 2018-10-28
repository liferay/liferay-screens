package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.rating.AssetRating;

import static com.liferay.mobile.screens.rating.RatingScreenlet.UPDATE_RATING_ACTION;

/**
 * @author Alejandro Hernández
 */
public class StarsRatingView extends BaseRatingView implements RatingBar.OnRatingBarChangeListener {

    private TextView totalCountTextView;
    private RatingBar averageRatingBar;
    private RatingBar userRatingBar;

    public StarsRatingView(Context context) {
        super(context);
    }

    public StarsRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StarsRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StarsRatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void showFinishOperation(String action, AssetRating assetRating) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        if (content != null) {
            content.setVisibility(View.VISIBLE);
        }

        userRatingBar.setRating(getRating(assetRating.getUserScore(), userRatingBar.getNumStars()));
        averageRatingBar.setRating(getRating(assetRating.getAverage(), averageRatingBar.getNumStars()));
        totalCountTextView.setText(getResources().getQuantityString(R.plurals.rating_count, assetRating.getTotalCount(),
            assetRating.getTotalCount()));
    }

    @Override
    public void enableEdition(boolean editable) {
        userRatingBar.setEnabled(editable);
    }

    @Override
    protected void setButton(View textView) {

    }

    @Override
    protected void setEmptyState(TextView textView, View view, int rating, AssetRating assetRating) {

    }

    @Override
    protected void clicked(double score, double userScore) {

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float score, boolean fromUser) {
        if (fromUser) {
            double normalizedScore = score / ratingBar.getNumStars();
            getScreenlet().performUserAction(UPDATE_RATING_ACTION, normalizedScore);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        userRatingBar = findViewById(R.id.userRatingBar);
        averageRatingBar = findViewById(R.id.averageRatingBar);
        totalCountTextView = findViewById(R.id.totalCountTextView);

        userRatingBar.setOnRatingBarChangeListener(this);
    }

    private float getRating(double score, int numStars) {
        return (float) score * numStars;
    }

    @Override
    public int getRatingsLength() {
        return 5;
    }
}
