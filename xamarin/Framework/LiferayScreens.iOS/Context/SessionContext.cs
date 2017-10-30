using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface SessionContext : NSObject
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface SessionContext
    {
        // @property (nonatomic, strong, class) SessionContext * _Nullable currentContext;
        [Static]
        [NullAllowed, Export("currentContext", ArgumentSemantic.Strong)]
        SessionContext CurrentContext { get; set; }

        // @property (readonly, nonatomic, strong) LRSession * _Nonnull session;
        [Export("session", ArgumentSemantic.Strong)]
        LRSession Session { get; }

        // @property (readonly, nonatomic, strong) User * _Nonnull user;
        [Export("user", ArgumentSemantic.Strong)]
        User User { get; }

        // @property (readonly, nonatomic, strong) CacheManager * _Nonnull cacheManager;
        //[Export("cacheManager", ArgumentSemantic.Strong)]
        //CacheManager CacheManager { get; }

        // @property (nonatomic, strong) CredentialsStorage * _Nonnull credentialsStorage;
        [Export("credentialsStorage", ArgumentSemantic.Strong)]
        CredentialsStorage CredentialsStorage { get; set; }

        // @property (readonly, nonatomic) int64_t userId;
        [Export("userId")]
        long UserId { get; }

        // @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull userAttributes;
        [Export("userAttributes", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> UserAttributes { get; }

        // -(instancetype _Nonnull)initWithSession:(LRSession * _Nonnull)session attributes:(NSDictionary<NSString *,id> * _Nonnull)attributes store:(id<CredentialsStore> _Nonnull)store __attribute__((objc_designated_initializer));
        [Export("initWithSession:attributes:store:")]
        [DesignatedInitializer]
        IntPtr Constructor(LRSession session, NSDictionary<NSString, NSObject> attributes, ICredentialsStore store);

        // @property (readonly, nonatomic, class) BOOL isLoggedIn;
        [Static]
        [Export("isLoggedIn")]
        bool IsLoggedIn { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nullable basicAuthUsername;
        [NullAllowed, Export("basicAuthUsername")]
        string BasicAuthUsername { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nullable basicAuthPassword;
        [NullAllowed, Export("basicAuthPassword")]
        string BasicAuthPassword { get; }

        // +(LRSession * _Nonnull)createEphemeralBasicSession:(NSString * _Nonnull)userName :(NSString * _Nonnull)password __attribute__((warn_unused_result));
        [Static]
        [Export("createEphemeralBasicSession::")]
        LRSession CreateEphemeralBasicSession(string userName, string password);

        // +(LRSession * _Nonnull)loginWithBasicWithUsername:(NSString * _Nonnull)username password:(NSString * _Nonnull)password userAttributes:(NSDictionary<NSString *,id> * _Nonnull)userAttributes;
        [Static]
        [Export("loginWithBasicWithUsername:password:userAttributes:")]
        LRSession LoginWithBasicWithUsername(string username, string password, NSDictionary<NSString, NSObject> userAttributes);

        // +(LRSession * _Nonnull)loginWithOAuthWithAuthentication:(LROAuth * _Nonnull)authentication userAttributes:(NSDictionary<NSString *,id> * _Nonnull)userAttributes;
        [Static]
        [Export("loginWithOAuthWithAuthentication:userAttributes:")]
        LRSession LoginWithOAuthWithAuthentication(LROAuth authentication, NSDictionary<NSString, NSObject> userAttributes);

        // +(LRSession * _Nonnull)loginWithCookieWithAuthentication:(LRCookieAuthentication * _Nonnull)authentication userAttributes:(NSDictionary<NSString *,id> * _Nonnull)userAttributes;
        //[Static]
        //[Export("loginWithCookieWithAuthentication:userAttributes:")]
        //LRSession LoginWithCookieWithAuthentication(LRCookieAuthentication authentication, NSDictionary<NSString, NSObject> userAttributes);

        // +(void)reloadCookieAuthWithSession:(LRSession * _Nullable)session callback:(LRCookieBlockCallback * _Nonnull)callback;
        //[Static]
        //[Export("reloadCookieAuthWithSession:callback:")]
        //void ReloadCookieAuthWithSession([NullAllowed] LRSession session, LRCookieBlockCallback callback);

        // -(LRSession * _Nonnull)createRequestSession __attribute__((warn_unused_result));
        [Export("createRequestSession")]
        LRSession CreateRequestSession();

        // -(BOOL)relogin:(void (^ _Nullable)(NSDictionary<NSString *,id> * _Nullable))completed __attribute__((warn_unused_result));
        [Export("relogin:")]
        bool Relogin([NullAllowed] Action<NSDictionary<NSString, NSObject>> completed);

        // -(BOOL)reloginBasic:(void (^ _Nullable)(NSDictionary<NSString *,id> * _Nullable))completed __attribute__((warn_unused_result));
        [Export("reloginBasic:")]
        bool ReloginBasic([NullAllowed] Action<NSDictionary<NSString, NSObject>> completed);

        // -(BOOL)reloginOAuth:(void (^ _Nullable)(NSDictionary<NSString *,id> * _Nullable))completed __attribute__((warn_unused_result));
        [Export("reloginOAuth:")]
        bool ReloginOAuth([NullAllowed] Action<NSDictionary<NSString, NSObject>> completed);

        // -(BOOL)refreshUserAttributes:(void (^ _Nullable)(NSDictionary<NSString *,id> * _Nullable))completed __attribute__((warn_unused_result));
        [Export("refreshUserAttributes:")]
        bool RefreshUserAttributes([NullAllowed] Action<NSDictionary<NSString, NSObject>> completed);

        // +(void)logout;
        [Static]
        [Export("logout")]
        void Logout();

        // -(BOOL)storeCredentials;
        [Export("storeCredentials")]
        bool StoreCredentials();

        // -(BOOL)removeStoredCredentials;
        [Export("removeStoredCredentials")]
        bool RemoveStoredCredentials();

        // +(BOOL)loadStoredCredentials;
        [Static]
        [Export("loadStoredCredentials")]
        bool LoadStoredCredentials();

        // +(BOOL)loadStoredCredentials:(CredentialsStorage * _Nonnull)storage __attribute__((warn_unused_result));
        [Static]
        [Export("loadStoredCredentials:")]
        bool LoadStoredCredentials(CredentialsStorage storage);

        // +(LRSession * _Nullable)createSessionFromCurrentSession __attribute__((warn_unused_result));
        [Static]
        [NullAllowed, Export("createSessionFromCurrentSession")]
        LRSession CreateSessionFromCurrentSession();
    }
}
