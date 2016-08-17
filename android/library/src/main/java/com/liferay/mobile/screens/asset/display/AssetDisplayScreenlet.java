package com.liferay.mobile.screens.asset.display;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.display.interactor.AssetDisplayInteractorImpl;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.dlfile.display.audio.AudioDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.pdf.PdfDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.video.VideoDisplayScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.HashMap;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayScreenlet extends BaseScreenlet<AssetDisplayViewModel, AssetDisplayInteractorImpl>
	implements AssetDisplayListener {

	public static final String STATE_LAYOUTS = "STATE_LAYOUTS";
	public static final String STATE_ENTRY_ID = "STATE_ENTRY_ID";

	public AssetDisplayScreenlet(Context context) {
		super(context);
	}

	public AssetDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AssetDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public AssetDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.AssetDisplayScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_layoutId, getDefaultLayoutId());

		autoLoad = typedArray.getBoolean(R.styleable.AssetDisplayScreenlet_autoLoad, true);
		entryId = typedArray.getInt(R.styleable.AssetDisplayScreenlet_entryId, 0);

		layouts = new HashMap<>();
		layouts.put(ImageDisplayScreenlet.class.getName(),
			typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_imagelayoutId, R.layout.image_display_default));
		layouts.put(VideoDisplayScreenlet.class.getName(),
			typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_videolayoutId, R.layout.video_display_default));
		layouts.put(AudioDisplayScreenlet.class.getName(),
			typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_audiolayoutId, R.layout.audio_display_default));
		layouts.put(PdfDisplayScreenlet.class.getName(),
			typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_pdflayoutId, R.layout.pdf_display_default));

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	public void loadAsset() {
		getInteractor().start(entryId);
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {

		AssetDisplayFactory factory = new AssetDisplayFactory();
		BaseScreenlet screenlet = factory.getScreenlet(getContext(), assetEntry, layouts, autoLoad);
		if (screenlet != null) {
			addView(screenlet, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			if (listener != null) {
				listener.onRetrieveAssetSuccess(assetEntry);
			}
		} else {
			LiferayLogger.e("Error loading screenlet");
			if (listener != null) {
				listener.error(new Exception("Error loading screenlet"), DEFAULT_ACTION);
			}
		}
	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}

	@Override
	public void error(Exception e, String userAction) {
		getViewModel().showFailedOperation(null, e);

		if (listener != null) {
			listener.error(e, DEFAULT_ACTION);
		}
	}

	@Override
	protected AssetDisplayInteractorImpl createInteractor(String actionName) {
		return new AssetDisplayInteractorImpl();
	}

	@Override
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		if (autoLoad) {
			autoLoad();
		}
	}

	//TODO now the autoload is required to be able to load child screenlets
	protected void autoLoad() {
		if (entryId != 0 && SessionContext.isLoggedIn()) {
			loadAsset();
		}
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor, Object... args) {
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		Bundle state = new Bundle();
		state.putParcelable(STATE_SUPER, superState);
		state.putSerializable(STATE_LAYOUTS, layouts);
		state.putLong(STATE_ENTRY_ID, entryId);
		return state;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle state = (Bundle) inState;

		layouts = (HashMap<String, Integer>) state.getSerializable(STATE_LAYOUTS);
		entryId = state.getLong(STATE_ENTRY_ID);

		Parcelable superState = state.getParcelable(STATE_SUPER);
		super.onRestoreInstanceState(superState);
	}

	public long getEntryId() {
		return entryId;
	}

	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}

	public void setListener(AssetDisplayListener listener) {
		this.listener = listener;
	}

	public void setAutoLoad(boolean autoLoad) {
		this.autoLoad = autoLoad;
	}

	private boolean autoLoad;
	private HashMap<String, Integer> layouts;
	private long entryId;
	private AssetDisplayListener listener;
}
