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
#import "NSString+Utils.h"
#import "LiferayLogger.h"
#import "DDLListScreenletViewController.h"

@interface DDLListScreenletViewController () <DDLListScreenletDelegate,
UIPickerViewDelegate, UIPickerViewDataSource>

@property (weak, nonatomic) IBOutlet UIPickerView *pickerView;
@property (weak, nonatomic) IBOutlet DDLListScreenlet *screenlet;
@property (weak, nonatomic) IBOutlet UITextField *recordSetIdTextField;
@property (weak, nonatomic) IBOutlet UITextField *labelFieldsTextField;
@property (weak, nonatomic) IBOutlet UIButton *loadButton;

@property (nonatomic, copy) NSArray *pickerDataNames;
@property (nonatomic, copy) NSArray *pickerDataClassNames;

@property (nonatomic) NSString *selectedObcClassName;

@end

@implementation DDLListScreenletViewController

- (void)viewDidLoad {
	[super viewDidLoad];

	self.pickerDataNames = @[
		@"No order",
		@"Id",
		@"Create date",
		@"Modified Date"
	];

	self.pickerDataClassNames = @[
		@"",
		@"com.liferay.dynamic.data.lists.util.comparator.DDLRecordIdComparator",
		@"com.liferay.dynamic.data.lists.util.comparator.DDLRecordCreateDateComparator",
		@"com.liferay.dynamic.data.lists.util.comparator.DDLRecordModifiedDateComparator"
	];

	self.selectedObcClassName = @"";

	self.screenlet.delegate = self;
	self.pickerView.delegate = self;
	self.pickerView.dataSource = self;

	self.labelFieldsTextField.text = [LiferayServerContext stringPropertyForKey:@"ddlLabelField"];
	self.recordSetIdTextField.text = [NSString
									  stringWithFormat:@"%lld", [LiferayServerContext longPropertyForKey:@"ddlRecordSetId"]];
}

- (IBAction)loadList:(id)sender {
	if ([self.recordSetIdTextField.text isNumeric]) {
		self.screenlet.recordSetId = self.recordSetIdTextField.text.longLongValue;
		self.screenlet.labelFields = self.labelFieldsTextField.text;
		self.screenlet.obcClassName = self.selectedObcClassName;
		[self.screenlet loadList];
	}
}

#pragma mark DDListScreenletDelegate

- (void)screenlet:(DDLListScreenlet *)screenlet onDDLListError:(NSError *)error {
	LiferayLog(error);
}

- (void)screenlet:(DDLListScreenlet *)screenlet onDDLSelectedRecord:(DDLRecord *)record {
	LiferayLog(record);
}

- (void)screenlet:(DDLListScreenlet *)screenlet
		onDDLListResponseRecords:(NSArray<DDLRecord *> *)records {

	LiferayLog(records);
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
	return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
	return self.pickerDataNames.count;
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row
	   inComponent:(NSInteger)component {

	self.selectedObcClassName = self.pickerDataClassNames[row];
}

- (NSAttributedString *)pickerView:(UIPickerView *)pickerView
			 attributedTitleForRow:(NSInteger)row forComponent:(NSInteger)component {

	NSDictionary *attrs = @{NSFontAttributeName : [UIFont systemFontOfSize:3]};

	return [[NSAttributedString alloc] initWithString:self.pickerDataNames[row] attributes:attrs];
}

@end
