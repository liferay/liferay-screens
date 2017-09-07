using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol PortletDisplayScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface PortletDisplayScreenletDelegate
    {
        // @optional -(void)onPortletPageLoaded:(PortletDisplayScreenlet * _Nonnull)screenlet url:(NSString * _Nonnull)url;
        [Export("onPortletPageLoaded:url:")]
        void OnPortletPageLoaded(PortletDisplayScreenlet screenlet, string url);

        // @optional -(void)screenlet:(PortletDisplayScreenlet * _Nonnull)screenlet onPortletError:(NSError * _Nonnull)error;
        [Export("screenlet:onPortletError:")]
        void Screenlet(PortletDisplayScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(PortletDisplayScreenlet * _Nonnull)screenlet onScriptMessageNamespace:(NSString * _Nonnull)namespace_ onScriptMessage:(NSString * _Nonnull)message;
        [Export("screenlet:onScriptMessageNamespace:onScriptMessage:")]
        void Screenlet(PortletDisplayScreenlet screenlet, string namespace_, string message);

        // @optional -(id<InjectableScript> _Nullable)screenlet:(PortletDisplayScreenlet * _Nonnull)screenlet cssFor:(NSString * _Nonnull)portlet __attribute__((warn_unused_result));
        [Export("screenlet:cssFor:")]
        [return: NullAllowed]
        IInjectableScript ScreenletCssFor(PortletDisplayScreenlet screenlet, string portlet);

        // @optional -(id<InjectableScript> _Nullable)screenlet:(PortletDisplayScreenlet * _Nonnull)screenlet jsFor:(NSString * _Nonnull)portlet __attribute__((warn_unused_result));
        [Export("screenlet:jsFor:")]
        [return: NullAllowed]
        IInjectableScript ScreenletJsFor(PortletDisplayScreenlet screenlet, string portlet);
    }
}
