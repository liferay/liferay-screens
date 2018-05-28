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
#import "LiferayLogger.h"
#import "CommentAddScreenletViewController.h"

@interface CommentAddScreenletViewController () <CommentAddScreenletDelegate>

@property (weak, nonatomic) IBOutlet CommentAddScreenlet *screenlet;

@end

@implementation CommentAddScreenletViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;
	self.screenlet.className = [LiferayServerContext stringPropertyForKey:@"commentScreenletClassName"];
	self.screenlet.classPK = [LiferayServerContext longPropertyForKey:@"commentScreenletClassPK"];
}

#pragma mark CommentAddScreenleteDelegate

- (void)screenlet:(CommentAddScreenlet *)screenlet onCommentAdded:(Comment *)comment {
	LiferayLog(comment.attributes);
}

- (void)screenlet:(CommentAddScreenlet *)screenlet onAddCommentError:(NSError *)error {
	LiferayLog(error.debugDescription);
}

@end
