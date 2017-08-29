using Foundation;
using ObjCRuntime;
using CoreGraphics;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface SignUpView_default : BaseScreenletView <SignUpViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface SignUpView_default : ISignUpViewModel
    {
        // @property (nonatomic, weak) UITextField * _Nullable emailAddressField __attribute__((iboutlet));
        [NullAllowed, Export("emailAddressField", ArgumentSemantic.Weak)]
        UITextField EmailAddressField { get; set; }

        // @property (nonatomic, weak) UITextField * _Nullable passwordField __attribute__((iboutlet));
        [NullAllowed, Export("passwordField", ArgumentSemantic.Weak)]
        UITextField PasswordField { get; set; }

        // @property (nonatomic, weak) UITextField * _Nullable firstNameField __attribute__((iboutlet));
        [NullAllowed, Export("firstNameField", ArgumentSemantic.Weak)]
        UITextField FirstNameField { get; set; }

        // @property (nonatomic, weak) UITextField * _Nullable lastNameField __attribute__((iboutlet));
        [NullAllowed, Export("lastNameField", ArgumentSemantic.Weak)]
        UITextField LastNameField { get; set; }

        // @property (nonatomic, weak) UIButton * _Nullable signUpButton __attribute__((iboutlet));
        [NullAllowed, Export("signUpButton", ArgumentSemantic.Weak)]
        UIButton SignUpButton { get; set; }

        // @property (nonatomic, weak) UIScrollView * _Nullable scrollView __attribute__((iboutlet));
        [NullAllowed, Export("scrollView", ArgumentSemantic.Weak)]
        UIScrollView ScrollView { get; set; }

        // -(void)onStartInteraction;
        [Export("onStartInteraction")]
        void OnStartInteraction();

        // -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
        [Export("onFinishInteraction:error:")]
        void OnFinishInteraction([NullAllowed] NSObject result, [NullAllowed] NSError error);

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)onSetTranslations;
        [Export("onSetTranslations")]
        void OnSetTranslations();

        // -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

        // @property (copy, nonatomic) NSString * _Nullable emailAddress;
        [NullAllowed, Export("emailAddress")]
        string EmailAddress { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable password;
        [NullAllowed, Export("password")]
        string Password { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable firstName;
        [NullAllowed, Export("firstName")]
        string FirstName { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable lastName;
        [NullAllowed, Export("lastName")]
        string LastName { get; set; }

        // @property (nonatomic) BOOL editCurrentUser;
        [Export("editCurrentUser")]
        bool EditCurrentUser { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable screenName;
        [NullAllowed, Export("screenName")]
        string ScreenName { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable middleName;
        [NullAllowed, Export("middleName")]
        string MiddleName { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable jobTitle;
        [NullAllowed, Export("jobTitle")]
        string JobTitle { get; set; }

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
