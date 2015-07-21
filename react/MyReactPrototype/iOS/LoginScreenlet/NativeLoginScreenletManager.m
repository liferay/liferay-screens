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

@implementation NativeLoginScreenletManager

RCT_EXPORT_MODULE();

@synthesize bridge = _bridge;

- (UIView *)view
{
	CGRect r = CGRectMake(0, 0, 300, 300);
	return [[NativeLoginScreenletView alloc] initWithFrame:r eventDispatcher:self.bridge.eventDispatcher];
}

- (dispatch_queue_t)methodQueue {
	return dispatch_get_main_queue();
}

- (NSDictionary *)customDirectEventTypes {
	return @{
		@"loginError": @{
			@"registrationName": @"onLoginError"
		},
        @"loginSuccess": @{
        	@"registrationName": @"onLoginSuccess"
        }
	};
}

RCT_EXPORT_VIEW_PROPERTY(themeName, NSString);

@end
