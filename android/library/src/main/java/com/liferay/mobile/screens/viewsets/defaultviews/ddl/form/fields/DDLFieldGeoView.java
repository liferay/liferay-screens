package com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.GeoLocation;
import com.liferay.mobile.screens.ddl.model.GeolocationField;
import com.liferay.mobile.screens.util.LiferayLocale;
import java.text.NumberFormat;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Víctor Galán Grande
 */

public class DDLFieldGeoView extends LinearLayout implements DDLFieldViewModel<GeolocationField>, TextWatcher {

	public static final double INVALID_LATITUDE = 90 + 1;
	public static final double INVALID_LONGITUDE = 180 + 1;
	protected GeolocationField field;
	protected TextView labelTextView;
	protected EditText latitudeEditText;
	protected EditText longitudeEditText;
	protected View parentView;
	protected Observable<GeolocationField> onChangedValueObservable = Observable.empty();

	public DDLFieldGeoView(Context context) {
		super(context);
	}

	public DDLFieldGeoView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public DDLFieldGeoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public DDLFieldGeoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public GeolocationField getField() {
		return field;
	}

	@Override
	public void setField(GeolocationField field) {
		this.field = field;

		if (labelTextView != null) {
			labelTextView.setVisibility(this.field.isShowLabel() ? VISIBLE : GONE);
			labelTextView.setText(this.field.getLabel());
		}

		refresh();
	}

	@Override
	public void refresh() {
		if (field.getCurrentValue() != null) {
			GeoLocation geoLocation = field.getCurrentValue();

			NumberFormat formatter = NumberFormat.getNumberInstance(LiferayLocale.getDefaultLocale());

			latitudeEditText.setText(formatter.format(geoLocation.getLatitude()));
			longitudeEditText.setText(formatter.format(geoLocation.getLongitude()));
		}
	}

	@Override
	public void onPostValidation(boolean valid) {
		String errorText = valid ? null : getResources().getString(R.string.invalid);

		if (labelTextView == null) {
			latitudeEditText.setError(errorText);
			longitudeEditText.setError(errorText);
		} else {
			labelTextView.setError(errorText);
		}
	}

	@Override
	public View getParentView() {
		return parentView;
	}

	@Override
	public void setParentView(View view) {
		this.parentView = view;
	}

	@Override
	public Observable<GeolocationField> getOnChangedValueObservable() {
		return onChangedValueObservable;
	}

	@Override
	public void setUpdateMode(boolean enabled) {
		latitudeEditText.setEnabled(enabled);
		longitudeEditText.setEnabled(enabled);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		labelTextView = findViewById(R.id.liferay_ddl_label);
		latitudeEditText = findViewById(R.id.liferay_latitude_edit_text);
		longitudeEditText = findViewById(R.id.liferay_longitude_edit_text);
		//TODO Add new button to get LAT and LON from GPS Location

		latitudeEditText.addTextChangedListener(this);
		longitudeEditText.addTextChangedListener(this);

		onChangedValueObservable = RxTextView.textChanges(latitudeEditText)
			.mergeWith(RxTextView.textChanges(longitudeEditText))
			.distinctUntilChanged()
			.map(new Func1<CharSequence, GeolocationField>() {
				@Override
				public GeolocationField call(CharSequence charSequence) {
					return field;
				}
			});
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String latitudeText = latitudeEditText.getText().toString();
		String longitudeText = longitudeEditText.getText().toString();

		double latitude = castToDoubleOrDefault(latitudeText, INVALID_LATITUDE);
		double longitude = castToDoubleOrDefault(longitudeText, INVALID_LONGITUDE);

		if (field != null) {
			GeoLocation geoLocation = new GeoLocation(latitude, longitude);
			field.setCurrentValue(geoLocation);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	private double castToDoubleOrDefault(String value, double defaultValue) {
		if (value.isEmpty() || "-".equals(value)) {
			return defaultValue;
		}

		return Double.parseDouble(value);
	}
}
