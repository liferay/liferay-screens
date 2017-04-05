package com.liferay.mobile.screens.demoform;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NavigationDrawerFragment extends Fragment {

	private ListView drawerListView;
	private AdapterView.OnItemClickListener onItemClickListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		drawerListView = (ListView) inflater.inflate(R.layout.drawer, container, false);
		drawerListView.setOnItemClickListener(this::selectItem);
		drawerListView.setAdapter(
			new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, android.R.id.text1,
				new String[] {
					getString(R.string.title_section1), getString(R.string.title_section2),
					getString(R.string.title_section3),
				}));
		return drawerListView;
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
}
