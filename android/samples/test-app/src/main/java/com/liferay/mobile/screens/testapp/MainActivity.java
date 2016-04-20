package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.testapp.fullview.LoginFullActivity;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;

/**
 * @author Silvio Santos
 */
public class MainActivity extends ThemeActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.activity_main);

		findViewById(R.id.ddl_form).setOnClickListener(this);
		findViewById(R.id.ddl_list).setOnClickListener(this);
		findViewById(R.id.asset_list).setOnClickListener(this);
		findViewById(R.id.web_content_list).setOnClickListener(this);
		findViewById(R.id.sign_up).setOnClickListener(this);
		findViewById(R.id.forgot_password).setOnClickListener(this);
		findViewById(R.id.user_portrait).setOnClickListener(this);
		findViewById(R.id.web_content_display_screenlet).setOnClickListener(this);
		findViewById(R.id.web_content_display_screenlet_structured).setOnClickListener(this);
		findViewById(R.id.add_bookmark).setOnClickListener(this);
		findViewById(R.id.journal_article_with_template).setOnClickListener(this);
		findViewById(R.id.filtered_asset).setOnClickListener(this);
		findViewById(R.id.login_full_screenlet).setOnClickListener(this);
		findViewById(R.id.change_theme).setOnClickListener(this);
		findViewById(R.id.login).setOnClickListener(this);
		findViewById(R.id.clear_cache).setOnClickListener(this);
		findViewById(R.id.clear_cache_forms).setOnClickListener(this);
		findViewById(R.id.sync_cache).setOnClickListener(this);
		findViewById(R.id.custom_interactor).setOnClickListener(this);
		findViewById(R.id.list_bookmarks).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ddl_form:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(DDLFormActivity.class));
				break;
			case R.id.ddl_list:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(DDLListActivity.class));
				break;
			case R.id.asset_list:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(SelectAssetActivity.class));
				break;
			case R.id.web_content_list:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(WebViewListActivity.class));
				break;
			case R.id.sign_up:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(SignUpActivity.class));
				break;
			case R.id.forgot_password:
				DefaultAnimation.startActivityWithAnimation(this,
					getIntentWithTheme(ForgotPasswordActivity.class));
				break;
			case R.id.user_portrait:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(UserPortraitActivity.class));
				break;
			case R.id.web_content_display_screenlet:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(WebContentDisplayActivity.class));
				break;
			case R.id.web_content_display_screenlet_structured:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(WebContentDisplayStructuredActivity.class));
				break;
			case R.id.add_bookmark:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(AddBookmarkActivity.class));
				break;
			case R.id.journal_article_with_template:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(JournalArticleWithTemplateActivity.class));
				break;
			case R.id.filtered_asset:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(FilteredAssetActivity.class));
				break;
			case R.id.login_full_screenlet:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(LoginFullActivity.class));
				break;
			case R.id.change_theme:
				finish();
				changeToNextTheme();
				startActivity(getIntentWithTheme(MainActivity.class));
				break;
			case R.id.clear_cache_forms:
				int formRows = CacheSQL.getInstance().clear(DefaultCachedType.DDL_FORM);
				int recordRows = CacheSQL.getInstance().clear(DefaultCachedType.DDL_RECORD);
				int listRows = CacheSQL.getInstance().clear(DefaultCachedType.DDL_LIST);
				int countRows = CacheSQL.getInstance().clear(DefaultCachedType.DDL_LIST_COUNT);

				String cacheFormsMessage = "Deleted " + formRows + " forms, " + recordRows + " records, " +
					listRows + " list rows and " + countRows + " count rows.";

				info(cacheFormsMessage);
				break;
			case R.id.clear_cache:
				boolean success = CacheSQL.getInstance().clear(this);
				String clearCacheMessage = "Cache cleared: " + (success ? "sucessfully" : "failed");
				info(clearCacheMessage);
				break;
			case R.id.sync_cache:
				CacheSQL.getInstance().resync();
				info("Launched resync process");
				break;
			case R.id.custom_interactor:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(CustomInteractorActivity.class));
				break;
			case R.id.list_bookmarks:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(ListBookmarksActivity.class));
				break;
			default:
				DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(LoginActivity.class));
		}
	}


}