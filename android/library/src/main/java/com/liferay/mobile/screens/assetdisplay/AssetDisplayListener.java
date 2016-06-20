package com.liferay.mobile.screens.assetdisplay;

/**
 * @author Sarai Díaz García
 */
public interface AssetDisplayListener {

  void onRetrieveAssetFailure(Exception exception);

  void onRetrieveAssetSuccess();

}
