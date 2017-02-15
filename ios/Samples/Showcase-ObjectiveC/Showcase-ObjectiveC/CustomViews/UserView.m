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

#import "UserView.h"

@interface UserView ()

@property (weak, nonatomic) IBOutlet UIView *contentView;
@property (weak, nonatomic) IBOutlet UserPortraitScreenlet *userPortraitScreenlet;
@property (weak, nonatomic) IBOutlet UILabel *usernameLabel;
@property (weak, nonatomic) IBOutlet UILabel *jobTitleLabel;
@property (weak, nonatomic) IBOutlet UILabel *emailLabel;
@property (weak, nonatomic) IBOutlet UILabel *nicknameLabel;

@end

@implementation UserView

- (instancetype)init {
	self = [super init];

	if (self) {
		[self setup];
	}

	return self;
}

- (void)setup {

	NSArray *nibViews = [[NSBundle mainBundle] loadNibNamed:@"UserView" owner:self options:nil];

	UIView *view = nibViews[0];

	self.contentView = view;
	[self addSubview:view];
	view.translatesAutoresizingMaskIntoConstraints = NO;

	NSDictionary *views = NSDictionaryOfVariableBindings(view);

	NSArray *hConstraints = [NSLayoutConstraint constraintsWithVisualFormat:@"H:|[view]|" options:0 metrics:nil views:views];
	NSArray *vConstraints = [NSLayoutConstraint constraintsWithVisualFormat:@"V:|[view]|" options:0 metrics:nil views:views];

	[NSLayoutConstraint activateConstraints:hConstraints];
	[NSLayoutConstraint activateConstraints:vConstraints];
}

- (void)setUser:(User *)user {
	[self.userPortraitScreenlet loadWithUserId:user.userId];
	self.usernameLabel.text = [NSString stringWithFormat:@"%@ %@", user.firstName, user.lastName];
	self.jobTitleLabel.text = user.jobTitle;
	self.emailLabel.text = user.email;
	self.nicknameLabel.text = user.screenName;
}

@end
