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

	public var contentStyle = "font-size:17"
	public var headerImageHeight: CGFloat = 125.0

	private let dateFormatter: NSDateFormatter = {
		let dateFormatter = NSDateFormatter()
		dateFormatter.dateStyle = NSDateFormatterStyle.LongStyle
		dateFormatter.locale = NSLocale(
			localeIdentifier: NSLocale.currentLocaleString)
		return dateFormatter
	}()

	private var selectedBlogsEntry: BlogsEntry?

	public var blogsEntry: BlogsEntry? {
		didSet {
			if let blogsEntry = blogsEntry {
				self.selectedBlogsEntry = blogsEntry
				self.setImage()
				self.setUserInfo()
				self.setDate()
				self.setTitleSubtitle()
				self.setContent()
			}
		}
	}

	private func setImage() {
		let imageId = self.selectedBlogsEntry?.coverImageFileEntryId
		if imageId != 0 {
			imageHeightConstraint?.constant = self.headerImageHeight

			imageDisplayScreenlet?.className = AssetClassNameIds.getClassName(AssetClassNameIdDLFileEntry)!
			imageDisplayScreenlet?.classPK = imageId!
			imageDisplayScreenlet?.load()
		}
		else {
			imageHeightConstraint?.constant = 0
		}
	}

	private func setUserInfo() {
		userPortraitScreenlet?.load(userId: self.selectedBlogsEntry!.userId)
		usernameLabel?.text = self.selectedBlogsEntry?.userName
	}

	private func setDate() {
		if let date = self.selectedBlogsEntry?.displayDate {
			dateLabel?.text = dateFormatter.stringFromDate(date)
		}
		else {
			dateLabel?.text = LocalizedString("default", key: "blog-unknown-date", obj: self)
		}
	}

	private func setTitleSubtitle() {
		titleLabel?.text = self.selectedBlogsEntry?.title
		subtitleLabel?.text = self.selectedBlogsEntry?.subtitle
	}

	private func setContent() {
		let content = "<span style=\"\(contentStyle)\">\(self.selectedBlogsEntry!.content)</span>"

		let encodedData = content.dataUsingEncoding(NSUTF8StringEncoding)!
		let attributedOptions : [String: AnyObject] = [
			NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType,
			NSCharacterEncodingDocumentAttribute: NSUTF8StringEncoding
		]
		contentLabel?.attributedText = try! NSAttributedString(data: encodedData, options: attributedOptions, documentAttributes: nil)
	}
}
