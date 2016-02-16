package com.liferay.mobile.screens.testapp;

import android.os.Bundle;

import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayListener;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayScreenlet;

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
	public String onWebContentReceived(WebContentDisplayScreenlet source, String html) {
		return html;
	}

	@Override
	public void onWebContentFailure(WebContentDisplayScreenlet source, Exception e) {
	}

	@Override
	public void loadingFromCache(boolean success) {
	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {
	}

	@Override
	public void storingToCache(Object object) {
	}
}
