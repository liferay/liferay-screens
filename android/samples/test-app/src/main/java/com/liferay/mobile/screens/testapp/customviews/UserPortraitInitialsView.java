package com.liferay.mobile.screens.testapp.customviews;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.testapp.R;
import com.liferay.mobile.screens.viewsets.defaultviews.userportrait.UserPortraitView;

/**
 * @author Sarai Díaz García
 */

public class UserPortraitInitialsView extends UserPortraitView {

    private TextView textView;

    public UserPortraitInitialsView(Context context) {
        super(context);
    }

    public UserPortraitInitialsView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public UserPortraitInitialsView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        textView = findViewById(R.id.user_portrait_initials_text_view);
    }

    @Override
    public void showPlaceholder(User user) {
        portraitImage.setImageBitmap(null);

        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corner));

        String nameInitial = user.getFirstName().substring(0, 1);
        String surnameInitial = user.getLastName().isEmpty() ? "" : user.getLastName().substring(0, 1);
        String fullNameUppercase = (nameInitial + surnameInitial).toUpperCase();

        textView.setText(fullNameUppercase);
    }
}
