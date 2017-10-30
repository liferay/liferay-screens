using AVKit;
using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface VideoDisplayView_default : BaseScreenletView <FileDisplayViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface VideoDisplayView_default : IFileDisplayViewModel
    {
        // @property (nonatomic, strong) AVPlayerViewController * _Nullable playerController;
        [NullAllowed, Export("playerController", ArgumentSemantic.Strong)]
        AVPlayerViewController PlayerController { get; set; }

        // @property (copy, nonatomic) NSURL * _Nullable url;
        [NullAllowed, Export("url", ArgumentSemantic.Copy)]
        NSUrl Url { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable title;
        [NullAllowed, Export("title")]
        string Title { get; set; }

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
