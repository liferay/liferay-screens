package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.cache.PendingItemsToSyncListener;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.testapp.fullview.LoginFullActivity;
import com.liferay.mobile.screens.testapp.postings.activity.ThingMainActivity;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;

/**
 * @author Silvio Santos
 */
public class MainActivity extends ThemeActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.activity_main);

		if (BuildConfig.DEBUG) {
			SessionContext.loadStoredCredentialsAndServer(CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES);
			LiferayLogger.e("User already logged in: " + SessionContext.isLoggedIn());
		}

		//Thing Screenlet
		findViewById(R.id.thing).setOnClickListener(this);

		//User
		findViewById(R.id.login).setOnClickListener(this);
		findViewById(R.id.sign_up).setOnClickListener(this);
		findViewById(R.id.forgot_password).setOnClickListener(this);
		findViewById(R.id.relogin).setOnClickListener(this);
		findViewById(R.id.user_portrait).setOnClickListener(this);
		findViewById(R.id.get_user).setOnClickListener(this);

		//Asset
		findViewById(R.id.asset_list).setOnClickListener(this);
		findViewById(R.id.asset_list_with_portlet_item_name).setOnClickListener(this);
		findViewById(R.id.asset_display).setOnClickListener(this);
		findViewById(R.id.asset_display_with_portlet_item_name).setOnClickListener(this);
		findViewById(R.id.user_display).setOnClickListener(this);

		//Comment
		findViewById(R.id.comment_list).setOnClickListener(this);
		findViewById(R.id.comment_display).setOnClickListener(this);
		findViewById(R.id.comment_add).setOnClickListener(this);

		//DDL
		findViewById(R.id.ddl_form).setOnClickListener(this);
		findViewById(R.id.ddl_list).setOnClickListener(this);

		//DDM
		findViewById(R.id.ddm_form).setOnClickListener(this);

		//Files
		//findViewById(R.id.audio_display).setOnClickListener(this);
		findViewById(R.id.image_display).setOnClickListener(this);
		//findViewById(R.id.pdf_display).setOnClickListener(this);
		//findViewById(R.id.video_display).setOnClickListener(this);

		//WebContent
		findViewById(R.id.web_content_list).setOnClickListener(this);
		findViewById(R.id.web_content_display_screenlet).setOnClickListener(this);
		findViewById(R.id.web_content_display_screenlet_structured).setOnClickListener(this);
		findViewById(R.id.web_content_display_screenlet_customcss).setOnClickListener(this);
		findViewById(R.id.web_content_with_template).setOnClickListener(this);

		//Rating
		findViewById(R.id.ratings).setOnClickListener(this);

		//Gallery
		findViewById(R.id.gallery).setOnClickListener(this);

		//Blog
		//findViewById(R.id.blog_display).setOnClickListener(this);

		//Portlet
		findViewById(R.id.portlet_display).setOnClickListener(this);

		//Bookmarks
		findViewById(R.id.list_bookmarks).setOnClickListener(this);
		findViewById(R.id.add_bookmark).setOnClickListener(this);

		//Others
		findViewById(R.id.login_full_screenlet).setOnClickListener(this);
		findViewById(R.id.clear_cache).setOnClickListener(this);
		findViewById(R.id.clear_cache_forms).setOnClickListener(this);
		findViewById(R.id.custom_interactor).setOnClickListener(this);
		findViewById(R.id.sync_cache).setOnClickListener(this);
		findViewById(R.id.change_theme).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.thing:
				start(ThingMainActivity.class);
				break;
			case R.id.sign_up:
				start(SignUpActivity.class);
				break;
			case R.id.forgot_password:
				start(ForgotPasswordActivity.class);
				break;
			case R.id.relogin:
				start(ReloginActivity.class);
				break;
			case R.id.user_portrait:
				start(UserPortraitActivity.class);
				break;
			case R.id.ddm_form:
				start(DDMFormActivity.class);
				break;
			case R.id.get_user:
				start(GetUserActivity.class);
				break;
			case R.id.asset_list:
				start(SelectAssetActivity.class);
				break;
			case R.id.asset_list_with_portlet_item_name:
				start(FilteredAssetActivity.class);
				break;
			case R.id.asset_display:
				Intent intentAsset = getIntentWithTheme(AssetDisplayActivity.class);
				intentAsset.putExtra("entryId",
					Long.valueOf(getResources().getString(R.string.liferay_image_entry_id)));
				DefaultAnimation.startActivityWithAnimation(this, intentAsset);
				break;
			case R.id.asset_display_with_portlet_item_name:
				Intent intentAssetPortletName = getIntentWithTheme(AssetDisplayActivity.class);
				intentAssetPortletName.putExtra("portletItemName",
					getResources().getString(R.string.liferay_portlet_item_name));
				DefaultAnimation.startActivityWithAnimation(this, intentAssetPortletName);
				break;
			case R.id.user_display:
				Intent intentUser = getIntentWithTheme(AssetDisplayActivity.class);
				intentUser.putExtra("entryId", Long.valueOf(getResources().getString(R.string.liferay_user_entryId)));
				DefaultAnimation.startActivityWithAnimation(this, intentUser);
				break;
			case R.id.comment_list:
				start(CommentsActivity.class);
				break;
			case R.id.comment_display:
				start(CommentDisplayActivity.class);
				break;
			case R.id.comment_add:
				start(CommentAddActivity.class);
				break;
			case R.id.ddl_form:
				start(DDLFormActivity.class);
				break;
			case R.id.ddl_list:
				start(DDLListActivity.class);
				break;
			case R.id.image_display:
				start(ImageDisplayActivity.class);
				break;
			case R.id.web_content_list:
				start(WebContentListActivity.class);
				break;
			case R.id.web_content_display_screenlet:
				start(WebContentDisplayActivity.class);
				break;
			case R.id.web_content_display_screenlet_structured:
				start(WebContentDisplayStructuredActivity.class);
				break;
			case R.id.web_content_display_screenlet_customcss:
				start(WebContentDisplayCustomCssActivity.class);
				break;
			case R.id.web_content_with_template:
				start(JournalArticleWithTemplateActivity.class);
				break;
			case R.id.ratings:
				start(RatingsActivity.class);
				break;
			case R.id.gallery:
				start(GalleryActivity.class);
				break;
			case R.id.portlet_display:
				Intent intentPortlet = getIntentWithTheme(WebActivity.class);
				intentPortlet.putExtra("url", getResources().getString(R.string.liferay_portlet_url));
				DefaultAnimation.startActivityWithAnimation(this, intentPortlet);
				break;
			case R.id.list_bookmarks:
				start(ListBookmarksActivity.class);
				break;
			case R.id.add_bookmark:
				start(AddBookmarkActivity.class);
				break;
			case R.id.login_full_screenlet:
				start(LoginFullActivity.class);
				break;
			case R.id.clear_cache:

				SessionContext.removeStoredCredentials(CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES);

				try {
					Long groupId = LiferayServerContext.getGroupId();
					Long userId = SessionContext.getUserId();
					boolean success = Cache.destroy(groupId, userId);
					info(getString(R.string.ddlform_cache_cleared_info) + " " + (success ? getString(
						R.string.successfully) : getString(R.string.failed)));
				} catch (Exception e) {
					LiferayLogger.e("Error clearing cache", e);
				}
				break;
			case R.id.clear_cache_forms:
				try {
					Long groupId = LiferayServerContext.getGroupId();
					Long userId = SessionContext.getUserId();
					boolean destroyed = Cache.destroy(groupId, userId, DDLFormEvent.class.getSimpleName());
					info(getString(R.string.ddlform_delete_cache_entries_info) + " " + (destroyed ? getString(
						R.string.successfully) : getString(R.string.failed)));
				} catch (Exception e) {
					LiferayLogger.e("Error clearing cache", e);
				}
				break;
			case R.id.custom_interactor:
				start(CustomInteractorActivity.class);
				break;
			case R.id.sync_cache:
				Cache.pendingItemsToSync(new PendingItemsToSyncListener() {
					@Override
					public void getItemsCount(int totalCount) {
						if (totalCount > 0) {
							Cache.resync();
							info(getString(R.string.resync_cache_info));
						} else {
							info(getString(R.string.resync_cache_empty));
						}
					}
				});
				break;
			case R.id.change_theme:
				finish();
				changeToNextTheme();
				start(MainActivity.class);
				break;
			default:
				start(LoginActivity.class);
		}
	}

	private void start(Class clasz) {
		DefaultAnimation.startActivityWithAnimation(this, getIntentWithTheme(clasz));
	}
}
