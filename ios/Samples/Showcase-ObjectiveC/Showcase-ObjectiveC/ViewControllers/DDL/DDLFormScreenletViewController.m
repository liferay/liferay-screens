//
//  DDLFormScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 04/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

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

- (void)screenlet:(DDLFormScreenlet *)screenlet onFormLoaded:(DDLRecord *)record {
    LiferayLog(record);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet onFormLoadError:(NSError *)error {
	LiferayLog(error);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet onFormSubmitError:(NSError *)error {
	LiferayLog(error);
}

- (void)screenlet:(DDLFormScreenlet *)screenlet onRecordLoaded:(DDLRecord *)record {
	LiferayLog(record);
    self.screenlet.hidden = NO;
}

- (void)screenlet:(DDLFormScreenlet *)screenlet onRecordLoadError:(NSError *)error {
	LiferayLog(error);
    self.screenlet.hidden = YES;
}

- (void)screenlet:(DDLFormScreenlet *)screenlet onFormSubmitted:(DDLRecord *)record {
	LiferayLog(record);
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
