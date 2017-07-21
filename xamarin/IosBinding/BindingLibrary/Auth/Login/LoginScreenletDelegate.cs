using ObjCRuntime;
using Foundation;

namespace LiferayScreens
{
    // @protocol LoginScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface LoginScreenletDelegate
    {
        // @optional -(void)screenlet:(BaseScreenlet * _Nonnull)screenlet onLoginResponseUserAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
        [Export("screenlet:onLoginResponseUserAttributes:")]
        void OnLoginResponseUserAttributes(BaseScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);

        // @optional -(void)screenlet:(BaseScreenlet * _Nonnull)screenlet onLoginError:(NSError * _Nonnull)error;
        [Export("screenlet:onLoginError:")]
        void OnLoginError(BaseScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(BaseScreenlet * _Nonnull)screenlet onCredentialsSavedUserAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
        [Export("screenlet:onCredentialsSavedUserAttributes:")]
        void OnCredentialsSavedUserAttributes(BaseScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);

        // @optional -(void)screenlet:(LoginScreenlet * _Nonnull)screenlet onCredentialsLoadedUserAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
        [Export("screenlet:onCredentialsLoadedUserAttributes:")]
        void OnCredentialsLoadedUserAttributes(LoginScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);
    }
}
