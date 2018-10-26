/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Víctor Galán Grande
 */
public class GeolocationField extends Field<GeoLocation> {

    public static final Parcelable.ClassLoaderCreator<GeolocationField> CREATOR =
        new Parcelable.ClassLoaderCreator<GeolocationField>() {

            @Override
            public GeolocationField createFromParcel(Parcel source, ClassLoader loader) {
                return new GeolocationField(source, loader);
            }

            public GeolocationField createFromParcel(Parcel in) {
                throw new AssertionError();
            }

            public GeolocationField[] newArray(int size) {
                return new GeolocationField[size];
            }
        };

    public GeolocationField() {
        super();
    }

    public GeolocationField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
        super(attributes, locale, defaultLocale);
    }

    protected GeolocationField(Parcel source, ClassLoader loader) {
        super(source, loader);
    }

    @Override
    protected GeoLocation convertFromString(String stringValue) {
        if (stringValue == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(stringValue);

            double latitude = jsonObject.getDouble("latitude");
            double longitude = jsonObject.getDouble("longitude");

            return new GeoLocation(latitude, longitude);
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected boolean doValidate() {
        GeoLocation currentValue = getCurrentValue();

        if (currentValue != null) {
            double latitude = currentValue.getLatitude();
            double longitude = currentValue.getLongitude();

            if (latitude > 90.0 || latitude < -90.0) {
                return false;
            }

            if (longitude > 180.0 || longitude < -180.0) {
                return false;
            }

            return true;
        } else {
            return !isRequired();
        }
    }

    @Override
    protected String convertToData(GeoLocation geoLocation) {
        if (geoLocation == null) {
            return null;
        }

        return "{\"latitude\":" + geoLocation.getLatitude() + ", \"longitude\":" + geoLocation.getLongitude() + "}";
    }

    @Override
    protected String convertToFormattedString(GeoLocation geoLocation) {
        return geoLocation.toString();
    }
}
