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

#import "LRMobilewidgetsassetService_v62.h"

/**
 * @author Bruno Farache
 */
@implementation LRMobilewidgetsassetService_v62

- (NSArray *)getEntriesWithEntryQuery:(LRJSONObjectWrapper *)entryQuery locale:(NSString *)locale error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"locale": locale
	}];

	[self mangleWrapperWithParams:_params name:@"entryQuery" className:@"com.liferay.portlet.asset.service.persistence.AssetEntryQuery" wrapper:entryQuery];
	NSDictionary *_command = @{@"/mobile-widgets-web/mobilewidgetsasset/get-entries": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

@end