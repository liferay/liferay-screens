using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol SignUpScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
	[Protocol, Model]
	interface SignUpScreenletDelegate
	{
	    // @optional -(void)screenlet:(SignUpScreenlet * _Nonnull)screenlet onSignUpResponseUserAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes;
	    [Export ("screenlet:onSignUpResponseUserAttributes:")]
	    void OnSignUpResponseUserAttributes (SignUpScreenlet screenlet, NSDictionary<NSString, NSObject> attributes);

	    // @optional -(void)screenlet:(SignUpScreenlet * _Nonnull)screenlet onSignUpError:(NSError * _Nonnull)error;
	    [Export ("screenlet:onSignUpError:")]
	    void OnSignUpError (SignUpScreenlet screenlet, NSError error);
	}
}
