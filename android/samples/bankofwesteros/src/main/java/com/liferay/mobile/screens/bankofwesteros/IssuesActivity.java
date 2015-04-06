package com.liferay.mobile.screens.bankofwesteros;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liferay.mobile.screens.bankofwesteros.views.WesterosCrouton;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class IssuesActivity extends CardActivity implements View.OnClickListener, DDLFormListener, BaseListListener<DDLEntry> {

	//FIXME this is wrong

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.issues);

		_backgroundCard = findViewById(R.id.background);

		_card1ToBackgroundMenu = (ImageView) findViewById(R.id.card1_to_background_menu);
		_card1ToBackgroundMenu.setOnClickListener(this);
		_card1ToBackground = (ImageView) findViewById(R.id.card1_to_background);
		_card1ToBackground.setOnClickListener(this);

		_reportIssueTitle = (TextView) findViewById(R.id.report_issue_title);

		_ddlFormScreenlet = (DDLFormScreenlet) findViewById(R.id.ddlform);
		_ddlFormScreenlet.setListener(this);
		_ddlListScreenlet = (DDLListScreenlet) findViewById(R.id.ddllist);
		_ddlListScreenlet.setListener(this);

		_sendButton = (Button) findViewById(R.id.submit);

		configureMenuEntries();
	}

	private void configureMenuEntries() {
		TextView callMenuEntry = (TextView) findViewById(R.id.call_menu_entry);
		callMenuEntry.setText(getCallSpannableString(), TextView.BufferType.SPANNABLE);
		callMenuEntry.setOnClickListener(this);
		findViewById(R.id.account_settings_menu_entry).setOnClickListener(this);
		findViewById(R.id.send_message_menu_entry).setOnClickListener(this);
		findViewById(R.id.sign_out_menu_entry).setOnClickListener(this);
	}

	private SpannableStringBuilder getCallSpannableString() {
		int darkGrayColor = getResources().getColor(R.color.westeros_dark_gray);
		int subTitleStart = 4;

		SpannableStringBuilder ssb = new SpannableStringBuilder("Call (Monday - Friday, 06:00 - 22:00)");
		ssb.setSpan(new StyleSpan(Typeface.NORMAL), subTitleStart, ssb.length(), 0);
		ssb.setSpan(new ForegroundColorSpan(darkGrayColor), subTitleStart, ssb.length(), 0);
		return ssb;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.card1_to_front_view:
				goLeftCard1();
				_reportIssueTitle.setText(getString(R.string.report_issue));
				break;
			case R.id.card1_to_background:
			case R.id.card1_to_background_menu:
				if (_currentCard == Card.BACKGROUND) {
					toCard1();
				}
				else {
					toBackground();
				}
				break;
			case R.id.account_settings_menu_entry:
				v.setBackgroundColor(getResources().getColor(android.R.color.white));
				startActivity(new Intent(this, AccountSettings.class));
				overridePendingTransition(0, 0);
				break;
			case R.id.call_menu_entry:
				v.setBackgroundColor(getResources().getColor(android.R.color.white));
				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:900000000")));
				break;
			case R.id.send_message_menu_entry:
				v.setBackgroundColor(getResources().getColor(android.R.color.white));
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:900000000")));
				break;
			case R.id.sign_out_menu_entry:
				v.setBackgroundColor(getResources().getColor(android.R.color.white));
				SessionContext.clearSession();
				Intent intent = new Intent(this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				break;
			default:
				super.onClick(v);
		}
	}

	@Override
	protected void animateScreenAfterLoad() {

		_backgroundCard.setY(_maxHeight);
		_card1.setY(_maxHeight);
		_card2.setY(_maxHeight);

		_card1.animate().y(_card1RestPosition);
		_card2.animate().y(_card2FoldedPosition).setListener(new EndAnimationListener() {
			@Override
			public void onAnimationEnd(Animator animator) {
				_backgroundCard.setY(0);
			}
		});
	}

	@Override
	protected void toBackground() {
		super.toBackground();
		_card1ToBackground.setImageResource(R.drawable.icon_up);
		_card1ToBackground.setVisibility(View.VISIBLE);
	}

	@Override
	protected void toCard1(Animator.AnimatorListener listener) {
		super.toCard1(listener);
		_ddlFormScreenlet.loadForm();

		textToNew();

		_card1ToBackground.setImageResource(R.drawable.icon_down);
	}

	private void textToNew() {
		_element = null;
		_ddlFormScreenlet.setRecordId(0);
		_reportIssueTitle.setText(getString(R.string.report_issue));
		_sendButton.setText("Send Issue".toUpperCase());
	}

	@Override
	protected void toCard2() {
		super.toCard2();
		if (_element != null) {
			_reportIssueTitle.setText("Edit Issue");
			_sendButton.setText("Save".toUpperCase());
			_ddlFormScreenlet.setRecordId((Integer) _element.getAttributes("recordId"));
			_ddlFormScreenlet.loadRecord();

			goLeftCard1();
		}
		else {
			textToNew();
		}
	}

	@Override
	public void onDDLFormLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		refreshList("Issue Created");
	}

	private void refreshList(final String message) {
		_ddlListScreenlet.loadPage(0);
		toCard1(new EndAnimationListener() {
			@Override
			public void onAnimationEnd(Animator animator) {
				WesterosCrouton.info(IssuesActivity.this, message.toUpperCase());
			}
		});
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		refreshList("Issue Updated");
	}


	@Override
	public void onDDLFormLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordAddFailed(Exception e) {

	}

	@Override
	public void onDDLFormUpdateRecordFailed(Exception e) {

	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {

	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {

	}

	@Override
	public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {

	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<DDLEntry> entries,
								   int rowCount) {

	}

	@Override
	public void onListItemSelected(DDLEntry element, View view) {
		_element = element;
		if (view.getId() == R.id.list_edit) {
			toCard2();
		}
		else if (view.getId() == R.id.list_view) {
			goRightCard1();
			_reportIssueTitle.setText("Edit Issue");

			TextView issueTitle = (TextView) findViewById(R.id.issue_title);
			issueTitle.setText(element.getValue("Title"));
			String date = new SimpleDateFormat("dd/MM/yyyy").format(element.getAttributes("createDate"));
			TextView createdAt = (TextView) findViewById(R.id.createdAt);
			createdAt.setText("Created " + date);
			TextView description = (TextView) findViewById(R.id.description);
			description.setText(element.getValue("Description"));
			String severity = element.getValue("Severity");
			severity = severity.replace("[\"", "");
			severity = severity.replace("\"]", "");
			TextView severityField = (TextView) findViewById(R.id.severity);
			severityField.setText("Severity: " + severity);
		}
	}


	private DDLFormScreenlet _ddlFormScreenlet;
	private DDLListScreenlet _ddlListScreenlet;

	private View _backgroundCard;

	private DDLEntry _element;

	private ImageView _card1ToBackground;
	private ImageView _card1ToBackgroundMenu;
	private TextView _reportIssueTitle;
	private Button _sendButton;
}
