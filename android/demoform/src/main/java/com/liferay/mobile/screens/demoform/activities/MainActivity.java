package com.liferay.mobile.screens.demoform.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.fragments.AccountFormFragment;
import com.liferay.mobile.screens.demoform.fragments.AccountsFragment;
import com.liferay.mobile.screens.demoform.fragments.ListAccountsFragment;
import com.liferay.mobile.screens.demoform.fragments.MenuFragment;
import com.liferay.mobile.screens.demoform.fragments.NewAccountFragment;
import com.liferay.mobile.screens.demoform.fragments.UserProfileFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

	private DrawerLayout drawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);

		boolean added = getIntent().getBooleanExtra("added", false);
		if (added) {
			Snackbar.make(findViewById(android.R.id.content), "Form added!", Snackbar.LENGTH_LONG).show();
		}

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		supportActionBar.setHomeButtonEnabled(true);

		ActionBarDrawerToggle drawerToggle =
			new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
				R.string.navigation_drawer_close);
		drawerLayout.addDrawerListener(drawerToggle);
		drawerLayout.post(drawerToggle::syncState);

		MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		menuFragment.setOnItemClickListener(this);

		AccountsFragment fragment = getFragment(0);
		loadFragment(fragment);
	}

	//@Override
	//public void onBackPressed() {
		//Intent intent = new Intent(this, LoginActivity.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//startActivity(intent);
		//finish();
	//}

	@Override
	public void onClick(View v) {
		//startActivity(new Intent(this, UserProfileActivity.class));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		drawerLayout.closeDrawers();

		AccountsFragment fragment = getFragment(position);
		loadFragment(fragment);
	}

	private void loadFragment(AccountsFragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("TAG").commit();
	}

	@NonNull
	private AccountsFragment getFragment(int position) {
		if (position == 0) {
			return new ListAccountsFragment();
		} else if (position == 1) {
			return new UserProfileFragment();
		}
		return new NewAccountFragment();
	}

	public void recordClicked(Record record) {

		AccountFormFragment accountFormFragment = AccountFormFragment.newInstance(record);
		loadFragment(accountFormFragment);
	}
}
