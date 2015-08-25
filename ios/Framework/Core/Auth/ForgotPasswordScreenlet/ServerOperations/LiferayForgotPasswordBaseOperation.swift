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


public class LiferayForgotPasswordBaseOperation: ServerOperation {

	public var companyId: Int64 = 0

	public var resultPasswordSent: Bool?

	internal let viewModel: ForgotPasswordViewModel

	private let anonymousUsername: String
	private let anonymousPassword: String


	public init(viewModel: ForgotPasswordViewModel, anonymousUsername: String, anonymousPassword: String) {
		self.viewModel = viewModel
		self.anonymousUsername = anonymousUsername
		self.anonymousPassword = anonymousPassword

		super.init()
	}


	//MARK ServerOperation

	override func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if viewModel.userName == nil {
				return ValidationError(message: LocalizedString("forgotpassword-screenlet", "validation", self))
			}
		}

		return error
	}

	override func doRun(#session: LRSession) {
		var outError: NSError?

		let result = sendForgotPasswordRequest(
				service: LRScreensuserService_v62(session: session),
				error: &outError)

		if outError != nil {
			lastError = outError!
			resultPasswordSent = nil
		}
		else if result != nil {
			lastError = nil
			resultPasswordSent = result
		}
		else {
			lastError = NSError.errorWithCause(.InvalidServerResponse, userInfo: nil)
			resultPasswordSent = nil
		}
	}

	override internal func createSession() -> LRSession? {
		return SessionContext.createAnonymousBasicSession(anonymousUsername, anonymousPassword)
	}

	//MARK: Template Methods
	
	internal func sendForgotPasswordRequest(
			#service: LRScreensuserService_v62,
			error: NSErrorPointer)
			-> Bool? {

		assertionFailure("sendForgotPasswordRequest must be overriden")

		return nil
	}

}
