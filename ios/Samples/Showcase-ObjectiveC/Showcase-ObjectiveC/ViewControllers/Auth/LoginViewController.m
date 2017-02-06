//
//  ViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 02/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//
@import LiferayScreens;
#import "LoginViewController.h"
#import "LiferayLogger.h"

@interface LoginViewController () <LoginScreenletDelegate>

@property (weak, nonatomic) IBOutlet UIView *loggedView;
@property (weak, nonatomic) IBOutlet UIView *loginView;
@property (weak, nonatomic) IBOutlet UILabel *loggedUsername;
@property (weak, nonatomic) IBOutlet LoginScreenlet *loginScreenlet;
@property (weak, nonatomic) IBOutlet UILabel *saveCredentialsLabel;
@property (weak, nonatomic) IBOutlet UILabel *loggedLabel;
@property (weak, nonatomic) IBOutlet UIButton *forgetPasswordButton;
@property (weak, nonatomic) IBOutlet UIButton *signOutButton;
@property (weak, nonatomic) IBOutlet UIButton *reloginButton;
@property (weak, nonatomic) IBOutlet UIButton *signUpButton;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.loginScreenlet.presentingViewController = self;
    self.loginScreenlet.delegate = self;

    [SessionContext loadStoredCredentials];
}

- (IBAction)credentialsValueChangedAction:(UISwitch *)sender {
    self.loginScreenlet.saveCredentials = [sender isOn];
}

- (IBAction)signOutAction {
    [SessionContext.currentContext removeStoredCredentials];
    [SessionContext logout];

    [self showLogged:YES];
}

- (IBAction)reloginAction {
    if (SessionContext.currentContext == nil) {
        NSLog(@"Session doesn't exist");
        return;
    }

    NSLog(@"%s", __PRETTY_FUNCTION__);

    [SessionContext.currentContext relogin:^(NSDictionary<NSString *,id> * _Nullable userAttributes) {
        if (userAttributes == nil) {
            NSLog(@"Relogin failed");
            [self showLogged:YES];
            return;
        }

        NSLog(@"Relogin completed: %@", userAttributes);
    }];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.navigationItem.title = @"LoginScreenlet";
    [self showLogged:NO];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    self.navigationItem.title = nil;
}

- (void)showLogged:(BOOL)animated {
    if (SessionContext.isLoggedIn) {
        self.loggedUsername.text = SessionContext.currentContext.basicAuthUsername;
    }

    [UIView animateWithDuration:animated ? 0.5 : 0 animations:^{
        self.loggedView.alpha = SessionContext.isLoggedIn ? 1.0 : 0.0;
        self.loginView.alpha = SessionContext.isLoggedIn ? 0.0 : 1.0;

        if (!SessionContext.isLoggedIn) {
            self.loginScreenlet.viewModel.userName = @"test@liferay.com";
            self.loginScreenlet.viewModel.password = @"test";
        }
    }];
}

- (void)screenlet:(BaseScreenlet *)screenlet onLoginResponseUserAttributes:(NSDictionary<NSString *,id> *)attributes {
	LiferayLog(attributes);
    [self showLogged:YES];
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
