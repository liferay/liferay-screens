package com.liferay.mobile.screens.rating;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.interactor.BaseRatingInteractor;
import com.liferay.mobile.screens.rating.interactor.delete.DeleteRatingInteractor;
import com.liferay.mobile.screens.rating.interactor.delete.DeleteRatingInteractorImpl;
import com.liferay.mobile.screens.rating.interactor.load.LoadRatingInteractor;
import com.liferay.mobile.screens.rating.interactor.load.LoadRatingInteractorImpl;
import com.liferay.mobile.screens.rating.interactor.update.UpdateRatingInteractor;
import com.liferay.mobile.screens.rating.interactor.update.UpdateRatingInteractorImpl;
import com.liferay.mobile.screens.rating.view.RatingViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.List;

/**
 * @author Alejandro Hernández
 */

public class RatingScreenlet extends BaseScreenlet<RatingViewModel, BaseRatingInteractor>
	implements RatingListener {

	public static final String LOAD_RATINGS_ACTION = "loadRatings";
	public static final String LOAD_USER_RATING_ACTION = "loadUserRating";
	public static final String ADD_RATING_ACTION = "addRating";
	public static final String UPDATE_RATING_ACTION = "updateRating";
	public static final String DELETE_RATING_ACTION = "deleteRating";

	public RatingScreenlet(Context context) {
		super(context);
	}

	public RatingScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public RatingScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override protected void onScreenletAttached() {
		super.onScreenletAttached();

		getViewModel().setReadOnly(_readOnly);

		if (_autoLoad) {
			autoLoad();
		}
	}

	@Override protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme()
			.obtainStyledAttributes(attributes, R.styleable.RatingScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.RatingScreenlet_layoutId, 0);

		_autoLoad = typedArray.getBoolean(R.styleable.RatingScreenlet_autoLoad, true);

		_readOnly = typedArray.getBoolean(R.styleable.RatingScreenlet_readOnly, false);

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		_entryId = typedArray.getString(R.styleable.RatingScreenlet_entryId);

		typedArray.recycle();

		return view;
	}

	@Override protected BaseRatingInteractor createInteractor(String actionName) {
		switch (actionName) {
			case LOAD_RATINGS_ACTION:
				return new LoadRatingInteractorImpl(getScreenletId());
			case ADD_RATING_ACTION:
				return new UpdateRatingInteractorImpl(getScreenletId());
			case DELETE_RATING_ACTION:
				return new DeleteRatingInteractorImpl(getScreenletId());
			default:
				return null;
		}
	}

	@Override protected void onUserAction(String userActionName, BaseRatingInteractor interactor,
		Object... args) {
		switch (userActionName) {
			case LOAD_RATINGS_ACTION:
				try {
					((LoadRatingInteractor) getInteractor(LOAD_RATINGS_ACTION)).loadRatings(
						castToLong(_entryId));
				} catch (Exception e) {
					LiferayLogger.e(e.getMessage());
					onRetrieveRatingEntriesFailure(e);
				}
				break;
			case ADD_RATING_ACTION:
				double score = (double) args[0];
				try {
					((UpdateRatingInteractor) interactor).addRating(_className, _classPK, score);
				} catch (Exception e) {
					LiferayLogger.e(e.getMessage());
					onAddRatingEntryFailure(e);
				}
				break;
			case DELETE_RATING_ACTION:
				try {
					((DeleteRatingInteractor) interactor).deleteRating(_className, _classPK);
				} catch (Exception e) {
					LiferayLogger.e(e.getMessage());
					onDeleteRatingEntryFailure(e);
				}
			default:
				break;
		}
	}

	protected void autoLoad() {
		if (_entryId != null && SessionContext.isLoggedIn()) {
			try {
				load();
			} catch (Exception e) {
				onRetrieveRatingEntriesFailure(e);
			}
		}
	}

	public void load() throws Exception {
		performUserAction(LOAD_RATINGS_ACTION);
	}

	@Override public void onRetrieveRatingEntriesFailure(Exception exception) {
		getViewModel().showFailedOperation(LOAD_RATINGS_ACTION, exception);

		if (_listener != null) {
			_listener.onRetrieveRatingEntriesFailure(exception);
		}
	}

	@Override public void onRetrieveRatingEntriesSuccess(long classPK, String className,
		List<RatingEntry> ratings) {
		getViewModel().showFinishOperation(LOAD_RATINGS_ACTION, ratings);

		_classPK = classPK;
		_className = className;

		if (hasUserEntry(ratings)) {
			getViewModel().showFinishOperation(LOAD_USER_RATING_ACTION, _userRatingEntry);
		}

		if (_listener != null) {
			_listener.onRetrieveRatingEntriesSuccess(classPK, className, ratings);
		}
	}

	@Override public void onAddRatingEntryFailure(Exception exception) {
		getViewModel().showFailedOperation(ADD_RATING_ACTION, exception);

		if (_listener != null) {
			_listener.onAddRatingEntryFailure(exception);
		}
	}

	@Override public void onAddRatingEntrySuccess(RatingEntry entry) {
		if (_userRatingEntry == null) {
			getViewModel().showFinishOperation(ADD_RATING_ACTION, entry);
		} else {
			getViewModel().showFinishOperation(UPDATE_RATING_ACTION, entry);
		}

		_userRatingEntry = entry;

		if (_listener != null) {
			_listener.onAddRatingEntrySuccess(entry);
		}
	}

	@Override public void onDeleteRatingEntryFailure(Exception exception) {
		getViewModel().showFailedOperation(DELETE_RATING_ACTION, exception);

		if (_listener != null) {
			_listener.onDeleteRatingEntryFailure(exception);
		}
	}

	@Override public void onDeleteRatingEntrySuccess() {
		if (_userRatingEntry != null) {
			getViewModel().showFinishOperation(DELETE_RATING_ACTION);
		}

		_userRatingEntry = null;

		if (_listener != null) {
			_listener.onDeleteRatingEntrySuccess();
		}
	}

	private boolean hasUserEntry(List<RatingEntry> ratings) {
		for (RatingEntry entry : ratings) {
			if (entry.getUserId() == SessionContext.getCurrentUser().getId()) {
				_userRatingEntry = entry;
				return true;
			}
		}
		return false;
	}

	public RatingListener getListener() {
		return _listener;
	}

	public void setListener(RatingListener listener) {
		this._listener = listener;
	}

	public boolean isAutoLoad() {
		return _autoLoad;
	}

	public void setAutoLoad(boolean autoLoad) {
		_autoLoad = autoLoad;
	}

	public String getEntryId() {
		return _entryId;
	}

	public void setEntryId(String entryId) {
		_entryId = entryId;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
		getViewModel().setReadOnly(_readOnly);
	}

	private RatingEntry _userRatingEntry;
	private RatingListener _listener;
	private long _classPK;
	private String _className;
	private String _entryId;
	private boolean _autoLoad;
	private boolean _readOnly;
}
