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

import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.portlet.util.CssScript;
import com.liferay.mobile.screens.portlet.util.InjectableScript;
import com.liferay.mobile.screens.portlet.util.JsScript;
import com.liferay.mobile.screens.portlet.util.RemoteCssScript;
import com.liferay.mobile.screens.portlet.util.RemoteJsScript;
import com.liferay.mobile.screens.util.AssetReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sarai Díaz García
 */
public class PortletConfiguration {

	private String portletUrl;
	private List<InjectableScript> scripts;
	private boolean isThemeEnabled;

	public PortletConfiguration(String portletUrl, List<InjectableScript> scripts, boolean isThemeEnabled) {
		this.portletUrl = portletUrl;
		this.scripts = scripts;
		this.isThemeEnabled = isThemeEnabled;
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

	public static class Builder {

		private String portletUrl;
		private List<String> localJs;
		private List<String> localCss;
		private List<String> remoteJs;
		private List<String> remoteCss;
		private List<Integer> localRawJs;
		private List<Integer> localRawCss;
		private boolean isThemeEnabled;

		public Builder(String portletUrl) {
			super();

			this.portletUrl = portletUrl;
			localJs = new ArrayList<>();
			localCss = new ArrayList<>();
			remoteJs = new ArrayList<>();
			remoteCss = new ArrayList<>();
			localRawJs = new ArrayList<>();
			localRawCss = new ArrayList<>();
			this.isThemeEnabled = true;
		}

		public Builder(String portletUrl, List<String> localJs, List<String> localCss,
			List<String> remoteJs, List<String> remoteCss, boolean isThemeEnabled) {
			this.portletUrl = portletUrl;
			this.localJs = localJs;
			this.localCss = localCss;
			this.remoteJs = remoteJs;
			this.remoteCss = remoteCss;
			this.isThemeEnabled = isThemeEnabled;
		}

		public Builder addLocalJs(String fileName) {
			this.localJs.add(fileName);
			return this;
		}

		public Builder addLocalCss(String fileName) {
			this.localCss.add(fileName);
			return this;
		}

		public Builder addRawCss(int rawCss) {
			this.localRawCss.add(rawCss);
			return this;
		}

		public Builder addRawJs(int rawJs) {
			this.localRawJs.add(rawJs);
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

		public PortletConfiguration load() {

			List<InjectableScript> allScripts = new ArrayList<>();

			for (String js : localJs) {
				String content = loadLocalContent(js);
				if (!content.isEmpty()) {
					allScripts.add(new JsScript(loadLocalContent(js)));
				}
			}

			for (String css : localCss) {
				String content = loadLocalContent(css);
				if (!content.isEmpty()) {
					allScripts.add(new CssScript(loadLocalContent(css)));
				}
			}

			for (String rJs : remoteJs) {
				allScripts.add(new RemoteJsScript(rJs));
			}

			for (String rCss : remoteCss) {
				allScripts.add(new RemoteCssScript(rCss));
			}

			for (int rawCss : localRawCss) {
				String content = loadLocalContent(rawCss);
				if (!content.isEmpty()) {
					allScripts.add(new CssScript(loadLocalContent(rawCss)));
				}
			}

			for (int rawJs : localRawJs) {
				String content = loadLocalContent(rawJs);
				if (!content.isEmpty()) {
					allScripts.add(new JsScript(content));
				}
			}

			return new PortletConfiguration(portletUrl, allScripts, isThemeEnabled);
		}

		private String loadLocalContent(String fileName) {
			return new AssetReader(LiferayScreensContext.getContext()).read(fileName);
		}

		private String loadLocalContent(int fileId) {
			return new AssetReader(LiferayScreensContext.getContext()).read(fileId);
		}
	}
}
