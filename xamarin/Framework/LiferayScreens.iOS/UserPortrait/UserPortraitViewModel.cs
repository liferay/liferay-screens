using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    interface IUserPortraitViewModel { }

    // @protocol UserPortraitViewModel
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
    interface UserPortraitViewModel
    {
        // @required @property (nonatomic, strong) UIImage * _Nullable image;
        [Abstract]
        [NullAllowed, Export("image", ArgumentSemantic.Strong)]
        UIImage Image { get; set; }

        // @required @property (nonatomic) CGFloat borderWidth;
        [Abstract]
        [Export("borderWidth")]
        nfloat BorderWidth { get; set; }

        // @required @property (nonatomic, strong) UIColor * _Nullable borderColor;
        [Abstract]
        [NullAllowed, Export("borderColor", ArgumentSemantic.Strong)]
        UIColor BorderColor { get; set; }

        // @required -(void)loadPlaceholderFor:(User * _Nonnull)user;
        [Abstract]
        [Export("loadPlaceholderFor:")]
        void LoadPlaceholderFor(User user);
    }
}
