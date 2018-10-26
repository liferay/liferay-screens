package com.liferay.mobile.screens.listbookmark;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class Bookmark implements Parcelable {

    public static final Creator<Bookmark> CREATOR = new Creator<Bookmark>() {
        @Override
        public Bookmark createFromParcel(Parcel in) {
            return new Bookmark(in);
        }

        @Override
        public Bookmark[] newArray(int size) {
            return new Bookmark[size];
        }
    };
    private String url;
    private Map values;

    public Bookmark() {
        super();
    }

    protected Bookmark(Parcel in) {
        url = in.readString();
    }

    public Bookmark(Map<String, Object> stringObjectMap) {
        url = (String) stringObjectMap.get("url");
        values = stringObjectMap;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getUrl() {
        return url;
    }

    public Map getValues() {
        return values;
    }

    public void setValues(Map values) {
        this.values = values;
    }
}
