//
//  HomeScreenletsTableViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 03/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

#import "HomeScreenletsTableViewController.h"

@interface HomeScreenletsTableViewController ()

@property (strong, nonatomic) NSArray<NSArray *> *data;

@end

@implementation HomeScreenletsTableViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.data = @[
		@[@"Assets", @"AssetListScrenlet", @"AssetDisplayScreenlet"],
        @[@"Comments", @"CommentListScreenlet", @"CommentDisplayScreenlet", @"CommentAddScreenlet"],
        @[@"DDL", @"DDLListScreenlet", @"DDLFormScreenlet"],
        @[@"Files", @"AudioDisplayScreenlet", @"ImageDisplayScreenlet", @"PdfDisplayScreenlet",
            @"VideoDisplayScreenlet"],
        @[@"Others", @"UserPortraitScreenlet", @"RatingScreenlet", @"ImageGalleryScreenlet",
            @"BlogDisplayScreenlet"],
    	@[@"WebContent", @"WebContentDisplayScreenlet", @"WebContentListScreenlet"]
    ];

    [self.tableView registerClass:[UITableViewCell class] forCellReuseIdentifier:@"default-cell"];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
	self.navigationItem.title = @"Screenlets available";
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    self.navigationItem.title = nil;
}


#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {

    return self.data.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {

    return self.data[section].count - 1;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"default-cell" forIndexPath:indexPath];

    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"default-cell"];
    }

    cell.textLabel.text = self.data[indexPath.section][indexPath.row + 1];
    
    return cell;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    return self.data[section][0];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *name = self.data[indexPath.section][indexPath.row + 1];

    if ([[NSBundle mainBundle] pathForResource:name ofType:@"storyboardc"] != nil) {
        UIStoryboard *storyboard = [UIStoryboard storyboardWithName:name bundle:nil];

        UIViewController *vc = [storyboard instantiateInitialViewController];

        if (vc) {
            vc.title = name;
            [self.navigationController  pushViewController:vc animated:YES];
        }
    }
    else {
        NSLog(@"Error: no Storyboard named %@", name);
    }
}

@end
