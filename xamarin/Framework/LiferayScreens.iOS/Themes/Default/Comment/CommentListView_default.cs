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
        // -(void)addComment:(Comment * _Nonnull)comment;
        [Export("addComment:")]
        void AddComment(Comment comment);

        // -(void)deleteComment:(Comment * _Nonnull)comment;
        [Export("deleteComment:")]
        void DeleteComment(Comment comment);

        // -(void)updateComment:(Comment * _Nonnull)comment;
        [Export("updateComment:")]
        void UpdateComment(Comment comment);

        // -(BOOL)tableView:(UITableView * _Nonnull)tableView canEditRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
        [Export("tableView:canEditRowAtIndexPath:")]
        bool TableViewCanEditRowAtIndexPath(UITableView tableView, NSIndexPath indexPath);

        // -(NSArray * _Nullable)tableView:(UITableView * _Nonnull)tableView editActionsForRowAtIndexPath:(NSIndexPath * _Nonnull)indexPath __attribute__((warn_unused_result));
        [Export("tableView:editActionsForRowAtIndexPath:")]
        [return: NullAllowed]
        NSObject[] TableViewEditActionsForRowAtIndexPath(UITableView tableView, NSIndexPath indexPath);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
