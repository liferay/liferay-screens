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

#import "LRJournalTemplateService_v62.h"

/**
 * @author Bruno Farache
 */
@implementation LRJournalTemplateService_v62

- (NSDictionary *)addTemplateWithGroupId:(long long)groupId templateId:(NSString *)templateId autoTemplateId:(BOOL)autoTemplateId structureId:(NSString *)structureId nameMap:(NSDictionary *)nameMap descriptionMap:(NSDictionary *)descriptionMap xsl:(NSString *)xsl formatXsl:(BOOL)formatXsl langType:(NSString *)langType cacheable:(BOOL)cacheable serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"templateId": templateId,
		@"autoTemplateId": @(autoTemplateId),
		@"structureId": structureId,
		@"nameMap": nameMap,
		@"descriptionMap": descriptionMap,
		@"xsl": xsl,
		@"formatXsl": @(formatXsl),
		@"langType": langType,
		@"cacheable": @(cacheable),
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/journaltemplate/add-template": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSOperation *)addTemplateWithGroupId:(long long)groupId templateId:(NSString *)templateId autoTemplateId:(BOOL)autoTemplateId structureId:(NSString *)structureId nameMap:(NSDictionary *)nameMap descriptionMap:(NSDictionary *)descriptionMap xsl:(NSString *)xsl formatXsl:(BOOL)formatXsl langType:(NSString *)langType cacheable:(BOOL)cacheable smallImage:(BOOL)smallImage smallImageURL:(NSString *)smallImageURL smallFile:(LRUploadData *)smallFile serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"templateId": templateId,
		@"autoTemplateId": @(autoTemplateId),
		@"structureId": structureId,
		@"nameMap": nameMap,
		@"descriptionMap": descriptionMap,
		@"xsl": xsl,
		@"formatXsl": @(formatXsl),
		@"langType": langType,
		@"cacheable": @(cacheable),
		@"smallImage": @(smallImage),
		@"smallImageURL": smallImageURL,
		@"smallFile": smallFile,
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/journaltemplate/add-template": _params};

	return [self.session upload:_command error:error];
}

- (NSDictionary *)copyTemplateWithGroupId:(long long)groupId oldTemplateId:(NSString *)oldTemplateId newTemplateId:(NSString *)newTemplateId autoTemplateId:(BOOL)autoTemplateId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"oldTemplateId": oldTemplateId,
		@"newTemplateId": newTemplateId,
		@"autoTemplateId": @(autoTemplateId)
	}];

	NSDictionary *_command = @{@"/journaltemplate/copy-template": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (void)deleteTemplateWithGroupId:(long long)groupId templateId:(NSString *)templateId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"templateId": templateId
	}];

	NSDictionary *_command = @{@"/journaltemplate/delete-template": _params};

	[self.session invoke:_command error:error];
}

- (NSArray *)getStructureTemplatesWithGroupId:(long long)groupId structureId:(NSString *)structureId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"structureId": structureId
	}];

	NSDictionary *_command = @{@"/journaltemplate/get-structure-templates": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSDictionary *)getTemplateWithGroupId:(long long)groupId templateId:(NSString *)templateId error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"templateId": templateId
	}];

	NSDictionary *_command = @{@"/journaltemplate/get-template": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSDictionary *)getTemplateWithGroupId:(long long)groupId templateId:(NSString *)templateId includeGlobalTemplates:(BOOL)includeGlobalTemplates error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"templateId": templateId,
		@"includeGlobalTemplates": @(includeGlobalTemplates)
	}];

	NSDictionary *_command = @{@"/journaltemplate/get-template": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSArray *)searchWithCompanyId:(long long)companyId groupIds:(NSArray *)groupIds templateId:(NSString *)templateId structureId:(NSString *)structureId structureIdComparator:(NSString *)structureIdComparator name:(NSString *)name description:(NSString *)description andOperator:(BOOL)andOperator start:(int)start end:(int)end obc:(LRJSONObjectWrapper *)obc error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"companyId": @(companyId),
		@"groupIds": groupIds,
		@"templateId": templateId,
		@"structureId": structureId,
		@"structureIdComparator": structureIdComparator,
		@"name": name,
		@"description": description,
		@"andOperator": @(andOperator),
		@"start": @(start),
		@"end": @(end),
	}];

	[self mangleWrapperWithParams:_params name:@"obc" className:@"com.liferay.portal.kernel.util.OrderByComparator" wrapper:obc];

	NSDictionary *_command = @{@"/journaltemplate/search": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSArray *)searchWithCompanyId:(long long)companyId groupIds:(NSArray *)groupIds keywords:(NSString *)keywords structureId:(NSString *)structureId structureIdComparator:(NSString *)structureIdComparator start:(int)start end:(int)end obc:(LRJSONObjectWrapper *)obc error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"companyId": @(companyId),
		@"groupIds": groupIds,
		@"keywords": keywords,
		@"structureId": structureId,
		@"structureIdComparator": structureIdComparator,
		@"start": @(start),
		@"end": @(end),
	}];

	[self mangleWrapperWithParams:_params name:@"obc" className:@"com.liferay.portal.kernel.util.OrderByComparator" wrapper:obc];

	NSDictionary *_command = @{@"/journaltemplate/search": _params};

	return (NSArray *)[self.session invoke:_command error:error];
}

- (NSNumber *)searchCountWithCompanyId:(long long)companyId groupIds:(NSArray *)groupIds keywords:(NSString *)keywords structureId:(NSString *)structureId structureIdComparator:(NSString *)structureIdComparator error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"companyId": @(companyId),
		@"groupIds": groupIds,
		@"keywords": keywords,
		@"structureId": structureId,
		@"structureIdComparator": structureIdComparator
	}];

	NSDictionary *_command = @{@"/journaltemplate/search-count": _params};

	return (NSNumber *)[self.session invoke:_command error:error];
}

- (NSNumber *)searchCountWithCompanyId:(long long)companyId groupIds:(NSArray *)groupIds templateId:(NSString *)templateId structureId:(NSString *)structureId structureIdComparator:(NSString *)structureIdComparator name:(NSString *)name description:(NSString *)description andOperator:(BOOL)andOperator error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"companyId": @(companyId),
		@"groupIds": groupIds,
		@"templateId": templateId,
		@"structureId": structureId,
		@"structureIdComparator": structureIdComparator,
		@"name": name,
		@"description": description,
		@"andOperator": @(andOperator)
	}];

	NSDictionary *_command = @{@"/journaltemplate/search-count": _params};

	return (NSNumber *)[self.session invoke:_command error:error];
}

- (NSDictionary *)updateTemplateWithGroupId:(long long)groupId templateId:(NSString *)templateId structureId:(NSString *)structureId nameMap:(NSDictionary *)nameMap descriptionMap:(NSDictionary *)descriptionMap xsl:(NSString *)xsl formatXsl:(BOOL)formatXsl langType:(NSString *)langType cacheable:(BOOL)cacheable serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"templateId": templateId,
		@"structureId": structureId,
		@"nameMap": nameMap,
		@"descriptionMap": descriptionMap,
		@"xsl": xsl,
		@"formatXsl": @(formatXsl),
		@"langType": langType,
		@"cacheable": @(cacheable),
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/journaltemplate/update-template": _params};

	return (NSDictionary *)[self.session invoke:_command error:error];
}

- (NSOperation *)updateTemplateWithGroupId:(long long)groupId templateId:(NSString *)templateId structureId:(NSString *)structureId nameMap:(NSDictionary *)nameMap descriptionMap:(NSDictionary *)descriptionMap xsl:(NSString *)xsl formatXsl:(BOOL)formatXsl langType:(NSString *)langType cacheable:(BOOL)cacheable smallImage:(BOOL)smallImage smallImageURL:(NSString *)smallImageURL smallFile:(LRUploadData *)smallFile serviceContext:(LRJSONObjectWrapper *)serviceContext error:(NSError **)error {
	NSMutableDictionary *_params = [NSMutableDictionary dictionaryWithDictionary:@{
		@"groupId": @(groupId),
		@"templateId": templateId,
		@"structureId": structureId,
		@"nameMap": nameMap,
		@"descriptionMap": descriptionMap,
		@"xsl": xsl,
		@"formatXsl": @(formatXsl),
		@"langType": langType,
		@"cacheable": @(cacheable),
		@"smallImage": @(smallImage),
		@"smallImageURL": smallImageURL,
		@"smallFile": smallFile,
	}];

	[self mangleWrapperWithParams:_params name:@"serviceContext" className:@"com.liferay.portal.service.ServiceContext" wrapper:serviceContext];

	NSDictionary *_command = @{@"/journaltemplate/update-template": _params};

	return [self.session upload:_command error:error];
}

@end