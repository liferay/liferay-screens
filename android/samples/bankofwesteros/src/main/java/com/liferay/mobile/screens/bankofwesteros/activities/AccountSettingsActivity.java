package com.liferay.mobile.screens.bankofwesteros.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.bankofwesteros.views.UpdateUserInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.UserPortraitListener;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.westeros.WesterosSnackbar;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class AccountSettingsActivity extends Activity implements View.OnClickListener, UserPortraitListener {

    private EditText firstName;
    private EditText lastName;
    private EditText emailAddress;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);

        findViewById(R.id.arrow_back_to_issues).setOnClickListener(this);
        findViewById(R.id.account_settings_save).setOnClickListener(this);

        User user = SessionContext.getCurrentUser();
        firstName = findViewById(R.id.first_name);
        firstName.setText(user.getFirstName());
        lastName = findViewById(R.id.last_name);
        lastName.setText(user.getLastName());
        emailAddress = findViewById(R.id.email_address);
        emailAddress.setText(user.getEmail());
        password = findViewById(R.id.password);
        BasicAuthentication basicAuth = (BasicAuthentication) SessionContext.getAuthentication();
        password.setText(basicAuth.getPassword());

        UserPortraitScreenlet userPortraitScreenlet = findViewById(R.id.userportrait);
        userPortraitScreenlet.setListener(this);
        userPortraitScreenlet.loadLoggedUserPortrait();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.account_settings_save) {
            saveUser();
        } else if (v.getId() == R.id.arrow_back_to_issues) {
            finish();
        }
    }

    @Override
    public Bitmap onUserPortraitLoadReceived(Bitmap bitmap) {
        return null;
    }

    @Override
    public void onUserPortraitUploaded() {
        WesterosSnackbar.showSnackbar(this, "Portrait updated", R.color.green_westeros);
    }

    @Override
    public void error(Exception e, String userAction) {
        WesterosSnackbar.showSnackbar(this, "Error at " + userAction, R.color.colorAccent_westeros);
    }

    private void saveUser() {
        final String firstName = this.firstName.getText().toString();
        final String lastName = this.lastName.getText().toString();
        final String emailAddress = this.emailAddress.getText().toString();
        final String newPassword = password.getText().toString();

        if (password.getText().toString().isEmpty()) {
            setError(password);
            return;
        }

        BasicAuthentication basicAuth = (BasicAuthentication) SessionContext.getAuthentication();
        if (basicAuth.getPassword().equals(password.getText().toString())) {
            setError(password);
            WesterosSnackbar.showSnackbar(this, "Password should be different", R.color.colorAccent_westeros);
            return;
        }

        UpdateUserInteractor updateUserInteractor = new UpdateUserInteractor();
        updateUserInteractor.saveUser(firstName, lastName, emailAddress, newPassword, new JSONObjectCallback() {
            @Override
            public void onSuccess(JSONObject result) {

                SessionContext.createBasicSession(emailAddress, newPassword);

                clearError(password);
                WesterosSnackbar.showSnackbar(AccountSettingsActivity.this, "User updated", R.color.green_westeros);
            }

            @Override
            public void onFailure(Exception exception) {
                LiferayLogger.e("error", exception);
                WesterosSnackbar.showSnackbar(AccountSettingsActivity.this, "Error updating user",
                    R.color.colorAccent_westeros);
            }
        });
    }

    private void setError(EditText editText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_warning, 0);
        editText.setBackgroundResource(R.drawable.westeros_warning_edit_text_white_drawable);
    }

    private void clearError(EditText editText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        editText.setBackgroundResource(R.drawable.westeros_edit_text_selector);
    }
}
