using Foundation;
using ObjCRuntime;
using UIKit;

namespace LiferayScreens
{
    interface IImageDisplayViewModel {}

    // @protocol ImageDisplayViewModel <FileDisplayViewModel>
    [Protocol, Model]
    interface ImageDisplayViewModel : FileDisplayViewModel
    {
        // @required @property (nonatomic) UIViewContentMode imageMode;
        [Abstract]
        [Export("imageMode", ArgumentSemantic.Assign)]
        UIViewContentMode ImageMode { get; set; }

        // @required @property (nonatomic, strong) UIImage * _Nullable placeholder;
        [Abstract]
        [NullAllowed, Export("placeholder", ArgumentSemantic.Strong)]
        UIImage Placeholder { get; set; }

        // @required @property (nonatomic) UIViewContentMode placeholderImageMode;
        [Abstract]
        [Export("placeholderImageMode", ArgumentSemantic.Assign)]
        UIViewContentMode PlaceholderImageMode { get; set; }
    }
}
