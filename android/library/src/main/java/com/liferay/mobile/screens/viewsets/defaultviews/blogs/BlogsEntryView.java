package com.liferay.mobile.screens.viewsets.defaultviews.blogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.blogs.BlogsEntry;
import com.liferay.mobile.screens.blogs.BlogsEntryDisplayViewModel;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Sarai Díaz García
 */
public class BlogsEntryView extends RelativeLayout implements BlogsEntryDisplayViewModel {

    private BaseScreenlet screenlet;
    private BlogsEntry blogsEntry;
    private ProgressBar progressBar;
    private TextView title;
    private TextView subtitle;
    private TextView content;
    private TextView userName;
    private TextView date;
    private UserPortraitScreenlet userPortraitScreenlet;
    private ImageDisplayScreenlet imageDisplayScreenlet;

    public BlogsEntryView(Context context) {
        super(context);
    }

    public BlogsEntryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlogsEntryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BlogsEntryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void showStartOperation(String actionName) {
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void showFinishOperation(String actionName) {
        throw new UnsupportedOperationException(
            "showFinishOperation(String) is not supported." + " Use showFinishOperation(BlogsEntry) instead.");
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {
        progressBar.setVisibility(GONE);
        LiferayLogger.e("Could not load file asset: " + e.getMessage());
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
    protected void onFinishInflate() {
        super.onFinishInflate();

        progressBar = findViewById(R.id.liferay_progress);

        imageDisplayScreenlet = findViewById(R.id.liferay_blog_image);

        userPortraitScreenlet = findViewById(R.id.user_portrait_screenlet);

        userName = findViewById(R.id.liferay_blog_username);
        date = findViewById(R.id.liferay_blog_date);

        title = findViewById(R.id.liferay_blog_title);
        subtitle = findViewById(R.id.liferay_blog_subtitle);
        content = findViewById(R.id.liferay_blog_content);
    }

    @Override
    public void showFinishOperation(BlogsEntry blogsEntry) {
        this.blogsEntry = blogsEntry;
        loadBlogsEntry();
        progressBar.setVisibility(GONE);
    }

    private void loadBlogsEntry() {
        long coverImageId = blogsEntry.getCoverImage();
        if (coverImageId != 0) {
            imageDisplayScreenlet.setClassName("com.liferay.document.library.kernel.model.DLFileEntry");
            imageDisplayScreenlet.setClassPK(coverImageId);
            imageDisplayScreenlet.load();
            imageDisplayScreenlet.setVisibility(VISIBLE);
        }

        userPortraitScreenlet.setUserId(blogsEntry.getUserId());
        userPortraitScreenlet.setEditable(false);
        userPortraitScreenlet.load();

        userName.setText(blogsEntry.getUserName());
        date.setText(blogsEntry.getDate());

        title.setText(blogsEntry.getTitle());

        if (blogsEntry.getSubtitle() != null && !"".equals(blogsEntry.getSubtitle())) {
            subtitle.setVisibility(VISIBLE);
            subtitle.setText(blogsEntry.getSubtitle());
        }
        content.setText(Html.fromHtml(blogsEntry.getContent()));
    }
}
