using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol CommentAddScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface CommentAddScreenletDelegate
    {
        // @optional -(void)screenlet:(CommentAddScreenlet * _Nonnull)screenlet onCommentAdded:(Comment * _Nonnull)comment;
        [Export("screenlet:onCommentAdded:")]
        void OnCommentAdded(CommentAddScreenlet screenlet, Comment comment);

        // @optional -(void)screenlet:(CommentAddScreenlet * _Nonnull)screenlet onAddCommentError:(NSError * _Nonnull)error;
        [Export("screenlet:onAddCommentError:")]
        void OnAddCommentError(CommentAddScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(CommentAddScreenlet * _Nonnull)screenlet onCommentUpdated:(Comment * _Nonnull)comment;
        [Export("screenlet:onCommentUpdated:")]
        void OnCommentUpdated(CommentAddScreenlet screenlet, Comment comment);

        // @optional -(void)screenlet:(CommentAddScreenlet * _Nonnull)screenlet onUpdateCommentError:(NSError * _Nonnull)error;
        [Export("screenlet:onUpdateCommentError:")]
        void OnUpdateCommentError(CommentAddScreenlet screenlet, NSError error);
    }
}
