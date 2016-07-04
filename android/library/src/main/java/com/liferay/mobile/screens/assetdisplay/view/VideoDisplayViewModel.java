package com.liferay.mobile.screens.assetdisplay.view;

import com.liferay.mobile.screens.assetdisplay.model.FileEntry;
import com.liferay.mobile.screens.base.view.BaseViewModel;

/**
 * @author Sarai Díaz García
 */
public interface VideoDisplayViewModel extends BaseViewModel {

	void showFinishOperation(FileEntry fileEntry);
}
