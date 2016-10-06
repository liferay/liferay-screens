package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.liferay.mobile.screens.asset.display.AssetDisplayInnerScreenletListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayActivity extends ThemeActivity implements AssetDisplayListener,
	AdapterView.OnItemSelectedListener {

	private ImageDisplayScreenlet screenlet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_display);

		screenlet = ((ImageDisplayScreenlet) findViewById(R.id.image_display_screenlet));

		screenlet.setEntryId(getIntent().getLongExtra("entryId", 0));
		screenlet.setListener(this);
		screenlet.load();

		((Spinner) findViewById(R.id.spinner_scale_type)).setOnItemSelectedListener(this);
	}

	@Override
	public void error(Exception e, String userAction) {
		error("Could not receive asset entry", e);
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		info("Asset entry received! -> " + assetEntry.getTitle());
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		screenlet.setScaleType(ImageView.ScaleType.values()[position]);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}
