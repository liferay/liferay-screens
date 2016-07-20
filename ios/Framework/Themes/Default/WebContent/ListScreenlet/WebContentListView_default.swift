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
import UIKit


public class WebContentListView_default: BaseListCollectionView {

	//MARK: BaseScreenletView

	override public func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}

	public override func doConfigureCollectionView(collectionView: UICollectionView) {
		collectionView.backgroundColor = .whiteColor()
	}

	public override func doCreateLayout() -> UICollectionViewLayout {
		let layout = UICollectionViewFlowLayout()
		layout.itemSize = CGSize(width: 300, height: 500)
		layout.sectionInset = UIEdgeInsets(top: 0, left: 20, bottom: 0, right: 20)
		layout.scrollDirection = .Vertical
		

		return layout
	}

	public override func doRegisterCellNibs() {
		collectionView?.registerClass(WebViewCell.self, forCellWithReuseIdentifier: "cell")
		collectionView?.registerClass(UICollectionViewCell.self, forCellWithReuseIdentifier: "progress")
	}

	override public func doFillLoadedCell(indexPath indexPath: NSIndexPath, cell: UICollectionViewCell, object:AnyObject) {
		guard let cell = cell as? WebViewCell, object = object as? WebContent
		else {
			return
		}

		cell.html = object.html ?? "No html available"
		cell.layer.borderWidth = 1;
		cell.layer.borderColor = UIColor.blackColor().CGColor
	}

	override public func doFillInProgressCell(indexPath indexPath: NSIndexPath, cell: UICollectionViewCell) {

		cell.backgroundColor = .blackColor()
	}

	public override func doGetCellId(indexPath indexPath: NSIndexPath, object: AnyObject?) -> String {
		if let _ = object {
			return "cell"
		}

		return "progress"
	}
}


public class WebViewCell : UICollectionViewCell {

	private var webView: UIWebView

	private let styles =
		".MobileCSS {padding: 4%; width: 92%;} " +
			".MobileCSS, .MobileCSS span, .MobileCSS p, .MobileCSS h1, .MobileCSS h2, .MobileCSS h3 { " +
			"font-size: 110%; font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif; font-weight: 200; } " +
			".MobileCSS img { width: 100% !important; } " +
	".span2, .span3, .span4, .span6, .span8, .span10 { width: 100%; }"

	public var html: String {
		get {
			return ""
		}
		set {
			let styledHtml = "<style>\(styles)</style><div class=\"MobileCSS\">\(newValue ?? "")</div>"

			webView.loadHTMLString(styledHtml, baseURL: NSURL(string:LiferayServerContext.server))
		}
	}

	override init(frame: CGRect) {
		webView = UIWebView()
		super.init(frame: frame)

		webView.frame = bounds
		addSubview(webView)
	}

	required public init?(coder aDecoder: NSCoder) {
		webView = UIWebView()
		super.init(coder: aDecoder)

		webView.frame = bounds
		addSubview(webView)
	}
}
