using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface DDLListView_default : BaseListTableView <DDLListViewModel>
    [BaseType(typeof(BaseListTableView))]
    interface DDLListView_default : IDDLListViewModel
    {
        // @property (copy, nonatomic) NSArray<NSString *> * _Nonnull labelFields;
        [Export("labelFields", ArgumentSemantic.Copy)]
        string[] LabelFields { get; set; }

        // -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
        [Export("doFillLoadedCellWithRow:cell:object:")]
        void DoFillLoadedCellWithRow(nint row, UITableViewCell cell, NSObject @object);

        // -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
        [Export("doFillInProgressCellWithRow:cell:")]
        void DoFillInProgressCellWithRow(nint row, UITableViewCell cell);

        // -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

        // -(NSString * _Nonnull)composeLabel:(DDLRecord * _Nonnull)record __attribute__((warn_unused_result));
        [Export("composeLabel:")]
        string ComposeLabel(DDLRecord record);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
