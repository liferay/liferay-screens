package com.liferay.mobile.screens.demoform.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liferay.mobile.screens.demoform.R;

public class ListAccountsFragment extends AccountsFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list_accounts, container, false);
	}

	@Override
	public String getName() {
		return "Accounts";
	}
}