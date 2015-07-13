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

#import "LRSCProductEntryService_v62.h"

/**
 * @author Bruno Farache
 */
@implementation LRSCProductEntryService_v62

- (NSDictionary *)addProductEntryWithName:(NSString *)name type:(NSString *)type tags:(NSString *)tags shortDescription:(NSString *)shortDescription longDescription:(NSString *)longDescription pageURL:(NSString *)pageURL author:(NSString *)author repoGroupId:(NSString *)repoGroupId repoArtifactId:(NSString *)repoArtifactId licenseIds:(NSArray *)licenseIds thumbnails:(NSArray *)thumbnails fullImages:(NSArray *)fullImages serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"name": name,
		@"type": type,
		@"tags": tags,
		@"shortDescription": shortDescription,
		@"longDescription": longDescription,
		@"pageURL": pageURL,
		@"author": author,
		@"repoGroupId": repoGroupId,
		@"repoArtifactId": repoArtifactId,
		@"licenseIds": licenseIds,
		@"thumbnails": thumbnails,
		@"fullImages": fullImages,
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/scproductentry/add-product-entry": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (void)deleteProductEntryWithProductEntryId:(long long)productEntryId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"productEntryId": @(productEntryId)
	}];

	NSDictionary *_command = @{@"/scproductentry/delete-product-entry": _params};

	[self.session invoke:_command error:error];
}

- (NSDictionary *)getProductEntryWithProductEntryId:(long long)productEntryId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"productEntryId": @(productEntryId)
	}];

	NSDictionary *_command = @{@"/scproductentry/get-product-entry": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateProductEntryWithProductEntryId:(long long)productEntryId name:(NSString *)name type:(NSString *)type tags:(NSString *)tags shortDescription:(NSString *)shortDescription longDescription:(NSString *)longDescription pageURL:(NSString *)pageURL author:(NSString *)author repoGroupId:(NSString *)repoGroupId repoArtifactId:(NSString *)repoArtifactId licenseIds:(NSArray *)licenseIds thumbnails:(NSArray *)thumbnails fullImages:(NSArray *)fullImages error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"productEntryId": @(productEntryId),
		@"name": name,
		@"type": type,
		@"tags": tags,
		@"shortDescription": shortDescription,
		@"longDescription": longDescription,
		@"pageURL": pageURL,
		@"author": author,
		@"repoGroupId": repoGroupId,
		@"repoArtifactId": repoArtifactId,
		@"licenseIds": licenseIds,
		@"thumbnails": thumbnails,
		@"fullImages": fullImages
	}];

	NSDictionary *_command = @{@"/scproductentry/update-product-entry": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

@end