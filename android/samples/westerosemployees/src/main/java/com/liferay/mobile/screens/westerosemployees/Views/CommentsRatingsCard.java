package com.liferay.mobile.screens.westerosemployees.Views;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.add.CommentAddListener;
import com.liferay.mobile.screens.comment.add.CommentAddScreenlet;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.westerosemployees.R;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;

/**
 * @author Víctor Galán Grande
 */
public abstract class CommentsRatingsCard extends Card implements CommentAddListener {

	private RatingScreenlet ratingScreenlet;

	private CommentListScreenlet commentListScreenlet;
	private CommentAddScreenlet commentAddScreenlet;

	private Card commentAddCard;


	public CommentsRatingsCard(Context context) {
		super(context);
	}

	public CommentsRatingsCard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommentsRatingsCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CommentsRatingsCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		ratingScreenlet = (RatingScreenlet) findViewById(R.id.rating_screenlet);
		commentListScreenlet = (CommentListScreenlet) findViewById(R.id.comment_list_screenlet);
		commentAddScreenlet = (CommentAddScreenlet) findViewById(R.id.comment_add_screenlet);
		commentAddScreenlet.setListener(this);

		commentAddCard = (Card) findViewById(R.id.comment_add_card);
	}

	protected void initializeRatingsAndComments(String className, long classPK) {
		ratingScreenlet.setClassName(className);
		ratingScreenlet.setClassPK(classPK);
		ratingScreenlet.load();

		commentListScreenlet.setClassName(className);
		commentListScreenlet.setClassPK(classPK);
		commentListScreenlet.loadPage(0);

		commentAddScreenlet.setClassName(className);
		commentAddScreenlet.setClassPK(classPK);
	}

	@Override
	public void onAddCommentSuccess(CommentEntry commentEntry) {
		commentAddCard.setState(CardState.MINIMIZED);
		commentListScreenlet.addNewCommentEntry(commentEntry);
	}

	@Override
	public void error(Exception e, String userAction) {

	}
}
