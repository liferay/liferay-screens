package com.liferay.mobile.screens.user;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.liferay.mobile.screens.viewsets.defaultviews.user.GetUserAdapter;
import com.liferay.mobile.screens.user.interactor.GetUserInteractor;
import com.liferay.mobile.screens.user.view.GetUserViewModel;
import com.liferay.mobile.screens.user.GetUserListener;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.R;

public class GetUserScreenlet extends
        BaseScreenlet<GetUserViewModel, GetUserInteractor>
        implements GetUserListener
{

    private GetUserListener listener;

    private String getUserByAttribute;

    public GetUserScreenlet(Context context)
    {
        super(context);
    }

    public GetUserScreenlet(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GetUserScreenlet(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onGetUserFailure(Exception exception)
    {
        getViewModel().showFailedOperation(null, exception);

        if (listener != null) {
            listener.onGetUserFailure(exception);
        }
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

        setListViewAdapter(null);
    }

    @Override
    public void onGetUserSuccess(User user)
    {
        getViewModel().showFinishOperation(null);

        if (listener != null) {
            listener.onGetUserSuccess(user);
        }

        setListViewAdapter(new GetUserAdapter(user.getValues()));
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.get_user_default, null);

        TypedArray typedArray =  context.getTheme().obtainStyledAttributes(attributes, R.styleable.GetUserScreenlet, 0, 0);
        getUserByAttribute = typedArray.getString(R.styleable.GetUserScreenlet_getUserBy);

        typedArray.recycle();

        return view;
    }

    @Override
    protected GetUserInteractor createInteractor(String actionName)
    {
        return new GetUserInteractor();
    }

    @Override
    protected void onUserAction(String userActionName, GetUserInteractor interactor, Object... args)
    {
        String textValue = getViewModel().getTextValue();

        interactor.start(textValue, getUserByAttribute);
    }

    private void setListViewAdapter(BaseAdapter adapter)
    {
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public GetUserListener getListener()
    {
        return listener;
    }

    public void setListener(GetUserListener listener)
    {
        this.listener = listener;
    }
}
