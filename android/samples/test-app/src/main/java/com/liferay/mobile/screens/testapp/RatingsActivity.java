package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.rating.RatingScreenlet;

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

	private RatingScreenlet _thumbScreenlet;
	private RatingScreenlet _starScreenlet;
	private RatingScreenlet _likeScreenlet;
	private Switch _readOnlySwitch;

	private RatingScreenlet _screenlet;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ratings);

		_thumbScreenlet = ((RatingScreenlet) findViewById(R.id.rating_thumb_screenlet));
		_starScreenlet = ((RatingScreenlet) findViewById(R.id.rating_star_screenlet));
		_likeScreenlet = ((RatingScreenlet) findViewById(R.id.rating_like_screenlet));

		_readOnlySwitch = (Switch) findViewById(R.id.switch_read_only);

		_starScreenlet.setListener(this);
		_likeScreenlet.setListener(this);
		_thumbScreenlet.setListener(this);

		_readOnlySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				_screenlet.setEditable(!isChecked);
				_screenlet.updateView();
			}
		});

		findViewById(R.id.button_rating_thumb).setOnClickListener(this);
		findViewById(R.id.button_rating_like).setOnClickListener(this);
		findViewById(R.id.button_rating_star).setOnClickListener(this);

		displayScreenlet(_thumbScreenlet);
	}

	@Override public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_rating_thumb:
				displayScreenlet(_thumbScreenlet);
				break;
			case R.id.button_rating_like:
				displayScreenlet(_likeScreenlet);
				break;
			case R.id.button_rating_star:
				displayScreenlet(_starScreenlet);
				break;
			default:
				break;
		}
	}

	private void displayScreenlet(RatingScreenlet screenlet) {
		hideScreenlets();
		_screenlet = screenlet;
		_screenlet.setVisibility(View.VISIBLE);
		_screenlet.setEditable(!_readOnlySwitch.isChecked());
		_screenlet.updateView();

		loadScreenlet();
	}

	private void loadScreenlet() {
		try {
			_screenlet.load();
		} catch (Exception e) {
			onRatingOperationFailure(e);
		}
	}

	private void hideScreenlets() {
		_starScreenlet.setVisibility(View.GONE);
		_thumbScreenlet.setVisibility(View.GONE);
		_likeScreenlet.setVisibility(View.GONE);
	}
}
