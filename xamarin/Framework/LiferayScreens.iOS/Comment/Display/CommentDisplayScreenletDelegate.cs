using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol CommentDisplayScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface CommentDisplayScreenletDelegate
    {
        // @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onCommentLoaded:(Comment * _Nonnull)comment;
        [Export("screenlet:onCommentLoaded:")]
        void OnCommentLoaded(CommentDisplayScreenlet screenlet, Comment comment);

        // @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onLoadCommentError:(NSError * _Nonnull)error;
        [Export("screenlet:onLoadCommentError:")]
        void OnLoadCommentError(CommentDisplayScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onCommentDeleted:(Comment * _Nonnull)comment;
        [Export("screenlet:onCommentDisplayDeleted:")]
        void OnCommentDeleted(CommentDisplayScreenlet screenlet, Comment comment);

        // @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onDeleteComment:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
        [Export("screenlet:onDeleteCommentDisplay:onError:")]
        void OnDeleteComment(CommentDisplayScreenlet screenlet, Comment comment, NSError error);

        // @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onCommentUpdated:(Comment * _Nonnull)comment;
        [Export("screenlet:onCommentDisplayUpdated:")]
        void OnCommentUpdated(CommentDisplayScreenlet screenlet, Comment comment);

        // @optional -(void)screenlet:(CommentDisplayScreenlet * _Nonnull)screenlet onUpdateComment:(Comment * _Nonnull)comment onError:(NSError * _Nonnull)error;
        [Export("screenlet:onUpdateCommentDisplay:onError:")]
        void OnUpdateComment(CommentDisplayScreenlet screenlet, Comment comment, NSError error);
    }
}
