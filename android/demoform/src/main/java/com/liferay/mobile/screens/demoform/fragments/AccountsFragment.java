package com.liferay.mobile.screens.demoform.fragments;

import android.support.v4.app.Fragment;

public abstract class AccountsFragment extends Fragment {

	public abstract String getName();

	@Override
	public void onResume() {
		super.onResume();

		getActivity().setTitle(getName());
	}
}
