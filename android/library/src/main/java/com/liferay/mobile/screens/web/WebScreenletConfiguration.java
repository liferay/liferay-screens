/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.web;

import androidx.core.util.Pair;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.ScriptReader;
import com.liferay.mobile.screens.viewsets.defaultviews.web.cordova.CordovaLifeCycleObserver;
import com.liferay.mobile.screens.web.util.CssScript;
import com.liferay.mobile.screens.web.util.InjectableScript;
import com.liferay.mobile.screens.web.util.JsScript;
import com.liferay.mobile.screens.web.util.RemoteCssScript;
import com.liferay.mobile.screens.web.util.RemoteJsScript;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sarai Díaz García
 */
public class WebScreenletConfiguration {

    private String url;
    private List<InjectableScript> scripts;
    private WebType webType;
    private CordovaLifeCycleObserver observer;
    private boolean isCordovaEnabled;

    public WebScreenletConfiguration(String url, List<InjectableScript> scripts, WebType webType,
        CordovaLifeCycleObserver observer, boolean isCordovaEnabled) {

        this.url = url;
        this.scripts = scripts;
        this.webType = webType;
        this.observer = observer;
        this.isCordovaEnabled = isCordovaEnabled;
    }

    public String getUrl() {
        return url;
    }

    public List<InjectableScript> getScripts() {
        return scripts;
    }

    public WebType getWebType() {
        return webType;
    }

    public CordovaLifeCycleObserver getObserver() {
        return observer;
    }

    public boolean isCordovaEnabled() {
        return isCordovaEnabled;
    }

    public enum WebType {LIFERAY_AUTHENTICATED, OTHER}

    public static class Builder {

        private String url;
        private List<String> localJs = new ArrayList<>();
        private List<String> localCss = new ArrayList<>();
        private List<String> remoteJs = new ArrayList<>();
        private List<String> remoteCss = new ArrayList<>();
        private List<Pair<Integer, String>> localRawJs = new ArrayList<>();
        private List<Pair<Integer, String>> localRawCss = new ArrayList<>();
        private WebType webType = WebType.LIFERAY_AUTHENTICATED;
        private CordovaLifeCycleObserver observer;
        private boolean isCordovaEnabled;

        public Builder(String url) {
            super();
            this.url = url;
        }

        public Builder addLocalJs(String fileName) {
            this.localJs.add(fileName);
            return this;
        }

        public Builder addLocalCss(String fileName) {
            this.localCss.add(fileName);
            return this;
        }

        public Builder addRawCss(int rawCss, String name) {
            this.localRawCss.add(new Pair<>(rawCss, name));
            return this;
        }

        public Builder addRawJs(int rawJs, String name) {
            this.localRawJs.add(new Pair<>(rawJs, name));
            return this;
        }

        public Builder addRemoteJs(String url) {
            this.remoteJs.add(url);
            return this;
        }

        public Builder addRemoteCss(String url) {
            this.remoteCss.add(url);
            return this;
        }

        public Builder setWebType(WebType webType) {
            this.webType = webType;
            return this;
        }

        public Builder enableCordova(CordovaLifeCycleObserver observer) {
            this.observer = observer;
            this.isCordovaEnabled = true;
            return this;
        }

        public WebScreenletConfiguration load() {

            List<InjectableScript> allScripts = new ArrayList<>();

            for (String js : localJs) {
                String content = loadLocalContent(js);
                if (!content.isEmpty()) {
                    allScripts.add(new JsScript(js, loadLocalContent(js)));
                }
            }

            for (Pair<Integer, String> pairJsName : localRawJs) {
                String content = loadLocalContent(pairJsName.first);
                if (!content.isEmpty()) {
                    allScripts.add(new JsScript(pairJsName.second, content));
                }
            }

            for (String css : localCss) {
                String content = loadLocalContent(css);
                if (!content.isEmpty()) {
                    allScripts.add(new CssScript(css, loadLocalContent(css)));
                }
            }

            for (Pair<Integer, String> pairCssName : localRawCss) {
                String content = loadLocalContent(pairCssName.first);
                if (!content.isEmpty()) {
                    allScripts.add(new CssScript(pairCssName.second, content));
                }
            }

            for (String rJs : remoteJs) {
                allScripts.add(new RemoteJsScript(rJs, rJs));
            }

            for (String rCss : remoteCss) {
                allScripts.add(new RemoteCssScript(rCss, rCss));
            }

            return new WebScreenletConfiguration(url, allScripts, webType, observer, isCordovaEnabled);
        }

        private String loadLocalContent(String fileName) {
            return new ScriptReader(LiferayScreensContext.getContext()).readScriptContent(fileName);
        }

        private String loadLocalContent(int fileId) {
            return new ScriptReader(LiferayScreensContext.getContext()).readScriptContent(fileId);
        }
    }
}
