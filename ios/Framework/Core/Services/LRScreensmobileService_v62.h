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

#ifdef LIFERAY_SCREENS_FRAMEWORK
	@import LRMobileSDK;
#else
	#import "LRBaseService.h"
#endif

/**
 * @author Bruno Farache
 */
@interface LRScreensmobileService_v62 : LRBaseService

- (NSDictionary *)addScreensMobileWithAppId:(NSString *)appId placeholderId:(NSString *)placeholderId assetEntryId:(long long)assetEntryId customContentText:(NSString *)customContentText serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error;
- (NSArray *)getContentWithAppId:(NSString *)appId groupId:(long long)groupId userContext:(NSDictionary *)userContext serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error;
- (NSArray *)getContentWithAppId:(NSString *)appId groupId:(long long)groupId placeholderId:(NSString *)placeholderId userContext:(NSDictionary *)userContext serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error;
- (NSDictionary *)getScreensMobileWithScreensMobileId:(long long)screensMobileId error:(NSError **)error;
- (NSDictionary *)updateScreensMobileWithScreensMobileId:(long long)screensMobileId tacticId:(long long)tacticId error:(NSError **)error;
- (NSDictionary *)updateScreensMobileWithScreensMobileId:(long long)screensMobileId appId:(NSString *)appId placeholderId:(NSString *)placeholderId assetEntryId:(long long)assetEntryId customContentText:(NSString *)customContentText serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error;

@end