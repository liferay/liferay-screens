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

#import "LRScreensmobileService_v62.h"

/**
 * @author Bruno Farache
 */
@implementation LRScreensmobileService_v62

- (NSDictionary *)addScreensMobileWithAppId:(NSString *)appId placeholderId:(NSString *)placeholderId assetEntryId:(long long)assetEntryId customContentText:(NSString *)customContentText serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"appId": appId,
		@"placeholderId": placeholderId,
		@"assetEntryId": @(assetEntryId),
		@"customContentText": customContentText,
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/channel-screens-mobile.screensmobile/add-screens-mobile": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSArray *)getContentWithAppId:(NSString *)appId groupId:(long long)groupId userContext:(NSDictionary *)userContext serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"appId": appId,
		@"groupId": @(groupId),
		@"userContext": userContext,
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/channel-screens-mobile.screensmobile/get-content": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSArray *)getContentWithAppId:(NSString *)appId groupId:(long long)groupId placeholderId:(NSString *)placeholderId userContext:(NSDictionary *)userContext serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"appId": appId,
		@"groupId": @(groupId),
		@"placeholderId": placeholderId,
		@"userContext": userContext,
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/channel-screens-mobile.screensmobile/get-content": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSDictionary *)getScreensMobileWithScreensMobileId:(long long)screensMobileId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"screensMobileId": @(screensMobileId)
	}];

	NSDictionary *_command = @{@"/channel-screens-mobile.screensmobile/get-screens-mobile": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateScreensMobileWithScreensMobileId:(long long)screensMobileId tacticId:(long long)tacticId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"screensMobileId": @(screensMobileId),
		@"tacticId": @(tacticId)
	}];

	NSDictionary *_command = @{@"/channel-screens-mobile.screensmobile/update-screens-mobile": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateScreensMobileWithScreensMobileId:(long long)screensMobileId appId:(NSString *)appId placeholderId:(NSString *)placeholderId assetEntryId:(long long)assetEntryId customContentText:(NSString *)customContentText serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"screensMobileId": @(screensMobileId),
		@"appId": appId,
		@"placeholderId": placeholderId,
		@"assetEntryId": @(assetEntryId),
		@"customContentText": customContentText,
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/channel-screens-mobile.screensmobile/update-screens-mobile": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

@end