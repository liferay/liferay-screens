package com.liferay.mobile.screens.assetdisplay;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.assetdisplay.interactor.AssetDisplayInteractorImpl;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.filedisplay.audio.AudioDisplayScreenlet;
import com.liferay.mobile.screens.filedisplay.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.filedisplay.pdf.PdfDisplayScreenlet;
import com.liferay.mobile.screens.filedisplay.video.VideoDisplayScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.HashMap;

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

		AssetDisplayFactory factory = new AssetDisplayFactory();
		BaseScreenlet screenlet = factory.getScreenlet(getContext(), assetEntry, _layouts, _autoLoad);
		screenlet.render(_layouts.get(screenlet.getClass().getName()));
		if (screenlet != null) {
			addView(screenlet, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		} else {
			LiferayLogger.e("Error loading screenlet");
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
			attributes, R.styleable.AssetDisplayScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_layoutId, getDefaultLayoutId());

		_autoLoad = typedArray.getBoolean(R.styleable.AssetDisplayScreenlet_autoLoad, true);
		_entryId = typedArray.getInt(R.styleable.AssetDisplayScreenlet_entryId, 0);

		_layouts = new HashMap<>();
		_layouts.put(ImageDisplayScreenlet.class.getName(),
			typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_imagelayoutId, R.layout.image_display_default));
		_layouts.put(VideoDisplayScreenlet.class.getName(),
			typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_videolayoutId, R.layout.video_display_default));
		_layouts.put(AudioDisplayScreenlet.class.getName(),
			typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_audiolayoutId, R.layout.audio_display_default));
		_layouts.put(PdfDisplayScreenlet.class.getName(),
			typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_pdflayoutId, R.layout.pdf_display_default));

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

		if (_autoLoad) {
			autoLoad();
		}
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor,
		Object... args) {
	}

	public void loadAsset() throws Exception {
		getInteractor().getAssetEntryExtended(_entryId);
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

	protected void autoLoad() {
		if (_entryId != 0 && SessionContext.isLoggedIn()) {
			try {
				loadAsset();
			} catch (Exception e) {
				onRetrieveAssetFailure(e);
			}
		}
	}

	private boolean _autoLoad;
	private HashMap<String, Integer> _layouts;
	private long _entryId;
	private AssetDisplayListener _listener;
}
