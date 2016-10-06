package com.liferay.mobile.screens.dlfile.display.image;

import android.widget.ImageView;
import com.liferay.mobile.screens.dlfile.display.BaseFileDisplayViewModel;

/**
 * @author Sarai Díaz García
 */
public interface ImageDisplayViewModel extends BaseFileDisplayViewModel {

	void setScaleType(ImageView.ScaleType scaleType);
}
