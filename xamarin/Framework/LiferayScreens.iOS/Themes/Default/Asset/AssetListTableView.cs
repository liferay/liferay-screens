using CoreGraphics;
using Foundation;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface AssetListTableView : BaseListTableView
    [BaseType(typeof(BaseListTableView))]
    interface AssetListTableView
    {
        // -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
        [Export("doFillLoadedCellWithRow:cell:object:")]
        void DoFillLoadedCellWithRow(nint row, UITableViewCell cell, NSObject @object);

        // -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
        [Export("doFillInProgressCellWithRow:cell:")]
        void DoFillInProgressCellWithRow(nint row, UITableViewCell cell);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
