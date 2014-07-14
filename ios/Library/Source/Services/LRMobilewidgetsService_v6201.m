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

#import "LRMobilewidgetsService_v6201.h"

/**
 * @author Bruno Farache
 */
@implementation LRMobilewidgetsService_v6201

- (BOOL)resetPasswordByEmailAddressWithCompanyId:(long long)companyId emailAddress:(NSString *)emailAddress error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"companyId": @(companyId),
		@"emailAddress": emailAddress
	}];

	NSDictionary *_command = @{@"/mobile-widgets-compat-portlet/mobilewidgets/reset-password-by-email-address": _params};

	return [self boolValue:(NSNumber *)[self.session invoke:_command error:error]];
}

- (BOOL)resetPasswordByScreenNameWithCompanyId:(long long)companyId screenName:(NSString *)screenName error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"companyId": @(companyId),
		@"screenName": screenName
	}];

	NSDictionary *_command = @{@"/mobile-widgets-compat-portlet/mobilewidgets/reset-password-by-screen-name": _params};

	return [self boolValue:(NSNumber *)[self.session invoke:_command error:error]];
}

- (BOOL)resetPasswordByUserIdWithUserId:(long long)userId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"userId": @(userId)
	}];

	NSDictionary *_command = @{@"/mobile-widgets-compat-portlet/mobilewidgets/reset-password-by-user-id": _params};

	return [self boolValue:(NSNumber *)[self.session invoke:_command error:error]];
}

@end