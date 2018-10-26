package com.liferay.mobile.screens.viewsets.defaultviews.comment.add;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.comment.add.view.CommentAddViewModel;

import static com.liferay.mobile.screens.base.BaseScreenlet.DEFAULT_ACTION;

/**
 * @author Alejandro Hernández
 */
public class CommentAddView extends RelativeLayout implements CommentAddViewModel, View.OnClickListener {

    private BaseScreenlet screenlet;
    private EditText addCommentEditText;
    private Button sendButton;

    public CommentAddView(Context context) {
        super(context);
    }

    public CommentAddView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentAddView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommentAddView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        sendButton = findViewById(R.id.comment_send);
        sendButton.setOnClickListener(this);
        addCommentEditText = findViewById(R.id.comment_add);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.comment_send) {
            String commentText = addCommentEditText.getText().toString();
            if (!commentText.isEmpty()) {
                sendButton.setEnabled(false);
                getScreenlet().performUserAction(DEFAULT_ACTION, commentText);
            }
        }
    }

    @Override
    public void showStartOperation(String actionName) {

    }

    @Override
    public void showFinishOperation(String actionName) {
        sendButton.setEnabled(true);
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {

    }

    @Override
    public BaseScreenlet getScreenlet() {
        return screenlet;
    }

    @Override
    public void setScreenlet(BaseScreenlet screenlet) {
        this.screenlet = screenlet;
    }
}

