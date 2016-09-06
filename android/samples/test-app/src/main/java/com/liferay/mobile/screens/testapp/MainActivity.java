package com.liferay.mobile.screens.testapp;

import android.content.Intent;
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
		findViewById(R.id.relogin).setOnClickListener(this);
		findViewById(R.id.list_comments).setOnClickListener(this);
		findViewById(R.id.ratings).setOnClickListener(this);
		findViewById(R.id.user_display).setOnClickListener(this);
		findViewById(R.id.gallery).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ddl_form:
				startActivity(DDLFormActivity.class);
				break;
			case R.id.ddl_list:
				startActivity(DDLListActivity.class);
				break;
			case R.id.asset_list:
				startActivity(SelectAssetActivity.class);
				break;
			case R.id.web_content_list:
				startActivity(WebViewListActivity.class);
				break;
			case R.id.sign_up:
				startActivity(SignUpActivity.class);
				break;
			case R.id.forgot_password:
				startActivity(ForgotPasswordActivity.class);
				break;
			case R.id.user_portrait:
				startActivity(UserPortraitActivity.class);
				break;
			case R.id.web_content_display_screenlet:
				startActivity(WebContentDisplayActivity.class);
				break;
			case R.id.web_content_display_screenlet_structured:
				startActivity(WebContentDisplayStructuredActivity.class);
				break;
			case R.id.add_bookmark:
				startActivity(AddBookmarkActivity.class);
				break;
			case R.id.journal_article_with_template:
				startActivity(JournalArticleWithTemplateActivity.class);
				break;
			case R.id.filtered_asset:
				startActivity(FilteredAssetActivity.class);
				break;
			case R.id.login_full_screenlet:
				startActivity(LoginFullActivity.class);
				break;
			case R.id.change_theme:
				finish();
				changeToNextTheme();
				startActivity(MainActivity.class);
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
				startActivity(CustomInteractorActivity.class);
				break;
			case R.id.list_bookmarks:
				startActivity(ListBookmarksActivity.class);
				break;
			case R.id.relogin:
				startActivity(ReloginActivity.class);
				break;
			case R.id.list_comments:
				startActivity(CommentsActivity.class);
				break;
			case R.id.ratings:
				startActivity(RatingsActivity.class);
				break;
			case R.id.gallery:
				startActivity(GalleryActivity.class);
				break;
			case R.id.user_display:
				Intent intent = getIntentWithTheme(AssetDisplayActivity.class);
				intent.putExtra("entryId", Long.valueOf(getResources().getString(R.string.liferay_user_entryId)));
				DefaultAnimation.startActivityWithAnimation(this, intent);
				break;
			default:
				startActivity(LoginActivity.class);
		}
	}

	private void startActivity(Class clasz) {
		DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(clasz));
	}
}