package com.liferay.mobile.screens.demoform.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.demoform.R;

public class AccountsFragment extends Fragment implements View.OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.accounts_list, container, false);

		TextView userNameTextView = (TextView) view.findViewById(R.id.liferay_username);
		userNameTextView.setOnClickListener(this);

		view.findViewById(R.id.userscreenlet_home).setOnClickListener(this);

		User currentUser = SessionContext.getCurrentUser();
		userNameTextView.setText(currentUser == null ? "" : currentUser.getFullName());

		return view;
	}

	@Override
	public void onClick(View v) {
		//getActivity().
	}
}