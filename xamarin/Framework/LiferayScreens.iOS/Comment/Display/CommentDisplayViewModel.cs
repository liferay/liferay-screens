using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    interface ICommentDisplayViewModel {}

    // @protocol CommentDisplayViewModel
    [Protocol, Model]
    interface CommentDisplayViewModel
    {
        // @required @property (nonatomic, strong) Comment * _Nullable comment;
        [Abstract]
        [NullAllowed, Export("comment", ArgumentSemantic.Strong)]
        Comment Comment { get; set; }

        // @required -(void)editComment;
        [Abstract]
        [Export("editComment")]
        void EditComment();
    }
}
