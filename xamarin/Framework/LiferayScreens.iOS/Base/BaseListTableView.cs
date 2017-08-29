using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface BaseListTableView : BaseListView <UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource>
    [BaseType(typeof(BaseListView))]
    interface BaseListTableView : IUIScrollViewDelegate, IUITableViewDelegate, IUITableViewDataSource
    {
        // @property (nonatomic, strong) UITableView * _Nullable tableView __attribute__((iboutlet));
        [NullAllowed, Export("tableView", ArgumentSemantic.Strong)]
        UITableView TableView { get; set; }

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
        [Export("onFinishInteraction:error:")]
        void OnFinishInteraction([NullAllowed] NSObject result, [NullAllowed] NSError error);

        // -(NSInteger)numberOfSectionsInTableView:(UITableView * _Nonnull)tableView __attribute__((warn_unused_result));
        [Export("numberOfSectionsInTableView:")]
        nint NumberOfSectionsInTableView(UITableView tableView);

        // -(NSString * _Nullable)tableView:(UITableView * _Nonnull)tableView titleForHeaderInSection:(NSInteger)section __attribute__((warn_unused_result));
        [Export("tableView:titleForHeaderInSection:")]
        [return: NullAllowed]
        string TableViewTitleForHeaderInSection(UITableView tableView, nint section);

        // -(void)tableView:(UITableView * _Nonnull)tableView didSelectRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath;
        [Export("tableView:didSelectRowAtIndexPath:")]
        void TableViewDidSelectRowAtIndexPath(UITableView tableView, NSIndexPath indexPath);

        // -(void)tableView:(UITableView * _Nonnull)tableView willDisplayCell:(UITableViewCell * _Nonnull)cell forRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath;
        [Export("tableView:willDisplayCell:forRowAtIndexPath:")]
        void TableViewWillDisplayCellForRowAtIndexPath(UITableView tableView, UITableViewCell cell, NSIndexPath indexPath);

        // -(UITableViewCell * _Nonnull)doDequeueReusableCellWithRow:(NSInteger)row object:(id _Nullable)object __attribute__((warn_unused_result));
        [Export("doDequeueReusableCellWithRow:object:")]
        UITableViewCell DoDequeueReusableCellWithRow(nint row, [NullAllowed] NSObject @object);

        // -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
        [Export("doFillLoadedCellWithRow:cell:object:")]
        void DoFillLoadedCellWithRow(nint row, UITableViewCell cell, NSObject @object);

        // -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
        [Export("doFillInProgressCellWithRow:cell:")]
        void DoFillInProgressCellWithRow(nint row, UITableViewCell cell);

        // -(void)doRegisterCellNibs;
        [Export("doRegisterCellNibs")]
        void DoRegisterCellNibs();

        // -(NSString * _Nonnull)doGetCellIdWithRow:(NSInteger)row object:(id _Nullable)object __attribute__((warn_unused_result));
        [Export("doGetCellIdWithRow:object:")]
        string DoGetCellIdWithRow(nint row, [NullAllowed] NSObject @object);

        // -(UITableViewCell * _Nonnull)doCreateCell:(NSString * _Nonnull)cellId __attribute__((warn_unused_result));
        [Export("doCreateCell:")]
        UITableViewCell DoCreateCell(string cellId);

        // -(UIView * _Nullable)createLoadingMoreView __attribute__((warn_unused_result));
        [NullAllowed, Export("createLoadingMoreView")]
        UIView CreateLoadingMoreView();

        // -(void)changeEditable:(BOOL)editable;
        [Export("changeEditable:")]
        void ChangeEditable(bool editable);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
