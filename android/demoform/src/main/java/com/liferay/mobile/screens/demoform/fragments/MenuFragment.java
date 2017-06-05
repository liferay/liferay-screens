package com.liferay.mobile.screens.demoform.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.views.MenuAccountsAdapter;

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
		drawerListView.setAdapter(new MenuAccountsAdapter(getActivity(), R.layout.menu_row, values));

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
}
