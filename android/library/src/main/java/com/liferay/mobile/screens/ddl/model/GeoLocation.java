package com.liferay.mobile.screens.ddl.model;

import java.io.Serializable;

/**
 * @author Víctor Galán Grande
 */

public class GeoLocation implements Serializable {

	private final double latitude;
	private final double longitude;

	public GeoLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "latitude=" + latitude + ", longitude=" + longitude;
	}
}
