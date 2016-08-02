package com.liferay.mobile.screens.testapp;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class RatingsActivity extends ThemeActivity
	implements RatingListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ratings);

		_container = (LinearLayout) findViewById(R.id.rating_screenlet_container);

		_readOnlySwitch = (Switch) findViewById(R.id.switch_read_only);
		_readOnlySwitch.setOnCheckedChangeListener(this);

		_buttons.add(findViewById(R.id.button_rating_thumb));
		_buttons.add(findViewById(R.id.button_rating_like));
		_buttons.add(findViewById(R.id.button_rating_star));
		_buttons.add(findViewById(R.id.button_rating_reactions));
		_buttons.add(findViewById(R.id.button_rating_emojis));

		for (View button : _buttons) {
			button.setOnClickListener(this);
		}

		displayScreenlet(R.layout.rating_thumb_default, R.string.liferay_rating_thumb_asset_id, 2);
		paintButton(R.id.button_rating_thumb);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_rating_thumb:
				displayScreenlet(R.layout.rating_thumb_default, R.string.liferay_rating_thumb_asset_id, 2);
				break;
			case R.id.button_rating_like:
				displayScreenlet(R.layout.rating_like_default, R.string.liferay_rating_like_asset_id, 1);
				break;
			case R.id.button_rating_star:
				displayScreenlet(R.layout.rating_star_bar_default, R.string.liferay_rating_star_asset_id, 5);
				break;
			case R.id.button_rating_reactions:
				displayScreenlet(R.layout.rating_reactions_default, R.string.liferay_rating_reactions_emojis_asset_id,
					5);
				break;
			case R.id.button_rating_emojis:
				displayScreenlet(R.layout.rating_emojis_default, R.string.liferay_rating_reactions_emojis_asset_id, 5);
				break;
		}

		paintButton(v.getId());
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		_screenlet.setEditable(!isChecked);
		_screenlet.updateView();
	}

	@Override
	public void onRatingOperationFailure(Exception exception) {
		error("There was an error loading screenlet", exception);
	}

	@Override
	public void onRatingOperationSuccess(AssetRating assetRating) {
		info("Screenlet loaded succesfully");
	}

	private void displayScreenlet(int layoutId, int entryId, int ratingsGroupCount) {
		_screenlet = new RatingScreenlet(this);
		_screenlet.setEntryId(Long.valueOf(getResources().getString(entryId)));
		_screenlet.setAutoLoad(true);
		_screenlet.setRatingsGroupCount(ratingsGroupCount);
		_screenlet.render(layoutId);
		_screenlet.setEditable(!_readOnlySwitch.isChecked());

		_container.removeAllViews();
		_container.addView(_screenlet);
	}

	private void paintButton(int id) {
		for (View button : _buttons) {
			TypedValue typedValue = new TypedValue();
			getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
			int color =
				id != button.getId() ? ContextCompat.getColor(this, android.R.color.darker_gray) : typedValue.data;
			button.getBackground().setColorFilter(color, PorterDuff.Mode.SRC);
		}
	}

	private Switch _readOnlySwitch;
	private RatingScreenlet _screenlet;
	private LinearLayout _container;
	private List<View> _buttons = new ArrayList<>();
}
