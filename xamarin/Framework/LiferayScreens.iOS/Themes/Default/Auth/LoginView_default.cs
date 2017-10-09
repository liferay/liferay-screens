using System;
using CoreGraphics;
using Foundation;
using ObjCRuntime;
using UIKit;

namespace LiferayScreens
{
	// @interface LoginView_default : BaseScreenletView <LoginViewModel, BasicAuthBasedType>
	[BaseType(typeof(BaseScreenletView))]
	interface LoginView_default : ILoginViewModel, IBasicAuthBasedType
	{
		// @property (nonatomic, weak) UITextField * _Nullable userNameField __attribute__((iboutlet));
		[NullAllowed, Export("userNameField", ArgumentSemantic.Weak)]
		UITextField UserNameField { get; set; }

		// @property (nonatomic, weak) UITextField * _Nullable passwordField __attribute__((iboutlet));
		[NullAllowed, Export("passwordField", ArgumentSemantic.Weak)]
		UITextField PasswordField { get; set; }

		// @property (nonatomic, weak) UIButton * _Nullable loginButton __attribute__((iboutlet));
		[NullAllowed, Export("loginButton", ArgumentSemantic.Weak)]
		UIButton LoginButton { get; set; }

		// @property (nonatomic, weak) UIButton * _Nullable authorizeButton __attribute__((iboutlet));
		[NullAllowed, Export("authorizeButton", ArgumentSemantic.Weak)]
		UIButton AuthorizeButton { get; set; }

		// @property (copy, nonatomic) NSString * _Nullable basicAuthMethod;
		[NullAllowed, Export("basicAuthMethod")]
		string BasicAuthMethod { get; set; }

		// @property (copy, nonatomic) NSString * _Nullable authType;
		[NullAllowed, Export("authType")]
		string AuthType { get; set; }

		// @property (copy, nonatomic) NSString * _Nullable userName;
		[NullAllowed, Export("userName")]
		string UserName { get; set; }

		// @property (copy, nonatomic) NSString * _Nullable password;
		[NullAllowed, Export("password")]
		string Password { get; set; }

		// -(void)configureAuthType;
		[Export("configureAuthType")]
		void ConfigureAuthType();

		// -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
		[Export("initWithFrame:")]
		[DesignatedInitializer]
		IntPtr Constructor(CGRect frame);
	}
}
