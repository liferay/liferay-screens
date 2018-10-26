package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.list.WebContentListScreenlet;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class WebContentListActivity extends ThemeActivity implements BaseListListener<WebContent> {

    private WebContentListScreenlet webContentListScreenlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_content_display_list);

        webContentListScreenlet = findViewById(R.id.web_content_list_screenlet);
        webContentListScreenlet.setListener(this);
    }

    @Override
    public void onListPageFailed(int startRow, Exception e) {
        error(getString(R.string.page_error), e);
    }

    @Override
    protected void onResume() {
        super.onResume();

        webContentListScreenlet.loadPage(0);
    }

    @Override
    public void onListPageReceived(int startRow, int endRow, List<WebContent> entries, int rowCount) {
        info(rowCount + " " + getString(R.string.rows_received_info) + " " + startRow);
    }

    @Override
    public void onListItemSelected(WebContent webContent, View view) {

        Intent intent = getIntentWithTheme(WebContentDisplayActivity.class);
        intent.putExtra("articleId", webContent.getArticleId());
        DefaultAnimation.startActivityWithAnimation(this, intent);
    }

    @Override
    public void error(Exception e, String userAction) {

    }
}
