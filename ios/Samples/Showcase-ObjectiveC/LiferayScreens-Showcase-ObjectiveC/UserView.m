//
//  UserView.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 03/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

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
