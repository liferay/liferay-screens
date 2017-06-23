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

import Foundation

@objc public protocol InjectableScript {
	var content: String { get }
}

public class JsScript: InjectableScript {
	public let content: String

	init(js: String) {
		content = js
	}
}

public class CssScript: InjectableScript {
	public let content: String

	init(css: String) {
		content = "var style = document.createElement('style');"
			+ "style.type = 'text/css';"
			+ "style.innerHTML = '\(css.replacingOccurrences(of: "\n", with: ""))';"
			+ "var head = document.getElementsByTagName('head')[0];"
			+ "head.appendChild(style);"
	}
}

public class RemoteJsScript: InjectableScript {
	public let content: String

	init(url: String) {
		content = "var script = document.createElement('script');"
			+ "script.language = 'javascript';"
			+ "script.type = 'text/javascript';"
			+ "script.src = '\(url)'"
			+ "var body = document.getElementsByTagName('body')[0];"
			+ "head.appendChild(script);"

	}
}

public class RemoteCssScript: InjectableScript {
	public let content: String

	init(url: String) {
		content = "var link = document.createElement('link');"
			+ "link.type = 'text/css';"
			+ "link.rel = 'stylesheet';"
			+ "link.href = '\(url)';"
			+ "var head = document.getElementsByTagName('head')[0];"
			+ "head.appendChild(link);"
	}
}
