//
//  ForgotPasswordScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 03/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

@import LiferayScreens;
#import "LiferayLogger.h"
#import "ForgotPasswordScreenletViewController.h"

@interface ForgotPasswordScreenletViewController () <ForgotPasswordScreenletDelegate>

@property (weak, nonatomic) IBOutlet ForgotPasswordScreenlet *screenlet;

@end

@implementation ForgotPasswordScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.screenlet.delegate = self;
}

- (void)screenlet:(ForgotPasswordScreenlet *)screenlet onForgotPasswordSent:(BOOL)passwordSent {
    LiferayLog(passwordSent ? @"YES": @"NO");
}

- (void)screenlet:(ForgotPasswordScreenlet *)screenlet onForgotPasswordError:(NSError *)error {
    LiferayLog(error);
}

@end
