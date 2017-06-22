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
		String jsFileContent = AssetReader.read(context, jsFile);
		String parsed = parseScript(jsFileContent);

		addScript(parsed, isNavigationFreeScript);
	}

	public void addCss(String css) {
		String styleScript = "let style = document.createElement('style');"
			+ "style.type = 'text/css';"
			+ "style.innerHTML='"
			+ parseScript(css)
			+ "';"
			+ "let head = document.getElementsByTagName('head')[0];"
			+ "head.appendChild(style);";

		addScript(styleScript, false);
	}

	public void addCss(int cssFile) {
		String cssFileContent = AssetReader.read(context, cssFile);
		String cssScript = "let style = document.createElement('style');"
			+ "style.type = 'text/css';"
			+ "style.innerHTML='"
			+ parseScript(cssFileContent)
			+ "';"
			+ "let head = document.getElementsByTagName('head')[0];"
			+ "head.appendChild(style);";

		addScript(cssScript, false);
	}

	public String generateInjectableJs() {
		StringBuilder injectableJs = new StringBuilder();
		StringBuilder functionCalls = new StringBuilder();

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

