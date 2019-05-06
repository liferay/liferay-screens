package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.add.CommentAddListener;
import com.liferay.mobile.screens.comment.add.CommentAddScreenlet;

/**
 * @author Sarai Díaz García
 */
public class CommentAddActivity extends ThemeActivity implements CommentAddListener {

    private CommentAddScreenlet commentAddScreenlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_add);

        commentAddScreenlet = findViewById(R.id.comment_add_screenlet);
        commentAddScreenlet.setListener(this);
    }

    @Override
    public void onAddCommentSuccess(CommentEntry commentEntry) {
        info(getString(R.string.comment_added_success));
    }

    @Override
    public void error(Exception e, String userAction) {
        error(getString(R.string.comment_error) + " " + userAction, e);
    }
}
