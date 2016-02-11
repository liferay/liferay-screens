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


@objc(LiferayOperationFactory)
public protocol LiferayOperationFactory {

	func createGetUserByEmailOperation(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailOperation

	func createGetUserByScreenNameOperation(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameOperation

	func createGetUserByUserIdOperation(userId userId: Int64) -> GetUserByUserIdOperation

	func createLoginByEmailOperation(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailOperation

	func createLoginByScreenNameOperation(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameOperation

	func createLoginByUserIdOperation(userId userId: Int64) -> GetUserByUserIdOperation

	func createForgotPasswordByEmailOperation(
		viewModel viewModel: ForgotPasswordViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> LiferayForgotPasswordBaseOperation

	func createForgotPasswordByScreenNameOperation(
		viewModel viewModel: ForgotPasswordViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> LiferayForgotPasswordBaseOperation

	func createForgotPasswordByUserIdOperation(
		viewModel viewModel: ForgotPasswordViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> LiferayForgotPasswordBaseOperation

	func createSignUpOperation(
		viewModel viewModel: SignUpViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> LiferaySignUpOperation

	func createUpdateCurrentUserOperation(
		viewModel viewModel: SignUpViewModel) -> LiferayUpdateCurrentUserOperation

	func createUploadUserPortraitOperation(
		userId userId: Int64,
		image: UIImage) -> LiferayUploadUserPortraitOperation

	func createAssetListPageOperation(
		startRow startRow: Int,
		endRow: Int,
		computeRowCount: Bool) -> LiferayAssetListPageOperation

	func createDDLListPageOperation(
		viewModel viewModel: DDLListViewModel,
		startRow: Int,
		endRow: Int,
		computeRowCount: Bool) -> LiferayDDLListPageOperation

	func createWebContentLoadFromArticleId(articleId articleId: String) -> LiferayWebContentLoadFromArticleIdOperation

	func createWebContentLoadFromClassPK(classPK classPK: Int64) -> LiferayWebContentLoadFromClassPKOperation

	func createDDLFormLoadOperation(structureId: Int64) -> LiferayDDLFormLoadOperation

	func createDDLFormRecordLoadOperation(recordId: Int64) -> LiferayDDLFormRecordLoadOperation

}


@objc(Liferay62OperationFactory)
public class Liferay62OperationFactory: NSObject, LiferayOperationFactory {

	public func createGetUserByEmailOperation(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailOperation {
		return Liferay62GetUserByEmailOperation(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createGetUserByScreenNameOperation(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameOperation {
		return Liferay62GetUserByScreenNameOperation(
			companyId: companyId,
			screenName: screenName)
	}

	public func createGetUserByUserIdOperation(userId userId: Int64) -> GetUserByUserIdOperation {
		return Liferay62GetUserByUserIdOperation(userId: userId)
	}

	public func createLoginByEmailOperation(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailOperation {
		return Liferay62LoginByEmailOperation(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createLoginByScreenNameOperation(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameOperation {
		return Liferay62LoginByScreenNameOperation(
			companyId: companyId,
			screenName: screenName)
	}

	public func createLoginByUserIdOperation(userId userId: Int64) -> GetUserByUserIdOperation {
		return Liferay62LoginByUserIdOperation(userId: userId)
	}

	public func createForgotPasswordByEmailOperation(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseOperation {
		return Liferay62ForgotPasswordEmailOperation(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByScreenNameOperation(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseOperation {
		return Liferay62ForgotPasswordScreenNameOperation(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByUserIdOperation(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseOperation {
		return Liferay62ForgotPasswordUserIdOperation(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createSignUpOperation(
			viewModel viewModel: SignUpViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferaySignUpOperation {
		return Liferay62SignUpOperation(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createUpdateCurrentUserOperation(
		viewModel viewModel: SignUpViewModel) -> LiferayUpdateCurrentUserOperation {
		return Liferay62UpdateCurrentUserOperation(viewModel: viewModel)
	}

	public func createUploadUserPortraitOperation(
			userId userId: Int64,
			image: UIImage) -> LiferayUploadUserPortraitOperation {
		return Liferay62UploadUserPortraitOperation(
			userId: userId,
			image: image)
	}

	public func createAssetListPageOperation(
			startRow startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> LiferayAssetListPageOperation {
		return Liferay62AssetListPageOperation(
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createDDLListPageOperation(
			viewModel viewModel: DDLListViewModel,
			startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> LiferayDDLListPageOperation {
		return Liferay62DDLListPageOperation(
			viewModel: viewModel,
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createWebContentLoadFromArticleId(articleId articleId: String) -> LiferayWebContentLoadFromArticleIdOperation {
		return Liferay62WebContentLoadFromArticleIdOperation(articleId: articleId)
	}

	public func createWebContentLoadFromClassPK(classPK classPK: Int64) -> LiferayWebContentLoadFromClassPKOperation {
		return Liferay62WebContentLoadFromClassPKOperation(classPK: classPK)
	}

	public func createDDLFormLoadOperation(structureId: Int64) -> LiferayDDLFormLoadOperation {
		return Liferay62DDLFormLoadOperation(
			structureId: NSNumber(longLong: structureId))
	}

	public func createDDLFormRecordLoadOperation(recordId: Int64) -> LiferayDDLFormRecordLoadOperation {
		return Liferay62DDLFormRecordLoadOperation(
			recordId: recordId)
	}

}


@objc(Liferay70OperationFactory)
public class Liferay70OperationFactory: NSObject, LiferayOperationFactory {

	public func createGetUserByEmailOperation(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailOperation {
		return Liferay70GetUserByEmailOperation(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createGetUserByScreenNameOperation(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameOperation {
		return Liferay70GetUserByScreenNameOperation(
			companyId: companyId,
			screenName: screenName)
	}

	public func createGetUserByUserIdOperation(userId userId: Int64) -> GetUserByUserIdOperation {
		return Liferay70GetUserByUserIdOperation(userId: userId)
	}

	public func createLoginByEmailOperation(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailOperation {
		return Liferay70LoginByEmailOperation(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createLoginByScreenNameOperation(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameOperation {
		return Liferay70LoginByScreenNameOperation(
			companyId: companyId,
			screenName: screenName)
	}

	public func createLoginByUserIdOperation(userId userId: Int64) -> GetUserByUserIdOperation {
		return Liferay70LoginByUserIdOperation(userId: userId)
	}

	public func createForgotPasswordByEmailOperation(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseOperation {
		return Liferay70ForgotPasswordEmailOperation(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByScreenNameOperation(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseOperation {
		return Liferay70ForgotPasswordScreenNameOperation(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByUserIdOperation(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseOperation {
		return Liferay70ForgotPasswordUserIdOperation(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createSignUpOperation(
			viewModel viewModel: SignUpViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferaySignUpOperation {
		return Liferay70SignUpOperation(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createUpdateCurrentUserOperation(
			viewModel viewModel: SignUpViewModel) -> LiferayUpdateCurrentUserOperation {
		return Liferay70UpdateCurrentUserOperation(viewModel: viewModel)
	}

	public func createUploadUserPortraitOperation(
			userId userId: Int64,
			image: UIImage) -> LiferayUploadUserPortraitOperation {
		return Liferay70UploadUserPortraitOperation(
			userId: userId,
			image: image)
	}

	public func createAssetListPageOperation(
			startRow startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> LiferayAssetListPageOperation {
		return Liferay70AssetListPageOperation(
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createDDLListPageOperation(
			viewModel viewModel: DDLListViewModel,
			startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> LiferayDDLListPageOperation {
		return Liferay70DDLListPageOperation(
			viewModel: viewModel,
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createWebContentLoadFromArticleId(articleId articleId: String) -> LiferayWebContentLoadFromArticleIdOperation {
		return Liferay70WebContentLoadFromArticleIdOperation(articleId: articleId)
	}

	public func createWebContentLoadFromClassPK(classPK classPK: Int64) -> LiferayWebContentLoadFromClassPKOperation {
		return Liferay70WebContentLoadFromClassPKOperation(classPK: classPK)
	}

	public func createDDLFormLoadOperation(structureId: Int64) -> LiferayDDLFormLoadOperation {
		return Liferay70DDLFormLoadOperation(
			structureId: NSNumber(longLong: structureId))
	}

	public func createDDLFormRecordLoadOperation(recordId: Int64) -> LiferayDDLFormRecordLoadOperation {
		return Liferay70DDLFormRecordLoadOperation(
			recordId: recordId)
	}

}