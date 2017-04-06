package com.liferay.mobile.screens.demoform.views;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.mobile.screens.demoform.R;

public class MenuAccountsAdapter extends ArrayAdapter<Object> {

	private final Activity activity;

	public MenuAccountsAdapter(Activity activity, int menu_row, Object[] values) {
		super(activity, menu_row, values);
		this.activity = activity;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.menu_row, parent, false);
		}

		Object[] item = (Object[]) getItem(position);

		TextView textView = ((TextView) convertView.findViewById(R.id.text_menu));
		textView.setText((String) item[0]);

		ImageView imageView = ((ImageView) convertView.findViewById(R.id.icon_menu));
		imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), (int) item[1]));

		return convertView;
	}
}