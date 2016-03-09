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

package com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.NumberField;

/**
 * @author Jose Manuel Navarro
 */
public class CustomRatingNumberView extends LinearLayout implements DDLFieldViewModel<NumberField>, RatingBar.OnRatingBarChangeListener {

	public CustomRatingNumberView(Context context) {
		super(context);
	}

	public CustomRatingNumberView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public CustomRatingNumberView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public NumberField getField() {
		return _field;
	}

	@Override
	public void setField(NumberField field) {
		_field = field;

		TextView label = (TextView) findViewById(R.id.liferay_ddl_label);

		if (_field.isShowLabel()) {
			label.setText(_field.getLabel());
			label.setVisibility(VISIBLE);
		}

		refresh();
	}

	@Override
	public void refresh() {
		float rating = 0;

		if (_field.getCurrentValue() != null) {
			if (_field.getEditorType() == Field.EditorType.INTEGER) {
				rating = _field.getCurrentValue().floatValue();
			}
			else {
				rating = valueToRating(_field.getCurrentValue().floatValue());
			}
		}

		_ratingBar.setRating(rating);
	}

	@Override
	public void onPostValidation(boolean valid) {
		//This field is always valid because it has always a value
	}

	@Override
	public View getParentView() {
		return _parentView;
	}

	@Override
	public void setParentView(View view) {
		_parentView = view;
	}

	@Override
	public void setPositionInParent(int position) {
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		if (!fromUser) {
			return;
		}

		if (_field.getEditorType() == Field.EditorType.INTEGER) {
			_field.setCurrentValue(rating);
		}
		else {
			_field.setCurrentValue(ratingToValue(rating));
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		setSaveEnabled(false);

		_ratingBar = (RatingBar) findViewById(R.id.liferay_ddl_custom_rating);

		_ratingBar.setOnRatingBarChangeListener(this);
	}

	protected float ratingToValue(float score) {
		// normalize the number to 0..1 value
		return (score / (float) _ratingBar.getNumStars());
	}

	protected float valueToRating(float ratio) {
		// normalize the number to 0..numStars value
		return (ratio * (float) _ratingBar.getNumStars());
	}

	protected NumberField _field;
	protected RatingBar _ratingBar;
	protected View _parentView;

}