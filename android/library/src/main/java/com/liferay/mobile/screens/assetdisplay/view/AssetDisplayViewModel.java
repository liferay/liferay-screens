package com.liferay.mobile.screens.assetdisplay.view;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.view.BaseViewModel;

/**
 * @author Sarai Díaz García
 */
public interface AssetDisplayViewModel extends BaseViewModel {

  void showFinishOperation(AssetEntry assetEntry);
}
