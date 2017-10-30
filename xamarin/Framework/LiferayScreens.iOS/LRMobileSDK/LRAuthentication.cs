using Foundation;

namespace LiferayScreens
{
    // @protocol LRAuthentication <NSObject>
    [Protocol, Model]
    [BaseType(typeof(NSObject))]
    interface LRAuthentication
    {
        // @required -(void)authenticate:(NSMutableURLRequest *)request;
        [Abstract]
        [Export("authenticate:")]
        void Authenticate(NSMutableUrlRequest request);
    }
}
