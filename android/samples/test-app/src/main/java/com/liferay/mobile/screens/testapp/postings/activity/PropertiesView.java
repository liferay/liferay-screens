package com.liferay.mobile.screens.testapp.postings.activity;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.testapp.R;

public class PropertiesView extends LinearLayout {

	private TextView textView;
	private EditText editText;

	public PropertiesView(Context context) {
		super(context);
	}

	public PropertiesView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public PropertiesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public PropertiesView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		textView = findViewById(R.id.label);
		editText = findViewById(R.id.value);
	}

	public String getPropertyValue() {
		return editText.getText().toString();
	}

	public void setPropertyName(String name) {
		textView.setText(name);
		setTag(name);
	}

	public void setPropertyValue(String value) {
		editText.setText(value);
	}
}
