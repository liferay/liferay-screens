using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface SignUpScreenlet : BaseScreenlet <AnonymousBasicAuthType>
    [BaseType(typeof(BaseScreenlet))]
    interface SignUpScreenlet : IAnonymousBasicAuthType
    {
        // @property (copy, nonatomic) NSString * _Nullable anonymousApiUserName;
        [NullAllowed, Export("anonymousApiUserName")]
        string AnonymousApiUserName { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable anonymousApiPassword;
        [NullAllowed, Export("anonymousApiPassword")]
        string AnonymousApiPassword { get; set; }

        // @property (nonatomic) BOOL autoLogin;
        [Export("autoLogin")]
        bool AutoLogin { get; set; }

        // @property (nonatomic) BOOL saveCredentials;
        [Export("saveCredentials")]
        bool SaveCredentials { get; set; }

        // @property (nonatomic) int64_t companyId;
        [Export("companyId")]
        long CompanyId { get; set; }

        [Wrap("WeakAutoLoginDelegate")]
        [NullAllowed]
        LoginScreenletDelegate AutoLoginDelegate { get; set; }

        // @property (nonatomic, weak) id<LoginScreenletDelegate> _Nullable autoLoginDelegate __attribute__((iboutlet));
        [NullAllowed, Export("autoLoginDelegate", ArgumentSemantic.Weak)]
        NSObject WeakAutoLoginDelegate { get; set; }

        [Wrap("WeakSignUpDelegate")]
        [NullAllowed]
        SignUpScreenletDelegate SignUpDelegate { get; }

        // @property (readonly, nonatomic, strong) id<SignUpScreenletDelegate> _Nullable signUpDelegate;
        [NullAllowed, Export("signUpDelegate", ArgumentSemantic.Strong)]
        NSObject WeakSignUpDelegate { get; }

        // @property (readonly, nonatomic, strong) id<SignUpViewModel> _Nonnull viewModel;
        [Export("viewModel", ArgumentSemantic.Strong)]
        ISignUpViewModel ViewModel { get; }

        // -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
        [Export("createInteractorWithName:sender:")]
        [return: NullAllowed]
        Interactor CreateInteractorWithName(string name, [NullAllowed] NSObject sender);

        // -(BOOL)loadCurrentUser __attribute__((warn_unused_result));
        [Export("loadCurrentUser")]
        bool LoadCurrentUser();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
