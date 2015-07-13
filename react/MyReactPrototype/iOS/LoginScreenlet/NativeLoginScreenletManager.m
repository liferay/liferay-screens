//
//  MyCompoViewManager.m
//  MyReactCompo
//
//  Created by jmWork on 10/07/15.
//  Copyright (c) 2015 Facebook. All rights reserved.
//

#import "NativeLoginScreenletManager.h"
#import "NativeLoginScreenletView.h"
#import "RCTBridge.h"
#import "RCTEventDispatcher.h"
#import "UIView+React.h"

@implementation NativeLoginScreenletManager

RCT_EXPORT_MODULE();

@synthesize bridge = _bridge;

- (void)screenlet:(BaseScreenlet * __nonnull)screenlet
		onLoginResponseUserAttributes:(NSDictionary * __nonnull)attributes {

		NSDictionary *event = @{
	    @"target": screenlet.reactTag,
			@"attributes": attributes
	  };

		// events??
		// https://github.com/facebook/react-native/blob/master/React/Modules/RCTUIManager.m#L1566
		[self.bridge.eventDispatcher sendInputEventWithName:@"topSubmitEditing" body:event];
}

- (void)screenlet:(BaseScreenlet * __nonnull)screenlet
		onLoginError:(NSError * __nonnull)error {
}

- (UIView *)view
{
	LoginScreenlet *screenlet = [[LoginScreenlet alloc] initWithFrame:CGRectMake(0, 0, 300, 300)];
	screenlet.delegate = self;
	return screenlet;
/*
	NSBundle *mainBundle = [NSBundle mainBundle];

	NSArray *views = [mainBundle loadNibNamed:@"LoginView_default" owner:self options:nil];

	NativeLoginScreenletView *view = (NativeLoginScreenletView *)views[0];
	__weak NativeLoginScreenletView *weakView = view;

	view.onLoginSuccess = ^void () {
		NSDictionary *event = @{
	    @"target": weakView.reactTag
	  };
		[self.bridge.eventDispatcher sendInputEventWithName:@"topSubmitEditing" body:event];
	};

	return view;
*/
}

- (dispatch_queue_t)methodQueue {
	return dispatch_get_main_queue();
}

//RCT_EXPORT_VIEW_PROPERTY(saveCredentials, BOOL);

@end
