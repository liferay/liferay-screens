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

#import "LRScreensjournalarticleService_v62.h"

/**
 * @author Bruno Farache
 */
@implementation LRScreensjournalarticleService_v62

- (NSString *)getJournalArticleContentWithGroupId:(long long)groupId classPK:(long long)classPK locale:(NSString *)locale error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"classPK": @(classPK),
		@"locale": locale
	}];

	NSDictionary *_command = @{@"/screens-web.screensjournalarticle/get-journal-article-content": _params};

	return (NSString *)[self.session invoke:_command error:error];
}

- (NSString *)getJournalArticleContentWithGroupId:(long long)groupId articleId:(NSString *)articleId templateId:(long long)templateId locale:(NSString *)locale error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"articleId": articleId,
		@"templateId": @(templateId),
		@"locale": locale
	}];

	NSDictionary *_command = @{@"/screens-web.screensjournalarticle/get-journal-article-content": _params};

	return (NSString *)[self.session invoke:_command error:error];
}

- (NSString *)getJournalArticleContentWithGroupId:(long long)groupId classPK:(long long)classPK templateId:(long long)templateId locale:(NSString *)locale error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"classPK": @(classPK),
		@"templateId": @(templateId),
		@"locale": locale
	}];

	NSDictionary *_command = @{@"/screens-web.screensjournalarticle/get-journal-article-content": _params};

	return (NSString *)[self.session invoke:_command error:error];
}

@end