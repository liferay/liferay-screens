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
@interface LRScreensratingsentryService_v70 : LRBaseService

- (NSDictionary *)updateRatingEntryWithClassPK:(long long)classPK className:(NSString *)className score:(double)score stepCount:(int)stepCount error:(NSError **)error;
- (NSDictionary *)getRatingsEntriesWithEntryId:(long long)entryId stepCount:(int)stepCount error:(NSError **)error;
- (NSDictionary *)getRatingsEntriesWithClassPK:(long long)classPK className:(NSString *)className stepCount:(int)stepCount error:(NSError **)error;
- (NSDictionary *)deleteRatingEntryWithClassPK:(long long)classPK className:(NSString *)className stepCount:(int)stepCount error:(NSError **)error;

@end