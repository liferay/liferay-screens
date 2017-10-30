using CoreGraphics;
using Foundation;
using System;

namespace LiferayScreens
{
    // @interface DDLFormView_default : DDLFormTableView
    [BaseType(typeof(DDLFormTableView))]
    interface DDLFormView_default
    {
        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
