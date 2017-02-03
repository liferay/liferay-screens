//
//  LiferayLogger.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 03/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

#import "LiferayLogger.h"

@implementation LiferayLogger

+ (void)logDelegateMessage:(NSString *)function args:(NSObject *)firstArg, ... {
	NSString *message = [NSString stringWithFormat:@"DELEGATE: %@ called", function];

    if (firstArg != nil) {
        if ([firstArg isKindOfClass:[NSError class]]) {
            [self errorMessage:message error:(NSError *) firstArg];
        }
        else {
            va_list args;
            va_start(args, firstArg);

            NSString *logMessage = [NSString
            		stringWithFormat:@"\n+++++++++++++++++++++\n%@ ->", message];

            for (NSObject *arg = firstArg; arg != nil; arg = va_arg(args, NSObject*))
            {
                logMessage = [logMessage stringByAppendingString:[NSString stringWithFormat:@"%@", arg]];
            }

            va_end(args);

            logMessage = [logMessage stringByAppendingString:@"\n+++++++++++++++++++++\n"];
            NSLog(@"%@", logMessage);
        }
    }
    else {
    	NSString *logMessage = [NSString
        		stringWithFormat:@"\n+++++++++++++++++++++\n"
        						"%@"
                                "\n+++++++++++++++++++++\n", message];
        NSLog(@"%@", logMessage);
    }

}

+ (void)errorMessage:(NSString *) message error:(NSError *)error {
	NSString *logMessage = [NSString
    		stringWithFormat:@"\n=====================\n"
            				"%@\n"
                            "Error: %@"
                            "\n=====================\n", message, error];
    NSLog(@"%@", logMessage);
}

@end
