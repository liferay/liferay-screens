using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface ImageDisplayView_default : BaseScreenletView <ImageDisplayViewModel, FileDisplayViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface ImageDisplayView_default : IImageDisplayViewModel, IFileDisplayViewModel
    {
        // @property (nonatomic, weak) UIImageView * _Nullable imageView __attribute__((iboutlet));
        [NullAllowed, Export("imageView", ArgumentSemantic.Weak)]
        UIImageView ImageView { get; set; }

        // @property (nonatomic) UIViewContentMode imageMode;
        [Export("imageMode", ArgumentSemantic.Assign)]
        UIViewContentMode ImageMode { get; set; }

        // @property (nonatomic) UIViewContentMode placeholderImageMode;
        [Export("placeholderImageMode", ArgumentSemantic.Assign)]
        UIViewContentMode PlaceholderImageMode { get; set; }

        // @property (nonatomic, strong) UIImage * _Nullable placeholder;
        [NullAllowed, Export("placeholder", ArgumentSemantic.Strong)]
        UIImage Placeholder { get; set; }

        // @property (copy, nonatomic) NSURL * _Nullable url;
        [NullAllowed, Export("url", ArgumentSemantic.Copy)]
        NSUrl Url { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable title;
        [NullAllowed, Export("title")]
        string Title { get; set; }

        // -(void)onStartInteraction;
        [Export("onStartInteraction")]
        void OnStartInteraction();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
