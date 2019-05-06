/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.asset.display;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.AssetFactory;
import com.liferay.mobile.screens.asset.display.interactor.AssetDisplayInteractor;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.blogs.BlogsEntryDisplayScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.dlfile.display.audio.AudioDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.pdf.PdfDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.video.VideoDisplayScreenlet;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sarai Díaz García
 */
public class AssetDisplayScreenlet extends BaseScreenlet<AssetDisplayViewModel, AssetDisplayInteractor>
    implements AssetDisplayListener {

    public static final String STATE_LAYOUTS = "STATE_LAYOUTS";
    public static final String STATE_ENTRY_ID = "STATE_ENTRY_ID";
    private boolean autoLoad;
    private Map<String, Integer> layouts;
    private long entryId;
    private String className;
    private long classPK;
    private String portletItemName;
    private AssetDisplayListener listener;
    private AssetDisplayInnerScreenletListener configurationListener;

    public AssetDisplayScreenlet(Context context) {
        super(context);
    }

    public AssetDisplayScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AssetDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AssetDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.AssetDisplayScreenlet, 0, 0);

        int layoutId = typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_layoutId, getDefaultLayoutId());

        autoLoad = typedArray.getBoolean(R.styleable.AssetDisplayScreenlet_autoLoad, true);
        entryId = typedArray.getInt(R.styleable.AssetDisplayScreenlet_entryId, 0);

        className = typedArray.getString(R.styleable.AssetDisplayScreenlet_className);
        classPK = typedArray.getInt(R.styleable.AssetDisplayScreenlet_classPK, 0);

        portletItemName = typedArray.getString(R.styleable.AssetDisplayScreenlet_portletItemName);

        layouts = new HashMap<>();
        layouts.put(ImageDisplayScreenlet.class.getName(),
            typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_imagelayoutId, R.layout.image_display_default));
        layouts.put(VideoDisplayScreenlet.class.getName(),
            typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_videolayoutId, R.layout.video_display_default));
        layouts.put(AudioDisplayScreenlet.class.getName(),
            typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_audiolayoutId, R.layout.audio_display_default));
        layouts.put(PdfDisplayScreenlet.class.getName(),
            typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_pdflayoutId, R.layout.pdf_display_default));
        layouts.put(BlogsEntryDisplayScreenlet.class.getName(),
            typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_blogsLayoutId,
                R.layout.blogsentry_display_default));
        layouts.put(WebContentDisplayScreenlet.class.getName(),
            typedArray.getResourceId(R.styleable.AssetDisplayScreenlet_webDisplayLayoutId,
                R.layout.webcontentdisplay_default));

        View view = LayoutInflater.from(context).inflate(layoutId, null);

        typedArray.recycle();

        return view;
    }

    /**
     * Searches the {@link AssetEntry} with the given attributes ({@link #entryId} or {@link #className}
     * and {@link #classPK}) and loads it in the screenlet.
     */
    public void loadAsset() {
        performUserAction();
    }

    /**
     * Loads the given {@link AssetEntry} in the screenlet. If there is no inner screenlet created,
     * the method tries to load it based on custom asset listener method. If this return null,
     * a new Intent is called to display it.
     *
     * @param assetEntry decorated AssetEntry.
     */
    public void load(AssetEntry assetEntry) {
        AssetEntry asset = AssetFactory.createInstance(assetEntry.getValues());
        BaseScreenlet screenlet = AssetDisplayFactory.getScreenlet(getContext(), asset, layouts);
        if (screenlet != null) {
            if (configurationListener != null) {
                configurationListener.onConfigureChildScreenlet(this, screenlet, asset);
            }

            getViewModel().showFinishOperation(screenlet);
        } else {

            View customView = null;
            if (configurationListener != null) {
                customView = configurationListener.onRenderCustomAsset(asset);
            }

            if (customView != null) {
                getViewModel().showFinishOperation(customView);
            } else {
                String server = getResources().getString(R.string.liferay_server);

                String url =
                    server + (asset instanceof ImageEntry ? ((ImageEntry) asset).getImageUrl() : asset.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(intent);
                } else {
                    LiferayLogger.e("Error loading screenlet");
                    if (listener != null) {
                        listener.error(new Exception("Error loading screenlet"), DEFAULT_ACTION);
                    }
                }
            }
        }
    }

    /**
     * Removes the created screenlet inside {@link AssetDisplayScreenlet}.
     */
    public void removeInnerScreenlet() {
        getViewModel().removeInnerScreenlet();
    }

    @Override
    public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
        load(assetEntry);

        if (listener != null) {
            listener.onRetrieveAssetSuccess(assetEntry);
        }
    }

    @Override
    public void error(Exception e, String userAction) {
        getViewModel().showFailedOperation(null, e);

        if (listener != null) {
            listener.error(e, DEFAULT_ACTION);
        }
    }

    @Override
    protected AssetDisplayInteractor createInteractor(String actionName) {
        return new AssetDisplayInteractor();
    }

    @Override
    protected void onScreenletAttached() {
        super.onScreenletAttached();

        if (autoLoad) {
            autoLoad();
        }
    }

    /**
     * Checks if there is a session created and if exists {@link #entryId} or {@link #className}
     * and {@link #classPK} attributes and then calls {@link #loadAsset()} method.
     */
    //TODO now the autoload is required to be able to load child screenlets
    protected void autoLoad() {
        boolean hasClassName = className != null && classPK != 0;
        boolean hasPortletItemName = portletItemName != null && !"".equals(portletItemName);

        if (SessionContext.isLoggedIn() && (entryId != 0 || hasClassName || hasPortletItemName)) {
            loadAsset();
        }
    }

    @Override
    protected void onUserAction(String userActionName, AssetDisplayInteractor interactor, Object... args) {
        if (entryId != 0) {
            interactor.start(entryId);
        } else if (className != null && !"".equals(className) && classPK != 0) {
            interactor.start(className, classPK);
        } else {
            interactor.start(portletItemName);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        Bundle state = new Bundle();
        state.putParcelable(STATE_SUPER, superState);
        state.putSerializable(STATE_LAYOUTS, (Serializable) layouts);
        state.putLong(STATE_ENTRY_ID, entryId);
        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable inState) {
        Bundle state = (Bundle) inState;

        layouts = (HashMap<String, Integer>) state.getSerializable(STATE_LAYOUTS);
        entryId = state.getLong(STATE_ENTRY_ID);

        Parcelable superState = state.getParcelable(STATE_SUPER);
        super.onRestoreInstanceState(superState);
    }

    public long getEntryId() {
        return entryId;
    }

    public void setEntryId(long entryId) {
        this.entryId = entryId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getClassPK() {
        return classPK;
    }

    public void setClassPK(long classPK) {
        this.classPK = classPK;
    }

    public String getPortletItemName() {
        return portletItemName;
    }

    public void setPortletItemName(String portletItemName) {
        this.portletItemName = portletItemName;
    }

    public AssetDisplayListener getListener() {
        return listener;
    }

    public void setListener(AssetDisplayListener listener) {
        this.listener = listener;
    }

    public AssetDisplayInnerScreenletListener getConfigurationListener() {
        return configurationListener;
    }

    public void setConfigurationListener(AssetDisplayInnerScreenletListener configurationListener) {
        this.configurationListener = configurationListener;
    }

    public boolean isAutoLoad() {
        return autoLoad;
    }

    public void setAutoLoad(boolean autoLoad) {
        this.autoLoad = autoLoad;
    }

    public Map<String, Integer> getLayouts() {
        return layouts;
    }

    public void setLayouts(Map<String, Integer> layouts) {
        this.layouts = layouts;
    }
}
