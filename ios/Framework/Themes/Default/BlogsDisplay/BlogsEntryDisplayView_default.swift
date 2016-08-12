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

	public var blogsEntry: BlogsEntry? {
		didSet {
			if let blogsEntry = blogsEntry {

				//Set image blog if exist
				let imageId = blogsEntry.coverImageFileEntryId
				if imageId != 0 {
					imageHeightConstraint?.constant = self.headerImageHeight

					imageDisplayScreenlet?.className = FileEntry.className
					imageDisplayScreenlet?.classPK = imageId
					imageDisplayScreenlet?.load()
				}
				else {
					imageHeightConstraint?.constant = 0
				}

				//TODO if you're doing several paragrahps, with comments, you need several methods

				//Set user portrait image
				userPortraitScreenlet?.load(userId: blogsEntry.userId)

				//Set username
				usernameLabel?.text = blogsEntry.userName

				//Set blog display date from timestamp
				if let date = blogsEntry.displayDate {
					let dateFormatter = NSDateFormatter()
					dateFormatter.dateStyle = NSDateFormatterStyle.LongStyle
					dateFormatter.locale = NSLocale(localeIdentifier: NSLocale.currentLocaleString)

					dateLabel?.text = dateFormatter.stringFromDate(date)
				}
				else {
					//TODO i18n
					dateLabel?.text = "Unknown date"
				}


				dateLabel?.text = blogsEntry.displayDateFormatted

				//Set blog title and subtitle
				titleLabel?.text = blogsEntry.title
				subtitleLabel?.text = blogsEntry.subtitle

				//Set html content
				let content = "<span style=\"\(contentStyle)\">\(blogsEntry.content)</span>"

				let encodedData = content.dataUsingEncoding(NSUTF8StringEncoding)!
				let attributedOptions : [String: AnyObject] = [
					NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType,
					NSCharacterEncodingDocumentAttribute: NSUTF8StringEncoding
				]
				contentLabel?.attributedText = try! NSAttributedString(data: encodedData, options: attributedOptions, documentAttributes: nil)
			}
		}
	}
}
