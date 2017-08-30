using Foundation;
using ObjCRuntime;
using UIKit;

namespace LiferayScreens
{
    interface IAssetDisplayViewModel { }

    // @protocol AssetDisplayViewModel
    [Protocol, Model]
    interface AssetDisplayViewModel
    {
        // @required @property (nonatomic, strong) Asset * _Nullable asset;
        [Abstract]
        [NullAllowed, Export("asset", ArgumentSemantic.Strong)]
        Asset Asset { get; set; }

        // @required @property (nonatomic, strong) UIView * _Nullable innerScreenlet;
        [Abstract]
        [NullAllowed, Export("innerScreenlet", ArgumentSemantic.Strong)]
        UIView InnerScreenlet { get; set; }
    }
}
