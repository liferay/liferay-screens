package com.liferay.mobile.screens.testapp.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.testapp.R;

import org.json.JSONObject;

import java.util.List;

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
	public void showImages(List<JSONObject> images) {
		for (JSONObject image : images) {
			TextSliderView textSliderView = createImageInGallery(image);
			_sliderShow.addSlider(textSliderView);
		}
	}

	@NonNull
	protected TextSliderView createImageInGallery(JSONObject image) {
		TextSliderView textSliderView = new TextSliderView(getContext());

		String title = image.optString("title");
		String url = image.optString("url");

		textSliderView.description(title).image(url)
			.setScaleType(BaseSliderView.ScaleType.FitCenterCrop);

		return textSliderView;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_sliderShow = (SliderLayout) findViewById(R.id.slider);
		_sliderShow.stopAutoCycle();
	}

	private BaseScreenlet screenlet;
	private SliderLayout _sliderShow;
}
