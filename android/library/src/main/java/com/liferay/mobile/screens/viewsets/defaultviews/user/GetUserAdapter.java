package com.liferay.mobile.screens.viewsets.defaultviews.user;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liferay.mobile.screens.context.User;

import java.util.ArrayList;
import java.util.Map;

public class GetUserAdapter extends BaseAdapter
{
    private final ArrayList jsonValues;

    private String[] keysToDisplay = {User.USER_ID, User.SCREEN_NAME, User.FIRST_NAME, User.LAST_NAME, User.EMAIL_ADDRESS, User.LANGUAGE_ID, User.EMAIL_ADDRESS_VERIFIED,
            User.LOCKOUT, User.AGREED_TERMS_USE};

    public GetUserAdapter(Map<String, Object> map)
    {
        jsonValues = new ArrayList();
        jsonValues.addAll(map.entrySet());
    }

    @Override
    public int getCount()
    {
        return jsonValues.size();
    }

    @Override
    public Map.Entry<String, Object> getItem(int position)
    {
        return (Map.Entry) jsonValues.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Map.Entry<String, Object> item = getItem(position);

        if (hasToBeDisplayed(item.getKey()))
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View customView = inflater.inflate(R.layout.custom_get_user_row, parent, false);

            TextView keyLabel = (TextView) customView.findViewById(R.id.keyLabel);
            TextView keyValue = (TextView) customView.findViewById(R.id.keyValue);

            keyLabel.setText(item.getKey());
            if (item.getValue() != null)
            {
                keyValue.setText(item.getValue().toString());
            }
            return customView;
        }
        else
        {
            return new View (parent.getContext());
        }

    }

    private boolean hasToBeDisplayed(String key)
    {
        for(String keyToDisplay : keysToDisplay)
        {
            if (key.equalsIgnoreCase(keyToDisplay))
            {
                return true;
            }
        }
        return false;
    }

}