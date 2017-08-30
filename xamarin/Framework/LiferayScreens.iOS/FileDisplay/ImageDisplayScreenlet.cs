using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface ImageDisplayScreenlet : FileDisplayScreenlet
    [BaseType(typeof(FileDisplayScreenlet))]
    interface ImageDisplayScreenlet
    {
        // @property (nonatomic, strong) UIImage * _Nullable placeholder;
        [NullAllowed, Export("placeholder", ArgumentSemantic.Strong)]
        UIImage Placeholder { get; set; }

        // @property (copy, nonatomic) NSString * _Nonnull mimeTypes;
        [Export("mimeTypes")]
        string MimeTypes { get; set; }

        // @property (readonly, nonatomic, strong) id<ImageDisplayViewModel> _Nullable imageDisplayViewModel;
        [NullAllowed, Export("imageDisplayViewModel", ArgumentSemantic.Strong)]
        IImageDisplayViewModel ImageDisplayViewModel { get; }

        // @property (nonatomic) UIViewContentMode imageMode;
        [Export("imageMode", ArgumentSemantic.Assign)]
        UIViewContentMode ImageMode { get; set; }

        // @property (nonatomic) UIViewContentMode placeholderImageMode;
        [Export("placeholderImageMode", ArgumentSemantic.Assign)]
        UIViewContentMode PlaceholderImageMode { get; set; }

        // @property (readonly, copy, nonatomic) NSArray<NSString *> * _Nonnull supportedMimeTypes;
        [Export("supportedMimeTypes", ArgumentSemantic.Copy)]
        string[] SupportedMimeTypes { get; }

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
