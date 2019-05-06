package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.liferay.mobile.screens.rating.AssetRating;

import static com.liferay.mobile.screens.rating.RatingScreenlet.DELETE_RATING_ACTION;
import static com.liferay.mobile.screens.rating.RatingScreenlet.UPDATE_RATING_ACTION;

/**
 * @author Alejandro Hernández
 */
public class ReactionsRatingView extends BaseRatingView implements View.OnClickListener {

    public ReactionsRatingView(Context context) {
        super(context);
    }

    public ReactionsRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReactionsRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ReactionsRatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setButton(View textView) {
        int colorId = ContextCompat.getColor(getContext(), android.R.color.darker_gray);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue typedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.colorPrimary, typedValue, true);
            colorId = typedValue.data;
        }

        ((ImageButton) textView).getDrawable().setColorFilter(colorId, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void setEmptyState(TextView textView, View view, int rating, AssetRating assetRating) {
        textView.setText(String.valueOf(rating));
        ((ImageButton) view).getDrawable().setColorFilter(null);
    }

    @Override
    protected void clicked(double score, double userScore) {
        String actionName = score == userScore ? DELETE_RATING_ACTION : UPDATE_RATING_ACTION;
        getScreenlet().performUserAction(actionName, score);
    }
}
