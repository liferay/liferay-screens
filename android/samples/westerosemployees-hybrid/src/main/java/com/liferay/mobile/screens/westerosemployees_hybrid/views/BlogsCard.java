package com.liferay.mobile.screens.westerosemployees_hybrid.views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;

import com.liferay.mobile.screens.web.WebScreenletConfiguration;
import com.liferay.mobile.screens.web.WebListener;
import com.liferay.mobile.screens.web.WebScreenlet;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;
import com.liferay.mobile.screens.westerosemployees_hybrid.utils.CardState;

/**
 * @author Víctor Galán Grande
 */
public class BlogsCard extends Card implements WebListener {

    private WebScreenlet webScreenlet;
    private boolean loaded;

    public BlogsCard(Context context) {
        super(context);
    }

    public BlogsCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlogsCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BlogsCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public ViewPropertyAnimator setState(CardState state) {
        if (!loaded && state.equals(CardState.NORMAL)) {
            loaded = true;
            loadCompanyNews();
        }

        return super.setState(state);
    }

    private void loadCompanyNews() {
        WebScreenletConfiguration configuration =
            new WebScreenletConfiguration.Builder("/web/westeros-hybrid/companynews").addRawCss(R.raw.blogs_portlet_css,
                "blogs_portlet_css.css").addRawJs(R.raw.blogs_portlet_js, "blogs_portlet_js.js").load();

        webScreenlet.setWebScreenletConfiguration(configuration);
        webScreenlet.load();
        webScreenlet.setListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        webScreenlet = findViewById(R.id.portlet_blogs);
    }

    @Override
    public void error(Exception e, String userAction) {

    }

    @Override
    public void onPageLoaded(String url) {

    }

    @Override
    public void onScriptMessageHandler(String namespace, final String body) {
        if ("blog-item".equals(namespace)) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    WebScreenletConfiguration configuration =
                        new WebScreenletConfiguration.Builder("/web/westeros-hybrid/detail?id=" + body).addRawCss(
                            R.raw.blog_portlet_css, "blog_portlet_css.css")
                            .addRawJs(R.raw.blog_portlet_js, "blog_portlet_js.js")
                            .load();

                    WebScreenlet webScreenlet = findViewById(R.id.portlet_blog_item);

                    webScreenlet.setWebScreenletConfiguration(configuration);
                    webScreenlet.load();

                    cardListener.moveCardRight(BlogsCard.this);
                }
            });
        }
    }
}
