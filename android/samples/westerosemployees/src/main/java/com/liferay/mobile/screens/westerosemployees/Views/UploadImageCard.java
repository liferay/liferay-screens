package com.liferay.mobile.screens.westerosemployees.views;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;

/**
 * @author Víctor Galán Grande
 */
public class UploadImageCard extends Card {

    public UploadImageCard(Context context) {
        super(context);
    }

    public UploadImageCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UploadImageCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public UploadImageCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void goRight() {
        super.goRight();

        normalY = normalY / 5;
        setState(CardState.NORMAL);
    }

    @Override
    public void goLeft() {
        super.goLeft();

        normalY = normalY * 5;
        setState(CardState.NORMAL);
    }
}
