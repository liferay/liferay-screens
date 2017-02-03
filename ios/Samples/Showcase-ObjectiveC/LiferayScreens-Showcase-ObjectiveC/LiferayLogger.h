//
//  LiferayLogger.h
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 03/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

#import <Foundation/Foundation.h>

#define LiferayLog(...)[LiferayLogger logDelegateMessage:[NSString stringWithFormat:@"%s",__PRETTY_FUNCTION__] args: __VA_ARGS__, nil];

@interface LiferayLogger : NSObject

+ (void)logDelegateMessage:(NSString *) function args:(NSObject *) firstArg, ... NS_REQUIRES_NIL_TERMINATION;

@end
