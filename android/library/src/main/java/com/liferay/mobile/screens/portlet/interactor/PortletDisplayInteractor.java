package com.liferay.mobile.screens.portlet.interactor;

import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.AssetFactory;
import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.ddl.form.connector.DLAppConnector;
import com.liferay.mobile.screens.portlet.PortletDisplayListener;
import com.liferay.mobile.screens.portlet.PortletDisplayScreenlet;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Sarai Díaz García
 */
public class PortletDisplayInteractor extends BaseCacheReadInteractor<PortletDisplayListener, PortletEvent> {

	@Override
	public PortletEvent execute(Object... args) throws Exception {
		JSONObject jsonObject = getAsset(args);
		return new PortletEvent(jsonObject);
	}

	@Override
	public void onSuccess(PortletEvent event) {
		AssetEntry assetEntry;
		try {
			Map<String, Object> map = JSONUtil.toMap(event.getJSONObject());
			assetEntry = AssetFactory.createInstance(map);
		} catch (JSONException ex) {
			event.setException(ex);
			onFailure(event);
			return;
		}
		getListener().onRetrieveAssetSuccess(assetEntry);
	}

	@Override
	public void onFailure(PortletEvent event) {
		getListener().error(event.getException(), PortletDisplayScreenlet.DEFAULT_ACTION);
	}

	@Override
	protected String getIdFromArgs(Object... args) {
		return (String) args[0];
	}

	private JSONObject getAsset(Object... args) throws Exception {
		DLAppConnector connector = ServiceProvider.getInstance().getDLAppConnector(getSession());
		// TODO extract groupId, folderId and title from args[0] (url)
		return connector.getFileEntry(20147, 0, "83.png");
	}
}
