using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol WebScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface WebScreenletDelegate
    {
        // @optional -(void)onWebLoad:(WebScreenlet * _Nonnull)screenlet url:(id)url;
        [Export("onWebLoad:url:")]
        void OnWebLoad(WebScreenlet screenlet, NSObject url);

        // @optional -(void)screenlet:(WebScreenlet * _Nonnull)screenlet onError:(NSError * _Nonnull)error;
        [Export("screenlet:onError:")]
        void Screenlet(WebScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(WebScreenlet * _Nonnull)screenlet onScriptMessageNamespace:(id)namespace_ onScriptMessage:(id)message;
        [Export("screenlet:onScriptMessageNamespace:onScriptMessage:")]
        void Screenlet(WebScreenlet screenlet, NSObject namespace_, NSObject message);
    }
}
