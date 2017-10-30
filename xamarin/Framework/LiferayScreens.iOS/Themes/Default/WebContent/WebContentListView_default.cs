using CoreGraphics;
using Foundation;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface WebContentListView_default : WebContentListTableView
    [BaseType(typeof(WebContentListTableView))]
    interface WebContentListView_default
    {
        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
