package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.widget.CompoundButton;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.CommentDisplayListener;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;

/**
 * @author Sarai Díaz García
 */
public class CommentDisplayActivity extends ThemeActivity implements CommentDisplayListener {

    private CommentDisplayScreenlet commentDisplayScreenlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_display);

        commentDisplayScreenlet = findViewById(R.id.comment_display_screenlet);
        commentDisplayScreenlet.setListener(this);
    }

    @Override
    public void onLoadCommentSuccess(CommentEntry commentEntry) {
        info(getString(R.string.comment_loaded_success, commentEntry.getCommentId()));
    }

    @Override
    public void onDeleteCommentSuccess(CommentEntry commentEntry) {
        info(getString(R.string.comment_deleted_success) + " " + commentEntry.getCommentId());
    }

    @Override
    public void onUpdateCommentSuccess(CommentEntry commentEntry) {
        info(getString(R.string.comment_updated_success) + " " + commentEntry.getCommentId());
    }

    @Override
    public void error(Exception e, String userAction) {
        error(getString(R.string.comment_error) + " " + userAction, e);
    }
}
