package com.liferay.mobile.screens.testapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
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
public class RatingsActivity extends ThemeActivity implements RatingListener, View.OnClickListener {

	@Override public void onRatingOperationFailure(Exception exception) {
		error("There was an error loading screenlet", exception);
	}

	@Override public void onRatingOperationSuccess(AssetRating assetRating) {
		info("Screenlet loaded succesfully");
	}

	private Switch _readOnlySwitch;

	private RatingScreenlet _screenlet;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ratings);

		_container = (LinearLayout) findViewById(R.id.rating_screenlet_container);

		_readOnlySwitch = (Switch) findViewById(R.id.switch_read_only);
		_readOnlySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				_screenlet.setEditable(!isChecked);
				_screenlet.updateView();
			}
		});

		_buttons.add((ImageButton) findViewById(R.id.button_rating_thumb));
		_buttons.add((ImageButton) findViewById(R.id.button_rating_like));
		_buttons.add((ImageButton) findViewById(R.id.button_rating_star));
		_buttons.add((ImageButton) findViewById(R.id.button_rating_reactions));

		for (ImageButton button : _buttons) {
			button.setOnClickListener(this);
		}

		displayScreenlet(R.layout.rating_thumb_default, R.string.liferay_rating_thumb_asset_id, 2);
		paintButton(R.id.button_rating_thumb);
	}

	private void paintButton(int id) {
		for (ImageButton button: _buttons) {
			button.getBackground().setColorFilter(ContextCompat.getColor(getApplicationContext(),
				id != button.getId() ? android.R.color.darker_gray : R.color.colorPrimary_default), PorterDuff.Mode.SRC);
		}
	}

	@Override public void onClick(View v) {
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
				displayScreenlet(R.layout.rating_reactions_default, R.string.liferay_rating_reactions_asset_id, 5);
				break;
		}

		paintButton(v.getId());
	}

	private void displayScreenlet(int layoutId, int entryId, int stepCount) {
		_screenlet = new RatingScreenlet(getApplicationContext());
		_screenlet.setEntryId(Long.valueOf(getResources().getString(entryId)));
		_screenlet.setAutoLoad(true);
		_screenlet.setStepCount(stepCount);
		_screenlet.render(layoutId);
		_screenlet.setEditable(!_readOnlySwitch.isChecked());

		_container.removeAllViews();
		_container.addView(_screenlet);
	}

	private LinearLayout _container;
	private List<ImageButton> _buttons = new ArrayList<>();
}
