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

@import LiferayScreens;
#import "LiferayLogger.h"
#import "DDLFormScreenletViewController.h"

@interface DDLFormScreenletViewController () <DDLFormScreenletDelegate>

@property (weak, nonatomic) IBOutlet DDLFormScreenlet *screenlet;
@property (weak, nonatomic) IBOutlet UITextField *recordIdTextField;
@property (weak, nonatomic) IBOutlet UIButton *loadButton;

@end

@implementation DDLFormScreenletViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.screenlet.delegate = self;
	self.screenlet.recordSetId = [LiferayServerContext longPropertyForKey:@"ddlRecordSetId"];
	self.screenlet.structureId = [LiferayServerContext longPropertyForKey:@"ddlStructureId"];

	self.recordIdTextField.text = [NSString stringWithFormat:@"%lld", [LiferayServerContext longPropertyForKey:@"ddlRecordId"]];
}

- (IBAction)loadForm:(id)sender {
	if (self.recordIdTextField.text.longLongValue) {
		self.screenlet.recordId = self.recordIdTextField.text.longLongValue;
		[self.recordIdTextField resignFirstResponder];
		[self.screenlet loadRecord];
	}
	else {
		[self.screenlet loadForm];
	}
}

#pragma mark DDLFormScreenletDelegate

- (void)screenlet:(DDLFormScreenlet *)screenlet onFormLoaded:(DDLRecord *)record {
	LiferayLog(record.attributes);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet onFormLoadError:(NSError *)error {
	LiferayLog(error.debugDescription);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet onFormSubmitError:(NSError *)error {
	LiferayLog(error.debugDescription);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet onRecordLoaded:(DDLRecord *)record {
	LiferayLog(record.attributes);
	self.screenlet.hidden = NO;
}

- (void)screenlet:(DDLFormScreenlet *)screenlet onRecordLoadError:(NSError *)error {
	LiferayLog(error.debugDescription);
	self.screenlet.hidden = YES;
}

- (void)screenlet:(DDLFormScreenlet *)screenlet onFormSubmitted:(DDLRecord *)record {
	LiferayLog(record.attributes);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onDocumentFieldUploadStarted:(DDMFieldDocument *)field {
	LiferayLog(field);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onDocumentField:(DDMFieldDocument *)field uploadError:(NSError *)error {
	LiferayLog(field, error);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onDocumentField:(DDMFieldDocument *)field
	 uploadResult:(NSDictionary<NSString *,id> *)result {
	LiferayLog(field, result);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet
		onDocumentField:(DDMFieldDocument *)field
	uploadedBytes:(uint64_t)bytes totalBytes:(uint64_t)total {
	LiferayLog(field, [NSNumber numberWithUnsignedLong:bytes], [NSNumber numberWithUnsignedLong:total]);
}


@end
