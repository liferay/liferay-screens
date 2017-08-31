using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface AssetDisplayView_default : BaseScreenletView <AssetDisplayViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface AssetDisplayView_default : IAssetDisplayViewModel
    {
        // @property (nonatomic, strong) Asset * _Nullable asset;
        [NullAllowed, Export("asset", ArgumentSemantic.Strong)]
        Asset Asset { get; set; }

        // @property (nonatomic, strong) UIView * _Nullable innerScreenlet;
        [NullAllowed, Export("innerScreenlet", ArgumentSemantic.Strong)]
        UIView InnerScreenlet { get; set; }

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
