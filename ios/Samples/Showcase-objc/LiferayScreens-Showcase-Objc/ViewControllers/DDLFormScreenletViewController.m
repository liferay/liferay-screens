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
#import "DDLFormScreenletViewController.h"

@interface DDLFormScreenletViewController ()

@property (nonatomic, weak) IBOutlet DDLFormScreenlet* screenlet;
@property (nonatomic, weak) IBOutlet UITextField* recordIdField;

@end


@implementation DDLFormScreenletViewController

- (IBAction)loadForm:(id)sender {
	long long recordId = [self.recordIdField.text longLongValue];
	if (recordId != 0) {
		self.screenlet.recordId = recordId;
		[self.recordIdField resignFirstResponder];
		[self.screenlet loadRecord];
	}
	else {
		[self.screenlet loadForm];
	}
}

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onFormLoaded:(DDLRecord *)record {
	NSLog(@"DELEGATE onFormLoaded -> %@", record);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onFormLoadError:(NSError *)error {
	NSLog(@"DELEGATE onFormLoadError -> %@", error);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onRecordLoaded:(DDLRecord *)record {
	NSLog(@"DELEGATE onRecordLoaded -> %@", record);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onRecordLoadError:(NSError *)error {
	NSLog(@"DELEGATE onRecordLoadError -> %@", error);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onFormSubmitted:(DDLRecord *)record {
	NSLog(@"DELEGATE onFormSubmitted -> %@", record);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onFormSubmitError:(NSError *)error {
	NSLog(@"DELEGATE onFormSubmitError -> %@", error);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onDocumentFieldUploadStarted:(DDLFieldDocument *)field {
	NSLog(@"DELEGATE onDocumentUploadStarted -> %@", field);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onDocumentField:(DDLFieldDocument *)field
		uploadedBytes:(NSUInteger)bytes
		sentBytes:(int64_t)sent
		totalBytes:(int64_t)total {
	NSLog(@"DELEGATE onDocumentUploadedBytes -> field=%@ %lu/%lld/%lld", field, (unsigned long)bytes, sent, total);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onDocumentField:(DDLFieldDocument *)field
		uploadResult:(NSDictionary *)result {
	NSLog(@"DELEGATE onDocumentUploadCompleted -> field=%@ result=%@", field, result);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onDocumentField:(DDLFieldDocument *)field
		uploadError:(NSError *)error {
	NSLog(@"DELEGATE onDocumentUploadError -> field=%@ error=%@", field, error);
}

@end
