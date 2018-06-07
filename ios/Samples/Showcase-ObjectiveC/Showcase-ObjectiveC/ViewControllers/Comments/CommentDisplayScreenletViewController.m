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
#import "CommentDisplayScreenletViewController.h"

@interface CommentDisplayScreenletViewController () <CommentDisplayScreenletDelegate>

@property (weak, nonatomic) IBOutlet CommentDisplayScreenlet *screenlet;
@property (weak, nonatomic) IBOutlet UITextField *commentIdText;

@end

@implementation CommentDisplayScreenletViewController

- (void)viewDidLoad {
	[super viewDidLoad];
	self.screenlet.delegate = self;
	
	self.commentIdText.text = [NSString stringWithFormat:@"%lld",
							   [LiferayServerContext longPropertyForKey:@"commentId"]];
}

- (IBAction)loadComment:(id)sender {
	if (self.commentIdText.text) {
		self.screenlet.commentId = self.commentIdText.text.longLongValue;
		[self.screenlet load];
	}
}

#pragma mark CommentDisplayScreenletDelegate

- (void)screenlet:(CommentDisplayScreenlet *)screenlet onCommentLoaded:(Comment *)comment {
	LiferayLog(comment.attributes);
	self.screenlet.hidden = false;
}

- (void)screenlet:(CommentDisplayScreenlet *)screenlet onLoadCommentError:(NSError *)error {
	LiferayLog(error.debugDescription);
	self.screenlet.hidden = true;
}

- (void)screenlet:(CommentDisplayScreenlet *)screenlet onCommentDeleted:(Comment *)comment {
	LiferayLog(comment.attributes);
	self.screenlet.hidden = true;
}

- (void)screenlet:(CommentDisplayScreenlet *)screenlet onCommentUpdated:(Comment *)comment {
	LiferayLog(comment.attributes);
}

- (void)screenlet:(CommentDisplayScreenlet *)screenlet onUpdateComment:(Comment *)comment onError:(NSError *)error {
	LiferayLog(comment.attributes, error.debugDescription);
}

@end
