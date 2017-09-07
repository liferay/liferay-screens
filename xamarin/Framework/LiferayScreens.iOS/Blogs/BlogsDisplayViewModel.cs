using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    interface IBlogsDisplayViewModel { }

    // @protocol BlogsDisplayViewModel
    [Protocol, Model]
    interface BlogsDisplayViewModel
    {
        // @required @property (nonatomic, strong) BlogsEntry * _Nullable blogsEntry;
        [Abstract]
        [NullAllowed, Export("blogsEntry", ArgumentSemantic.Strong)]
        BlogsEntry BlogsEntry { get; set; }
    }
}
