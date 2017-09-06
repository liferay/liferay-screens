using CoreGraphics;
using Foundation;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface CommentListView_default : BaseListTableView <CommentListViewModel>
    [BaseType(typeof(BaseListTableView))]
    interface CommentListView_default : ICommentListViewModel
    {
        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

        // -(void)addComment:(Comment * _Nonnull)comment;
        [Export("addComment:")]
        void AddComment(Comment comment);

        // -(void)deleteComment:(Comment * _Nonnull)comment;
        [Export("deleteComment:")]
        void DeleteComment(Comment comment);

        // -(void)updateComment:(Comment * _Nonnull)comment;
        [Export("updateComment:")]
        void UpdateComment(Comment comment);

        // -(void)doRegisterCellNibs;
        [Export("doRegisterCellNibs")]
        void DoRegisterCellNibs();

        // -(UITableViewCell * _Nonnull)doDequeueReusableCellWithRow:(NSInteger)row object:(id _Nullable)object __attribute__((warn_unused_result));
        [Export("doDequeueReusableCellWithRow:object:")]
        UITableViewCell DoDequeueReusableCellWithRow(nint row, [NullAllowed] NSObject @object);

        // -(NSString * _Nonnull)doGetCellIdWithRow:(NSInteger)row object:(id _Nullable)object __attribute__((warn_unused_result));
        [Export("doGetCellIdWithRow:object:")]
        string DoGetCellIdWithRow(nint row, [NullAllowed] NSObject @object);

        // -(void)doFillLoadedCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell object:(id _Nonnull)object;
        [Export("doFillLoadedCellWithRow:cell:object:")]
        void DoFillLoadedCellWithRow(nint row, UITableViewCell cell, NSObject @object);

        // -(void)doFillInProgressCellWithRow:(NSInteger)row cell:(UITableViewCell * _Nonnull)cell;
        [Export("doFillInProgressCellWithRow:cell:")]
        void DoFillInProgressCellWithRow(nint row, UITableViewCell cell);

        // -(CGFloat)tableView:(UITableView * _Nonnull)tableView heightForRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
        [Export("tableView:heightForRowAtIndexPath:")]
        nfloat TableView(UITableView tableView, NSIndexPath indexPath);

        // -(BOOL)tableView:(UITableView * _Nonnull)tableView canEditRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
        [Export("tableView:canEditRowAtIndexPath:")]
        bool TableViewCanEditRowAtIndexPath(UITableView tableView, NSIndexPath indexPath);

        // -(NSArray * _Nullable)tableView:(UITableView * _Nonnull)tableView editActionsForRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
        [Export("tableView:editActionsForRowAtIndexPath:")]
        //[Verify(StronglyTypedNSArray)]
        [return: NullAllowed]
        NSObject[] TableViewEditActionsForRowAtIndexPath(UITableView tableView, NSIndexPath indexPath);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
