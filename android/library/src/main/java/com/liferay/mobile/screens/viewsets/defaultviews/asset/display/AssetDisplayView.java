package com.liferay.mobile.screens.viewsets.defaultviews.asset.display;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.display.AssetDisplayViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayView extends FrameLayout implements AssetDisplayViewModel {

    private BaseScreenlet screenlet;
    private ProgressBar progressBar;
    private ViewGroup container;

    public AssetDisplayView(Context context) {
        super(context);
    }

    public AssetDisplayView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public AssetDisplayView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        progressBar = findViewById(R.id.liferay_progress);
        container = findViewById(R.id.inner_screenlet_container);
    }

    @Override
    public void showStartOperation(String actionName) {
        progressBar.setVisibility(VISIBLE);
        container.setVisibility(GONE);
    }

    @Override
    public void showFinishOperation(String actionName) {
        throw new UnsupportedOperationException(
            "showFinishOperation(String) is not supported." + " Use showFinishOperation(BaseScreenlet) instead.");
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {
        progressBar.setVisibility(GONE);
        LiferayLogger.e("Could not load asset", e);
    }

    @Override
    public BaseScreenlet getScreenlet() {
        return screenlet;
    }

    @Override
    public void setScreenlet(BaseScreenlet screenlet) {
        this.screenlet = screenlet;
    }

    @Override
    public void showFinishOperation(View view) {
        //TODO Check the type of the child screenlet and reuse it if possible
        container.removeAllViews();
        container.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(GONE);
        container.setVisibility(VISIBLE);
        LiferayLogger.d("Asset display loaded successfully");
    }

    @Override
    public void removeInnerScreenlet() {
        container.removeAllViews();
    }
}