package com.liferay.mobile.screens.westerosemployees.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import androidx.appcompat.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.add.CommentAddListener;
import com.liferay.mobile.screens.comment.add.CommentAddScreenlet;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.westerosemployees.R;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;
import com.liferay.mobile.screens.westerosemployees.views.Card;

public class ModalDetailActivity extends AppCompatActivity implements CommentAddListener {

    private AssetDisplayScreenlet assetDisplayScreenlet;
    private RatingScreenlet ratingScreenlet;
    private CommentListScreenlet commentListScreenlet;
    private CommentAddScreenlet commentAddScreenlet;
    private Card commentAddCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String className = getIntent().getStringExtra("className");
        long classPK = getIntent().getLongExtra("classPK", 0);
        String mimeType = getIntent().getStringExtra("mimeType");

        findViews(className, mimeType);

        commentAddScreenlet.setListener(this);

        loadScreenlets(className, classPK);
    }

    private void findViews(String className, String mimeType) {
        if (className.contains("BlogsEntry")) {
            setContentView(R.layout.blogs_detail_subview);
            assetDisplayScreenlet = findViewById(R.id.asset_display_screenlet_blog);
        } else if (className.contains("DLFileEntry")) {
            if (mimeType.contains("image")) {
                setContentView(R.layout.gallery_detail_subview);
                assetDisplayScreenlet = findViewById(R.id.asset_display_screenlet_image);
            } else {
                setContentView(R.layout.documents_detail_subview);
                assetDisplayScreenlet = findViewById(R.id.asset_display_screenlet_doc);
            }
        }

        ratingScreenlet = findViewById(R.id.rating_screenlet);
        commentListScreenlet = findViewById(R.id.comment_list_screenlet);
        commentAddScreenlet = findViewById(R.id.comment_add_screenlet);

        commentAddCard = findViewById(R.id.comment_add_card);
    }

    private void loadScreenlets(String className, long classPK) {
        if (assetDisplayScreenlet != null
            && ratingScreenlet != null
            && commentListScreenlet != null
            && commentAddScreenlet != null) {
            assetDisplayScreenlet.setClassName(className);
            assetDisplayScreenlet.setClassPK(classPK);
            assetDisplayScreenlet.loadAsset();

            ratingScreenlet.setClassName(className);
            ratingScreenlet.setClassPK(classPK);
            ratingScreenlet.load();

            commentListScreenlet.setClassName(className);
            commentListScreenlet.setClassPK(classPK);
            commentListScreenlet.loadPage(0);

            commentAddScreenlet.setClassName(className);
            commentAddScreenlet.setClassPK(classPK);
        } else {
            finish();
        }
    }

    @Override
    public void onAddCommentSuccess(CommentEntry commentEntry) {
        hideSoftKeyBoard();
        commentAddCard.setState(CardState.MINIMIZED);
        commentListScreenlet.addNewCommentEntry(commentEntry);
    }

    @Override
    public void error(Exception e, String userAction) {

    }

    private void hideSoftKeyBoard() {
        Activity activity = LiferayScreensContext.getActivityFromContext(this);
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) {
            IBinder windowToken = activity.getCurrentFocus().getWindowToken();

            if (windowToken != null) {
                imm.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }
}
