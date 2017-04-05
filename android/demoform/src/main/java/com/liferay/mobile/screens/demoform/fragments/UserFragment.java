package com.liferay.mobile.screens.demoform.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.display.AssetDisplayInnerScreenletListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.activities.MainActivity;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

public class UserFragment extends Fragment implements AssetDisplayInnerScreenletListener, View.OnClickListener {

	private TextView userNameText;
	private TextView jobTitleText;
	private TextView emailText;
	private TextView screenNameText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.user_display, container, false);

		userNameText = (TextView) view.findViewById(R.id.user_name_text);
		jobTitleText = (TextView) view.findViewById(R.id.user_job_title_text);
		emailText = (TextView) view.findViewById(R.id.user_email_text);
		screenNameText = (TextView) view.findViewById(R.id.user_screen_name_text);

		AssetDisplayScreenlet screenlet = ((AssetDisplayScreenlet) view.findViewById(R.id.asset_display_screenlet));
		screenlet.setClassName("com.liferay.portal.kernel.model.User");
		screenlet.setClassPK(SessionContext.getCurrentUser().getId());
		screenlet.setInnerListener(this);
		screenlet.loadAsset();

		return view;
	}

	@Override
	public void onConfigureChildScreenlet(AssetDisplayScreenlet screenlet, BaseScreenlet innerScreenlet,
		AssetEntry assetEntry) {
	}

	@Override
	public View onRenderCustomAsset(AssetEntry assetEntry) {
		if (assetEntry instanceof User) {
			View view = getActivity().getLayoutInflater().inflate(R.layout.user_profile, null);
			User user = new User(assetEntry.getValues());

			view.findViewById(R.id.sign_out_button).setOnClickListener(this);

			UserPortraitScreenlet userPortraitScreenlet =
				(UserPortraitScreenlet) view.findViewById(R.id.user_portrait_screenlet);
			userNameText = (TextView) view.findViewById(R.id.user_name_text);
			jobTitleText = (TextView) view.findViewById(R.id.user_job_title_text);
			emailText = (TextView) view.findViewById(R.id.user_email_text);
			screenNameText = (TextView) view.findViewById(R.id.user_screen_name_text);

			userPortraitScreenlet.setUserId(user.getId());
			userPortraitScreenlet.load();

			userNameText.setText(user.getFirstName() + " " + user.getLastName());
			jobTitleText.setText(user.getJobTitle());
			emailText.setText(user.getEmail());
			screenNameText.setText(user.getScreenName());

			return view;
		}
		return null;
	}

	@Override
	public void onClick(View v) {
		SessionContext.logout();
		SessionContext.removeStoredCredentials(CredentialsStorageBuilder.StorageType.AUTO);

		Intent intent = new Intent(getActivity(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		getActivity().finish();
	}
}