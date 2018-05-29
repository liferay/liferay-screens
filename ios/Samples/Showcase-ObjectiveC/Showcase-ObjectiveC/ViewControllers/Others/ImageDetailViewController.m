//
//  ImageDetailViewController.m
//  LiferayScreens-Showcase-ObjectiveC
//
//  Created by Victor Galan on 29/05/2018.
//  Copyright © 2018 liferay. All rights reserved.
//

#import "ImageDetailViewController.h"
@import LiferayScreens;
@import Kingfisher;

@interface ImageDetailViewController ()

@property (nonatomic, strong) IBOutlet UIImageView *imageView;

@end

@implementation ImageDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];

	NSData *data = [[NSData alloc] initWithContentsOfURL:[NSURL URLWithString:self.imageUrl]];
	
	self.imageView.image = [[UIImage alloc] initWithData:data];
}


@end
