package com.liferay.mobile.screens.rating.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.rating.RatingListener;

/**
 * @author Alejandro Hernández
 */
public interface RatingInteractor extends Interactor<RatingListener> {
    void load(long assetId)
        throws Exception;
}
