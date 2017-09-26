using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol WebScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface WebScreenletDelegate
    {
        // @optional -(void)onWebLoad:(WebScreenlet * _Nonnull)screenlet url:(NSString * _Nonnull)url;
        [Export("onWebLoad:url:")]
        void OnWebLoad(WebScreenlet screenlet, string url);

        // @optional -(void)screenlet:(WebScreenlet * _Nonnull)screenlet onError:(NSError * _Nonnull)error;
        [Export("screenlet:onError:")]
        void Screenlet(WebScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(WebScreenlet * _Nonnull)screenlet onScriptMessageNamespace:(NSString * _Nonnull)namespace_ onScriptMessage:(NSString * _Nonnull)message;
        [Export("screenlet:onScriptMessageNamespace:onScriptMessage:")]
        void Screenlet(WebScreenlet screenlet, string namespace_, string message);
    }
}
