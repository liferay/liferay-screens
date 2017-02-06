
//
//  AssetTypesViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 06/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

@import LiferayScreens;
#import "AssetListScreenletViewController.h"
#import "AssetTypesViewController.h"

@interface AssetTypesViewController ()

@property (nonatomic, copy) NSArray *assetClasses;
@property (nonatomic, copy) NSArray *assetClassesIds;

@end

@implementation AssetTypesViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.assetClasses = @[
		@"Group",
        @"Layout",
        @"Organization",
        @"User",
        @"UserGroup",
        @"BlogsEntry",
        @"BookmarksEntry",
        @"BookmarksFolder",
        @"DLFileEntry",
        @"DLFolder",
        @"DLFileEntryMetadata",
        @"DLFileEntryType",
        @"DLFileRank",
        @"DLFileShortcut",
        @"DLFileVersion",
        @"DDLRecord",
        @"DDLRecordSet",
        @"DDLRecordVersion",
        @"JournalArticle",
        @"JournalArticleImage",
        @"JournalFolder",
        @"MBMessage",
        @"MBThread",
        @"MBCategory",
        @"MBDiscussion",
        @"MBMailingList",
        @"WikiPage",
        @"WikiPageResource",
        @"WikiNode"
    ];

    self.assetClassesIds = @[
         @20045,
         @20047,
         @20059,
         @20087,
         @20088,
         @20011,
         @27301,
         @27302,
         @20015,
         @20021,
         @20016,
         @20017,
         @20018,
         @20019,
         @20020,
         @29413,
         @29414,
         @29415,
         @29591,
         @29592,
         @29596,
         @20032,
         @20034,
         @20029,
         @20030,
         @20031,
         @27802,
         @27803,
         @27801
    ];

}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];

    self.title = nil;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.assetClasses.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView
		cellForRowAtIndexPath:(NSIndexPath *)indexPath {

    UITableViewCell *cell = [[UITableViewCell alloc]
    		initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"default-cell"];

    cell.textLabel.text = self.assetClasses[indexPath.row];

    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *selectedAssetType = self.assetClasses[indexPath.row];
    NSNumber *assetClassId = self.assetClassesIds[indexPath.row];

    [self performSegueWithIdentifier:@"assetList" sender:@[selectedAssetType, assetClassId]];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {

    AssetListScreenletViewController *vc = segue.destinationViewController;
    vc.assetType = sender[0];
    vc.assetClassId = [((NSNumber *)sender[1]) longLongValue];
}

@end
