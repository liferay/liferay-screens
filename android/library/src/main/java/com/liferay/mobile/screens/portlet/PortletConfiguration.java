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

	public String portletUrl;
	public List<InjectableScript> scripts;
	public boolean automaticMode;

	public PortletConfiguration(String portletUrl, List<InjectableScript> scripts, boolean automaticMode) {
		this.portletUrl = portletUrl;
		this.scripts = scripts;
		this.automaticMode = automaticMode;
	}

	public static class Builder {

		private String portletUrl;
		private List<String> localJs = new ArrayList<>();
		private List<String> localCss = new ArrayList<>();
		private List<String> remoteJs = new ArrayList<>();
		private List<String> remoteCss = new ArrayList<>();
		private List<Integer> localRawCss = new ArrayList<>();
		private List<Integer> localRawJs = new ArrayList<>();
		private boolean automaticMode;

		public Builder() {
			super();
		}

		public Builder(String portletUrl, List<String> localJs, List<String> localCss,
			List<String> remoteJs, List<String> remoteCss, boolean automaticMode) {
			this.portletUrl = portletUrl;
			this.localJs = localJs;
			this.localCss = localCss;
			this.remoteJs = remoteJs;
			this.remoteCss = remoteCss;
			this.automaticMode = automaticMode;
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

		public Builder setAutomaticModeOn() {
			this.automaticMode = true;
			return this;
		}

		public PortletConfiguration load() {

			List<InjectableScript> allScripts = new ArrayList<>();

			for (String js : localJs) {
				allScripts.add(new JsScript(loadLocalContent(js)));
			}

			for (String css : localCss) {
				allScripts.add(new CssScript(loadLocalContent(css)));
			}

			for (String rJs : remoteJs) {
				allScripts.add(new RemoteJsScript(rJs));
			}

			for (String rCss : remoteCss) {
				allScripts.add(new RemoteCssScript(rCss));
			}

			for (int rawCss : localRawCss) {
				allScripts.add(new CssScript(loadLocalContent(rawCss)));
			}

			for (int rawJs : localRawJs) {
				allScripts.add(new JsScript(loadLocalContent(rawJs)));
			}

			return new PortletConfiguration(portletUrl, allScripts, automaticMode);
		}

		private String loadLocalContent(String fileName) {
			return new AssetReader(LiferayScreensContext.getContext()).read(fileName);
		}

		private String loadLocalContent(int fileId) {
			return new AssetReader(LiferayScreensContext.getContext()).read(fileId);
		}
	}
}
