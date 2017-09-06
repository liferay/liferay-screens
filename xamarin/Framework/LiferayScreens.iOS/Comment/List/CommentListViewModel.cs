using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    interface ICommentListViewModel { }

    // @protocol CommentListViewModel
    [Protocol, Model]
    interface CommentListViewModel
    {
        // @required -(void)addComment:(Comment * _Nonnull)comment;
        [Abstract]
        [Export("addComment:")]
        void AddComment(Comment comment);

        // @required -(void)deleteComment:(Comment * _Nonnull)comment;
        [Abstract]
        [Export("deleteComment:")]
        void DeleteComment(Comment comment);

        // @required -(void)updateComment:(Comment * _Nonnull)comment;
        [Abstract]
        [Export("updateComment:")]
        void UpdateComment(Comment comment);
    }
}
