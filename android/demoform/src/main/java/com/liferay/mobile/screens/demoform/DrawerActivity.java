package com.liferay.mobile.screens.demoform;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import com.liferay.mobile.screens.demoform.activities.MainActivity;
import com.liferay.mobile.screens.demoform.fragments.AccountsFragment;
import com.liferay.mobile.screens.demoform.fragments.NewAccountFragment;
import com.liferay.mobile.screens.demoform.fragments.UserFragment;

public class DrawerActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

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

		NavigationDrawerFragment navigationDrawerFragment =
			(NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		navigationDrawerFragment.setOnItemClickListener(this);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		//startActivity(new Intent(this, UserProfileActivity.class));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		drawerLayout.closeDrawers();

		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = getFragment(position);

		fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
	}

	@NonNull
	private Fragment getFragment(int position) {
		if (position == 0) {
			return new AccountsFragment();
		} else if (position == 1) {
			return new UserFragment();
		}
		return new NewAccountFragment();
	}
}
