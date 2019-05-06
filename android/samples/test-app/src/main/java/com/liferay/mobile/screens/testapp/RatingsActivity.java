package com.liferay.mobile.screens.testapp;

import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.SwitchCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
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

    private final List<View> buttons = new ArrayList<>();
    private SwitchCompat readOnlySwitch;
    private RatingScreenlet ratingScreenlet;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratings);

        container = findViewById(R.id.rating_screenlet_container);

        readOnlySwitch = findViewById(R.id.switch_read_only);
        readOnlySwitch.setOnCheckedChangeListener(this);

        buttons.add(findViewById(R.id.button_rating_thumb));
        buttons.add(findViewById(R.id.button_rating_like));
        buttons.add(findViewById(R.id.button_rating_star));
        buttons.add(findViewById(R.id.button_rating_reactions));
        buttons.add(findViewById(R.id.button_rating_emojis));

        for (View button : buttons) {
            button.setOnClickListener(this);
        }

        displayScreenlet(R.layout.rating_thumb_default, R.string.liferay_rating_thumb_asset_id);
        tintButtons(R.id.button_rating_thumb);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_rating_like:
                displayScreenlet(R.layout.rating_like_default, R.string.liferay_rating_like_asset_id);
                break;
            case R.id.button_rating_star:
                displayScreenlet(R.layout.rating_star_bar_default, R.string.liferay_rating_star_asset_id);
                break;
            case R.id.button_rating_reactions:
                displayScreenlet(R.layout.rating_reactions_default, R.string.liferay_rating_reactions_emojis_asset_id);
                break;
            case R.id.button_rating_emojis:
                displayScreenlet(R.layout.rating_emojis_default, R.string.liferay_rating_reactions_emojis_asset_id);
                break;
            case R.id.button_rating_thumb:
            default:
                displayScreenlet(R.layout.rating_thumb_default, R.string.liferay_rating_thumb_asset_id);
                break;
        }

        tintButtons(v.getId());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ratingScreenlet.enableEdition(!isChecked);
    }

    @Override
    public void error(Exception exception, String userAction) {
        error(getString(R.string.screenlet_error), exception);
    }

    @Override
    public void onRatingOperationSuccess(AssetRating assetRating) {
        info(getString(R.string.screenlet_info));
    }

    private void displayScreenlet(int layoutId, int entryId) {
        ratingScreenlet = new RatingScreenlet(this);
        ratingScreenlet.setEntryId(Long.parseLong(getResources().getString(entryId)));
        ratingScreenlet.setAutoLoad(true);
        ratingScreenlet.render(layoutId);
        ratingScreenlet.enableEdition(!readOnlySwitch.isChecked());

        container.removeAllViews();
        container.addView(ratingScreenlet);
    }

    private void tintButtons(int id) {
        for (View button : buttons) {
            int color = id != button.getId() ? getDarkerGray() : getPrimaryColor();
            button.getBackground().setColorFilter(color, PorterDuff.Mode.SRC);
        }
    }

    @ColorInt
    private int getDarkerGray() {
        return ContextCompat.getColor(this, android.R.color.darker_gray);
    }

    @ColorInt
    private int getPrimaryColor() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
