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
public class DocsCard extends Card implements WebListener {

    WebScreenlet webScreenlet;
    private boolean loaded;

    public DocsCard(Context context) {
        super(context);
    }

    public DocsCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DocsCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DocsCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public ViewPropertyAnimator setState(CardState state) {
        if (!loaded && state.equals(CardState.NORMAL)) {
            loaded = true;
            loadDocuments();
        }

        return super.setState(state);
    }

    private void loadDocuments() {
        WebScreenletConfiguration configuration =
            new WebScreenletConfiguration.Builder("/web/westeros-hybrid/documents").addRawCss(R.raw.docs_portlet_css,
                "docs_portlet_css.css").addRawJs(R.raw.docs_portlet_js, "docs_portlet_js.js").load();

        webScreenlet.setWebScreenletConfiguration(configuration);
        webScreenlet.load();
        webScreenlet.setListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        webScreenlet = findViewById(R.id.portlet_docs);
    }

    @Override
    public void goLeft() {
        super.goLeft();
    }

    @Override
    public void error(Exception e, String userAction) {

    }

    @Override
    public void onPageLoaded(String url) {

    }

    @Override
    public void onScriptMessageHandler(String namespace, final String body) {
        if ("doc-item".equals(namespace)) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    WebScreenletConfiguration configuration =
                        new WebScreenletConfiguration.Builder("/web/westeros-hybrid/detail?id=" + body).addRawCss(
                            R.raw.detail_css, "detail_css.css").addRawJs(R.raw.detail_js, "detail_js.js").load();

                    WebScreenlet webScreenlet = findViewById(R.id.portlet_doc_item);

                    webScreenlet.setWebScreenletConfiguration(configuration);
                    webScreenlet.load();

                    cardListener.moveCardRight(DocsCard.this);
                }
            });
        }
    }
}
