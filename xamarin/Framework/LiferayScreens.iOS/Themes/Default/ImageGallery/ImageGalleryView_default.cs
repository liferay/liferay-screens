using CoreGraphics;
using Foundation;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface ImageGalleryView_default : ImageGalleryCollectionViewBase
    [BaseType(typeof(ImageGalleryCollectionViewBase))]
    interface ImageGalleryView_default
    {
        // @property (readonly, nonatomic, class) NSInteger DefaultColumns;
        [Static]
        [Export("DefaultColumns")]
        nint DefaultColumns { get; }

        // @property (nonatomic) CGFloat spacing;
        [Export("spacing")]
        nfloat Spacing { get; set; }

        // @property (nonatomic) NSInteger columnNumber;
        [Export("columnNumber")]
        nint ColumnNumber { get; set; }

        // -(void)changeLayout;
        [Export("changeLayout")]
        void ChangeLayout();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
