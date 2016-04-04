/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

#import "ObjCTryCatch.h"

@implementation ObjCTryCatch

+(BOOL)catchBlock:(void (^)(void))block error:(NSError **)error {
	BOOL result;
	@try {
		block();
		result = YES;
	}
	@catch (NSException *exception) {
		result = NO;
		if (error) {
			*error = [[NSError alloc] initWithDomain:exception.name code:1 userInfo:exception.userInfo];
		}
	}
	@finally {
		return result;
	}
}

+ (void)throwException:(NSException *)exception {
	@throw exception;
}

@end
