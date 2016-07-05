package com.liferay.mobile.screens.assetdisplay;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.assetdisplay.interactor.AssetDisplayInteractorImpl;
import com.liferay.mobile.screens.assetdisplay.view.AssetDisplayViewModel;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayScreenlet extends BaseScreenlet<AssetDisplayViewModel, AssetDisplayInteractorImpl>
	implements AssetDisplayListener {

	public AssetDisplayScreenlet(Context context) {
		super(context);
	}

	public AssetDisplayScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AssetDisplayScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		getViewModel().showFinishOperation(assetEntry);

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
			attributes, R.styleable.AssetDisplayScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_layoutId, getDefaultLayoutId());

		_entryId = typedArray.getInt(R.styleable.AssetDisplayScreenlet_entryId, 0);

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	@Override
	protected AssetDisplayInteractorImpl createInteractor(String actionName) {
		return new AssetDisplayInteractorImpl(getScreenletId());
	}

	@Override
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		try {
			loadAsset();
		} catch (Exception e) {
			LiferayLogger.e("Could not load asset: " + e.toString());
		}
	}

	public void loadAsset() throws Exception {
		getInteractor().getAssetEntryExtended(_entryId);
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor,
		Object... args) {
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		this._entryId = entryId;
	}

	public void setListener(AssetDisplayListener listener) {
		_listener = listener;
	}

	private long _entryId;
	private AssetDisplayListener _listener;
}
