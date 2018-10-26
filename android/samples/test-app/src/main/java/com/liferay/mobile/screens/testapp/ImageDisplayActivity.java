package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayScreenlet;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayActivity extends ThemeActivity
    implements AssetDisplayListener, AdapterView.OnItemSelectedListener {

    private ImageDisplayScreenlet screenlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_display);

        screenlet = findViewById(R.id.image_display_screenlet);
        screenlet.load();
        screenlet.setListener(this);

        Spinner spinner = findViewById(R.id.spinner_scale_type);
        spinner.setSelection(3);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void error(Exception e, String userAction) {
        error(getString(R.string.asset_error), e);
    }

    @Override
    public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
        info(getString(R.string.asset_received_info) + " " + assetEntry.getTitle());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        screenlet.setScaleType(ImageView.ScaleType.values()[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
