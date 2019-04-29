package com.liferay.mobile.screens.viewsets.defaultviews.comment.display;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;
import com.liferay.mobile.screens.comment.display.view.CommentDisplayViewModel;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

/**
 * @author Alejandro Hernández
 */
public class CommentDisplayView extends FrameLayout implements CommentDisplayViewModel, View.OnClickListener {

    private TextView userNameTextView;
    private TextView createDateTextView;
    private TextView bodyTextView;
    private TextView editedTextView;
    private UserPortraitScreenlet userPortraitScreenlet;
    private EditText editBodyEditText;
    private ImageButton editImageButton;
    private ImageButton deleteImageButton;
    private boolean deleting;
    private boolean editing;
    private BaseScreenlet screenlet;
    private ViewGroup contentGroup;
    private ProgressBar progressBar;
    private CommentEntry commentEntry;

    public CommentDisplayView(Context context) {
        super(context);
    }

    public CommentDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommentDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void showFinishOperation(String actionName) {
        throw new AssertionError("Should not be called!");
    }

    public void showFinishOperation(String actionName, boolean editable) {
        loadView(editable);
    }

    @Override
    public void showFinishOperation(String loadCommentAction, boolean editable, CommentEntry commentEntry) {
        this.commentEntry = commentEntry;
        loadView(editable);
    }

    @Override
    public void showStartOperation(String actionName) {
        progressBar.setVisibility(VISIBLE);
        contentGroup.setVisibility(GONE);
    }

    private void loadView(boolean editable) {
        progressBar.setVisibility(GONE);
        contentGroup.setVisibility(VISIBLE);

        if (commentEntry != null) {
            enableEditionButtons(editable);
            showEditionButtons();
            userPortraitScreenlet.setUserId(commentEntry.getUserId());
            userPortraitScreenlet.load();
            userNameTextView.setText(commentEntry.getUserName());
            createDateTextView.setText(commentEntry.getCreateDateAsTimeSpan());
            editedTextView.setVisibility(
                commentEntry.getModifiedDate() != commentEntry.getCreateDate() ? VISIBLE : GONE);
            bodyTextView.setText(Html.fromHtml(commentEntry.getBody()).toString().replaceAll("\n", "").trim());
        }
    }

    private void enableEditionButtons(boolean editable) {
        editImageButton.setVisibility(editable && commentEntry.isUpdatable() ? VISIBLE : GONE);
        deleteImageButton.setVisibility(editable && commentEntry.isDeletable() ? VISIBLE : GONE);

        if (editable) {
            editImageButton.setOnClickListener(this);
            deleteImageButton.setOnClickListener(this);
        }
    }

    private void showEditionButtons() {

        editBodyEditText.setVisibility(editing ? VISIBLE : GONE);
        bodyTextView.setVisibility(editing ? GONE : VISIBLE);

        boolean inAction = editing || deleting;
        editImageButton.setImageResource(inAction ? R.drawable.default_ok : R.drawable.default_comment_edit);
        changeButtonBackgroundDrawable(editImageButton,
            inAction ? R.drawable.default_button_selector_green : R.drawable.default_button_selector);

        deleteImageButton.setImageResource(inAction ? R.drawable.default_cancel : R.drawable.default_comment_delete);
        changeButtonBackgroundDrawable(deleteImageButton,
            inAction ? R.drawable.default_button_selector_red : R.drawable.default_button_selector);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.comment_edit_or_confirm) {
            if (editing) {
                String editedText = editBodyEditText.getText().toString();
                if (!editedText.equals(bodyTextView.getText())) {
                    getScreenlet().performUserAction(CommentDisplayScreenlet.UPDATE_COMMENT_ACTION, editedText);
                }
            } else if (deleting) {
                getScreenlet().performUserAction(CommentDisplayScreenlet.DELETE_COMMENT_ACTION);
            } else {
                editBodyEditText.setText(bodyTextView.getText());
            }
            editing = !editing && !deleting;
            deleting = false;
            showEditionButtons();
        } else if (v.getId() == R.id.comment_delete_or_cancel) {
            deleting = !editing && !deleting;
            editing = false;
            showEditionButtons();
        }
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {
        progressBar.setVisibility(GONE);
        contentGroup.setVisibility(VISIBLE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        userNameTextView = findViewById(R.id.comment_user_name);
        bodyTextView = findViewById(R.id.comment_body);
        userPortraitScreenlet = findViewById(R.id.comment_user_portrait);
        createDateTextView = findViewById(R.id.comment_create_date);
        editedTextView = findViewById(R.id.comment_edited);
        editImageButton = findViewById(R.id.comment_edit_or_confirm);
        deleteImageButton = findViewById(R.id.comment_delete_or_cancel);
        editBodyEditText = findViewById(R.id.comment_edit_body);
        progressBar = findViewById(R.id.liferay_progress);
        contentGroup = findViewById(R.id.comment_display_content);
    }

    @Override
    public BaseScreenlet getScreenlet() {
        return screenlet;
    }

    @Override
    public void setScreenlet(BaseScreenlet screenlet) {
        this.screenlet = screenlet;
    }

    private void changeButtonBackgroundDrawable(ImageButton button, int drawable) {
        Drawable drawableCompat = ContextCompat.getDrawable(getContext(), drawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(drawableCompat);
        } else {
            button.setBackgroundDrawable(drawableCompat);
        }
    }
}
