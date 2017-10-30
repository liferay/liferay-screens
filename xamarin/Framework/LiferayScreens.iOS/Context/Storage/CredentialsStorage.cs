using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    interface ICredentialsStore {}

    // @interface CredentialsStorage : NSObject
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface CredentialsStorage
    {
        // @property (readonly, nonatomic, strong) id<CredentialsStore> _Nonnull credentialsStore;
        [Export("credentialsStore", ArgumentSemantic.Strong)]
        ICredentialsStore CredentialsStore { get; }

        // -(instancetype _Nonnull)initWithStore:(id<CredentialsStore> _Nonnull)store __attribute__((objc_designated_initializer));
        [Export("initWithStore:")]
        [DesignatedInitializer]
        IntPtr Constructor(ICredentialsStore store);

        // +(CredentialsStorage * _Nullable)createFromStoredAuthType __attribute__((warn_unused_result));
        [Static]
        [NullAllowed, Export("createFromStoredAuthType")]
        CredentialsStorage CreateFromStoredAuthType();

        // -(BOOL)storeWithSession:(LRSession * _Nullable)session userAttributes:(NSDictionary<NSString *,id> * _Nonnull)userAttributes __attribute__((warn_unused_result));
        [Export("storeWithSession:userAttributes:")]
        bool StoreWithSession([NullAllowed] LRSession session, NSDictionary<NSString, NSObject> userAttributes);

        // -(BOOL)remove __attribute__((warn_unused_result));
        [Export("remove")]
        bool Remove();
    }
}
