package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.view.RatingViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.ArrayList;
import java.util.List;

import static com.liferay.mobile.screens.rating.RatingScreenlet.LOAD_RATINGS_ACTION;

/**
 * @author Alejandro Hernández
 */
public abstract class BaseRatingView extends LinearLayout implements RatingViewModel, View.OnClickListener {

    protected ProgressBar progressBar;
    protected ViewGroup content;
    protected List<View> views;
    protected List<TextView> labels;
    private BaseScreenlet screenlet;
    private double userScore;

    public BaseRatingView(Context context) {
        super(context);
    }

    public BaseRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseRatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private static List viewsByTag(ViewGroup root, String tag) {
        List views = new ArrayList<>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(viewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tag.equals(tagObj)) {
                views.add(child);
            }
        }
        return views;
    }

    @Override
    public void showStartOperation(String actionName) {
        if (LOAD_RATINGS_ACTION.equals(actionName)) {
            if (progressBar != null) {
                progressBar.setVisibility(VISIBLE);
            }
            if (content != null) {
                content.setVisibility(GONE);
            }
        }
    }

    @Override
    public void showFinishOperation(String actionName) {
        throw new AssertionError();
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {
        if (progressBar != null) {
            progressBar.setVisibility(GONE);
        }

        if (content != null) {
            content.setVisibility(VISIBLE);
        }

        LiferayLogger.e(getContext().getString(R.string.ratings_error), e);
    }

    @Override
    public void showFinishOperation(String actionName, AssetRating assetRating) {

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        if (content != null) {
            content.setVisibility(View.VISIBLE);
        }

        int[] ratings = assetRating.getRatings();

        for (int i = 0; i < labels.size(); i++) {
            setEmptyState(labels.get(i), views.get(i), ratings[i], assetRating);
        }

        userScore = assetRating.getUserScore();
        if (userScore != -1) {
            int position = userScore == 1 ? views.size() - 1 : (int) (userScore * views.size());
            setButton(views.get(position));
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        progressBar = findViewById(R.id.liferay_rating_progress);
        content = findViewById(R.id.liferay_rating_content);

        if (content != null) {
            String buttonsTag = getContext().getString(R.string.screenlet_rating_button_tag);
            views = (List<View>) viewsByTag(content, buttonsTag);
            String labelsTag = getContext().getString(R.string.screenlet_rating_label_tag);
            labels = (List<TextView>) viewsByTag(content, labelsTag);
        }
    }

    @Override
    public int getRatingsLength() {
        return views.size();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        for (int i = 0; i < views.size(); i++) {
            if (views.get(i).getId() == id) {
                double score = (double) i / views.size();
                clicked(score, userScore);
                break;
            }
        }
    }

    @Override
    public void enableEdition(boolean editable) {
        for (View textView : views) {
            textView.setOnClickListener(editable ? this : null);
            textView.setEnabled(editable);
        }
    }

    protected abstract void setButton(View textView);

    protected abstract void setEmptyState(TextView textView, View view, int rating, AssetRating assetRating);

    protected abstract void clicked(double score, double userScore);

    @Override
    public BaseScreenlet getScreenlet() {
        return screenlet;
    }

    @Override
    public void setScreenlet(BaseScreenlet screenlet) {
        this.screenlet = screenlet;
    }
}
