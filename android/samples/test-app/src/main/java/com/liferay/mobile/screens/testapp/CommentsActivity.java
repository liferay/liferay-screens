package com.liferay.mobile.screens.testapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.comment.add.CommentAddListener;
import com.liferay.mobile.screens.comment.add.CommentAddScreenlet;
import com.liferay.mobile.screens.comment.display.CommentDisplayListener;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;
import com.liferay.mobile.screens.comment.list.CommentListListener;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.util.LiferayLocale;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class CommentsActivity extends ThemeActivity
	implements CommentListListener, CommentAddListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener,
	CommentDisplayListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.comment_list);

		findViewById(R.id.add_comment_button).setOnClickListener(this);
		findViewById(R.id.comment_load_display).setOnClickListener(this);

		noCommentView = findViewById(R.id.no_comment_display);

		loadEditText = (EditText) findViewById(R.id.comment_id_display);

		((SwitchCompat) findViewById(R.id.comment_switch_editable)).setOnCheckedChangeListener(this);

		listScreenlet = (CommentListScreenlet) findViewById(R.id.comment_list_screenlet);
		listScreenlet.setGroupId(LiferayServerContext.getGroupId());
		listScreenlet.setListener(this);

		displayScreenlet = (CommentDisplayScreenlet) findViewById(R.id.comment_display_screenlet);
		displayScreenlet.setGroupId(LiferayServerContext.getGroupId());
		displayScreenlet.setListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		listScreenlet.loadPage(0);
	}

	@Override
	public void onLoadCommentFailure(long commentId, Exception e) {
		error(String.format(LiferayLocale.getDefaultLocale(), "Error loading comment with id: %d", commentId), e);
		displayScreenletVisible(false);
	}

	private void displayScreenletVisible(boolean visible) {
		if (visible) {
			noCommentView.setVisibility(View.GONE);
			displayScreenlet.setVisibility(View.VISIBLE);
		} else {
			noCommentView.setVisibility(View.VISIBLE);
			displayScreenlet.setVisibility(View.GONE);
		}
	}

	@Override
	public void onLoadCommentSuccess(CommentEntry commentEntry) {
		info(String.format(LiferayLocale.getDefaultLocale(),"Comment with id: %d succesfully loaded", commentEntry.getCommentId()));
	}

	@Override
	public void onDeleteCommentFailure(CommentEntry commentEntry, Exception e) {
		error(String.format(LiferayLocale.getDefaultLocale(),"Error deleting comment with id: %d", commentEntry.getCommentId()), e);
	}

	@Override
	public void onDeleteCommentSuccess(CommentEntry commentEntry) {
		info(String.format(LiferayLocale.getDefaultLocale(),"Comment with id: %d succesfully deleted", commentEntry.getCommentId()));
		if (commentEntry.getCommentId() == displayScreenlet.getCommentId()) {
			displayScreenletVisible(false);
			listScreenlet.removeCommentEntry(commentEntry);
		}
	}

	@Override
	public void onUpdateCommentFailure(CommentEntry commentEntry, Exception e) {
		error(String.format(LiferayLocale.getDefaultLocale(),"Error updating comment with id: %d", commentEntry.getCommentId()), e);
	}

	@Override
	public void onUpdateCommentSuccess(CommentEntry commentEntry) {
		info(String.format(LiferayLocale.getDefaultLocale(),"Comment with id: %d succesfully updated", commentEntry.getCommentId()));
		if (commentEntry.getCommentId() == displayScreenlet.getCommentId()) {
			displayScreenlet.load();
			listScreenlet.refreshView();
		}
	}

	@Override
	public void onAddCommentFailure(String body, Exception e) {
		error("Error adding comment", e);
	}

	@Override
	public void onAddCommentSuccess(CommentEntry commentEntry) {
		info(String.format(LiferayLocale.getDefaultLocale(),"Comment succesfully added, new id: %d", commentEntry.getCommentId()));
		dialog.cancel();
		listScreenlet.addNewCommentEntry(commentEntry);
	}

	@Override
	public void onListPageFailed(BaseListScreenlet source, int startRow, int endRow, Exception e) {
		error(String.format(LiferayLocale.getDefaultLocale(),"Error receiving page: %d", startRow), e);
	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int startRow, int endRow, List<CommentEntry> entries,
		int rowCount) {
	}

	@Override
	public void onListItemSelected(CommentEntry element, View view) {
		loadEditText.setText(String.valueOf(element.getCommentId()));
	}

	@Override
	public void loadingFromCache(boolean success) {
	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {
	}

	@Override
	public void storingToCache(Object object) {
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.add_comment_button) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage("Type your comment and press 'Send'").setTitle("Add new comment");

			LayoutInflater inflater = getLayoutInflater();

			View dialogView = inflater.inflate(R.layout.add_comment_dialog, null);

			builder.setView(dialogView);

			CommentAddScreenlet screenlet = (CommentAddScreenlet) dialogView.findViewById(R.id.comment_add_screenlet);
			screenlet.setListener(this);

			dialog = builder.create();
			dialog.show();
		} else if (id == R.id.comment_load_display) {
			String editTextString = loadEditText.getText().toString();
			if (!editTextString.isEmpty()) {
				long commentId = Long.parseLong(editTextString);
				displayScreenlet.setCommentId(commentId);
				displayScreenletVisible(true);
				displayScreenlet.load();
			}
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		listScreenlet.setEditable(isChecked);
		displayScreenlet.setEditable(isChecked);
		listScreenlet.refreshView();
		displayScreenlet.refreshView();
	}

	private CommentListScreenlet listScreenlet;
	private AlertDialog dialog;
	private CommentDisplayScreenlet displayScreenlet;
	private EditText loadEditText;
	private View noCommentView;
}
