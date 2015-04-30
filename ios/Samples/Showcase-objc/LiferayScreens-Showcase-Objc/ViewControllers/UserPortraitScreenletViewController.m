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
#import "UserPortraitScreenletViewController.h"
#import "UIImage+Grayscale.h"

@interface UserPortraitScreenletViewController ()

@property (weak, nonatomic) IBOutlet UITextField *userIdField;
@property (weak, nonatomic) IBOutlet UserPortraitScreenlet *screenlet;
@property (weak, nonatomic) IBOutlet UserPortraitScreenlet *screenletWithDelegate;

@end


@implementation UserPortraitScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];

	if ([SessionContext hasSession]) {
		self.userIdField.text = [[SessionContext userAttribute:@"userId"] description];
	}

	self.screenletWithDelegate.delegate = self;
}

- (IBAction)loadPortrait:(id)sender {
	[self.screenlet loadWithUserId:[self.userIdField.text longLongValue]];
	[self.screenletWithDelegate loadWithUserId:[self.userIdField.text longLongValue]];
}

- (UIImage *)onUserPortraitResponse:(UIImage *)image {
	NSLog(@"DELEGATE onUserPortraitResponse -> (%d x %d)", (int)image.size.width, (int)image.size.height);

	UIImage *converted = [image convertToGrayscale];
	return converted ? converted : image;
}

- (void)onUserPortraitError:(NSError *)error {
	NSLog(@"DELEGATE onUserPortraitError -> %@", error);
}


@end
