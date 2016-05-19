package com.liferay.mobile.screens.testapp.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.testapp.R;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class GalleryView extends RelativeLayout implements GalleryViewModel {


	public GalleryView(Context context) {
		super(context);
	}

	public GalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void showStartOperation(String actionName) {

	}

	@Override
	public void showFinishOperation(String actionName) {
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {

	}

	@Override
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}

	@Override
	public void showImages(JSONArray images) {
		try {
			for (int i = 0; i < images.length(); i++) {
				JSONObject image = images.getJSONObject(i);
				TextSliderView textSliderView = createImageInGallery(image);
				_sliderShow.addSlider(textSliderView);
			}
		}
		catch (JSONException e) {
			LiferayLogger.e("Error showing image");
			showFailedOperation(null, e);
		}

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_sliderShow = (SliderLayout) findViewById(R.id.slider);
		_sliderShow.stopAutoCycle();
	}

	@NonNull
	private TextSliderView createImageInGallery(JSONObject image) throws JSONException {
		TextSliderView textSliderView = new TextSliderView(getContext());

		String title = image.getString("title");
		String url = LiferayServerContext.getServer()
			+ "/documents/"
			+ image.getLong("groupId") + "/"
			+ image.getLong("folderId") + "/"
			+ title + "/"
			+ image.getString("uuid");

		textSliderView
			.description(title)
			.image(url)
			.setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
		return textSliderView;
	}

	private BaseScreenlet screenlet;
	private SliderLayout _sliderShow;
}
