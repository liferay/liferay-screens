package com.liferay.mobile.screens.viewsets.defaultviews.comment.display;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;
import com.liferay.mobile.screens.comment.display.view.CommentDisplayViewModel;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

/**
 * @author Alejandro Hernández
 */
public class CommentDisplayView extends FrameLayout implements CommentDisplayViewModel {

	public CommentDisplayView(Context context) {
		super(context);
	}

	public CommentDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void refreshView() {
		if (editable) {
			editImageButton.setVisibility(VISIBLE);
			deleteImageButton.setVisibility(VISIBLE);
		} else {
			editImageButton.setVisibility(GONE);
			deleteImageButton.setVisibility(GONE);
		}

		deletionMode(false);
		editionMode(false);

		if (commentEntry != null) {
			userPortraitScreenlet.setUserId(commentEntry.getUserId());

			userNameTextView.setText(commentEntry.getUserName());

			createDateTextView.setText(commentEntry.getCreateDateAsTimeSpan());

			if (commentEntry.getModifiedDate() != commentEntry.getCreateDate()) {
				editedTextView.setVisibility(VISIBLE);
			} else {
				editedTextView.setVisibility(GONE);
			}

			bodyTextView.setText(Html.fromHtml(commentEntry.getBody()).toString().replaceAll("\n", "").trim());

			if (commentEntry.isEditable()) {
				editBodyEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							editionMode(false);
							editCommentBody();
							return true;
						}
						return false;
					}
				});

				editImageButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (isDeleting) {
							getScreenlet().performUserAction(CommentDisplayScreenlet.DELETE_COMMENT_ACTION);
						} else {
							editionMode(!isEditing);
							editCommentBody();
						}
					}
				});
			}

			if (commentEntry.isDeletable()) {
				deleteImageButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						deletionMode(!isDeleting);
					}
				});
			}
		}
	}

	@Override
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public void showStartOperation(String actionName) {
		progressBar.setVisibility(View.VISIBLE);
		contentGroup.setVisibility(View.GONE);
	}

	@Override
	public void showFinishOperation(String actionName) {
		progressBar.setVisibility(View.GONE);
		contentGroup.setVisibility(View.VISIBLE);

		userPortraitScreenlet.load();
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		progressBar.setVisibility(View.GONE);
		contentGroup.setVisibility(View.VISIBLE);
	}

	private void editCommentBody() {
		if (!isEditing) {
			String editedText = editBodyEditText.getText().toString();
			if (!editedText.equals(bodyTextView.getText())) {
				getScreenlet().performUserAction(CommentDisplayScreenlet.UPDATE_COMMENT_ACTION, editedText);
			}
		}
	}

	private void editionMode(boolean on) {
		isEditing = on;

		if (isEditing && viewSwitcher.getCurrentView() != editBodyEditText) {
			viewSwitcher.showNext();
		} else if (!isEditing && viewSwitcher.getCurrentView() != bodyTextView) {
			viewSwitcher.showPrevious();
		}

		InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (isEditing) {
			editImageButton.setImageResource(R.drawable.default_ok);

			//Set selection at end of input
			editBodyEditText.requestFocus();
			editBodyEditText.setText("");
			editBodyEditText.append(bodyTextView.getText());

			imm.showSoftInput(editBodyEditText, InputMethodManager.SHOW_FORCED);
		} else {
			editImageButton.setImageResource(R.drawable.default_comment_edit);

			imm.hideSoftInputFromWindow(editBodyEditText.getWindowToken(), 0);
		}
	}

	private void deletionMode(boolean on) {
		isDeleting = on;

		changeButtonBackgroundDrawable(deleteImageButton,
			isDeleting ? R.drawable.default_button_selector_red : R.drawable.default_button_selector);

		deleteImageButton.setImageResource(isDeleting ? R.drawable.default_cancel : R.drawable.default_comment_delete);

		changeButtonBackgroundDrawable(editImageButton,
			isDeleting ? R.drawable.default_button_selector_green : R.drawable.default_button_selector);

		editImageButton.setImageResource(isDeleting ? R.drawable.default_ok : R.drawable.default_comment_edit);
	}

	private void changeButtonBackgroundDrawable(ImageButton button, int drawable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			button.setBackground(ContextCompat.getDrawable(getContext(), drawable));
		} else {
			button.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), drawable));
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		userNameTextView = (TextView) findViewById(R.id.comment_user_name);
		bodyTextView = (TextView) findViewById(R.id.comment_body);
		userPortraitScreenlet = (UserPortraitScreenlet) findViewById(R.id.comment_user_portrait);
		createDateTextView = (TextView) findViewById(R.id.comment_create_date);
		editedTextView = (TextView) findViewById(R.id.comment_edited);
		editImageButton = (ImageButton) findViewById(R.id.comment_edit_button);
		deleteImageButton = (ImageButton) findViewById(R.id.comment_delete_button);
		editBodyEditText = (EditText) findViewById(R.id.comment_edit_body);
		viewSwitcher = (ViewSwitcher) findViewById(R.id.comment_view_switcher);

		progressBar = (ProgressBar) findViewById(R.id.liferay_progress);
		contentGroup = (ViewGroup) findViewById(R.id.comment_display_content);
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
	public void setCommentEntry(CommentEntry commentEntry) {
		this.commentEntry = commentEntry;
	}

	private TextView userNameTextView;
	private TextView bodyTextView;
	private UserPortraitScreenlet userPortraitScreenlet;
	private TextView createDateTextView;
	private TextView editedTextView;
	private ImageButton editImageButton;
	private ImageButton deleteImageButton;
	private EditText editBodyEditText;
	private ViewSwitcher viewSwitcher;

	private boolean editable;
	private boolean isDeleting;
	private boolean isEditing;

	private BaseScreenlet screenlet;
	private ViewGroup contentGroup;
	private ProgressBar progressBar;
	private CommentEntry commentEntry;
}
