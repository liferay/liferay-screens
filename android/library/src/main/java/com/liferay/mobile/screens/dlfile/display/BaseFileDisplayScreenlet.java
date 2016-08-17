package com.liferay.mobile.screens.dlfile.display;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.interactor.AssetDisplayInteractorImpl;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Sarai Díaz García
 */
public abstract class BaseFileDisplayScreenlet
	extends BaseScreenlet<BaseFileDisplayViewModel, AssetDisplayInteractorImpl> implements AssetDisplayListener {

	public static final String STATE_ENTRY_ID = "STATE_ENTRY_ID";
	public static final String STATE_FILE_ENTRY = "STATE_FILE_ENTRY";

	public BaseFileDisplayScreenlet(Context context) {
		super(context);
	}

	public BaseFileDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseFileDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public BaseFileDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.AssetDisplayScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_layoutId, getDefaultLayoutId());

		autoLoad = typedArray.getBoolean(R.styleable.AssetDisplayScreenlet_autoLoad, true);
		entryId = typedArray.getInt(R.styleable.AssetDisplayScreenlet_entryId, 0);

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		fileEntry = (FileEntry) assetEntry;
		getViewModel().showFinishOperation(fileEntry);

		if (listener != null) {
			listener.onRetrieveAssetSuccess(assetEntry);
		}
	}

	@Override
	public void loadingFromCache(boolean success) {
		if (listener != null) {
			listener.loadingFromCache(success);
		}
	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {
		if (listener != null) {
			listener.retrievingOnline(triedInCache, e);
		}
	}

	@Override
	public void storingToCache(Object object) {
		if (listener != null) {
			listener.storingToCache(object);
		}
	}

	@Override
	public void error(Exception e, String userAction) {
		getViewModel().showFailedOperation(null, e);

		if (listener != null) {
			listener.error(e, userAction);
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

	protected void autoLoad() {
		if (SessionContext.isLoggedIn()) {
			if (fileEntry == null) {
				AssetDisplayInteractorImpl assetDisplayInteractor = new AssetDisplayInteractorImpl();
				assetDisplayInteractor.onScreenletAttached(this);
				assetDisplayInteractor.start(entryId);
			} else {
				onRetrieveAssetSuccess(fileEntry);
			}
		}
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		Bundle state = new Bundle();
		state.putParcelable(STATE_SUPER, superState);
		state.putLong(STATE_ENTRY_ID, entryId);
		state.putParcelable(STATE_FILE_ENTRY, fileEntry);
		return state;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle state = (Bundle) inState;

		entryId = state.getLong(STATE_ENTRY_ID);
		fileEntry = state.getParcelable(STATE_FILE_ENTRY);

		Parcelable superState = state.getParcelable(STATE_SUPER);
		super.onRestoreInstanceState(superState);
	}

	public long getEntryId() {
		return entryId;
	}

	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}

	public void setListener(AssetDisplayListener listener) {
		this.listener = listener;
	}

	public void setFileEntry(FileEntry fileEntry) {
		this.fileEntry = fileEntry;
	}

	public void setAutoLoad(boolean autoLoad) {
		this.autoLoad = autoLoad;
	}

	protected boolean autoLoad;
	protected long entryId;
	protected AssetDisplayListener listener;
	protected FileEntry fileEntry;
}
