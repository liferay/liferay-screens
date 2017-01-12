package com.liferay.mobile.screens.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.liferay.mobile.screens.context.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetUserAdapter extends BaseAdapter {

	private final List jsonValues;

	public GetUserAdapter(Map<String, Object> map) {
		jsonValues = new ArrayList(map.entrySet());
	}

	@Override
	public int getCount() {
		return jsonValues.size();
	}

	@Override
	public Map.Entry<String, Object> getItem(int position) {
		return (Map.Entry) jsonValues.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_get_user_row, parent, false);
		}

		Map.Entry<String, Object> item = getItem(position);
		TextView keyLabel = (TextView) convertView.findViewById(R.id.keyLabel);
		keyLabel.setText(item.getKey());
		if (item.getValue() != null) {
			TextView keyValue = (TextView) convertView.findViewById(R.id.keyValue);
			keyValue.setText(item.getValue().toString());
		}
		return convertView;
	}

}
