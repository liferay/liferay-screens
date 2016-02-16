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


@objc(LiferayConnectorFactory)
public protocol LiferayConnectorFactory {

	func createGetUserByEmailConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailConnector

	func createGetUserByScreenNameConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameConnector

	func createGetUserByUserIdConnector(userId userId: Int64) -> GetUserByUserIdConnector

	func createLoginByEmailConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailConnector

	func createLoginByScreenNameConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameConnector

	func createLoginByUserIdConnector(userId userId: Int64) -> GetUserByUserIdConnector

	func createForgotPasswordByEmailConnector(
		viewModel viewModel: ForgotPasswordViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> LiferayForgotPasswordBaseConnector

	func createForgotPasswordByScreenNameConnector(
		viewModel viewModel: ForgotPasswordViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> LiferayForgotPasswordBaseConnector

	func createForgotPasswordByUserIdConnector(
		viewModel viewModel: ForgotPasswordViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> LiferayForgotPasswordBaseConnector

	func createSignUpConnector(
		viewModel viewModel: SignUpViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> LiferaySignUpConnector

	func createUpdateCurrentUserConnector(
		viewModel viewModel: SignUpViewModel) -> LiferayUpdateCurrentUserConnector

	func createUploadUserPortraitConnector(
		userId userId: Int64,
		image: UIImage) -> LiferayUploadUserPortraitConnector

	func createAssetListPageConnector(
		startRow startRow: Int,
		endRow: Int,
		computeRowCount: Bool) -> LiferayAssetListPageConnector

	func createDDLListPageConnector(
		viewModel viewModel: DDLListViewModel,
		startRow: Int,
		endRow: Int,
		computeRowCount: Bool) -> LiferayDDLListPageConnector

	func createWebContentLoadFromArticleId(articleId articleId: String) -> LiferayWebContentLoadFromArticleIdConnector

	func createWebContentLoadFromClassPK(classPK classPK: Int64) -> LiferayWebContentLoadFromClassPKConnector

	func createDDLFormLoadConnector(structureId: Int64) -> LiferayDDLFormLoadConnector

	func createDDLFormRecordLoadConnector(recordId: Int64) -> LiferayDDLFormRecordLoadConnector

	func createDDLFormSubmitConnector(
		values values: [String:AnyObject],
		viewModel: DDLFormViewModel?) -> LiferayDDLFormSubmitConnector

	func createDDLFormUploadConnector(
		document document: DDLFieldDocument,
		filePrefix: String,
		repositoryId: Int64,
		folderId: Int64) -> LiferayDDLFormUploadConnector

}


@objc(Liferay62ConnectorFactory)
public class Liferay62ConnectorFactory: NSObject, LiferayConnectorFactory {

	public func createGetUserByEmailConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailConnector {
		return Liferay62GetUserByEmailConnector(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createGetUserByScreenNameConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameConnector {
		return Liferay62GetUserByScreenNameConnector(
			companyId: companyId,
			screenName: screenName)
	}

	public func createGetUserByUserIdConnector(userId userId: Int64) -> GetUserByUserIdConnector {
		return Liferay62GetUserByUserIdConnector(userId: userId)
	}

	public func createLoginByEmailConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailConnector {
		return Liferay62LoginByEmailConnector(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createLoginByScreenNameConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameConnector {
		return Liferay62LoginByScreenNameConnector(
			companyId: companyId,
			screenName: screenName)
	}

	public func createLoginByUserIdConnector(userId userId: Int64) -> GetUserByUserIdConnector {
		return Liferay62LoginByUserIdConnector(userId: userId)
	}

	public func createForgotPasswordByEmailConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseConnector {
		return Liferay62ForgotPasswordEmailConnector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByScreenNameConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseConnector {
		return Liferay62ForgotPasswordScreenNameConnector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByUserIdConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseConnector {
		return Liferay62ForgotPasswordUserIdConnector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createSignUpConnector(
			viewModel viewModel: SignUpViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferaySignUpConnector {
		return Liferay62SignUpConnector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createUpdateCurrentUserConnector(
		viewModel viewModel: SignUpViewModel) -> LiferayUpdateCurrentUserConnector {
		return Liferay62UpdateCurrentUserConnector(viewModel: viewModel)
	}

	public func createUploadUserPortraitConnector(
			userId userId: Int64,
			image: UIImage) -> LiferayUploadUserPortraitConnector {
		return Liferay62UploadUserPortraitConnector(
			userId: userId,
			image: image)
	}

	public func createAssetListPageConnector(
			startRow startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> LiferayAssetListPageConnector {
		return Liferay62AssetListPageConnector(
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createDDLListPageConnector(
			viewModel viewModel: DDLListViewModel,
			startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> LiferayDDLListPageConnector {
		return Liferay62DDLListPageConnector(
			viewModel: viewModel,
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createWebContentLoadFromArticleId(articleId articleId: String) -> LiferayWebContentLoadFromArticleIdConnector {
		return Liferay62WebContentLoadFromArticleIdConnector(articleId: articleId)
	}

	public func createWebContentLoadFromClassPK(classPK classPK: Int64) -> LiferayWebContentLoadFromClassPKConnector {
		return Liferay62WebContentLoadFromClassPKConnector(classPK: classPK)
	}

	public func createDDLFormLoadConnector(structureId: Int64) -> LiferayDDLFormLoadConnector {
		return Liferay62DDLFormLoadConnector(structureId: structureId)
	}

	public func createDDLFormRecordLoadConnector(recordId: Int64) -> LiferayDDLFormRecordLoadConnector {
		return Liferay62DDLFormRecordLoadConnector(recordId: recordId)
	}

	public func createDDLFormSubmitConnector(
			values values: [String:AnyObject],
			viewModel: DDLFormViewModel?) -> LiferayDDLFormSubmitConnector {
		return Liferay62DDLFormSubmitConnector(
			values: values,
			viewModel: viewModel)
	}

	public func createDDLFormUploadConnector(
			document document: DDLFieldDocument,
			filePrefix: String,
			repositoryId: Int64,
			folderId: Int64) -> LiferayDDLFormUploadConnector {
		return Liferay62DDLFormUploadConnector(
			document: document,
			filePrefix: filePrefix,
			repositoryId: repositoryId,
			folderId: folderId)
	}

}


@objc(Liferay70ConnectorFactory)
public class Liferay70ConnectorFactory: NSObject, LiferayConnectorFactory {

	public func createGetUserByEmailConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailConnector {
		return Liferay70GetUserByEmailConnector(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createGetUserByScreenNameConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameConnector {
		return Liferay70GetUserByScreenNameConnector(
			companyId: companyId,
			screenName: screenName)
	}

	public func createGetUserByUserIdConnector(userId userId: Int64) -> GetUserByUserIdConnector {
		return Liferay70GetUserByUserIdConnector(userId: userId)
	}

	public func createLoginByEmailConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailConnector {
		return Liferay70LoginByEmailConnector(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createLoginByScreenNameConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameConnector {
		return Liferay70LoginByScreenNameConnector(
			companyId: companyId,
			screenName: screenName)
	}

	public func createLoginByUserIdConnector(userId userId: Int64) -> GetUserByUserIdConnector {
		return Liferay70LoginByUserIdConnector(userId: userId)
	}

	public func createForgotPasswordByEmailConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseConnector {
		return Liferay70ForgotPasswordEmailConnector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByScreenNameConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseConnector {
		return Liferay70ForgotPasswordScreenNameConnector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByUserIdConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferayForgotPasswordBaseConnector {
		return Liferay70ForgotPasswordUserIdConnector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createSignUpConnector(
			viewModel viewModel: SignUpViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> LiferaySignUpConnector {
		return Liferay70SignUpConnector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createUpdateCurrentUserConnector(
			viewModel viewModel: SignUpViewModel) -> LiferayUpdateCurrentUserConnector {
		return Liferay70UpdateCurrentUserConnector(viewModel: viewModel)
	}

	public func createUploadUserPortraitConnector(
			userId userId: Int64,
			image: UIImage) -> LiferayUploadUserPortraitConnector {
		return Liferay70UploadUserPortraitConnector(
			userId: userId,
			image: image)
	}

	public func createAssetListPageConnector(
			startRow startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> LiferayAssetListPageConnector {
		return Liferay70AssetListPageConnector(
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createDDLListPageConnector(
			viewModel viewModel: DDLListViewModel,
			startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> LiferayDDLListPageConnector {
		return Liferay70DDLListPageConnector(
			viewModel: viewModel,
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createWebContentLoadFromArticleId(articleId articleId: String) -> LiferayWebContentLoadFromArticleIdConnector {
		return Liferay70WebContentLoadFromArticleIdConnector(articleId: articleId)
	}

	public func createWebContentLoadFromClassPK(classPK classPK: Int64) -> LiferayWebContentLoadFromClassPKConnector {
		return Liferay70WebContentLoadFromClassPKConnector(classPK: classPK)
	}

	public func createDDLFormLoadConnector(structureId: Int64) -> LiferayDDLFormLoadConnector {
		return Liferay70DDLFormLoadConnector(structureId: structureId)
	}

	public func createDDLFormRecordLoadConnector(recordId: Int64) -> LiferayDDLFormRecordLoadConnector {
		return Liferay70DDLFormRecordLoadConnector(recordId: recordId)
	}

	public func createDDLFormSubmitConnector(
			values values: [String:AnyObject],
			viewModel: DDLFormViewModel?) -> LiferayDDLFormSubmitConnector {
		return Liferay70DDLFormSubmitConnector(
			values: values,
			viewModel: viewModel)
	}

	public func createDDLFormUploadConnector(
			document document: DDLFieldDocument,
			filePrefix: String,
			repositoryId: Int64,
			folderId: Int64) -> LiferayDDLFormUploadConnector {
		return Liferay70DDLFormUploadConnector(
			document: document,
			filePrefix: filePrefix,
			repositoryId: repositoryId,
			folderId: folderId)
	}

}