//
//  MyCompoView.h
//  MyReactCompo
//
//  Created by jmWork on 10/07/15.
//  Copyright (c) 2015 Facebook. All rights reserved.
//

#import "RCTView.h"

@interface NativeLoginScreenletView : RCTView

@property (nonatomic, copy) void (^onLoginSuccess)();
@property (nonatomic, copy) void (^onLoginFailure)();

@end
