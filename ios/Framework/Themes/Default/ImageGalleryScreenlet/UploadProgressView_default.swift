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

public class UploadProgressView_default: UploadProgressViewBase {

	@IBOutlet weak private var previewImage: UIImageView!
	@IBOutlet weak private var progressView: UIProgressView!
	@IBOutlet weak private var informationText: UILabel!

	public var cancelClosure: (Void -> Void)?

	private var images: [UIImage] = []
	private var uploadsCount = 0

	public override func awakeFromNib() {
		progressView.progress = 0
		addShadow()
	}

	public func addShadow() {
		layer.masksToBounds = false;
		layer.shadowOffset = CGSize(width: 0, height: 0);
		layer.shadowRadius = 5;
		layer.shadowOpacity = 0.3;
	}

	@IBAction func cancelButton() {
		cancelClosure?()
		hide()
	}

	public override func setProgress(progress: Float) {
		progressView.progress = progress
	}

	public override func addUpload(thumbnail: UIImage? = nil) {
		uploadsCount += 1

		if let thumbnail = thumbnail {
			images.append(thumbnail)
		}

		if uploadsCount == 1 {
			previewImage.image = images.popLast()
		}

		updateInformationText()
	}

	public override func uploadComplete() {
		if uploadsCount == 1 {
			hide()
		}
		else {
			uploadsCount -= 1
			progressView.progress = 0
			previewImage.image = images.popLast()
			updateInformationText()
		}
	}

	public func hide() {
		UIView.animateWithDuration(0.5 , animations: {
			self.alpha = 0
		}) { _ in
			self.removeFromSuperview()
		}
	}

	private func updateInformationText() {
		if uploadsCount == 1 {
			informationText.text = "Uploading image..."
		}
		else {
			informationText.text = "Uploading image 1 from \(uploadsCount)"
		}
	}
}
