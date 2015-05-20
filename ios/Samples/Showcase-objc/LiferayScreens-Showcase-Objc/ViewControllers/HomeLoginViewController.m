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
#import "HomeLoginViewController.h"

@interface HomeLoginViewController ()

@property (nonatomic, assign) IBOutlet UIView* loggedView;
@property (nonatomic, assign) IBOutlet UILabel* loggedLabel;
@property (nonatomic, assign) IBOutlet LoginScreenlet* screenlet;

@end


@implementation HomeLoginViewController

- (IBAction)credentialsValueChangedAction:(UISwitch *)sender {
	self.screenlet.saveCredentials = sender.on;
}

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;

	self.screenlet.viewModel.userName = @"test@liferay.com";
	self.screenlet.viewModel.password = @"test";

	[self showLoggedWithAnimation:NO];
}


- (void)showLoggedWithAnimation:(BOOL)animated {
	if ([SessionContext hasSession]) {
		self.loggedLabel.text = [SessionContext currentUserName];
	}

	[UIView animateWithDuration:animated ? 0.5 : 0.0 animations:^{
		self.loggedView.alpha = [SessionContext hasSession] ? 1.0 : 0.0;
		self.screenlet.alpha = [SessionContext hasSession] ? 0.0 : 1.0;
	}];
}

- (IBAction)onSignOutAction {
	[SessionContext clearSession];
	[SessionContext removeStoredSession];
	[self showLoggedWithAnimation:YES];
}

- (void)screenlet:(BaseScreenlet *)screenlet
		onLoginResponseUserAttributes:(NSDictionary *)attributes {
	NSLog(@"DELEGATE onLoginResponse -> %@", attributes);

	[self showLoggedWithAnimation:YES];
}

- (void)screenlet:(BaseScreenlet *)screenlet
		onLoginError:(NSError *)error {
	NSLog(@"DELEGATE onLoginError -> %@", error);
}

- (void)onScreenletCredentialsSaved:(BaseScreenlet *)screenlet {
	NSLog(@"DELEGATE onCredentialsSaved");
}

- (void)onScreenletCredentialsLoaded:(BaseScreenlet *)screenlet {
	NSLog(@"DELEGATE onCredentialsLoaded");
}

@end
