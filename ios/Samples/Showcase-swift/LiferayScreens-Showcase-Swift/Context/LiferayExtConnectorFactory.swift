//
//  Liferay62ConnectorFactory.swift
//  LiferayScreensEE
//
//  Created by Javier Gamarra Olmedo on 13/02/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import Foundation
import LiferayScreens

@objc(LiferayExtConnectorFactory)
open class LiferayExtConnectorFactory: Liferay62ConnectorFactory {

	override open func createAssetLoadByClassPKConnector(_ className: String, classPK: Int64) -> AssetLoadByClassPKLiferayConnector? {
		return Liferay62AssetLoadByClassPKConnector(className: className, classPK: classPK)
	}

	override open func createAssetLoadByEntryIdConnector(_ entryId: Int64) -> AssetLoadByEntryIdLiferayConnector? {
		return Liferay62AssetLoadByEntryIdConnector(entryId: entryId)
	}

	override open func createRatingLoadByEntryIdConnector(
			entryId: Int64,
			ratingsGroupCount: Int32) -> RatingLoadByEntryIdLiferayConnector? {
		return Liferay62RatingLoadByEntryIdConnector(entryId: entryId, ratingsGroupCount: ratingsGroupCount)
	}

	override open func createRatingLoadByClassPKConnector(
			_ classPK: Int64,
			className: String,
			ratingsGroupCount: Int32) -> RatingLoadByClassPKLiferayConnector? {
		return Liferay62RatingLoadByClassPKConnector(classPK: classPK, className: className, ratingsGroupCount: ratingsGroupCount)
	}

	override open func createRatingUpdateConnector(
			classPK: Int64,
			className: String,
			score: Double,
			ratingsGroupCount: Int32) -> RatingUpdateLiferayConnector? {
		return Liferay62RatingUpdateConnector(classPK:classPK, className: className, score: score, ratingsGroupCount: ratingsGroupCount)
	}
	
	override open func createRatingDeleteConnector(
			classPK: Int64,
			className: String,
			ratingsGroupCount: Int32) -> RatingDeleteLiferayConnector? {
		return Liferay62RatingDeleteConnector(classPK: classPK, className: className, ratingsGroupCount: ratingsGroupCount)
	}

	override open func createImageGalleryDeleteConnector(_ imageEntryId: Int64) -> ImageGalleryDeleteConnector? {
		return Liferay62ImageGalleryDeleteConnector(imageEntryId: imageEntryId)
	}

	override open func createImageGalleryPageConnector(startRow: Int,
			endRow:Int,
			computeRowCount:Bool,
			repositoryId: Int64,
			folderId: Int64,
			mimeTypes: [String]) -> ImageGalleryPageLiferayConnector {
		return Liferay70ImageGalleryPageLiferayConnector(startRow: startRow,
			endRow:endRow,
			computeRowCount:computeRowCount,
			repositoryId: repositoryId,
			folderId: folderId,
			mimeTypes: mimeTypes
		)
	}

	override open func createImageGalleryUploadConnector(repositoryId: Int64,
			folderId: Int64,
			sourceFileName: String,
			mimeType: String,
			title: String,
			descrip: String,
			changeLog: String,
			image: UIImage,
			onUploadBytes: ImageGalleryUploadConnector.OnProgress?) -> ImageGalleryUploadConnector {

		return Liferay62ImageGalleryUploadConnector(repositoryId: repositoryId, folderId: folderId,
			sourceFileName: sourceFileName,
			mimeType: mimeType,
			title: title,
			descrip: descrip,
			changeLog: changeLog,
			image: image,
			onUploadBytes: onUploadBytes)
	}

	override open func createCommentListPageConnector(
			className: String,
			classPK: Int64,
			startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> CommentListPageLiferayConnector? {
		return Liferay62CommentListPageConnector(className: className, classPK: classPK, startRow: startRow, endRow: endRow, computeRowCount: computeRowCount)
	}

	override open func createCommentAddConnector(
			className: String,
			classPK: Int64,
			body: String) -> CommentAddLiferayConnector? {
		return Liferay62CommentAddConnector(className: className, classPK: classPK, body: body)
	}

	override open func createCommentLoadConnector(
			commentId: Int64) -> CommentLoadLiferayConnector? {
		return Liferay62CommentLoadConnector(commentId: commentId)
	}

	override open func createCommentDeleteConnector(commentId: Int64) -> CommentDeleteLiferayConnector? {
		return Liferay62CommentDeleteConnector(commentId: commentId)
	}

	override open func createCommentUpdateConnector(
			commentId: Int64,
			body: String) -> CommentUpdateLiferayConnector? {
		return Liferay62CommentUpdateConnector(commentId: commentId, body: body)
	}

}
