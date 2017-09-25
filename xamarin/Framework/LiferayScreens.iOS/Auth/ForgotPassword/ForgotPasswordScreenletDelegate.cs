using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol ForgotPasswordScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface ForgotPasswordScreenletDelegate
    {
        // @optional -(void)screenlet:(ForgotPasswordScreenlet * _Nonnull)screenlet onForgotPasswordSent:(BOOL)passwordSent;
        [Export("screenlet:onForgotPasswordSent:")]
        void OnForgotPasswordSent(ForgotPasswordScreenlet screenlet, bool passwordSent);

        // @optional -(void)screenlet:(ForgotPasswordScreenlet * _Nonnull)screenlet onForgotPasswordError:(NSError * _Nonnull)error;
        [Export("screenlet:onForgotPasswordError:")]
        void OnForgotPasswordError(ForgotPasswordScreenlet screenlet, NSError error);
    }
}
