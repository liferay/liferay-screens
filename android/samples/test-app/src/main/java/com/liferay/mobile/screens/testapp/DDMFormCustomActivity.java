package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Paulo Cruz
 */
public class DDMFormCustomActivity extends ThemeActivity implements View.OnClickListener {

    private EditText formInstanceIdEditText;
    private Button getFormInstanceButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddm_form_custom);

        formInstanceIdEditText = findViewById(R.id.form_instance_id_edit_text);
        getFormInstanceButton = findViewById(R.id.get_form_instance_button);

        getFormInstanceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            long formInstanceId = Long.valueOf(formInstanceIdEditText.getText().toString());

            Intent intent = new Intent(this, DDMFormActivity.class);
            intent.putExtra(DDMFormActivity.FORM_INSTANCE_ID_KEY, formInstanceId);

            startActivity(intent);
        } catch (NumberFormatException e) {
            formInstanceIdEditText.setError(getString(R.string.invalid));
        }
    }
}
