//
//  SignUpViewController.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 18/07/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import Foundation


public class SignUpViewController: UIViewController, SignUpWidgetDelegate {

	@IBOutlet var widget: SignUpWidget?

	override public func viewDidLoad() {
		widget!.delegate = self
		widget!.becomeFirstResponder()
	}

	public func onSignUpError(error: NSError)  {
		println("Error signing up -> " + error.description)
	}

	public func onSignUpResponse(attributes: NSDictionary)  {
		println("Signed up -> " + attributes.description)
	}

}
