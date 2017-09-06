using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol CommentListScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface CommentListScreenletDelegate
    {
        // @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onListResponseComments:(NSArray<Comment *> * _Nonnull)comments;
        [Export("screenlet:onListResponseComments:")]
        void OnListResponseComments(CommentListScreenlet screenlet, Comment[] comments);

        // @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onCommentListError:(NSError * _Nonnull)error;
        [Export("screenlet:onCommentListError:")]
        void OnCommentListError(CommentListScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onSelectedComment:(Comment * _Nonnull)comment;
        [Export("screenlet:onSelectedComment:")]
        void OnSelectedComment(CommentListScreenlet screenlet, Comment comment);

        // @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onDeletedComment:(Comment * _Nonnull)comment;
        [Export("screenlet:onDeletedComment:")]
        void OnDeletedComment(CommentListScreenlet screenlet, Comment comment);

        // @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onCommentDelete:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
        [Export("screenlet:onCommentDelete:onError:")]
        void OnCommentDelete(CommentListScreenlet screenlet, Comment comment, NSError error);

        // @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onUpdatedComment:(Comment * _Nonnull)comment;
        [Export("screenlet:onUpdatedComment:")]
        void OnUpdatedComment(CommentListScreenlet screenlet, Comment comment);

        // @optional -(void)screenlet:(CommentListScreenlet * _Nonnull)screenlet onCommentUpdate:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
        [Export("screenlet:onCommentUpdate:onError:")]
        void OnCommentUpdate(CommentListScreenlet screenlet, Comment comment, NSError error);
    }
}
