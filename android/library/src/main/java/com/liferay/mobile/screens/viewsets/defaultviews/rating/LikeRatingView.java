package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.rating.AssetRating;

import static com.liferay.mobile.screens.rating.RatingScreenlet.DELETE_RATING_ACTION;
import static com.liferay.mobile.screens.rating.RatingScreenlet.UPDATE_RATING_ACTION;

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

    public LikeRatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setButton(View textView) {
        ((ImageButton) textView).setImageResource(R.drawable.default_thumb_up);
    }

    @Override
    protected void setEmptyState(TextView textView, View view, int rating, AssetRating assetRating) {
        textView.setText(getContext().getString(R.string.rating_total, assetRating.getTotalCount()));
        ((ImageButton) view).setImageResource(R.drawable.default_thumb_up_outline);
    }

    @Override
    protected void clicked(double score, double userScore) {
        getScreenlet().performUserAction(userScore != -1 ? DELETE_RATING_ACTION : UPDATE_RATING_ACTION, score);
    }
}
