using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol CredentialsStore
    [Protocol, Model]
    interface CredentialsStore
    {
        // @required @property (readonly, nonatomic, strong) id<LRAuthentication> _Nullable authentication;
        [Abstract]
        [NullAllowed, Export("authentication", ArgumentSemantic.Strong)]
        LRAuthentication Authentication { get; }

        // @required @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nullable userAttributes;
        [Abstract]
        [NullAllowed, Export("userAttributes", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> UserAttributes { get; }

        // @required -(BOOL)storeCredentials:(LRSession * _Nullable)session userAttributes:(NSDictionary<NSString *,id> * _Nullable)userAttributes __attribute__((warn_unused_result));
        [Abstract]
        [Export("storeCredentials:userAttributes:")]
        bool StoreCredentialsUserAttributes([NullAllowed] LRSession session, [NullAllowed] NSDictionary<NSString, NSObject> userAttributes);

        // @required -(BOOL)removeStoredCredentials __attribute__((warn_unused_result));
        [Abstract]
        [Export("removeStoredCredentials")]
        bool RemoveStoredCredentials();

        // @required -(BOOL)loadStoredCredentials __attribute__((warn_unused_result));
        [Abstract]
        [Export("loadStoredCredentials")]
        bool LoadStoredCredentials();
    }
}
