//
//  SignUpScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 03/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

@import LiferayScreens;
#import "LiferayLogger.h"
#import "SignUpScreenletViewController.h"

@interface SignUpScreenletViewController () <SignUpScreenletDelegate, LoginScreenletDelegate>

@property (weak, nonatomic) IBOutlet SignUpScreenlet *screenlet;

@end

@implementation SignUpScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.screenlet.delegate = self;
    self.screenlet.autoLoginDelegate = self;
    self.screenlet.anonymousApiUserName = [LiferayServerContext stringPropertyForKey:@"anonymousUsername"];
    self.screenlet.anonymousApiPassword = [LiferayServerContext stringPropertyForKey:@"anonymousPassword"];
}

- (IBAction)credentialsValueChangedAction:(UISwitch *)sender {
    self.screenlet.saveCredentials = [sender isOn];
}

- (IBAction)loginValueChangedAction:(UISwitch *)sender {
    self.screenlet.autoLogin = [sender isOn];
}

#pragma mark SignUpDelegate

- (void)screenlet:(SignUpScreenlet *)screenlet onSignUpResponseUserAttributes:(NSDictionary<NSString *,id> *)attributes {
    LiferayLog(attributes);
}

- (void)screenlet:(SignUpScreenlet *)screenlet onSignUpError:(NSError *)error {
	LiferayLog(error);
}

#pragma mark LoginScreenletDelegate

- (void)screenlet:(BaseScreenlet *)screenlet onLoginResponseUserAttributes:(NSDictionary<NSString *,id> *)attributes {
    LiferayLog(attributes);
}

- (void)screenlet:(BaseScreenlet *)screenlet onLoginError:(NSError *)error {
    LiferayLog(error);
}

- (void)screenlet:(BaseScreenlet *)screenlet onCredentialsSavedUserAttributes:(NSDictionary<NSString *,id> *)attributes {
    LiferayLog(attributes);
}

- (void)screenlet:(LoginScreenlet *)screenlet onCredentialsLoadedUserAttributes:(NSDictionary<NSString *,id> *)attributes {
    LiferayLog(attributes);
}


@end
