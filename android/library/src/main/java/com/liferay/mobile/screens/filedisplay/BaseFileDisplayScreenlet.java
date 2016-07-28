package com.liferay.mobile.screens.filedisplay;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.assetdisplay.AssetDisplayListener;
import com.liferay.mobile.screens.assetdisplay.interactor.AssetDisplayInteractorImpl;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Sarai Díaz García
 */
public abstract class BaseFileDisplayScreenlet
	extends BaseScreenlet<BaseFileDisplayViewModel, AssetDisplayInteractorImpl>
	implements AssetDisplayListener {

	public BaseFileDisplayScreenlet(Context context) {
		super(context);
	}

	public BaseFileDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseFileDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
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
	protected AssetDisplayInteractorImpl createInteractor(String actionName) {
		return new AssetDisplayInteractorImpl(this.getScreenletId());
	}

	@Override
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		if (_autoLoad) {
			autoLoad();
		}
	}

	public int getEntryId() {
		return _entryId;
	}

	public void setEntryId(int entryId) {
		this._entryId = entryId;
	}

	public void setListener(AssetDisplayListener listener) {
		_listener = listener;
	}

	public void setFileEntry(FileEntry fileEntry) {
		_fileEntry = fileEntry;
	}

	public void setAutoLoad(boolean autoLoad) {
		this._autoLoad = autoLoad;
	}

	protected void autoLoad() {
		if (SessionContext.isLoggedIn()) {
			try {
				onRetrieveAssetSuccess(_fileEntry);
			} catch (Exception e) {
				onRetrieveAssetFailure(e);
			}
		}
	}

	protected boolean _autoLoad;
	protected int _entryId;
	protected AssetDisplayListener _listener;
	protected FileEntry _fileEntry;
}
