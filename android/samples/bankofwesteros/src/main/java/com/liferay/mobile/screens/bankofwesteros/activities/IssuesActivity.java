package com.liferay.mobile.screens.bankofwesteros.activities;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.jakewharton.rxbinding.view.RxView;
import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.bankofwesteros.utils.Card;
import com.liferay.mobile.screens.bankofwesteros.utils.EndAnimationListener;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.viewsets.westeros.WesterosSnackbar;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class IssuesActivity extends CardActivity
	implements View.OnClickListener, DDLFormListener, BaseListListener<Record>, View.OnTouchListener {

	private DDLFormScreenlet ddlFormScreenlet;
	private DDLListScreenlet ddlListScreenlet;
	private Record record;
	private View backgroundCard;
	private ImageView card1ToBackground;
	private ImageView card1ToBackgroundMenu;
	private TextView reportIssueTitle;
	private Button sendButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.issues);

		backgroundCard = findViewById(R.id.background);

		card1ToBackgroundMenu = (ImageView) findViewById(R.id.card1_to_background_menu);
		card1ToBackgroundMenu.setOnClickListener(this);
		card1ToBackground = (ImageView) findViewById(R.id.card1_to_background);
		card1ToBackground.setOnClickListener(this);

		reportIssueTitle = (TextView) findViewById(R.id.report_issue_title);

		ddlFormScreenlet = (DDLFormScreenlet) findViewById(R.id.ddlform);
		ddlFormScreenlet.setListener(this);
		ddlListScreenlet = (DDLListScreenlet) findViewById(R.id.ddllist);
		ddlListScreenlet.setListener(this);

		sendButton = (Button) findViewById(R.id.liferay_form_submit);

		TextView callMenuEntry = (TextView) findViewById(R.id.call_menu_entry);
		callMenuEntry.setText(getCallSpannableString(), TextView.BufferType.SPANNABLE);

		tryToCall(callMenuEntry);

		findViewById(R.id.account_settings_menu_entry).setOnTouchListener(this);
		findViewById(R.id.send_message_menu_entry).setOnTouchListener(this);
		findViewById(R.id.sign_out_menu_entry).setOnTouchListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		//we don't want to crash if activity gets restored without session
		if (!SessionContext.isLoggedIn()) {
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.card1_to_front_view:
				goLeftCard1();
				reportIssueTitle.setText(getString(R.string.report_issue));
				break;
			case R.id.card1_to_background:
			case R.id.card1_to_background_menu:
				if (cardHistory.peek() == Card.BACKGROUND) {
					toCard1();
					card1ToBackgroundMenu.setImageDrawable(
						ResourcesCompat.getDrawable(getResources(), R.drawable.icon_options_red, getTheme()));
				} else {
					card1ToBackgroundMenu.setImageDrawable(
						ResourcesCompat.getDrawable(getResources(), R.drawable.icon_options_close, getTheme()));
					toBackground();
				}
				break;
			default:
				super.onClick(v);
		}
	}

	@Override
	public void onListPageFailed(int startRow, Exception e) {

	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List<Record> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(Record element, View view) {
		selectDDLEntry(element);
		if (view.getId() == R.id.liferay_list_edit) {
			toCard2();
		} else if (view.getId() == R.id.liferay_list_view) {
			goRightCard1(element);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			v.setBackgroundColor(ResourcesCompat.getColor(getResources(), android.R.color.white, getTheme()));
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			launchMenu(v);
		}
		return false;
	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		reloadListAndShowResult("Issue Created");
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		reloadListAndShowResult("Issue Updated");
	}

	@Override
	public void onDDLFormLoaded(Record record) {
	}

	@Override
	public void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes) {
	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {
	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {
	}

	@Override
	public void error(Exception e, String userAction) {

	}

	@Override
	protected void animateScreenAfterLoad() {
		cardHistory.offer(Card.CARD1);
		card1ToBackgroundMenu.setImageDrawable(
			ResourcesCompat.getDrawable(getResources(), R.drawable.icon_options_red, getTheme()));

		//TODO extract this animation
		backgroundCard.setY(maxHeight);
		card1.setY(maxHeight);
		card2.setY(maxHeight);

		card1.animate().y(card1RestPosition);
		card2.animate().y(card2FoldedPosition).setListener(new EndAnimationListener() {

			@Override
			public void onAnimationEnd(Animator animator) {
				backgroundCard.setY(0);
			}
		});
	}

	@Override
	protected void toBackground() {
		super.toBackground();
		card1ToBackground.setImageResource(R.drawable.icon_up);
		card1ToBackground.setVisibility(View.VISIBLE);
	}

	@Override
	protected void toCard1(Animator.AnimatorListener listener) {
		super.toCard1(listener);
		ddlFormScreenlet.loadForm();

		clearDDLEntrySelected();

		card1ToBackgroundMenu.setImageDrawable(
			ResourcesCompat.getDrawable(getResources(), R.drawable.icon_options_red, getTheme()));
		card1ToBackground.setImageResource(R.drawable.icon_down);
	}

	@Override
	protected void toCard2() {
		super.toCard2();
		if (record != null) {
			ddlFormScreenlet.setRecordId(record.getRecordId());
			ddlFormScreenlet.loadRecord();
			goLeftCard1();
		} else {
			clearDDLEntrySelected();
		}
	}

	private SpannableStringBuilder getCallSpannableString() {
		int darkGrayColor = ResourcesCompat.getColor(getResources(), R.color.textColorSecondary_westeros, getTheme());
		int subTitleStart = 4;

		SpannableStringBuilder ssb = new SpannableStringBuilder(getString(R.string.call_menu_entry));
		ssb.setSpan(new StyleSpan(Typeface.NORMAL), subTitleStart, ssb.length(), 0);
		ssb.setSpan(new ForegroundColorSpan(darkGrayColor), subTitleStart, ssb.length(), 0);
		return ssb;
	}

	private void selectDDLEntry(Record entry) {
		record = entry;
		reportIssueTitle.setText(getString(R.string.edit_issue));
		sendButton.setText(getString(R.string.save).toUpperCase());
	}

	private void clearDDLEntrySelected() {
		record = null;
		reportIssueTitle.setText(getString(R.string.report_issue));
		sendButton.setText(getString(R.string.send).toUpperCase());
		ddlFormScreenlet.setRecordId(0);
	}

	private void reloadListAndShowResult(final String message) {
		ddlListScreenlet.loadPage(0);
		toCard1(new EndAnimationListener() {

			@Override
			public void onAnimationEnd(Animator animator) {
				WesterosSnackbar.showSnackbar(IssuesActivity.this, message.toUpperCase(), R.color.green_westeros);
			}
		});
	}

	private void goRightCard1(Record element) {
		TextView issueTitle = (TextView) findViewById(R.id.issue_title);
		Object serverValue = element.getServerValue(getString(R.string.liferay_recordset_fields));
		issueTitle.setText(String.valueOf(serverValue));

		String date = DateFormat.getDateTimeInstance().format(element.getServerAttribute("createDate"));
		((TextView) findViewById(R.id.createdAt)).setText(getString(R.string.created, date));

		TextView description = (TextView) findViewById(R.id.description);
		description.setText(String.valueOf(element.getServerValue("Description")));

		String severity = String.valueOf(element.getServerValue("Severity"));
		if (severity != null) {
			severity = severity.replace("[\"", "");
			severity = severity.replace("\"]", "");
			TextView severityField = (TextView) findViewById(R.id.severity);
			severityField.setText(getString(R.string.severity_detail, severity));
		}

		goRightCard1();
	}

	private void launchMenu(View v) {
		int color = android.R.color.transparent;
		switch (v.getId()) {
			case R.id.account_settings_menu_entry:
				startActivity(new Intent(this, AccountSettingsActivity.class));
				overridePendingTransition(0, 0);
				break;
			case R.id.call_menu_entry:
				color = R.color.light_gray_westeros;

				break;
			case R.id.send_message_menu_entry:
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.default_sms_uri))));
				break;
			case R.id.sign_out_menu_entry:
			default:
				color = R.color.light_gray_westeros;

				SessionContext.logout();
				Intent intent = new Intent(this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				break;
		}
		v.setBackgroundColor(ResourcesCompat.getColor(getResources(), color, getTheme()));
	}

	private void tryToCall(final View button) {
		RxView.clicks(button)
			.compose(new RxPermissions(this).ensure(Manifest.permission.CALL_PHONE))
			.subscribe(result -> {
				button.setBackgroundColor(
					ResourcesCompat.getColor(getResources(), R.color.light_gray_westeros, getTheme()));
				if (result
					&& ActivityCompat.checkSelfPermission(IssuesActivity.this, Manifest.permission.CALL_PHONE)
					== PackageManager.PERMISSION_GRANTED) {
					startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(getString(R.string.default_telephone_uri))));
				}
			});
	}
}