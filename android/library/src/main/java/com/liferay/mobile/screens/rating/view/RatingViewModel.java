package com.liferay.mobile.screens.rating.view;

import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.rating.RatingEntry;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public interface RatingViewModel extends BaseViewModel {
    void showFinishOperation(String actionName, Object argument);
}
