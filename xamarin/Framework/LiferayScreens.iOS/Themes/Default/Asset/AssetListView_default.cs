using CoreGraphics;
using Foundation;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface AssetListView_default : AssetListTableView
    [BaseType(typeof(AssetListTableView))]
    interface AssetListView_default
    {
        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
