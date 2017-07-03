package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.portlet.PortletConfiguration;
import com.liferay.mobile.screens.portlet.PortletDisplayListener;
import com.liferay.mobile.screens.portlet.PortletDisplayScreenlet;
import com.liferay.mobile.screens.portlet.util.InjectableScript;
import com.liferay.mobile.screens.portlet.util.JsScript;
import com.liferay.mobile.screens.util.AssetReader;

/**
 * @author Sarai Díaz García
 */
public class PortletDisplayActivity extends ThemeActivity implements PortletDisplayListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.portlet_display);

		PortletDisplayScreenlet screenlet = (PortletDisplayScreenlet) findViewById(R.id.portlet_display_screenlet);

		if (getIntent().getStringExtra("url") != null) {
			PortletConfiguration portletConfiguration = new PortletConfiguration.Builder(getIntent().getStringExtra("url"))
				.addLocalCss("portlet.css")
				.addRawCss(R.raw.portletcss)
				.disableTheme()
				.load();

			screenlet.setPortletConfiguration(portletConfiguration);

			screenlet.setListener(this);
			screenlet.load();
		}

	}

	@Override
	public void error(Exception e, String userAction) {
		error(getString(R.string.portlet_display_error), e);
	}

	@Override
	public void onRetrievePortletSuccess(String url) {
		info(getString(R.string.portlet_display_success));
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		info(getString(R.string.asset_received_info) + " " + assetEntry.getTitle());
	}

	@Override
	public void onScriptMessageHandler(String namespace, String body) {
		if ("gallery".equals(namespace)) {
			String[] allImgSrc = body.split(",");
			int imgSrcPosition = Integer.parseInt(allImgSrc[allImgSrc.length - 1]);

			Intent intent = new Intent(getApplicationContext(), DetailMediaGalleryActivity.class);
			intent.putExtra("allImgSrc", allImgSrc);
			intent.putExtra("imgSrcPosition", imgSrcPosition);
			startActivity(intent);
		}
	}

	@Override
	public InjectableScript cssForPortlet(String portlet) {
		if ("com_liferay_document_library_web_portlet_IGDisplayPortlet".equals(portlet)) {
			return new JsScript(new AssetReader(getApplicationContext()).read("gallery.css"));
		}

		return null;
	}

	@Override
	public InjectableScript jsForPortlet(String portlet) {
		if ("com_liferay_document_library_web_portlet_IGDisplayPortlet".equals(portlet)) {
			return new JsScript(new AssetReader(getApplicationContext()).read("gallery.js"));
		}

		return null;
	}
}
