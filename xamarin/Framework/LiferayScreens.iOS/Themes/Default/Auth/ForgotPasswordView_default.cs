using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface ForgotPasswordView_default : BaseScreenletView <ForgotPasswordViewModel, BasicAuthBasedType>
    [BaseType(typeof(BaseScreenletView))]
    interface ForgotPasswordView_default : IForgotPasswordViewModel, IBasicAuthBasedType
    {
        // @property (nonatomic, weak) UITextField * _Nullable userNameField __attribute__((iboutlet));
        [NullAllowed, Export("userNameField", ArgumentSemantic.Weak)]
        UITextField UserNameField { get; set; }

        // @property (nonatomic, weak) UIButton * _Nullable requestPasswordButton __attribute__((iboutlet));
        [NullAllowed, Export("requestPasswordButton", ArgumentSemantic.Weak)]
        UIButton RequestPasswordButton { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable userName;
        [NullAllowed, Export("userName")]
        string UserName { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable basicAuthMethod;
        [NullAllowed, Export("basicAuthMethod")]
        string BasicAuthMethod { get; set; }

        // @property (nonatomic) BOOL saveCredentials;
        [Export("saveCredentials")]
        bool SaveCredentials { get; set; }

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
