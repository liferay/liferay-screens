package com.liferay.mobile.screens.rating;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.rating.interactor.RatingInteractor;
import com.liferay.mobile.screens.rating.interactor.RatingInteractorImpl;
import com.liferay.mobile.screens.rating.view.RatingViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Alejandro Hernández
 */

public class RatingScreenlet extends BaseScreenlet<RatingViewModel, RatingInteractor>
	implements RatingListener {

	public static final String LOAD_RATINGS_ACTION = "loadRatings";
	public static final String ADD_RATING_ACTION = "addRating";
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

		_entryId = castToLong(typedArray.getString(R.styleable.RatingScreenlet_entryId));
		_className = typedArray.getString(R.styleable.RatingScreenlet_className);
		_classPK = castToLong(typedArray.getString(R.styleable.RatingScreenlet_classPK));

		_stepCount = typedArray.getInt(R.styleable.RatingScreenlet_stepCount, 2);

		typedArray.recycle();

		return view;
	}

	@Override protected RatingInteractor createInteractor(String actionName) {
		return new RatingInteractorImpl(getScreenletId());
	}

	@Override protected void onUserAction(String userActionName, RatingInteractor interactor,
		Object... args) {
		try {
			switch (userActionName) {
				case LOAD_RATINGS_ACTION:
					interactor.loadRatings(_entryId, _classPK, _className, _stepCount);
					break;
				case ADD_RATING_ACTION:
					double score = (double) args[0];
					interactor.addRating(_classPK, _className, score, _stepCount);
					break;
				case DELETE_RATING_ACTION:
					interactor.deleteRating(_classPK, _className, _stepCount);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			LiferayLogger.e(e.getMessage());
			onRatingOperationFailure(e);
		}
	}

	protected void autoLoad() {
		if (SessionContext.isLoggedIn()) {
			try {
				load();
			} catch (Exception e) {
				onRatingOperationFailure(e);
			}
		}
	}

	public void load() throws Exception {
		performUserAction(LOAD_RATINGS_ACTION);
	}

	@Override public void onRatingOperationFailure(Exception exception) {
		getViewModel().showFailedOperation(null, exception);

		if (_listener != null) {
			_listener.onRatingOperationFailure(exception);
		}
	}

	@Override public void onRatingOperationSuccess(AssetRating assetRating) {
		getViewModel().showFinishOperation(null, assetRating);

		_classPK = assetRating.getClassPK();
		_className = assetRating.getClassName();

		if (_listener != null) {
			_listener.onRatingOperationSuccess(assetRating);
		}
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

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
		getViewModel().setReadOnly(_readOnly);
	}

	public int getStepCount() {
		return _stepCount;
	}

	public void setStepCount(int stepCount) {
		this._stepCount = stepCount;
	}

	private RatingListener _listener;

	private long _entryId;
	private boolean _autoLoad;
	private boolean _readOnly;
	private long _classPK;
	private int _stepCount;
	private String _className;
}
