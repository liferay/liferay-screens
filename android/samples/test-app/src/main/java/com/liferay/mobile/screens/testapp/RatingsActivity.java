package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import com.liferay.mobile.screens.rating.RatingEntry;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class RatingsActivity extends ThemeActivity implements RatingListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.ratings);

    RatingScreenlet screenlet =
        (RatingScreenlet) findViewById(R.id.rating_screenlet);
    screenlet.setListener(this);
  }

  @Override public void onRetrieveRatingEntriesFailure(Exception exception) {
    error("Couldn't fetch ratings", exception);
  }

  @Override public void onAddRatingEntryFailure(Exception exception) {
    error("Couldn't update/add rating", exception);
  }

  @Override public void onDeleteRatingEntryFailure(Exception exception) {
    error("Couldn't delete rating", exception);
  }

  @Override public void onRetrieveRatingEntriesSuccess(long classPK, String className, List<RatingEntry> ratings) {
    info("Ratings fetched succesfully!");
  }

  @Override public void onAddRatingEntrySuccess(RatingEntry entry) {
    info("Rating added/updated succesfully!");
  }

  @Override public void onDeleteRatingEntrySuccess() {
    info("Rating deleted succesfully!");
  }
}
