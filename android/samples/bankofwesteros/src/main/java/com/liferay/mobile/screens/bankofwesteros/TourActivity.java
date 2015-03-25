package com.liferay.mobile.screens.bankofwesteros;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;

/**
 * @author Javier Gamarra
 */
public class TourActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {


	public static final String LAYOUT_ID = "layoutId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tour);

		_viewPager = (ViewPager) findViewById(R.id.pager);
		_pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		_viewPager.setAdapter(_pagerAdapter);
		_viewPager.setOnPageChangeListener(this);

		_tourButton = (Button) findViewById(R.id.tour_button);
		_tourButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (_viewPager.getCurrentItem() == NUM_PAGES - 1) {
			startActivity(new Intent(this, MainActivity.class));
		}
		else {
			_viewPager.setCurrentItem(_viewPager.getCurrentItem() + 1, true);
		}
	}

	@Override
	public void onBackPressed() {
		if (_viewPager.getCurrentItem() == 0) {
			super.onBackPressed();
		}
		else {
			_viewPager.setCurrentItem(_viewPager.getCurrentItem() - 1);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		_tourButton.setText(getButtonText(position));
	}

	@Override
	public void onPageSelected(int position) {
		_tourButton.setText(getButtonText(position));
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	private String getButtonText(int currentItem) {
		if (currentItem == 0) {
			return "Tour 0";
		}
		else if (currentItem == 1) {
			return "Tour 1";
		}
		else {
			return "Tour 2";
		}
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			TourPageFragment tourPageFragment = new TourPageFragment();
			Bundle bundle = new Bundle();
			bundle.putInt(LAYOUT_ID, getLayout(position));
			tourPageFragment.setArguments(bundle);

			return tourPageFragment;
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}

		@Override
		public CharSequence getPageTitle(int position) {

			SpannableStringBuilder sb = new SpannableStringBuilder(" ");

			int drawableId = _viewPager.getCurrentItem() == position ? R.drawable.pagination_on : R.drawable.pagination_off;

			Drawable drawable = getResources().getDrawable(drawableId, getTheme());
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
			sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			return sb;
		}

		private int getLayout(int position) {
			if (position == 0) {
				return R.layout.tour0;
			}
			else if (position == 1) {
				return R.layout.tour1;
			}
			else {
				return R.layout.tour2;
			}
		}
	}

	private Button _tourButton;
	private ViewPager _viewPager;
	private PagerAdapter _pagerAdapter;

	private static final int NUM_PAGES = 3;
}
