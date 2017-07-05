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

import android.content.Context;
import com.liferay.mobile.screens.util.AssetReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Víctor Galán Grande
 */
public class JavascriptInjector {

	private Map<String, String> scripts;
	private List<String> navigationFreeScripts;
	private Context context;

	public JavascriptInjector(Context context) {
		this.scripts = new TreeMap<>();
		this.navigationFreeScripts = new ArrayList<>();
		this.context = context.getApplicationContext();
	}

	public void addJs(String js) {
		addJs(js, false);
	}

	public void addJsFile(int jsFile) {
		addJsFile(jsFile, false);
	}

	public void addJs(String js, boolean isNavigationFreeScript) {
		addScript(parseScript(js), isNavigationFreeScript);
	}

	public void addJsFile(int jsFile, boolean isNavigationFreeScript) {
		AssetReader assetReader = new AssetReader(context);
		String jsFileContent = assetReader.read(jsFile);
		String parsed = parseScript(jsFileContent);

		addScript(parsed, isNavigationFreeScript);
	}

	public void addCss(String css) {
		String styleScript = "var style = document.createElement('style');"
			+ "style.type = 'text/css';"
			+ "style.innerHTML='"
			+ parseScript(css)
			+ "';"
			+ "var head = document.getElementsByTagName('head')[0];"
			+ "head.appendChild(style);";

		addScript(styleScript, false);
	}

	public String generateInjectableJs() {
		StringBuilder injectableJs = new StringBuilder();

		injectableJs.append("javascript:(function(){");

		for (Map.Entry<String, String> entry : scripts.entrySet()) {
			injectableJs.append(entry.getValue());
			injectableJs.append(entry.getKey()).append("();");
		}

		for (String navigationScript : navigationFreeScripts) {
			injectableJs.append("window.Screens.addScreensScript(");
			injectableJs.append(navigationScript);
			injectableJs.append(");");
		}

		injectableJs.append("}());");

		return injectableJs.toString();
	}

	private void addScript(String script, boolean isNavigationFreeScript) {
		String functionName = "script" + scripts.size();

		String wrapped = wrapScriptInFunction(functionName, script);

		scripts.put(functionName, wrapped);

		if (isNavigationFreeScript) {
			navigationFreeScripts.add(functionName);
		}
	}

	private String parseScript(String script) {
		return script.replace("\t", "").replace("\n", "");
	}

	private String wrapScriptInFunction(String functionName, String script) {
		return "function " + functionName + "() {" + script + "}";
	}
}
