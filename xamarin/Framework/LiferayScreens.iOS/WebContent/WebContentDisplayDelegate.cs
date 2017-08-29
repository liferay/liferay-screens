using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol WebContentDisplayScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface WebContentDisplayScreenletDelegate
    {
        // @optional -(NSString * _Nullable)screenlet:(WebContentDisplayScreenlet * _Nonnull)screenlet onWebContentResponse:(NSString * _Nonnull)html __attribute__((warn_unused_result));
        [Export("screenlet:onWebContentResponse:")]
        [return: NullAllowed]
        string Screenlet(WebContentDisplayScreenlet screenlet, string html);

        // @optional -(void)screenlet:(WebContentDisplayScreenlet * _Nonnull)screenlet onRecordContentResponse:(DDLRecord * _Nonnull)record;
        [Export("screenlet:onRecordContentResponse:")]
        void Screenlet(WebContentDisplayScreenlet screenlet, DDLRecord record);

        // @optional -(void)screenlet:(WebContentDisplayScreenlet * _Nonnull)screenlet onWebContentError:(NSError * _Nonnull)error;
        [Export("screenlet:onWebContentError:")]
        void Screenlet(WebContentDisplayScreenlet screenlet, NSError error);
    }
}
