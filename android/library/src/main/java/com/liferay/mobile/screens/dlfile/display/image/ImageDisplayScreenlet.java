package com.liferay.mobile.screens.dlfile.display.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.dlfile.display.BaseFileDisplayScreenlet;
import com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display.ImageDisplayView;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayScreenlet extends BaseFileDisplayScreenlet<ImageDisplayViewModel> {

	private ImageView.ScaleType scaleType;

	public ImageDisplayScreenlet(Context context) {
		super(context);
	}

	public ImageDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ImageDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.ImageDisplayScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.ImageDisplayScreenlet_layoutId, getDefaultLayoutId());

		autoLoad = typedArray.getBoolean(R.styleable.ImageDisplayScreenlet_autoLoad, true);
		entryId = typedArray.getInt(R.styleable.ImageDisplayScreenlet_entryId, 0);

		className = typedArray.getString(R.styleable.ImageDisplayScreenlet_className);
		classPK = typedArray.getInt(R.styleable.ImageDisplayScreenlet_classPK, 0);

		Integer scaleTypeAttribute = typedArray.getInteger(R.styleable.ImageDisplayScreenlet_imageScaleType,
			ImageView.ScaleType.FIT_CENTER.ordinal());
		scaleType = ImageView.ScaleType.values()[scaleTypeAttribute];

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		((ImageDisplayView) view).setScaleType(scaleType);

		typedArray.recycle();

		return view;
	}

	public ImageView.ScaleType getScaleType() {
		return scaleType;
	}

	public void setScaleType(ImageView.ScaleType scaleType) {
		this.scaleType = scaleType;
		getViewModel().setScaleType(scaleType);
	}
}
