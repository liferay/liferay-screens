package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayListener;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;

public class JournalArticleWithTemplateActivity extends ThemeActivity implements WebContentDisplayListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal_article_with_template);
    }

    @Override
    protected void onResume() {
        super.onResume();

        WebContentDisplayScreenlet journalArticleWithTemplate =
            findViewById(R.id.journal_article_with_template_screenlet);
        journalArticleWithTemplate.load();
        journalArticleWithTemplate.setListener(this);

        WebContentDisplayScreenlet journalArticleWithTemplateAlternative =
            findViewById(R.id.journal_article_with_template_alternative);
        journalArticleWithTemplateAlternative.load();
        journalArticleWithTemplateAlternative.setListener(this);
    }

    @Override
    public WebContent onWebContentReceived(WebContent webContent) {
        return webContent;
    }

    @Override
    public boolean onUrlClicked(String url) {
        return true;
    }

    @Override
    public boolean onWebContentTouched(View view, MotionEvent event) {
        return false;
    }

    @Override
    public void error(Exception e, String userAction) {

    }
}
