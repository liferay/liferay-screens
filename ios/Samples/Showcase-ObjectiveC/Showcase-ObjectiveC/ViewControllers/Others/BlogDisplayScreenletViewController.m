//
//  BlogDisplayScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 06/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

@import LiferayScreens;
#import "LiferayLogger.h"
#import "BlogDisplayScreenletViewController.h"

@interface BlogDisplayScreenletViewController () <BlogsEntryDisplayScreenletDelegate>

@property (weak, nonatomic) IBOutlet BlogsEntryDisplayScreenlet *screenlet;
@property (weak, nonatomic) IBOutlet UITextField *blogClassPKLabel;
@property (weak, nonatomic) IBOutlet UIButton *loadButton;

@end

@implementation BlogDisplayScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.screenlet.delegate = self;

    self.blogClassPKLabel.text = [LiferayServerContext stringPropertyForKey:@"blogDisplayClassPK"];
}

- (IBAction)loadBlog:(id)sender {
    if (self.blogClassPKLabel.text.length > 0) {
        self.screenlet.classPK = self.blogClassPKLabel.text.longLongValue;
        [self.screenlet load];
    }
}

- (void)screenlet:(BlogsEntryDisplayScreenlet *)screenlet
		onBlogEntryResponse:(BlogsEntry *)blogEntry {

    screenlet.hidden = NO;
    LiferayLog(blogEntry);
}

- (void)screenlet:(BlogsEntryDisplayScreenlet *)screenlet onBlogEntryError:(NSError *)error {
    screenlet.hidden = YES;
    LiferayLog(error);
}

@end
