using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol BlogsEntryDisplayScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface BlogsEntryDisplayScreenletDelegate
    {
        // @optional -(void)screenlet:(BlogsEntryDisplayScreenlet * _Nonnull)screenlet onBlogEntryResponse:(BlogsEntry * _Nonnull)blogEntry;
        [Export("screenlet:onBlogEntryResponse:")]
        void OnBlogEntryResponse(BlogsEntryDisplayScreenlet screenlet, BlogsEntry blogEntry);

        // @optional -(void)screenlet:(BlogsEntryDisplayScreenlet * _Nonnull)screenlet onBlogEntryError:(NSError * _Nonnull)error;
        [Export("screenlet:onBlogEntryError:")]
        void OnBlogEntryError(BlogsEntryDisplayScreenlet screenlet, NSError error);
    }
}
