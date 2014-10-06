//
//  SignUpViewController.swift
//  Liferay-iOS-Screenlets-Swift-Sample
//
//  Created by jmWork on 18/07/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import Foundation


public class SignUpViewController: UIViewController, SignUpScreenletDelegate {

	@IBOutlet var screenlet: SignUpScreenlet?

	override public func viewDidLoad() {
		screenlet!.delegate = self
		screenlet!.becomeFirstResponder()
	}

	public func onSignUpError(error: NSError)  {
		println("Error signing up -> " + error.description)
	}

	public func onSignUpResponse(attributes: NSDictionary)  {
		println("Signed up -> " + attributes.description)
	}

}
