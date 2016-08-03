package com.liferay.mobile.screens.rating.view;

import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.rating.AssetRating;

/**
 * @author Alejandro Hernández
 */
public interface RatingViewModel extends BaseViewModel {

	void showFinishOperation(String actionName, AssetRating argument);

	void enableEdition(boolean editable);
}
