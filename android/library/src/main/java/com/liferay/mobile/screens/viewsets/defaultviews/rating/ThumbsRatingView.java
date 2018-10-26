package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.rating.AssetRating;

import static android.view.View.OnClickListener;
import static com.liferay.mobile.screens.R.drawable.default_thumb_down;
import static com.liferay.mobile.screens.R.drawable.default_thumb_down_outline;
import static com.liferay.mobile.screens.R.drawable.default_thumb_up;
import static com.liferay.mobile.screens.R.drawable.default_thumb_up_outline;
import static com.liferay.mobile.screens.rating.RatingScreenlet.DELETE_RATING_ACTION;
import static com.liferay.mobile.screens.rating.RatingScreenlet.UPDATE_RATING_ACTION;

/**
 * @author Alejandro Hernández
 */
public class ThumbsRatingView extends BaseRatingView implements OnClickListener {

    public ThumbsRatingView(Context context) {
        super(context);
    }

    public ThumbsRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThumbsRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ThumbsRatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setButton(View textView) {
        ((ImageView) textView).setImageResource(
            textView.getId() == R.id.positiveRatingButton ? default_thumb_up : default_thumb_down);
    }

    @Override
    protected void setEmptyState(TextView textView, View view, int rating, AssetRating assetRating) {
        textView.setText(getContext().getString(R.string.rating_total, rating));
        ((ImageView) view).setImageResource(
            view.getId() == R.id.positiveRatingButton ? default_thumb_up_outline : default_thumb_down_outline);
    }

    @Override
    protected void clicked(double score, double userScore) {
        String actionName = score == userScore ? DELETE_RATING_ACTION : UPDATE_RATING_ACTION;
        getScreenlet().performUserAction(actionName, score);
    }
}
