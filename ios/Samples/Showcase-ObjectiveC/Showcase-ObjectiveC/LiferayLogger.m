/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

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
				logMessage = [logMessage stringByAppendingString:[NSString stringWithFormat:@" %@ ", arg]];
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
