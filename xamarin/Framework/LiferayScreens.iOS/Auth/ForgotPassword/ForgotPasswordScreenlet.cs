using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface ForgotPasswordScreenlet : BaseScreenlet <AnonymousBasicAuthType, BasicAuthBasedType>
    [BaseType(typeof(BaseScreenlet))]
    interface ForgotPasswordScreenlet : IAnonymousBasicAuthType, IBasicAuthBasedType
    {
        // @property (copy, nonatomic) NSString * _Nullable anonymousApiUserName;
        [NullAllowed, Export("anonymousApiUserName")]
        string AnonymousApiUserName { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable anonymousApiPassword;
        [NullAllowed, Export("anonymousApiPassword")]
        string AnonymousApiPassword { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable basicAuthMethod;
        [NullAllowed, Export("basicAuthMethod")]
        string BasicAuthMethod { get; set; }

        [Wrap("WeakForgotPasswordDelegate")]
        [NullAllowed]
        ForgotPasswordScreenletDelegate ForgotPasswordDelegate { get; }

        // @property (readonly, nonatomic, strong) id<ForgotPasswordScreenletDelegate> _Nullable forgotPasswordDelegate;
        [NullAllowed, Export("forgotPasswordDelegate", ArgumentSemantic.Strong)]
        NSObject WeakForgotPasswordDelegate { get; }

        // @property (readonly, nonatomic, strong) id<ForgotPasswordViewModel> _Nonnull viewModel;
        [Export("viewModel", ArgumentSemantic.Strong)]
        IForgotPasswordViewModel ViewModel { get; }

        // @property (nonatomic) BOOL saveCredentials;
        [Export("saveCredentials")]
        bool SaveCredentials { get; set; }

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
