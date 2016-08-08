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
		getInteractor().getAssetEntry(entryId);
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
				listener.onRetrieveAssetFailure(new Exception("Error loading screenlet"));
			}
		}
	}

	@Override
	public void onRetrieveAssetFailure(Exception e) {
		getViewModel().showFailedOperation(null, e);

		if (listener != null) {
			listener.onRetrieveAssetFailure(e);
		}
	}

	@Override
	protected AssetDisplayInteractorImpl createInteractor(String actionName) {
		return new AssetDisplayInteractorImpl(getScreenletId());
	}

	@Override
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		if (autoLoad) {
			autoLoad();
		}
	}

	protected void autoLoad() {
		if (entryId != 0 && SessionContext.isLoggedIn()) {
			loadAsset();
		}
	}

	@Override
	protected void onUserAction(String userActionName, AssetDisplayInteractorImpl interactor, Object... args) {
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
	private HashMap<String, Integer> layouts = new HashMap<>();
	private long entryId;
	private AssetDisplayListener listener;
}
