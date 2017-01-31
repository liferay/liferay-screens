package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.MotionEvent;
import android.webkit.WebView;
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
			(WebContentDisplayScreenlet) findViewById(R.id.journal_article_with_template_screenlet);
		journalArticleWithTemplate.load();

		WebContentDisplayScreenlet journalArticleWithTemplateAlternative =
			(WebContentDisplayScreenlet) findViewById(R.id.journal_article_with_template_alternative);
		journalArticleWithTemplateAlternative.load();

		journalArticleWithTemplate.setListener(this);
	}

	@Override
	public WebContent onWebContentReceived(WebContent html) {
		return html;
	}

	@Override
	public boolean onWebContentClicked(WebView.HitTestResult result, MotionEvent event) {
		return false;
	}

	@Override
	public void error(Exception e, String userAction) {

	}
}
