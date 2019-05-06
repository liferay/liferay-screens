package com.liferay.mobile.screens.testapp;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.add.CommentAddListener;
import com.liferay.mobile.screens.comment.add.CommentAddScreenlet;
import com.liferay.mobile.screens.comment.display.CommentDisplayListener;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;
import com.liferay.mobile.screens.comment.list.CommentListListener;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;

/**
 * @author Alejandro Hernández
 */
public class CommentsActivity extends ThemeActivity
    implements CommentListListener, CommentAddListener, OnClickListener, CompoundButton.OnCheckedChangeListener,
    CommentDisplayListener {

    private CommentDisplayScreenlet displayScreenlet;
    private CommentListScreenlet listScreenlet;
    private AlertDialog dialog;
    private View noCommentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_list);

        noCommentView = findViewById(R.id.no_comment_display);

        SwitchCompat switchCompat = findViewById(R.id.comment_switch_editable);
        switchCompat.setOnCheckedChangeListener(this);

        findViewById(R.id.add_comment_button).setOnClickListener(this);
        listScreenlet = findViewById(R.id.comment_list_screenlet);
        listScreenlet.setListener(this);

        displayScreenlet = findViewById(R.id.comment_display_screenlet);
        displayScreenlet.setListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        listScreenlet.loadPage(0);
    }

    private void showDisplayScreenlet(boolean visible) {
        noCommentView.setVisibility(visible ? GONE : VISIBLE);
        displayScreenlet.setVisibility(visible ? VISIBLE : GONE);
    }

    @Override
    public void onLoadCommentSuccess(CommentEntry commentEntry) {
        info(getString(R.string.comment_loaded_success, commentEntry.getCommentId()));
    }

    @Override
    public void onDeleteCommentSuccess(CommentEntry commentEntry) {
        info(getString(R.string.comment_deleted_success) + " " + commentEntry.getCommentId());
        showDisplayScreenlet(false);
    }

    @Override
    public void onUpdateCommentSuccess(CommentEntry commentEntry) {
        info(getString(R.string.comment_updated_success) + " " + commentEntry.getCommentId());
        showDisplayScreenlet(false);
    }

    @Override
    public void onAddCommentSuccess(CommentEntry commentEntry) {
        info(getString(R.string.comment_added_success) + " " + commentEntry.getCommentId());
        dialog.cancel();
        listScreenlet.addNewCommentEntry(commentEntry);
    }

    @Override
    public void onListPageFailed(int startRow, Exception e) {
        error(getString(R.string.page_error), e);
    }

    @Override
    public void onListPageReceived(int startRow, int endRow, List<CommentEntry> entries, int rowCount) {
        info(rowCount + " " + getString(R.string.rows_received_info) + " " + startRow);
    }

    @Override
    public void onListItemSelected(CommentEntry element, View view) {

        displayScreenlet.setCommentId(element.getCommentId());
        showDisplayScreenlet(true);
        displayScreenlet.load();
    }

    @Override
    public void error(Exception e, String userAction) {
        error(getString(R.string.comment_error) + " " + userAction, e);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_comment_button) {
            dialog = createDialog();
            dialog.show();
        }
    }

    @Override
    protected void onPause() {
        if (dialog != null) {
            dialog.dismiss();
        }

        super.onPause();
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.type_comment_send)).setTitle(getString(R.string.add_new_comment));

        View dialogView = getLayoutInflater().inflate(R.layout.add_comment_dialog, null);
        builder.setView(dialogView);

        CommentAddScreenlet screenlet = dialogView.findViewById(R.id.comment_add_screenlet);
        screenlet.setListener(this);

        return builder.create();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        listScreenlet.allowEdition(isChecked);
        displayScreenlet.allowEdition(isChecked);
    }
}
