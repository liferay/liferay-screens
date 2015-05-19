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
#import "SignUpScreenletViewController.h"

@interface SignUpScreenletViewController ()

@property (nonatomic, assign) IBOutlet SignUpScreenlet* screenlet;

@end


@implementation SignUpScreenletViewController

- (IBAction)loginValueChangedAction:(UISwitch *)sender {
	self.screenlet.autoLogin = sender.on;
}

- (IBAction)credentialsValueChangedAction:(UISwitch *)sender {
	self.screenlet.saveCredentials = sender.on;
}

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;
	self.screenlet.autoLoginDelegate = self;
}

- (void)screenlet:(SignUpScreenlet *)screenlet
		onSignUpResponseUserAttributes:(NSDictionary *)attributes {
	NSLog(@"DELEGATE onSignUpResponse -> %@", attributes);
}

- (void)screenlet:(SignUpScreenlet *)screenlet
		onSignUpError:(NSError *)error {
	NSLog(@"DELEGATE onSignUpError -> %@", error);
}

- (void)screenlet:(BaseScreenlet *)screenlet onLoginResponseUserAttributes:(NSDictionary *)attributes {
	NSLog(@"DELEGATE onLoginResponse (autologin delegate) -> %@", attributes);
}

- (void)screenlet:(BaseScreenlet *)screenlet onLoginError:(NSError *)error {
	NSLog(@"DELEGATE onLoginError (autologin delegate) -> %@", error);
}

- (void)onScreenletCredentialsSaved:(BaseScreenlet *)screenlet {
	NSLog(@"DELEGATE onCredentialsSaved (autologin delegate)");
}

- (void)onScreenletCredentialsLoaded:(BaseScreenlet *)screenlet {
	NSLog(@"DELEGATE onCredentialsLoaded (autologin delegate)");
}

@end
