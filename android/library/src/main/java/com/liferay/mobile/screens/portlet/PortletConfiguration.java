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

package com.liferay.mobile.screens.portlet;

import android.support.v4.util.Pair;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.portlet.util.CssScript;
import com.liferay.mobile.screens.portlet.util.InjectableScript;
import com.liferay.mobile.screens.portlet.util.JsScript;
import com.liferay.mobile.screens.portlet.util.RemoteCssScript;
import com.liferay.mobile.screens.portlet.util.RemoteJsScript;
import com.liferay.mobile.screens.util.AssetReader;
import com.liferay.mobile.screens.viewsets.defaultviews.portlet.cordova.CordovaLifeCycleObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sarai Díaz García
 */
public class PortletConfiguration {

	private String portletUrl;
	private List<InjectableScript> scripts;
	private boolean isThemeEnabled;
	private WebType webType;
	private CordovaLifeCycleObserver observer;
	private boolean isCordovaEnabled;

	public PortletConfiguration(String portletUrl, List<InjectableScript> scripts,
		boolean isThemeEnabled, WebType webType, CordovaLifeCycleObserver observer,
		boolean isCordovaEnabled) {

		this.portletUrl = portletUrl;
		this.scripts = scripts;
		this.isThemeEnabled = isThemeEnabled;
		this.webType = webType;
		this.observer = observer;
		this.isCordovaEnabled = isCordovaEnabled;
	}

	public String getPortletUrl() {
		return portletUrl;
	}

	public List<InjectableScript> getScripts() {
		return scripts;
	}

	public boolean isThemeEnabled() {
		return isThemeEnabled;
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

	public enum WebType {LIFERAY_AUTHENTICATED, LIFERAY, OTHER}

	public static class Builder {

		private String portletUrl;
		private List<String> localJs = new ArrayList<>();
		private List<String> localCss = new ArrayList<>();
		private List<String> remoteJs = new ArrayList<>();
		private List<String> remoteCss = new ArrayList<>();
		private List<Pair<Integer, String>> localRawJs = new ArrayList<>();
		private List<Pair<Integer, String>> localRawCss = new ArrayList<>();
		private boolean isThemeEnabled = true;
		private WebType webType = WebType.LIFERAY_AUTHENTICATED;
		private CordovaLifeCycleObserver observer;
		private boolean isCordovaEnabled = false;

		public Builder(String portletUrl) {
			super();
			this.portletUrl = portletUrl;
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

		public Builder disableTheme() {
			this.isThemeEnabled = false;
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

		public PortletConfiguration load() {

			List<InjectableScript> allScripts = new ArrayList<>();

			for (String js : localJs) {
				String content = loadLocalContent(js);
				if (!content.isEmpty()) {
					allScripts.add(new JsScript(js, loadLocalContent(js)));
				}
			}

			for (String css : localCss) {
				String content = loadLocalContent(css);
				if (!content.isEmpty()) {
					allScripts.add(new CssScript(css, loadLocalContent(css)));
				}
			}

			for (String rJs : remoteJs) {
				allScripts.add(new RemoteJsScript(rJs, rJs));
			}

			for (String rCss : remoteCss) {
				allScripts.add(new RemoteCssScript(rCss, rCss));
			}

			for (Pair<Integer, String> pairCssName : localRawCss) {
				String content = loadLocalContent(pairCssName.first);
				if (!content.isEmpty()) {
					allScripts.add(new CssScript(pairCssName.second, content));
				}
			}

			for (Pair<Integer, String> pairJsName : localRawJs) {
				String content = loadLocalContent(pairJsName.first);
				if (!content.isEmpty()) {
					allScripts.add(new JsScript(pairJsName.second, content));
				}
			}

			return new PortletConfiguration(portletUrl, allScripts, isThemeEnabled, webType,
				observer, isCordovaEnabled);
		}

		private String loadLocalContent(String fileName) {
			return new AssetReader(LiferayScreensContext.getContext()).read(fileName);
		}

		private String loadLocalContent(int fileId) {
			return new AssetReader(LiferayScreensContext.getContext()).read(fileId);
		}
	}
}
