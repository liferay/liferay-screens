package com.liferay.mobile.screens.viewsets.defaultviews.rating;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.rating.AssetRating;
import java.util.ArrayList;
import java.util.List;

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
	public void showFinishOperation(String actionName, AssetRating assetRating) {
		if (progressBar != null) {
			progressBar.setVisibility(View.GONE);
		}
		if (content != null) {
			content.setVisibility(View.VISIBLE);

			int[] ratings = assetRating.getRatings();

			userScore = assetRating.getUserScore();

			if (ratings.length != reactions.size()) {
				throw new AssertionError("The number of buttons is different than the step count");
			}

			for (int i = 0; i < reactions.size(); i++) {
				labels.get(i).setText(String.valueOf(ratings[i]));
			}

			for (ImageButton button : reactions) {
				int color = ContextCompat.getColor(getContext(), android.R.color.darker_gray);
				button.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
			}

			if (userScore != -1) {
				int position = userScore == 1 ? reactions.size() - 1 : (int) (userScore * reactions.size());
				reactions.get(position).getDrawable().setColorFilter(getPrimaryColor(), PorterDuff.Mode.SRC_ATOP);
			}
		}
	}

	@ColorInt
	private int getPrimaryColor() {
		TypedValue typedValue = new TypedValue();
		getContext().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
		return typedValue.data;
	}

	@Override
	public void enableEdition(boolean editable) {
		for (ImageButton button : reactions) {
			button.setOnClickListener(editable ? this : null);
			button.setEnabled(editable);
		}
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();

		for (int i = 0; i < reactions.size(); i++) {
			if (reactions.get(i).getId() == id) {
				double score = (double) i / reactions.size();
				String action = score == userScore ? DELETE_RATING_ACTION : UPDATE_RATING_ACTION;
				getScreenlet().performUserAction(action, score);
				break;
			}
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (content != null) {
			reactions = new ArrayList<>();
			labels = new ArrayList<>();
			findChildViewsIn(content);
		}
	}

	private void findChildViewsIn(ViewGroup viewGroup) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View view = viewGroup.getChildAt(i);
			if (view instanceof ImageButton) {
				reactions.add((ImageButton) view);
			} else if (view instanceof TextView) {
				labels.add((TextView) view);
			} else if (view instanceof ViewGroup) {
				findChildViewsIn((ViewGroup) view);
			}
		}
	}

	private List<ImageButton> reactions;
	private List<TextView> labels;
	private double userScore;
}
