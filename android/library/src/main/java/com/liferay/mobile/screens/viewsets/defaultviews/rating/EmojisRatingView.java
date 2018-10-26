package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.rating.AssetRating;

import static com.liferay.mobile.screens.rating.RatingScreenlet.DELETE_RATING_ACTION;
import static com.liferay.mobile.screens.rating.RatingScreenlet.UPDATE_RATING_ACTION;

/**
 * @author Alejandro Hernández
 */
public class EmojisRatingView extends BaseRatingView implements View.OnClickListener {

    public static final String[] EMOJIS = new String[] {
        "\uD83D\uDE1C", "\uD83D\uDE01", "\uD83D\uDE02", "\uD83D\uDE0E"
    };

    public EmojisRatingView(Context context) {
        super(context);
    }

    public EmojisRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojisRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EmojisRatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setButton(View textView) {
        textView.setAlpha(1);
    }

    @Override
    protected void setEmptyState(TextView textView, View view, int rating, AssetRating assetRating) {
        textView.setText(String.valueOf(rating));
        view.setAlpha(0.4f);
        ((TextView) view).setText(EMOJIS[views.indexOf(view)]);
    }

    @Override
    protected void clicked(double score, double userScore) {
        String action = score == userScore ? DELETE_RATING_ACTION : UPDATE_RATING_ACTION;
        getScreenlet().performUserAction(action, score);
    }
}
