package com.liferay.mobile.screens.assetdisplay;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.assetdisplay.interactor.AssetDisplayInteractorImpl;
import com.liferay.mobile.screens.assetdisplay.model.FileEntry;
import com.liferay.mobile.screens.assetdisplay.view.ImageDisplayViewModel;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Sarai Díaz García
 */
public class ImageDisplayScreenlet extends BaseScreenlet<ImageDisplayViewModel, AssetDisplayInteractorImpl>
	implements AssetDisplayListener {

	public ImageDisplayScreenlet(Context context) {
		super(context);
	}

	public ImageDisplayScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public ImageDisplayScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		try {
			_fileEntry = (FileEntry) assetEntry;
			getViewModel().showFinishOperation(_fileEntry);
		} catch (Exception e) {
			LiferayLogger.e(e.getMessage());
		}

		if (_listener != null) {
			_listener.onRetrieveAssetSuccess(assetEntry);
		}
	}

	@Override
	public void onRetrieveAssetFailure(Exception e) {
		getViewModel().showFailedOperation(null, e);

		if (_listener != null) {
			_listener.onRetrieveAssetFailure(e);
		}
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.ImageDisplayScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.ImageDisplayScreenlet_layoutId, getDefaultLayoutId());

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	@Override
	protected AssetDisplayInteractorImpl createInteractor(String actionName) {
		return new AssetDisplayInteractorImpl(this.getScreenletId());
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor,
		Object... args) {
	}

	public void setListener(AssetDisplayListener listener) {
		_listener = listener;
	}

	public void setFileEntry(FileEntry fileEntry) {
		onRetrieveAssetSuccess(fileEntry);
	}

	private FileEntry _fileEntry;
	private AssetDisplayListener _listener;
}
