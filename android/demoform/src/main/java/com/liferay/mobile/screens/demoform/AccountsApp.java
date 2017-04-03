package com.liferay.mobile.screens.demoform;

import android.Manifest;
import android.app.Application;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.liferay.mobile.screens.ddl.form.EventProperty;
import com.liferay.mobile.screens.ddl.form.EventType;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.analytics.TrackingAction;
import java.util.UUID;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class AccountsApp extends Application
	implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
	com.google.android.gms.location.LocationListener {

	private long timer;
	private GoogleApiClient googleApiClient;
	private Location location;
	private Record currentRecord;
	private UUID uuid;

	@Override
	public void onCreate() {
		super.onCreate();

		if (googleApiClient == null) {
			googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
			googleApiClient.connect();
		}
		uuid = UUID.randomUUID();

		timer = SystemClock.currentThreadTimeMillis();
	}

	@Override
	public void onConnected(Bundle bundle) {

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED
			&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
			!= PERMISSION_GRANTED) {
			return;
		}

		LocationRequest locationRequest = new LocationRequest();
		locationRequest.setInterval(10000);
		locationRequest.setFastestInterval(5000);
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

		Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
		if (lastLocation != null) {
			location = lastLocation;
			EventProperty eventProperty = new EventProperty(EventType.APP_START, "Opening app", "app");
			eventProperty.setTime(0L);
			TrackingAction.post(this, eventProperty, lastLocation);
		}
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			this.location = location;
		}
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		googleApiClient.disconnect();

		EventProperty eventProperty = new EventProperty(EventType.APP_END, "Closing app", "app");
		eventProperty.setTime(timer);
		TrackingAction.post(this, eventProperty, location);
	}

	public Location getLocation() {
		return location;
	}

	public void setCurrentRecord(Record currentRecord) {
		this.currentRecord = currentRecord;
	}

	public long getTimer() {
		return timer;
	}

	public UUID getUUID() {
		return uuid;
	}
}
