//
//  CommentAddScreenletViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galán on 03/02/2017.
//  Copyright © 2017 liferay. All rights reserved.
//

@import LiferayScreens;
#import "LiferayLogger.h"
#import "CommentAddScreenletViewController.h"

@interface CommentAddScreenletViewController () <CommentAddScreenletDelegate>

@property (weak, nonatomic) IBOutlet CommentAddScreenlet *screenlet;

@end

@implementation CommentAddScreenletViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.screenlet.delegate = self;
    self.screenlet.className = [LiferayServerContext stringPropertyForKey:@"commentScreenletClassName"];
    self.screenlet.classPK = [LiferayServerContext longPropertyForKey:@"commentScreenletClassPK"];
}

- (void)screenlet:(CommentAddScreenlet *)screenlet onCommentAdded:(Comment *)comment {
    LiferayLog(comment);
}

- (void)screenlet:(CommentAddScreenlet *)screenlet onAddCommentError:(NSError *)error {
    LiferayLog(error);
}

@end
