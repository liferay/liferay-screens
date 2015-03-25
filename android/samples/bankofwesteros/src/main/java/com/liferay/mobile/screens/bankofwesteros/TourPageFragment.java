package com.liferay.mobile.screens.bankofwesteros;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Javier Gamarra
 */
public class TourPageFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		int layoutId = getArguments().getInt(TourActivity.LAYOUT_ID);

		return inflater.inflate(layoutId, container, false);
	}
}
