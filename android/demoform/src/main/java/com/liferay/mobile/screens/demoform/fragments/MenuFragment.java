package com.liferay.mobile.screens.demoform.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.demoform.R;

public class MenuFragment extends Fragment implements View.OnClickListener {

	private ListView drawerListView;
	private AdapterView.OnItemClickListener onItemClickListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu_drawer, container, false);
		drawerListView = (ListView) view.findViewById(R.id.drawer_list_view);
		drawerListView.setOnItemClickListener(this::selectItem);
		Object[] values = new Object[] {
			new Object[] { getString(R.string.accounts), R.drawable.ic_account_balance_wallet_black_24dp },
			new Object[] { getString(R.string.new_account), R.drawable.ic_account_balance_black_24dp },
			new Object[] { "Transfers", R.drawable.ic_attach_money_black_24dp },
			new Object[] { "Bill payments", R.drawable.ic_autorenew_black_24dp },
			new Object[] { "Stocks", R.drawable.ic_trending_up_black_24dp },
			new Object[] { "Customer service", R.drawable.ic_people_black_24dp }
		};
		drawerListView.setAdapter(new MenuAccountsAdapter(getContext(), R.layout.menu_row, values));

		User user = SessionContext.getCurrentUser();
		TextView userNameText = (TextView) view.findViewById(R.id.liferay_username);
		userNameText.setOnClickListener(this);
		userNameText.setText(user.getFullName());
		view.findViewById(R.id.userscreenlet_home).setOnClickListener(this);

		drawerListView.setItemChecked(0, true);
		return view;
	}

	private void selectItem(AdapterView<?> adapterView, View view, int position, long id) {
		if (drawerListView != null) {
			drawerListView.setItemChecked(position, true);
		}
		if (onItemClickListener != null) {
			onItemClickListener.onItemClick(adapterView, view, position, id);
		}
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	@Override
	public void onClick(View v) {
		if (onItemClickListener != null) {
			onItemClickListener.onItemClick(null, v, 10, 0);
		}
	}

	private class MenuAccountsAdapter extends ArrayAdapter<Object> {
		public MenuAccountsAdapter(Context context, int menu_row, Object[] values) {
			super(context, menu_row, values);
		}

		@NonNull
		@Override
		public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.menu_row, parent, false);
			}

			Object[] item = (Object[]) getItem(position);

			TextView textView = ((TextView) convertView.findViewById(R.id.text_menu));
			textView.setText((String) item[0]);

			ImageView imageView = ((ImageView) convertView.findViewById(R.id.icon_menu));
			imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), (int) item[1]));

			return convertView;
		}
	}
}
