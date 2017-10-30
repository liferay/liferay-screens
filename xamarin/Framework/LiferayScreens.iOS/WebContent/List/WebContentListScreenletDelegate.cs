using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol WebContentListScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface WebContentListScreenletDelegate
    {
        // @optional -(void)screenlet:(WebContentListScreenlet * _Nonnull)screenlet onWebContentListResponse:(NSArray<WebContent *> * _Nonnull)contents;
        [Export("screenlet:onWebContentListResponse:")]
        void OnWebContentListResponse(WebContentListScreenlet screenlet, WebContent[] contents);

        // @optional -(void)screenlet:(WebContentListScreenlet * _Nonnull)screenlet onWebContentListError:(NSError * _Nonnull)error;
        [Export("screenlet:onWebContentListError:")]
        void OnWebContentListError(WebContentListScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(WebContentListScreenlet * _Nonnull)screenlet onWebContentSelected:(WebContent * _Nonnull)content;
        [Export("screenlet:onWebContentSelected:")]
        void OnWebContentSelected(WebContentListScreenlet screenlet, WebContent content);
    }
}
