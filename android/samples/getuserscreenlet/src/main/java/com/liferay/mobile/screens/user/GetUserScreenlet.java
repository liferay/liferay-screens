package com.liferay.mobile.screens.user;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.user.interactor.GetUserInteractor;
import com.liferay.mobile.screens.user.view.GetUserViewModel;
import java.util.HashMap;
import java.util.Map;

public class GetUserScreenlet extends BaseScreenlet<GetUserViewModel, GetUserInteractor> implements GetUserListener {

	private GetUserListener listener;
	private String getUserBy;
	protected String[] keysToDisplay = {
		User.USER_ID, User.SCREEN_NAME, User.FIRST_NAME, User.LAST_NAME, User.EMAIL_ADDRESS, User.LANGUAGE_ID,
		User.EMAIL_ADDRESS_VERIFIED, User.LOCKOUT, User.AGREED_TERMS_USE
	};

	public GetUserScreenlet(Context context) {
		super(context);
	}

	public GetUserScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GetUserScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public GetUserScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void onGetUserFailure(Exception exception) {

		getViewModel().showFailedOperation(null, exception);
		setListViewAdapter(null);

		if (listener != null) {
			listener.onGetUserFailure(exception);
		}
	}

	@Override
	public void onGetUserSuccess(User user) {

		getViewModel().showFinishOperation(null);
		Map<String, Object> values = filter(user.getValues(), keysToDisplay);
		setListViewAdapter(new GetUserAdapter(values));

		if (listener != null) {
			listener.onGetUserSuccess(user);
		}
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		View view = LayoutInflater.from(context).inflate(R.layout.get_user_default, null);

		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.GetUserScreenlet, 0, 0);
		getUserBy = typedArray.getString(R.styleable.GetUserScreenlet_getUserBy);

		typedArray.recycle();

		return view;
	}

	@Override
	protected GetUserInteractor createInteractor(String actionName) {
		return new GetUserInteractor();
	}

	@Override
	protected void onUserAction(String userActionName, GetUserInteractor interactor, Object... args) {

		String textValue = getViewModel().getTextValue();
		interactor.start(textValue, getUserBy);
	}

	private Map<String, Object> filter(Map<String, Object> values, String[] keysToDisplay) {
		Map<String, Object> filteredValues = new HashMap<>();
		for (String key : keysToDisplay) {
			filteredValues.put(key, values.get(key));
		}
		return filteredValues;
	}

	private void setListViewAdapter(BaseAdapter adapter) {
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(adapter);
	}

	public GetUserListener getListener() {
		return listener;
	}

	public void setListener(GetUserListener listener) {
		this.listener = listener;
	}
}
