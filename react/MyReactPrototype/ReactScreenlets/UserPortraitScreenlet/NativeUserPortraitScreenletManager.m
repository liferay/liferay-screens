//
//  NativeUserPortraitScreenletManaget.m
//  MyReactPrototype
//
//  Created by Iliyan Peychev on 7/21/15.
//  Copyright (c) 2015 Facebook. All rights reserved.
//

#import "NativeUserPortraitScreenletManager.h"
#import "NativeUserPortraitScreenletView.h"
#import "RCTBridge.h"
#import "RCTEventDispatcher.h"

@implementation NativeUserPortraitScreenletManager

RCT_EXPORT_MODULE();

@synthesize bridge = _bridge;

- (UIView *)view
{
  CGRect r = CGRectMake(0, 0, 30, 30);
  NativeUserPortraitScreenletView *s = [[NativeUserPortraitScreenletView alloc] initWithFrame:r eventDispatcher:self.bridge.eventDispatcher];
  
  s.autoresizingMask = (UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight);
  
  return s;
}

- (dispatch_queue_t)methodQueue {
  return dispatch_get_main_queue();
}

- (NSDictionary *)customDirectEventTypes {
  return @{
    @"portraitLoaded": @{
      @"registrationName": @"onPortraitLoaded"
    },
    @"portraitError": @{
      @"registrationName": @"onPortraitError"
    }
  };
}


RCT_EXPORT_VIEW_PROPERTY(themeName, NSString);
RCT_EXPORT_VIEW_PROPERTY(userId, int);

@end
