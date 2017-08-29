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

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)onSetTranslations;
        [Export("onSetTranslations")]
        void OnSetTranslations();

        // -(void)onStartInteraction;
        [Export("onStartInteraction")]
        void OnStartInteraction();

        // -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
        [Export("onFinishInteraction:error:")]
        void OnFinishInteraction([NullAllowed] NSObject result, [NullAllowed] NSError error);

        // -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
