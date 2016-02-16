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

	func createGetUserByEmailLiferayConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailLiferayConnector

	func createGetUserByScreenNameLiferayConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameLiferayConnector

	func createGetUserByUserIdLiferayConnector(userId userId: Int64) -> GetUserByUserIdLiferayConnector

	func createLoginByEmailConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailLiferayConnector

	func createLoginByScreenNameConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameLiferayConnector

	func createLoginByUserIdConnector(userId userId: Int64) -> GetUserByUserIdLiferayConnector

	func createForgotPasswordByEmailConnector(
		viewModel viewModel: ForgotPasswordViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> ForgotPasswordBaseLiferayConnector

	func createForgotPasswordByScreenNameConnector(
		viewModel viewModel: ForgotPasswordViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> ForgotPasswordBaseLiferayConnector

	func createForgotPasswordByUserIdConnector(
		viewModel viewModel: ForgotPasswordViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> ForgotPasswordBaseLiferayConnector

	func createSignUpConnector(
		viewModel viewModel: SignUpViewModel,
		anonymousUsername: String,
		anonymousPassword: String) -> SignUpLiferayConnector

	func createUpdateCurrentUserConnector(
		viewModel viewModel: SignUpViewModel) -> UpdateCurrentUserLiferayConnector

	func createUploadUserPortraitConnector(
		userId userId: Int64,
		image: UIImage) -> UploadUserPortraitLiferayConnector

	func createAssetListPageConnector(
		startRow startRow: Int,
		endRow: Int,
		computeRowCount: Bool) -> AssetListPageLiferayConnector

	func createDDLListPageConnector(
		viewModel viewModel: DDLListViewModel,
		startRow: Int,
		endRow: Int,
		computeRowCount: Bool) -> DDLListPageLiferayConnector

	func createWebContentLoadFromArticleId(articleId articleId: String) -> WebContentLoadFromArticleIdLiferayConnector

	func createWebContentLoadFromClassPK(classPK classPK: Int64) -> WebContentLoadFromClassPKLiferayConnector

	func createDDLFormLoadConnector(structureId: Int64) -> DDLFormLoadLiferayConnector

	func createDDLFormRecordLoadConnector(recordId: Int64) -> DDLFormRecordLoadLiferayConnector

	func createDDLFormSubmitConnector(
		values values: [String:AnyObject],
		viewModel: DDLFormViewModel?) -> DDLFormSubmitLiferayConnector

	func createDDLFormUploadConnector(
		document document: DDLFieldDocument,
		filePrefix: String,
		repositoryId: Int64,
		folderId: Int64) -> DDLFormUploadLiferayConnector

}


@objc(Liferay62ConnectorFactory)
public class Liferay62ConnectorFactory: NSObject, LiferayConnectorFactory {

	public func createGetUserByEmailLiferayConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailLiferayConnector {
		return GetUserByEmailLiferay62Connector(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createGetUserByScreenNameLiferayConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameLiferayConnector {
		return GetUserByScreenNameLiferay62Connector(
			companyId: companyId,
			screenName: screenName)
	}

	public func createGetUserByUserIdLiferayConnector(userId userId: Int64) -> GetUserByUserIdLiferayConnector {
		return GetUserByUserIdLiferay62Connector(userId: userId)
	}

	public func createLoginByEmailConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailLiferayConnector {
		return LoginByEmailLiferay62Connector(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createLoginByScreenNameConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameLiferayConnector {
		return LoginByScreenNameLiferay62Connector(
			companyId: companyId,
			screenName: screenName)
	}

	public func createLoginByUserIdConnector(userId userId: Int64) -> GetUserByUserIdLiferayConnector {
		return LoginByUserIdLiferay62Connector(userId: userId)
	}

	public func createForgotPasswordByEmailConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> ForgotPasswordBaseLiferayConnector {
		return ForgotPasswordEmailLiferay62Connector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByScreenNameConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> ForgotPasswordBaseLiferayConnector {
		return ForgotPasswordScreenNameLiferay62Connector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByUserIdConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> ForgotPasswordBaseLiferayConnector {
		return ForgotPasswordUserIdLiferay62Connector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createSignUpConnector(
			viewModel viewModel: SignUpViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> SignUpLiferayConnector {
		return Liferay62SignUpConnector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createUpdateCurrentUserConnector(
		viewModel viewModel: SignUpViewModel) -> UpdateCurrentUserLiferayConnector {
		return Liferay62UpdateCurrentUserConnector(viewModel: viewModel)
	}

	public func createUploadUserPortraitConnector(
			userId userId: Int64,
			image: UIImage) -> UploadUserPortraitLiferayConnector {
		return Liferay62UploadUserPortraitConnector(
			userId: userId,
			image: image)
	}

	public func createAssetListPageConnector(
			startRow startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> AssetListPageLiferayConnector {
		return Liferay62AssetListPageConnector(
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createDDLListPageConnector(
			viewModel viewModel: DDLListViewModel,
			startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> DDLListPageLiferayConnector {
		return Liferay62DDLListPageConnector(
			viewModel: viewModel,
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createWebContentLoadFromArticleId(articleId articleId: String) -> WebContentLoadFromArticleIdLiferayConnector {
		return Liferay62WebContentLoadFromArticleIdConnector(articleId: articleId)
	}

	public func createWebContentLoadFromClassPK(classPK classPK: Int64) -> WebContentLoadFromClassPKLiferayConnector {
		return Liferay62WebContentLoadFromClassPKConnector(classPK: classPK)
	}

	public func createDDLFormLoadConnector(structureId: Int64) -> DDLFormLoadLiferayConnector {
		return Liferay62DDLFormLoadConnector(structureId: structureId)
	}

	public func createDDLFormRecordLoadConnector(recordId: Int64) -> DDLFormRecordLoadLiferayConnector {
		return Liferay62DDLFormRecordLoadConnector(recordId: recordId)
	}

	public func createDDLFormSubmitConnector(
			values values: [String:AnyObject],
			viewModel: DDLFormViewModel?) -> DDLFormSubmitLiferayConnector {
		return Liferay62DDLFormSubmitConnector(
			values: values,
			viewModel: viewModel)
	}

	public func createDDLFormUploadConnector(
			document document: DDLFieldDocument,
			filePrefix: String,
			repositoryId: Int64,
			folderId: Int64) -> DDLFormUploadLiferayConnector {
		return Liferay62DDLFormUploadConnector(
			document: document,
			filePrefix: filePrefix,
			repositoryId: repositoryId,
			folderId: folderId)
	}

}


@objc(Liferay70ConnectorFactory)
public class Liferay70ConnectorFactory: NSObject, LiferayConnectorFactory {

	public func createGetUserByEmailLiferayConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailLiferayConnector {
		return GetUserByEmailLiferay70Connector(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createGetUserByScreenNameLiferayConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameLiferayConnector {
		return GetUserByScreenNameLiferay70Connector(
			companyId: companyId,
			screenName: screenName)
	}

	public func createGetUserByUserIdLiferayConnector(userId userId: Int64) -> GetUserByUserIdLiferayConnector {
		return GetUserByUserIdLiferay70Connector(userId: userId)
	}

	public func createLoginByEmailConnector(companyId companyId: Int64, emailAddress: String) -> GetUserByEmailLiferayConnector {
		return LoginByEmailLiferay70Connector(
			companyId: companyId,
			emailAddress: emailAddress)
	}

	public func createLoginByScreenNameConnector(companyId companyId: Int64, screenName: String) -> GetUserByScreenNameLiferayConnector {
		return LoginByScreenNameLiferay70Connector(
			companyId: companyId,
			screenName: screenName)
	}

	public func createLoginByUserIdConnector(userId userId: Int64) -> GetUserByUserIdLiferayConnector {
		return LoginByUserIdLiferay70Connector(userId: userId)
	}

	public func createForgotPasswordByEmailConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> ForgotPasswordBaseLiferayConnector {
		return ForgotPasswordEmailLiferay70Connector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByScreenNameConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> ForgotPasswordBaseLiferayConnector {
		return ForgotPasswordScreenNameLiferay70Connector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createForgotPasswordByUserIdConnector(
			viewModel viewModel: ForgotPasswordViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> ForgotPasswordBaseLiferayConnector {
		return ForgotPasswordUserIdLiferay70Connector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createSignUpConnector(
			viewModel viewModel: SignUpViewModel,
			anonymousUsername: String,
			anonymousPassword: String) -> SignUpLiferayConnector {
		return Liferay70SignUpConnector(
			viewModel: viewModel,
			anonymousUsername: anonymousUsername,
			anonymousPassword: anonymousPassword)
	}

	public func createUpdateCurrentUserConnector(
			viewModel viewModel: SignUpViewModel) -> UpdateCurrentUserLiferayConnector {
		return Liferay70UpdateCurrentUserConnector(viewModel: viewModel)
	}

	public func createUploadUserPortraitConnector(
			userId userId: Int64,
			image: UIImage) -> UploadUserPortraitLiferayConnector {
		return Liferay70UploadUserPortraitConnector(
			userId: userId,
			image: image)
	}

	public func createAssetListPageConnector(
			startRow startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> AssetListPageLiferayConnector {
		return Liferay70AssetListPageConnector(
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createDDLListPageConnector(
			viewModel viewModel: DDLListViewModel,
			startRow: Int,
			endRow: Int,
			computeRowCount: Bool) -> DDLListPageLiferayConnector {
		return Liferay70DDLListPageConnector(
			viewModel: viewModel,
			startRow: startRow,
			endRow: endRow,
			computeRowCount: computeRowCount)
	}

	public func createWebContentLoadFromArticleId(articleId articleId: String) -> WebContentLoadFromArticleIdLiferayConnector {
		return Liferay70WebContentLoadFromArticleIdConnector(articleId: articleId)
	}

	public func createWebContentLoadFromClassPK(classPK classPK: Int64) -> WebContentLoadFromClassPKLiferayConnector {
		return Liferay70WebContentLoadFromClassPKConnector(classPK: classPK)
	}

	public func createDDLFormLoadConnector(structureId: Int64) -> DDLFormLoadLiferayConnector {
		return Liferay70DDLFormLoadConnector(structureId: structureId)
	}

	public func createDDLFormRecordLoadConnector(recordId: Int64) -> DDLFormRecordLoadLiferayConnector {
		return Liferay70DDLFormRecordLoadConnector(recordId: recordId)
	}

	public func createDDLFormSubmitConnector(
			values values: [String:AnyObject],
			viewModel: DDLFormViewModel?) -> DDLFormSubmitLiferayConnector {
		return Liferay70DDLFormSubmitConnector(
			values: values,
			viewModel: viewModel)
	}

	public func createDDLFormUploadConnector(
			document document: DDLFieldDocument,
			filePrefix: String,
			repositoryId: Int64,
			folderId: Int64) -> DDLFormUploadLiferayConnector {
		return Liferay70DDLFormUploadConnector(
			document: document,
			filePrefix: filePrefix,
			repositoryId: repositoryId,
			folderId: folderId)
	}

}