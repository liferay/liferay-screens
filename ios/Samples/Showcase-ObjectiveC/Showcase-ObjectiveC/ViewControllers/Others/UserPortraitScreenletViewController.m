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

@import LiferayScreens;
#import "NSString+Utils.h"
#import "UIImage+GrayScale.h"
#import "LiferayLogger.h"
#import "UserPortraitScreenletViewController.h"

@interface UserPortraitScreenletViewController () <UserPortraitScreenletDelegate>

@property (weak, nonatomic) IBOutlet UITextField *userIdField;
@property (weak, nonatomic) IBOutlet UserPortraitScreenlet *screenlet;
@property (weak, nonatomic) IBOutlet UserPortraitScreenlet *screenletWithDelegate;
@property (weak, nonatomic) IBOutlet UserPortraitScreenlet *editableScreenlet;
@property (weak, nonatomic) IBOutlet UIButton *loadButton;

@end

@implementation UserPortraitScreenletViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenletWithDelegate.delegate = self;
	self.editableScreenlet.presentingViewController = self;

	self.userIdField.text = [NSString
							 stringWithFormat:@"%lld",SessionContext.currentContext.user.userId];
}

- (IBAction)loadPortrait:(id)sender {
	if (self.userIdField.text) {
		NSString *userIdFieldText = self.userIdField.text;
		int64_t companyId = LiferayServerContext.companyId;

		if ([userIdFieldText isNumeric]) {
			[self.screenlet loadWithUserId:userIdFieldText.longLongValue];
			[self.screenletWithDelegate loadWithUserId:userIdFieldText.longLongValue];
			[self.editableScreenlet loadWithUserId:userIdFieldText.longLongValue];
		}
		else if ([userIdFieldText containsString:@"@"]) {
			[self.screenlet loadWithCompanyId:companyId emailAddress:userIdFieldText];
			[self.screenletWithDelegate loadWithCompanyId:companyId emailAddress:userIdFieldText];
			[self.editableScreenlet loadWithCompanyId:companyId emailAddress:userIdFieldText];
		}
		else {
			[self.screenlet loadWithCompanyId:companyId screenName:userIdFieldText];
			[self.screenletWithDelegate loadWithCompanyId:companyId screenName:userIdFieldText];
			[self.editableScreenlet loadWithCompanyId:companyId screenName:userIdFieldText];
		}
	}
	else {
		[self.screenlet loadLoggedUserPortrait];
		[self.screenletWithDelegate loadLoggedUserPortrait];
		[self.editableScreenlet loadLoggedUserPortrait];
	}
}

#pragma mark UserPortraiScreenletDelegate

- (UIImage *)screenlet:(UserPortraitScreenlet *)screenlet
		onUserPortraitResponseImage:(UIImage *)image {
	LiferayLog(image);

	return [image grayScaleImage];
}

- (void)screenlet:(UserPortraitScreenlet *)screenlet
		onUserPortraitUploaded:(NSDictionary<NSString *,id> *)attributes {

	LiferayLog(attributes);
}

- (void)screenlet:(UserPortraitScreenlet *)screenlet onUserPortraitUploadError:(NSError *)error {
	LiferayLog(error);
}

- (void)screenlet:(UserPortraitScreenlet *)screenlet onUserPortraitError:(NSError *)error {
	LiferayLog(error);
}

@end
