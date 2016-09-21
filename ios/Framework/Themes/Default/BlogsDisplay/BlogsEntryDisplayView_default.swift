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


public class BlogsEntryDisplayView_default: BaseScreenletView, BlogsDisplayViewModel {

	@IBOutlet weak var imageDisplayScreenlet: ImageDisplayScreenlet?
	@IBOutlet weak var userPortraitScreenlet: UserPortraitScreenlet?

	@IBOutlet weak var usernameLabel: UILabel?
	@IBOutlet weak var dateLabel: UILabel?

	@IBOutlet weak var titleLabel: UILabel?
	@IBOutlet weak var subtitleLabel: UILabel?
	@IBOutlet weak var contentLabel: UILabel?
	@IBOutlet weak var imageHeightConstraint: NSLayoutConstraint?

	public var headerImageHeight: CGFloat = 125.0

	public let dateFormatter: NSDateFormatter = {
		let dateFormatter = NSDateFormatter()
		dateFormatter.dateStyle = NSDateFormatterStyle.LongStyle
		dateFormatter.locale = NSLocale(
			localeIdentifier: NSLocale.currentLocaleString)
		return dateFormatter
	}()

	public var blogsEntry: BlogsEntry? {
		didSet {
			if let _ = blogsEntry {
				self.loadBlog()
			}
		}
	}

	public func loadBlog() {
		self.loadImage()
		self.loadUserInfo()
		self.loadDate()
		self.loadTitleSubtitle()
		self.loadContent()
	}

	public func loadImage() {
		let imageId = self.blogsEntry!.coverImageFileEntryId
		if imageId != 0 {
			imageHeightConstraint?.constant = self.headerImageHeight

			imageDisplayScreenlet?.className = AssetClasses.getClassName(AssetClassNameKey_DLFileEntry)!
			imageDisplayScreenlet?.classPK = imageId
			imageDisplayScreenlet?.load()
		}
		else {
			imageHeightConstraint?.constant = 0
		}
	}

	public func loadUserInfo() {
		userPortraitScreenlet?.load(userId: self.blogsEntry!.userId ?? 0)
		usernameLabel?.text = self.blogsEntry!.userName
	}

	public func loadDate() {
		if let date = self.blogsEntry!.displayDate {
			dateLabel?.text = dateFormatter.stringFromDate(date)
		}
		else {
			dateLabel?.text = LocalizedString("default", key: "blog-unknown-date", obj: self)
		}
	}

	public func loadTitleSubtitle() {
		titleLabel?.text = self.blogsEntry!.title
		subtitleLabel?.text = self.blogsEntry!.subtitle
	}

	public func loadContent() {
		contentLabel?.attributedText = self.blogsEntry!.content.toHtmlTextWithAttributes(
			self.dynamicType.defaultAttributedTextAttributes())
	}

	public class func defaultAttributedTextAttributes() -> [String: NSObject] {
		let paragrahpStyle = NSMutableParagraphStyle()
		paragrahpStyle.lineBreakMode = .ByWordWrapping

		var attributes: [String: NSObject] = [NSParagraphStyleAttributeName: paragrahpStyle]

		let font = UIFont(name: "HelveticaNeue", size: 17)

		if let font = font {
			attributes[NSFontAttributeName] = font
		}

		return attributes
	}
}
